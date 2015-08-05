<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<body>
	<div style="padding-left: 30px;font-size: 20px;padding-top:10px;">本地文件上传到HDFS或Demo文件上传</div>  
	<br>
	
	<div style="padding-left: 30px;font-size: 20px;padding-top:10px;">  
	 
		<table>
		<!--  统一命名：
			上传本地文件：WEB-INF/classes/data/<algorithm_type>/<algorithm>.txt
			上传HDFS文件：/user/root/<algorithm_type>/<algorithm>/input.txt
		 -->
			<tr>
				<td><label for="name">本地路径:</label>
				</td>
				<td><input class="easyui-validatebox" type="text" value="d:/splitDataset.txt"
					id="upload_input" data-options="required:true" style="width:300px" />
				</td>
			</tr>
			<tr>
				<td><label for="name">HDFS路径:</label>
				</td>
				<td><input class="easyui-validatebox" type="text" 
				value="/user/root/recommenders/splitDataset/input.txt"
					id="upload_output" data-options="required:true" style="width:300px" />
				</td>
				<td><a id="upload_submit" class="easyui-linkbutton"
					data-options="iconCls:'icon-door_in'">上传</a></td>
			</tr>
			<tr><td></td></tr>
			
			<tr>
				<td>推荐MR数据初始化</td>
				<td><select id="recommenders_select" class="easyui-combobox" name="dept"
					style="width:200px;">
						<option value="splitDataset">splitDataset</option>
						<option value="evaluateFactorization">evaluateFactorization</option> 
						<option value="itemsimilarity">itemsimilarity</option>

				</select></td>
				<td><a id="upload_recommenders_submit" class="easyui-linkbutton"
					data-options="iconCls:'icon-door_in'">数据初始化</a>
				</td>
			</tr>
			
			<tr>
				<td>分类MR数据初始化</td>
				<td><select id="classification_select" class="easyui-combobox" name="dept"
					style="width:200px;">
						<option value="trainnb">trainnb</option>
						<option value="testnb">testnb</option> 
						<option value="buildforest">buildforest</option>

				</select></td>
				<td><a id="upload_classification_submit" class="easyui-linkbutton"
					data-options="iconCls:'icon-door_in'">数据初始化</a>
				</td>
			</tr>
			
			<tr>
				<td>聚类MR数据初始化</td>
				<td><select id="clustering_select" class="easyui-combobox" name="dept"
					style="width:200px;">
						<option value="kmeans">kmeans</option>
						<option value="fkmeans">fkmeans</option> 
						<option value="cvb">cvb</option>

				</select></td>
				<td><a id="upload_clustering_submit" class="easyui-linkbutton"
					data-options="iconCls:'icon-door_in'">数据初始化</a>
				</td>
			</tr>
			
			<tr>
				<td>Utils数据初始化</td>
				<td><select id="utils_select" class="easyui-combobox" name="dept"
					style="width:200px;">
						<option value="describe">describe</option>
						<option value="fkmeans">fkmeans</option> 
						<option value="cvb">cvb</option>

				</select></td>
				<td><a id="upload_utils_submit" class="easyui-linkbutton"
					data-options="iconCls:'icon-door_in'">数据初始化</a>
				</td>
			</tr>
			
		</table>
	<div id="upload_return" style="padding-left: 30px;font-size: 20px;padding-top:10px;"></div>
	</div> 
	<script type="text/javascript" src="js/preprocess.js"></script>  

</body>

