/**
 * 
 */
package com.fz.mahout;

import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.RowSimilarityJob;
import org.apache.mahout.utils.SplitInput;

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
		
		String[] arg= {
				"-i","input/test.txt",
				"-mro","output",
				"-tr","train",
				"-te","test",
				"-sp","20",
				"-rp","20",
				"-c","UTF-8",
				"-xm","mapreduce"
		};
		
		ToolRunner.run(new SplitInput(), arg);
	}

}
