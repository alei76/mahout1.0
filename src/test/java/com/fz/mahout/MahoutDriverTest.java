/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.als.DatasetSplitter;

import com.fz.util.TestHUtils;

/**
 * @author fansy
 * @date 2015-7-31
 */
public class MahoutDriverTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		TestHUtils.set();
		
		testDatasetSplitter();
	}
	
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

}
