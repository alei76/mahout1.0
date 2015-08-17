/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.als.DatasetSplitter;
import org.apache.mahout.cf.taste.hadoop.als.FactorizationEvaluator;
import org.apache.mahout.cf.taste.hadoop.als.ParallelALSFactorizationJob;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.VectorSimilarityMeasures;

import com.fz.util.TestHUtils;

/**
 * Recommenders 
 * @author fansy
 * @date 2015-7-31
 */
public class MahoutRecommendersDriverTest {
  
	/**
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		TestHUtils.set();
		
//		
//		getSimilarity();
//		testItemRecommenderJob();
		
		
//		testDatasetSplitter();
//		testParallelALSFactorizationJob();
//		testAlsRecommenderJob();
		testEvaluateFactorization();
	}
	
	
	/**
	 * recommenders/ datasetsplitter
	 * @throws Exception
	 */
	public static void testDatasetSplitter() throws Exception{
		String[] arg= {
				"-i","/user/root/user.txt",
				"-o","recommenders/train_test_output",
				"-t","0.9",
				"-p","0.1"
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path("recommenders/train_test_output"), true);
		ToolRunner.run(TestHUtils.getConf(), new DatasetSplitter(),arg);
	}
	
	
	public static void testItemRecommenderJob() throws Exception{
		String[] arg= {
				"-i","/user/root/recommenders/recommenditembased/input.csv",
				"-o","/user/root/recommenders/recommenditembased/output",
				"-n","9",
//				"--usersFile","?",
//				"--itemsFile","?",
//				"-f","?",
//				"-uif","?",
//				"-b",
				"-mxp","10",// 每个用户在最后的推荐阶段的最大参考数
				"-mp","1",// 在计算相似度时，用户最小的参考数，如果用户的参考数低于此数值，用户将会被忽略
				"-m","100",// 每个项目最大的相似度个数
				"-mpiis","500",//在计算项目相似度时，每个用户或项目最大的参考个数，大于此值将会被采样处理
				"-s","SIMILARITY_COOCCURRENCE",
//				"-tr","?",// 丢弃项目对相似度小于此数值的项目对；
				"-opfsm","/user/root/recommenders/recommenditembased/output_similarityMatrix",
				"--tempDir","temp"
				
//				"--help"
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path("/user/root/recommenders/recommenditembased/output"), true);
		TestHUtils.getFs().delete(new Path("/user/root/recommenders/recommenditembased/output_similarityMatrix"),
				true);
		ToolRunner.run(TestHUtils.getConf(), new org.apache.mahout.cf.taste.hadoop.item.RecommenderJob(),arg);
	}
	
	
	public static void testParallelALSFactorizationJob() throws Exception{
		String[] arg= {
				"-i","/user/root/recommenders/splitDataset/train_test_output/trainingSet",
				"-o","/user/root/recommenders/parallelALS/output",
				"--lambda","0.065",
//				 "--implicitFeedback", "?",
//				 "--alpha","?",
				 "--numFeatures","20",
				 "--numIterations","10",
				 "--numThreadsPerSolver","1",
				 "--tempDir","temp"
//				"--help"
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
//		TestHUtils.getFs().delete(new Path("recommenders/train_test_output"), true);
		ToolRunner.run(TestHUtils.getConf(), new ParallelALSFactorizationJob(),arg);
	}
	
	/**
	 * 参考http://blog.csdn.net/fansy1990/article/details/12259975
	 * @throws Exception
	 */
	public static void testAlsRecommenderJob() throws Exception{
		String[] arg= {
				"-i","/user/root/recommenders/parallelALS/output/userRatings",
				"-o","/user/root/recommenders/recommendfactorized/output",
				"--userFeatures","/user/root/recommenders/parallelALS/output/U",
				"--itemFeatures","/user/root/recommenders/parallelALS/output/M",
				"--numRecommendations","10",
				"--maxRating","5",// 最大的评分值
				"--numThreads","1",
////				"--usesLongIDs","?",
////				"--userIDIndex","?",
//				"--itemIDIndex","?",// 
				"--tempDir","temp"
				
//				"--help"
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path("/user/root/recommenders/recommendfactorized/output"), true);
		ToolRunner.run(TestHUtils.getConf(), new org.apache.mahout.cf.taste.hadoop.als.RecommenderJob(),arg);
	}
	public static void testEvaluateFactorization() throws Exception{
		String[] arg= {
				"-i","/user/root/recommenders/splitDataset/train_test_output/probeSet",//
				"-o","/user/root/recommenders/evaluateFactorization/output",
				"--userFeatures","/user/root/recommenders/parallelALS/output/U",
				"--itemFeatures","/user/root/recommenders/parallelALS/output/M",
//////				"--usesLongIDs","?",
				"--tempDir","temp"
				
//				"--help"
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path("/user/root/recommenders/evaluateFactorization/output"), true);
		ToolRunner.run(TestHUtils.getConf(),new  FactorizationEvaluator(),arg);
	}
	public static void getSimilarity(){
		String t=VectorSimilarityMeasures.list();
		System.out.println(t);
	}
	
	

}
