/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;

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
//		testInputDriver();
//		simpleTest();
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
