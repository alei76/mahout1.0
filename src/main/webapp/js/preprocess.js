/**
 * 预处理部分js
 * 
 */
$(function(){
	// =====upload 数据上传，如果是初始化会把arg3赋值为 “initial” ,表明是要进行初始化操作，否则是上传
	$('#upload_submit').bind('click', function(){
		var input=$('#upload_input').val();
		var output=$('#upload_output').val();
		// 弹出进度框
		popupProgressbar('数据上传','数据上传中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_upload.action',{arg1:input,arg2:output});
	});
	
	
	$('#upload_recommenders_submit').bind('click', function(){
		var select_value=$('#recommenders_select').combobox("getValue");;
		var algorithm='recommenders';
		// 弹出进度框
		popupProgressbar('数据上传','数据上传中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_upload.action',{arg1:select_value,arg2:algorithm,arg3:"initial"});
	});
	
	$('#upload_classification_submit').bind('click', function(){
		var select_value=$('#classification_select').combobox("getValue");;
		var algorithm='classification';
		// 弹出进度框
		popupProgressbar('数据上传','数据上传中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_upload.action',{arg1:select_value,arg2:algorithm,arg3:"initial"});
	});
	
	$('#upload_clustering_submit').bind('click', function(){
		var select_value=$('#clustering_select').combobox("getValue");;
		var algorithm='clustering';
		// 弹出进度框
		popupProgressbar('数据上传','数据上传中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_upload.action',{arg1:select_value,arg2:algorithm,arg3:"initial"});
	});
	//=======upload
	
	
	
	// ============ readtxt
	$('#readtxt_submit').bind('click', function(){
		var select_value=$('#readtxt_select').combobox("getValue");;
		var input=$('#readtxt_input').val();
		// 弹出进度框
		popupProgressbar('提示','数据读取中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_readtxt.action',{arg2:select_value,arg1:input});
	});
	// ============ readtxt
	
});
