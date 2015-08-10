/**
 * 
 */
package org.apache.mahout.clustering.evaluation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.clustering.AbstractCluster;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.iterator.ClusterWritable;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.common.ClassUtils;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.commandline.DefaultOptionCreator;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.iterator.sequencefile.PathFilters;
import org.apache.mahout.common.iterator.sequencefile.PathType;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileDirIterable;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileValueIterable;
import org.apache.mahout.math.VectorWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fz.util.HUtils;

/**
 * @author fansy
 * @date 2015年8月10日
 */
public final class RepresentativePointsDriver extends AbstractJob {
	  
	  public static final String STATE_IN_KEY = "org.apache.mahout.clustering.stateIn";
	  
	  public static final String DISTANCE_MEASURE_KEY = "org.apache.mahout.clustering.measure";
	  
	  private static final Logger log = LoggerFactory.getLogger(RepresentativePointsDriver.class);
	  
	  private RepresentativePointsDriver() {}
	  
	  public static void main(String[] args) throws Exception {
	    ToolRunner.run(HUtils.getConf(), new RepresentativePointsDriver(), args);
	  }
	  
	  @Override
	  public int run(String[] args) throws ClassNotFoundException, IOException, InterruptedException {
	    addInputOption();
	    addOutputOption();
	    addOption("clusteredPoints", "cp", "The path to the clustered points", true);
	    addOption(DefaultOptionCreator.distanceMeasureOption().create());
	    addOption(DefaultOptionCreator.maxIterationsOption().create());
	    addOption(DefaultOptionCreator.methodOption().create());
	    if (parseArguments(args) == null) {
	      return -1;
	    }
	    
	    Path input = getInputPath();
	    Path output = getOutputPath();
	    String distanceMeasureClass = getOption(DefaultOptionCreator.DISTANCE_MEASURE_OPTION);
	    int maxIterations = Integer.parseInt(getOption(DefaultOptionCreator.MAX_ITERATIONS_OPTION));
	    boolean runSequential = getOption(DefaultOptionCreator.METHOD_OPTION).equalsIgnoreCase(
	        DefaultOptionCreator.SEQUENTIAL_METHOD);
	    DistanceMeasure measure = ClassUtils.instantiateAs(distanceMeasureClass, DistanceMeasure.class);
	    Path clusteredPoints = new Path(getOption("clusteredPoints"));
	    run(getConf(), input, clusteredPoints, output, measure, maxIterations, runSequential);
	    return 0;
	  }
	  
	  /**
	   * Utility to print out representative points
	   * 
	   * @param output
	   *          the Path to the directory containing representativePoints-i folders
	   * @param numIterations
	   *          the int number of iterations to print
	   */
	  public static void printRepresentativePoints(Path output, int numIterations) {
	    for (int i = 0; i <= numIterations; i++) {
	      Path out = new Path(output, "representativePoints-" + i);
	      System.out.println("Representative Points for iteration " + i);
	      Configuration conf = HUtils.getConf();
	      for (Pair<IntWritable,VectorWritable> record : new SequenceFileDirIterable<IntWritable,VectorWritable>(out,
	          PathType.LIST, PathFilters.logsCRCFilter(), null, true, conf)) {
	        System.out.println("\tC-" + record.getFirst().get() + ": "
	            + AbstractCluster.formatVector(record.getSecond().get(), null));
	      }
	    }
	  }
	  
	  public static void run(Configuration conf, Path clustersIn, Path clusteredPointsIn, Path output,
	      DistanceMeasure measure, int numIterations, boolean runSequential) throws IOException, InterruptedException,
	      ClassNotFoundException {
	    Path stateIn = new Path(output, "representativePoints-0");
	    writeInitialState(stateIn, clustersIn);
	    
	    for (int iteration = 0; iteration < numIterations; iteration++) {
	      log.info("Representative Points Iteration {}", iteration);
	      // point the output to a new directory per iteration
	      Path stateOut = new Path(output, "representativePoints-" + (iteration + 1));
	      runIteration(conf, clusteredPointsIn, stateIn, stateOut, measure, runSequential);
	      // now point the input to the old output directory
	      stateIn = stateOut;
	    }
	    
	    conf.set(STATE_IN_KEY, stateIn.toString());
	    conf.set(DISTANCE_MEASURE_KEY, measure.getClass().getName());
	  }
	  
