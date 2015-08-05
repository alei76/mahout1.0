/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.als.DatasetSplitter;
import org.apache.mahout.classifier.df.tools.Describe;

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
		
		testDescribe();
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
	
	

}
