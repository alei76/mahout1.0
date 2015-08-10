/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.fs.Path;
import org.apache.mahout.classifier.df.tools.Describe;
import org.apache.mahout.utils.SequenceFileDumper;
import org.apache.mahout.utils.SplitInput;
import org.apache.mahout.utils.clustering.ClusterDumper;
import org.apache.mahout.utils.vectors.RowIdJob;
import org.apache.mahout.utils.vectors.VectorDumper;

import com.fz.util.TestHUtils;

/**
 * utils 
 * @author fansy
 * @date 2015-7-31
 */
public class MahoutUtilsDriverTest {
  
	/**
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		TestHUtils.set();
		
//		testDescribe();
		
//		testVectorDumper();
//		testClusterDumper();
//		testSeqDumper();
//		testlucene_vector();
		testarff_vector();
//		testrowid();
//		testsplit();
	}
	
	
	/**
	 * utils/describe 
	 * 路径要写全
	 * @throws Exception
	 */
	public static void testDescribe() throws Exception{
		String info ="hdfs://node101:8020/user/root/utils/descrite/input.info";
		String[] arg= {
				"-p","hdfs://node101:8020/user/root/utils/describe/input.txt",  
	            "-f",info,
	            "-d","I","9","N","L"
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path(info), true);
		Describe.main(arg);
	}
	
	/**
	 * utils/vectordump 
	 * 
	 * @throws Exception
	 */
	public static void testVectorDumper() throws Exception{
		// TODO vectodump
		String[] arg= {
				
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
		VectorDumper.main(arg);
		
	}
	/**
	 * clusterdumper
	 * @throws Exception
	 */
	public static void testClusterDumper() throws Exception{
		// TODO clusterdump 等聚类完成后，测试
		String[] arg= {
			"-i","hdfs://node101:8020/user/root/clustering/kmeans/output/clusters-3-final",
			"-o","d:/clusters.dat",
			"-of","TEXT",
			"-p","hdfs://node101:8020/user/root/clustering/kmeans/output/clusteredPoints/part-m-00000",
			"-sp","2",
//			"-e",
			"-dm","org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure",
			"--tempDir","temp",
//				"--help"
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
//		TestHUtils.getFs().delete(new Path("tmp/representative"), true);
		
		TestHUtils.getFs().delete(new Path("/use/root/utils/clusterdumper/output"),true);
		ClusterDumper.main(arg);
		
	}
	/**
	 * sequencefiledumper
	 * @throws Exception
	 */
	public static void testSeqDumper() throws Exception{
//		  seqdump 等有序列化文件产生后，测试
		String[] arg= {
//				"-i","hdfs://node101:8020/user/root/clustering/kmeans/output/clusteredPoints/part-m-00000",
//				"-o","d:/clusters.dat",
//				"-n","10",
				"--help"
				
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
		SequenceFileDumper.main(arg);
		
	}
	
	public static void testlucene_vector() throws Exception{
		// TODO lucene_vector 待定，测试
		String[] arg= {
				
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
		org.apache.mahout.utils.vectors.lucene.Driver.main(arg);
		
	}
	/**
	 *  arff_vector
	 *  全路径
	 * @throws Exception
	 */
	public static void testarff_vector() throws Exception{
		// TODO arff_vector 测试失败，input需要使用本地文件，待定
		
		String[] arg= {
//				"-d","hdfs://node101:8020/user/root/utils/arff_vector/input.arff",
				"-d","D:/workspase/book/mahout1.0/src/main/resources/data/utils/arff_vector.arff",
//				"-o","D:/workspase/book/mahout1.0/src/main/resources/data/utils/arff_vector.seq",
				"-o","hdfs://node101:8020/user/root/utils/arff_vector/output/arff_vector.seq",
//				"-t","hdfs://node101:8020/user/root/utils/arff_vector/dictonary.txt",
				"-t","D:/workspase/book/mahout1.0/src/main/resources/data/utils/arff_vector.dictonary",
				"-l","|"
//				"--help"
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
		org.apache.mahout.utils.vectors.arff.Driver.main(arg);
		
	}
	
	public static void testrowid() throws Exception{
		// TODO rowid 待定，测试
		String[] arg= {
				
		};
//				TestHUtils.getFs().delete(new Path("temp"), true);
		RowIdJob.main(arg);
		
	}	
	
	public static void testsplit() throws Exception{
		// TODO split 测试
		// testSplitSize, testSplitPct, testRandomSelectionSize, testRandomSelectionPct
		String[] arg= {
				"-i","hdfs://node101:8020/user/root/utils/split/input.txt",
				"-tr","hdfs://node101:8020/user/root/utils/split/train_output",
				"-te","hdfs://node101:8020/user/root/utils/split/test_output",
//				"-ss","1",
//				"-sp","20",
				"-c","UTF-8",
				"-ow",
				"-mro","hdfs://node101:8020/user/root/utils/split/mr_output",
//				"-rs","1",
				"-rp","20"
		};
//				TestHUtils.getFs().delete(new Path("temp"), true);
		SplitInput.main(arg);
		
	}
	
}
