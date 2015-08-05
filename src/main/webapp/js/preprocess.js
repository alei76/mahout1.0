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
		callByAJax('cloud/cloud_submitJobNotMR.action',{algorithm:'Upload',
			arg1:input,arg2:output});
	});
	
	$('#upload_recommenders_submit').bind('click', function(){
		var select_value=$('#recommenders_select').combobox("getValue");;
		var algorithm_type='recommenders';
		// 弹出进度框
		popupProgressbar('数据上传','数据上传中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_submitJobNotMR.action',{algorithm:'Upload',
			arg1:select_value,arg2:algorithm_type,arg3:"initial"});
	});
	
	$('#upload_classification_submit').bind('click', function(){
		var select_value=$('#classification_select').combobox("getValue");;
		var algorithm_type='classification';
		// 弹出进度框
		popupProgressbar('数据上传','数据上传中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_submitJobNotMR.action',{algorithm:'Upload',
			arg1:select_value,arg2:algorithm_type,arg3:"initial"});
	});
	
	$('#upload_clustering_submit').bind('click', function(){
		var select_value=$('#clustering_select').combobox("getValue");;
		var algorithm_type='clustering';
		// 弹出进度框
		popupProgressbar('数据上传','数据上传中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_submitJobNotMR.action',{algorithm:'Upload',
			arg1:select_value,arg2:algorithm_type,arg3:"initial"});
	});
	
	$('#upload_utils_submit').bind('click', function(){
		var select_value=$('#utils_select').combobox("getValue");;
		var algorithm_type='utils';
		// 弹出进度框
		popupProgressbar('数据上传','数据上传中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_submitJobNotMR.action',{algorithm:'Upload',
			arg1:select_value,arg2:algorithm_type,arg3:"initial"});
	});
	
	//=======upload ===================
	
	
	
	// ============ readtxt
	$('#readtxt_submit').bind('click', function(){
		var select_value=$('#readtxt_select').combobox("getValue");;
		var input=$('#readtxt_input').val();
		// 弹出进度框
		popupProgressbar('提示','数据读取中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_submitJobNotMR.action',{algorithm:'ReadTxt',arg1:input,arg2:select_value});
	});
	// ============ readtxt =================
	
	
	// ============ describe
	$('#describe_submit').bind('click', function(){
		var select_value=$('#describe_select').combobox("getValue");;
		var input=$('#describe_input').val();
		var output=$('#describe_output').val();
		var description=$('#describe_description').val();
		// 弹出进度框
		popupProgressbar('提示','数据读取中...',1000);
		// ajax 异步提交任务
		callByAJax('cloud/cloud_submitJobNotMR.action',{algorithm:'DescribeTest'
			,arg1:input,arg2:output,arg3:description,arg4:select_value});
	});
	// ============ describe ================
	
});
