/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.spectral.kmeans.SpectralKMeansDriver;
import org.apache.mahout.clustering.streaming.mapreduce.StreamingKMeansDriver;

import com.fz.driver.SimpleDriver;
import com.fz.util.TestHUtils;

/**
 * clustering 
 * @author fansy
 * @date 2015-7-31
 */
public class MahoutClusteringDriverTest {
  
	/**
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		TestHUtils.set();
		
		testKMeansDriver();
//		testFuzzyKMeansDriver();
//		testSpectralKMeansDriver();
//		testInputDriver();
//		simpleTest();
//		testStreamingKMeansDriver();
	}
	
	
	/**
	 * kmeans
	 * @throws Exception
	 */
	public static void testKMeansDriver() throws Exception{
		String[] arg= {
				"-i","/user/root/clustering/kmeans/input.seq",
				"-o","/user/root/clustering/kmeans/output",
				"-c","/user/root/clustering/kmeans/clusters",
				"-dm","org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure",
				"-k","6",
				"-cd","0.5",
				"-x","3",
				"--tempDir" ,"temp",
				"-cl"
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path("/user/root/clustering/kmeans/output"), true);
		int ret =ToolRunner.run(TestHUtils.getConf(), new KMeansDriver(),arg);
		System.out.println(ret);
	}
	/**
	 * canopy
	 * @throws Exception
	 */
	public static void testCanopyDriver() throws Exception{
		String[] arg= {
				"-i","/user/root/clustering/kmeans/input.seq",
				"-o","/user/root/clustering/kmeans/output",
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path("/user/root/clustering/kmeans/output"), true);
		int ret =ToolRunner.run(TestHUtils.getConf(), new CanopyDriver(),arg);
		System.out.println(ret);
	}
	
	public static void testFuzzyKMeansDriver() throws Exception{
		String[] arg= {
				"-i","/user/root/clustering/fuzzykmeans/input.seq",
				"-o","/user/root/clustering/fuzzykmeans/output",
				"-c","/user/root/clustering/fuzzykmeans/clusters",
				"-dm","org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure",
				"-k","6",
				"-cd","0.5",
				"-x","3",
				"-m","2.0" ,// 系数归一化因子，必须大于1
				"--tempDir" ,"temp",
				"-cl",
				"-e","true",
				"-t","0"
//				"--help"
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path("/user/root/clustering/fuzzykmeans/output"), true);
		int ret =ToolRunner.run(TestHUtils.getConf(), new FuzzyKMeansDriver(),arg);
		System.out.println(ret);
	}
	
	public static void testSpectralKMeansDriver() throws Exception{
		String[] arg= {
				"-i","/user/root/clustering/spectralkmeans/input.csv",
				"-o","/user/root/clustering/spectralkmeans/output",
				"-d","15",
				"-dm","org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure",
				"-k","6",
				"-cd","0.5",
				"-x","3",
//				"-ssvd",//
				"--tempDir" ,"temp",
				"-t","1",
				"-oh","30000",
				"-p","15",
				"-q","0"
//				"--help"
		};
		TestHUtils.getFs().delete(new Path("temp"), true);
		TestHUtils.getFs().delete(new Path("/user/root/clustering/spectralkmeans/output"), true);
		int ret =ToolRunner.run(TestHUtils.getConf(), new SpectralKMeansDriver(),arg);
		System.out.println(ret);
	}
	
	public static void testStreamingKMeansDriver() throws Exception{
		String[] arg= {
//				"-i","/user/root/clustering/spectralkmeans/input.csv",
//				"-o","/user/root/clustering/spectralkmeans/output",
//				"-d","15",
//				"-dm","org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure",
//				"-k","6",
//				"-cd","0.5",
//				"-x","3",
////				"-ssvd",//
//				"--tempDir" ,"temp",
//				"-t","1",
//				"-oh","30000",
//				"-p","15",
//				"-q","0"
				"--help"
		};
//		TestHUtils.getFs().delete(new Path("temp"), true);
//		TestHUtils.getFs().delete(new Path("/user/root/clustering/spectralkmeans/output"), true);
//		int ret =ToolRunner.run(TestHUtils.getConf(), new StreamingKMeansDriver(),arg);
		StreamingKMeansDriver.main(arg);;
//		System.out.println(ret);
	}
	
	
	
	public static void testInputDriver() throws Exception{
		String[] args=new String[]{
				"hdfs://node101:8020/user/root/prepare/inputdriver/input.txt2",
				"hdfs://node101:8020/user/root/prepare/inputdriver/output",
				"org.apache.mahout.math.RandomAccessSparseVector"
				};
		
//		InputDrver.main(args);
		TestHUtils.getFs().delete(new Path("/user/root/prepare/inputdriver/output"), true);
		ToolRunner.run(TestHUtils.getConf(), new InputDriver(), args);
	}
	
	
	public static void simpleTest() throws Exception{
		String[] args=new String[]{
			"a","b"	,"0"
		};
		TestHUtils.getFs().delete(new Path("b"), true);
		ToolRunner.run(TestHUtils.getConf(), new SimpleDriver(), args);
	}
	
	

}
