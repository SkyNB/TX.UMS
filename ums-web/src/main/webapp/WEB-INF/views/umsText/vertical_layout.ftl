<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<!--全局-->
		<link href="${request.contextPath}/resources/index/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
        <script src="${request.contextPath}/resources/home/homeZhu/bootstrap/js/vue.min.js"></script>
        <script src="${request.contextPath}/resources/home/homeZhu/bootstrap/js/jquery-3.1.1.min.js"></script>
        <link href="${request.contextPath}/resources/index/plugins/bootstrap/css/icons/icomoon/styles.css" rel="stylesheet" />
		<link href="${request.contextPath}/resources/index/public/css/loading_xbox.css" rel="stylesheet" />
		<link href="${request.contextPath}/resources/index/public/css/main_layout.css" rel="stylesheet" />
		<!--kendoui-->
		<link href="${request.contextPath}/resources/index/plugins/kendoui/style/kendo.common-bootstrap.min.css" rel="stylesheet" />
		<link href="${request.contextPath}/resources/index/plugins/kendoui/style/kendo.bootstrap.min.css" rel="stylesheet" />
		<script src="${request.contextPath}/resources/index/plugins/kendoui/js/kendo.all.min.js"></script>
		<!--<script src="./plugins/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>-->
		<!---->
		<script src="${request.contextPath}/resources/index/public/js/store.min.js"></script>
		<#--<script src="${request.contextPath}/resources/index/js/add_static_node.js"></script>-->
		<!---->
		<link href="${request.contextPath}/resources/index/plugins/vertical_layout/main_layout.css" rel="stylesheet" />
	</head>

	<body>
		<div id="main">
			<!--底部连接菜单栏开始-->
			<div class="navbar navbar-default" id="navbar-second">
				<ul class="nav navbar-nav no-border visible-xs-block">
					<li>
						<a class="text-center collapsed" data-toggle="collapse" data-target="#navbar-second-toggle"><i class="glyphicon glyphicon-align-justify"></i></a>
					</li>
				</ul>

				<div class="navbar-collapse collapse" id="navbar-second-toggle">
					<ul class="nav navbar-nav">
						<li>
							<img src="${request.contextPath}/resources/index/imgs/lnet_logo.png" style="height: 24px;margin-top: 9px;position: relative;right: 10px;cursor: pointer;">
						</li>

						<li class="dropdown" v-for="(itm,index) in menuBar">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
								{{itm.menuname}}<span class="caret"></span>
							</a>

							<ul class="dropdown-menu width-200">
								<li class="dropdown-submenu " v-for="(itm2,index2) in itm.chindren" v-if="itm2&&itm2.chindren">
									<a class="dropdown-toggle" data-toggle="dropdown">{{itm2.menuname}}</a>
									<dropdown-menu v-bind:chindren="itm2.chindren"></dropdown-menu>
								</li>
								<li v-else>
									<a onclick="addTab(this)" v-bind:data-id="itm2.menuid" v-bind:data-href="itm2.menuurl">{{itm2.menuname}}</a>
								</li>
							</ul>
						</li>
					</ul>

					<ul class="nav navbar-nav navbar-right">
						<li>
							<a href="#">
								<i class="glyphicon glyphicon-user position-left"></i>登录人
								<span class="label label-inline position-right bg-success-400">系统管理员</span>
							</a>
						</li>

						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<i class="icon-cog5"></i>
								<span class="visible-xs-inline-block position-right">Share</span>
								<span class="caret"></span>
							</a>

							<ul class="dropdown-menu dropdown-menu-right">
								<li>
									<a href="#"><i class="icon-vcard"></i>个人信息</a>
								</li>
								<li>
									<a href="#"><i class="icon-cog5"></i>菜单</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="horizontalLayout"><i class="icon-toggle"></i>切换布局</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="#"><i class="icon-exit"></i>退出登录</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<!--底部连接菜单栏结束-->
			<div id="tabstrip">
				<ul class="">
					<li class="k-state-active ">
						<i class="icon-home" style="font-size: 12px;"></i>&nbsp;主页
					</li>
				</ul>
				<div>
                    <iframe src="${request.contextPath}/resources/index/demo.3.html"></iframe>
				</div>
			</div>
		</div>
		<!--加载动画-->
		<div id="main_loading" style="background-color: #f5f5f5;position: fixed;top: 0;left: 0;right: 0;bottom: 0;">
			<div class="loading_xbox center-display">
				<div>加载中</div>
				<div>&nbsp;</div>
				<div class="loading_xbox_xs">
					<div class="pace_activity"></div>
				</div>
			</div>
		</div>
		<script src="${request.contextPath}/resources/index/plugins/bootstrap/js/bootstrap.min.js"></script>
        <script src="${request.contextPath}/resources/index/public/js/to_treeJson.js"></script>
        <script src="${request.contextPath}/resources/index/plugins/vertical_layout/main_layout.js"></script>
		<script>
            store.set('layout',"vertical_layout");
            layout.menuBar =transData(${userData}, "menuid", "parentid", "chindren"); //---加载菜单
		</script>
	</body>

</html>