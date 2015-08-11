/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.classifier.naivebayes.training.TrainNaiveBayesJob;

import com.fz.util.TestHUtils;

/**
 * classification 
 * @author fansy
 * @date 2015-7-31
 */
public class MahoutClassificationDriverTest {
  
	/**
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		TestHUtils.set();
		
		testTrainNaiveBayesJob();
	}
	
	
	/**
	 * classification/ trainnb
	 * @throws Exception
	 */
	public static void testTrainNaiveBayesJob() throws Exception{
		String[] arg= {
				"-i","/user/root/process/generate_classify/input.seq",
				"-o","/user/root/classification/trainnb/output",
				"-a","1.0",
				"-li","/user/root/classification/trainnb/labelIndex",
				"-ow",
				"--tempDir","temp",
//				"-c" // 不使用此参数，此参数未调通  TODO  
//				"--help"
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
//		TestHUtils.getFs().delete(new Path("recommenders/train_test_output"), true);
		ToolRunner.run(TestHUtils.getConf(), new TrainNaiveBayesJob(),arg);
	}
	
	

}
