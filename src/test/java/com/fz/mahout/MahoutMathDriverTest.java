/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.math.hadoop.similarity.VectorDistanceSimilarityJob;

import com.fz.util.TestHUtils;

/**
 * math 
 * @author fansy
 * @date 2015-7-31
 */
public class MahoutMathDriverTest {
  
	/**
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		TestHUtils.set();
		
		testVectorDistanceSimilarityJob();
	}
	
	
	/**
	 * math/ vecdist
	 * @throws Exception
	 */
	public static void testVectorDistanceSimilarityJob() throws Exception{
		String[] arg= {
//				"-i","/user/root/prepare/inputdriver/output/part-m-00000",
//				"-o","/user/root/math/vecdist/output",
//				"-dm","org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure",
//				"-s","/user/root/prepare/inputdriver/output/part-m-00000",
//				"-mx",String.valueOf(Double.MAX_VALUE),
//				"-ot","pw",
//				"-ow"
				"--help"
		};
		ToolRunner.run(TestHUtils.getConf(), new VectorDistanceSimilarityJob(),arg);
	}
	
	

}