	  private static void writeInitialState(Path output, Path clustersIn) throws IOException {
	    Configuration conf = HUtils.getConf();
	    FileSystem fs = FileSystem.get(output.toUri(), conf);
	    for (FileStatus dir : fs.globStatus(clustersIn)) {
	      Path inPath = dir.getPath();
	      for (FileStatus part : fs.listStatus(inPath, PathFilters.logsCRCFilter())) {
	        Path inPart = part.getPath();
	        Path path = new Path(output, inPart.getName());
	        try (SequenceFile.Writer writer =
	                 new SequenceFile.Writer(fs, conf, path, IntWritable.class, VectorWritable.class)){
	          for (ClusterWritable clusterWritable : new SequenceFileValueIterable<ClusterWritable>(inPart, true, conf)) {
	            Cluster cluster = clusterWritable.getValue();
	            if (log.isDebugEnabled()) {
	              log.debug("C-{}: {}", cluster.getId(), AbstractCluster.formatVector(cluster.getCenter(), null));
	            }
	            writer.append(new IntWritable(cluster.getId()), new VectorWritable(cluster.getCenter()));
	          }
	        }
	      }
	    }
	  }
	  
	  private static void runIteration(Configuration conf, Path clusteredPointsIn, Path stateIn, Path stateOut,
	      DistanceMeasure measure, boolean runSequential) throws IOException, InterruptedException, ClassNotFoundException {
	    if (runSequential) {
	      runIterationSeq(conf, clusteredPointsIn, stateIn, stateOut, measure);
	    } else {
	      runIterationMR(conf, clusteredPointsIn, stateIn, stateOut, measure);
	    }
	  }
	  
	  /**
	   * Run the job using supplied arguments as a sequential process
	   * 
	   * @param conf
	   *          the Configuration to use
	   * @param clusteredPointsIn
	   *          the directory pathname for input points
	   * @param stateIn
	   *          the directory pathname for input state
	   * @param stateOut
	   *          the directory pathname for output state
	   * @param measure
	   *          the DistanceMeasure to use
	   */
	  private static void runIterationSeq(Configuration conf, Path clusteredPointsIn, Path stateIn, Path stateOut,
	      DistanceMeasure measure) throws IOException {
	    
	    Map<Integer,List<VectorWritable>> repPoints = RepresentativePointsMapper.getRepresentativePoints(conf, stateIn);
	    Map<Integer,WeightedVectorWritable> mostDistantPoints = new HashMap<>();
	    FileSystem fs = FileSystem.get(clusteredPointsIn.toUri(), conf);
	    for (Pair<IntWritable,WeightedVectorWritable> record
	        : new SequenceFileDirIterable<IntWritable,WeightedVectorWritable>(clusteredPointsIn, PathType.LIST,
	            PathFilters.logsCRCFilter(), null, true, conf)) {
	      RepresentativePointsMapper.mapPoint(record.getFirst(), record.getSecond(), measure, repPoints, mostDistantPoints);
	    }
	    int part = 0;
	    try (SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, new Path(stateOut, "part-m-" + part++),
	        IntWritable.class, VectorWritable.class)){
	      for (Entry<Integer,List<VectorWritable>> entry : repPoints.entrySet()) {
	        for (VectorWritable vw : entry.getValue()) {
	          writer.append(new IntWritable(entry.getKey()), vw);
	        }
	      }
	    }
	    try (SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, new Path(stateOut, "part-m-" + part++),
	        IntWritable.class, VectorWritable.class)){
	      for (Map.Entry<Integer,WeightedVectorWritable> entry : mostDistantPoints.entrySet()) {
	        writer.append(new IntWritable(entry.getKey()), new VectorWritable(entry.getValue().getVector()));
	      }
	    }
	  }
	  
	  /**
	   * Run the job using supplied arguments as a Map/Reduce process
	   * 
	   * @param conf
	   *          the Configuration to use
	   * @param input
	   *          the directory pathname for input points
	   * @param stateIn
	   *          the directory pathname for input state
	   * @param stateOut
	   *          the directory pathname for output state
	   * @param measure
	   *          the DistanceMeasure to use
	   */
	  private static void runIterationMR(Configuration conf, Path input, Path stateIn, Path stateOut,
	      DistanceMeasure measure) throws IOException, InterruptedException, ClassNotFoundException {
	    conf.set(STATE_IN_KEY, stateIn.toString());
	    conf.set(DISTANCE_MEASURE_KEY, measure.getClass().getName());
	    Job job = new Job(conf, "Representative Points Driver running over input: " + input);
	    job.setJarByClass(RepresentativePointsDriver.class);
	    job.setOutputKeyClass(IntWritable.class);
	    job.setOutputValueClass(VectorWritable.class);
	    job.setMapOutputKeyClass(IntWritable.class);
	    job.setMapOutputValueClass(WeightedVectorWritable.class);
	    
	    FileInputFormat.setInputPaths(job, input);
	    FileOutputFormat.setOutputPath(job, stateOut);
	    
	    job.setMapperClass(RepresentativePointsMapper.class);
	    job.setReducerClass(RepresentativePointsReducer.class);
	    job.setInputFormatClass(SequenceFileInputFormat.class);
	    job.setOutputFormatClass(SequenceFileOutputFormat.class);
	    
	    boolean succeeded = job.waitForCompletion(true);
	    if (!succeeded) {
	      throw new IllegalStateException("Job failed!");
	    }
	  }
	}
