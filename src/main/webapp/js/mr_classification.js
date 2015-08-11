$(function(){
	// trainnb---
	$('#trainnb_submit').bind('click', function(){
		
		// 检查是否有“MR监控页面”，如果有，则退出，并提示关闭
		if(exitsMRmonitor()){
			return ;
		}	
		var input=$('#trainnb_input').val();//
		var output=$('#trainnb_output').val();//
		var alphaI=$('#trainnb_alphaI').val();//
		var labelIndex=$('#trainnb_labelIndex').val();
//		var trainComplementary=$('#trainnb_trainComplementary').combobox("getValue");;
		
		var jobnums_='2'; // 一共的MR个数
		// 弹出进度框
		popupProgressbar('分类MR','trainnb任务提交中...',1000);
		// ajax 异步提交任务
		
		callByAJax('cloud/cloud_submitIterMR.action',{algorithm:"TrainNaiveBayesJobRunnable",jobnums:jobnums_,
			arg1:input,arg2:output,arg3:alphaI,arg4:labelIndex
//			,arg5:trainComplementary
			});
		
	});
	
	// ------trainnb
	

	
	
});
