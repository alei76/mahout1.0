/**
 * 
 */
package org.apache.mahout.clustering.spectral;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.mahout.math.VectorWritable;

import com.fz.util.HUtils;

/**
 * @author fansy
 * @date 2015年8月10日
 */
public final class UnitVectorizerJob {

	  private UnitVectorizerJob() {
	  }

	  public static void runJob(Path input, Path output)
	    throws IOException, InterruptedException, ClassNotFoundException {
	    
	    Configuration conf = HUtils.getConf();
	    Job job = Job.getInstance(conf, "UnitVectorizerJob");
	    
	    job.setInputFormatClass(SequenceFileInputFormat.class);
	    job.setOutputKeyClass(IntWritable.class);
	    job.setOutputValueClass(VectorWritable.class);
	    job.setOutputFormatClass(SequenceFileOutputFormat.class);
	    job.setMapperClass(UnitVectorizerMapper.class);
	    job.setNumReduceTasks(0);
	    
	    FileInputFormat.addInputPath(job, input);
	    FileOutputFormat.setOutputPath(job, output);

	    job.setJarByClass(UnitVectorizerJob.class);

	    boolean succeeded = job.waitForCompletion(true);
	    if (!succeeded) {
	      throw new IllegalStateException("Job failed!");
	    }
	  }
	  
	  public static class UnitVectorizerMapper
	    extends Mapper<IntWritable, VectorWritable, IntWritable, VectorWritable> {
	    
	    @Override
	    protected void map(IntWritable row, VectorWritable vector, Context context) 
	      throws IOException, InterruptedException {
	      context.write(row, new VectorWritable(vector.get().normalize(2)));
	    }

	  }
	}

