/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.classifier.ConfusionMatrixDumper;
import org.apache.mahout.classifier.df.mapreduce.BuildForest;
import org.apache.mahout.classifier.df.mapreduce.TestForest;
import org.apache.mahout.classifier.naivebayes.test.TestNaiveBayesDriver;
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
		
//		testTrainNaiveBayesJob();
		
//		testTestNaiveBayesDriver();
//		testBuildForest();
//		testTestForest();
		testConfusionMatrixDumper();
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
//				"-c" //  TODO  不使用此参数，此参数未调通   
//				"--help"
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
//		TestHUtils.getFs().delete(new Path("recommenders/train_test_output"), true);
		ToolRunner.run(TestHUtils.getConf(), new TrainNaiveBayesJob(),arg);
	}
	
	public static void testConfusionMatrixDumper() throws Exception{
		String[] arg= {
//				"-i","/user/root/process/generate_classify/input.seq",
//				"-o","/user/root/classification/trainnb/output",
//				"-a","1.0",
//				"-li","/user/root/classification/trainnb/labelIndex",
//				"-ow",
//				"--tempDir","temp",
////				"-c" //  TODO  不使用此参数，此参数未调通   
				"--help"
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
//		TestHUtils.getFs().delete(new Path("recommenders/train_test_output"), true);
//		ToolRunner.run(TestHUtils.getConf(), new ConfusionMatrixDumper(),arg);
		ConfusionMatrixDumper.main(arg);
	}
	
	/**
	 * TODO   能分类的数据很少？
	 * @throws Exception
	 */
	public static void testTestNaiveBayesDriver() throws Exception{
		String[] arg= {
				"-i","/user/root/process/generate_classify/input.seq",
				"-o","/user/root/classification/testnb/output",
				"-m","/user/root/classification/trainnb/output/",
				"-l","/user/root/classification/trainnb/labelIndex",
				"-ow",
				"--tempDir","temp"
//				"-c" // 不使用此参数，此参数未调通  
//				"--help"
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
//		TestHUtils.getFs().delete(new Path("recommenders/train_test_output"), true);
		ToolRunner.run(TestHUtils.getConf(), new TestNaiveBayesDriver(),arg);
	}
	/**
	 * 拷贝mahout的lib下面的：
	 * xstream-1.4.4.jar，xmlpull-1.1.3.1.jar，xpp3_min-1.1.4c.jar，commons-lang3-3.1.jar
	 * 到hadoop lib目录
	 * @throws Exception
	 */
	public static void testBuildForest() throws Exception{
		String[] arg= {
				"-d","/user/root/classification/buildforest/input.csv",
				"-ds","/user/root/utils/describe/input.info",
				"-o","/user/root/classification/buildforest/output",
				"-nc",
//				"-sl","3",
//				"-ms","2",
//				"-mp","0.001",
				"-p",
				"-t","10",   
//				"--help"
		};
		String tmpPath = new Path("/user/root/classification/buildforest/output").getName();
		TestHUtils.getFs().delete(new Path(TestHUtils.getFs().getWorkingDirectory(),tmpPath),
				true);
		TestHUtils.getFs().delete(new Path("/user/root/classification/buildforest/output"), true);
		ToolRunner.run(TestHUtils.getConf(), new BuildForest(),arg);
	}
	
	public static void testTestForest() throws Exception{
		// TODO 输入数据没有，暂时不测试
		String[] arg= {
				"-i","/user/root/classification/buildforest/input.csv",
				"-ds","/user/root/utils/describe/input.info",
				"-m","/user/root/classification/buildforest/output",
				"-o","/user/root/classification/testforest/output",
//				"-mr",
				"-a"
////				"-sl","3",
////				"-ms","2",
////				"-mp","0.001",
//				"-p",
//				"-t","10",   
//				"--help"
		};
//		String tmpPath = new Path("/user/root/classification/buildforest/output").getName();
//		TestHUtils.getFs().delete(new Path(TestHUtils.getFs().getWorkingDirectory(),tmpPath),
//				true);
		TestHUtils.getFs().delete(new Path("/user/root/classification/testforest/output"), true);
		ToolRunner.run(TestHUtils.getConf(), new TestForest(),arg);
	}

}
