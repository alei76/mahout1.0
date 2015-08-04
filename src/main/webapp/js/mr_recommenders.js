$(function(){
	// splitDataset---
	$('#splitDataset_submit').bind('click', function(){
		
		// 检查是否有“MR监控页面”，如果有，则退出，并提示关闭
		if(exitsMRmonitor()){
			return ;
		}	
		var input=$('#splitDataset_input').val();// 局部密度阈值
		var output=$('#splitDataset_output').val();// 最小距离阈值
		var trainPercent=$('#splitDataset_trainPercent').val();
		var testPercent=$('#splitDataset_testPercent').val();
		
		// 弹出进度框
		popupProgressbar('推荐MR','splitDataset任务提交中...',1000);
		// ajax 异步提交任务
		
		callByAJax('cloud/cloud_submitJob.action',{algorithm:"DatasetSplitterRunnable",jobnums:"3",
			arg1:input,arg2:output,arg3:trainPercent,arg4:testPercent});
		
	});
	
	// ------splitDataset
	
	
});
