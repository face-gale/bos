<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>accordion---折叠面板</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body class="easyui-layout">
	<!-- 使用div指定的区域 -->
	<div title="XX管理系统" data-options="region:'north'" style="height: 100px">北部区域</div>
	<div title="系统菜单" data-options="region:'west'" style="width: 200px">
		<div class="easyui-accordion" data-options="fit:'true'">
			<div title="面板一">
				<a class="easyui-linkbutton" onclick="doAdd();">棉花</a>
				<script type="text/javascript">
					function doAdd() {
						$("#tt")
								.tabs(
										"add",
										{
											title : '这个可是动态的',
											content : '<iframe frameborder="0" width="100%" height="100%" src="page_base_staff.action"></iframe>',
											closable : true,
											iconCls : 'icon-search'
										});

					}
				</script>
			</div>
			<div title="面板二">
				<ul id="ztree1" class="ztree"></ul>
				<script type="text/javascript">
					$(function() {
						//创建ztree菜单
						var setting = {};
						//构造json数据
						var zNodes = [ {
							name : '系统数据'
						}, 
						{
							name : '用户管理',
							children : [ 
								{
									name : "用户添加"
								}, 
								{
									name : "用户修改"
								} 
							]
						}, 
						{
							name : "权限管理"
						}

						];
						//创建ztree
						$.fn.zTree.init($("#ztree1"),setting, zNodes);
					});
				</script>
			</div>
			<div title="面板三">
				<ul id=ztree2 class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//当页面加载完后，动态创建ztree菜单
						var setting2 = {
								data : {
									simpleData : {
										enable : true//启用简单json数据描述节点数据 
									}
									
								}
						};//设置ztree相关的属性
						//构建json数据
						var zNodes2 = [
							
							{id:'1',pId:'0',name:'系统管理'},
							{id:'2',pId:'0',name:'用户管理'},
							{id:'21',pId:'2',name:'用户添加'},
							{id:'22',pId:'2',name:'用户修改'},
							{id:'3',pId:'0',name:'权限管理'},
							
						];
						
						//创建ztree
						$.fn.zTree.init($("#ztree2"),setting2,zNodes2);
					});
				</script>
			</div>
			<div title="面板四">
				<ul id = "ztree3" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						//设置一个菜单
						var setting3 = {
								//设置属性
							data : {
								//设置简单json数据来设置节点
								simpleData : {
									enable : true
								}
							},
							callback: {
								onClick: function(event, treeId, treeNode){
									var page = treeNode.page;
									if(page != undefined){
										var e = $("#tt").tabs("exists",treeNode.name);
										if(e){
											$("#tt").tabs("select",treeNode.name);
										}else{
											$("#tt")
											.tabs(
													"add",
													{
														title : treeNode.name,
														content : '<iframe frameborder="0" width="100%" height="100%" src="'+page+'"></iframe>',
														closable : true,
														iconCls : 'icon-search'
													});
										}
										
									}
								}
							}		
								
						};
						//发送ajax请求 获取ztree节点
						var url = "${pageContext.request.contextPath}/json/menu.json";
						$.post(url,{},function(data){
							$.fn.zTree.init($("#ztree3"),setting3,data);
						},"json");
					});
				</script>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="tt" class="easyui-tabs" data-options="fit:'true'">
			<div data-options="closable:true,iconCls:'icon-help'" title="面板一">棉花</div>
			<div title="面板二">玉米</div>
			<div title="面板三">小麦</div>
			<div title="面板四">水稻</div>
		</div>
	</div>
	<div data-options="region:'east'" style="width: 100px">东部区域</div>
	<div data-options="region:'south'" style="height: 50px">南部区域</div>
</body>
</html>