/**
 * 
 */
package com.fz.driver;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.fz.util.HUtils;

/**
 * @author fansy
 * @date 2015年8月6日
 */
public class SimpleDriver extends Configured implements Tool {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		ToolRunner.run(new Configuration(), new SimpleDriver(), args);
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = HUtils.getConf();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();
		if (otherArgs.length != 3) {
			System.err
					.println("Usage: fz.fastcluster.SortJob <in> <out> <num_reducer>");
			System.exit(4);
		}

		Job job = Job.getInstance(conf,
				"sort the input:"
						+ otherArgs[0]);
		job.setJarByClass(SimpleDriver.class);
		job.setMapperClass(Mapper.class);
//		job.setCombinerClass(SortReducer.class);
//		job.setReducerClass(SortReducer.class);
		
		
		job.setNumReduceTasks(0);
		
		job.setOutputKeyClass(Writable.class);
		job.setOutputValueClass(Writable.class);
		
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
//		job.setInputFormatClass(SequenceFileInputFormat.class);
		
		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));

		SequenceFileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
		FileSystem.get(conf).delete(new Path(otherArgs[1]), true);
		return job.waitForCompletion(true) ? 0 : 1;
	}

}
