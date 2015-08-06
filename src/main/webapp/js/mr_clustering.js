$(function(){
	// kmeans---
	$('#kmeans_submit').bind('click', function(){
		
		// 检查是否有“MR监控页面”，如果有，则退出，并提示关闭
		if(exitsMRmonitor()){
			return ;
		}	
		var input=$('#kmeans_input').val();//
		var output=$('#kmeans_output').val();//
		var clusters=$('#kmeans_clusters').val();//
		var k=$('#kmeans_k').val();
		var convergenceDelta=$('#kmeans_convergenceDelta').val();
		var maxIter=$('#kmeans_maxIter').val();
		var clustering=$('#kmeans_clustering').combobox("getValue");
		var distanceMeasure=$('#kmeans_distanceMeasure').combobox("getValue");
		
		// 弹出进度框
		popupProgressbar('聚类MR','kmeans任务提交中...',1000);
		// ajax 异步提交任务
		
		callByAJax('cloud/cloud_submitIterMR.action',{algorithm:"KMenasDriverRunnable",jobnums:"3",
			arg1:input,arg2:output,arg3:clusters,arg4:k,
			arg5:convergenceDelta,arg6:maxIter,arg7:clustering,arg8:distanceMeasure});
		
	});
	
	// ------splitDataset
	
	
});
