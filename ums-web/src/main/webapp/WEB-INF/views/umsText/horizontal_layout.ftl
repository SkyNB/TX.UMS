<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
        <meta charset="UTF-8">
        <title>首页</title>
        <!--全局-->
        <link href="${request.contextPath}/resources/index/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<!--<script src="http://cdn.bootcss.com/vue/2.1.8/vue.min.js"></script>
        <script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>-->
        <script src="${request.contextPath}/resources/home/homeZhu/bootstrap/js/vue.min.js"></script>
        <script src="${request.contextPath}/resources/home/homeZhu/bootstrap/js/jquery-3.1.1.min.js"></script>
        <link href="${request.contextPath}/resources/index/plugins/bootstrap/css/icons/icomoon/styles.css" rel="stylesheet" />
        <link href="${request.contextPath}/resources/index/public/css/loading_xbox.css" rel="stylesheet" />
        <link href="${request.contextPath}/resources/index/public/css/main_layout.css" rel="stylesheet" />
        <!--kendoui-->
        <link href="${request.contextPath}/resources/index/plugins/kendoui/style/kendo.common-material.min.css" rel="stylesheet" />
        <link href="${request.contextPath}/resources/index/plugins/kendoui/style/kendo.material.min.css" rel="stylesheet" />
        <script src="${request.contextPath}/resources/index/plugins/kendoui/js/kendo.all.min.js"></script>
        <!--<script src="./plugins/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script> 特殊使用-->

        <script src="${request.contextPath}/resources/index/public/js/store.min.js"></script>
        <#--<script src="${request.contextPath}/resources/index/public/js/add_static_node.js"></script>-->
        <!--mian-->
        <link href="${request.contextPath}/resources/index/plugins/horizontal_layout/switch1.css" rel="stylesheet" />
        <link href="${request.contextPath}/resources/index/plugins/horizontal_layout/float-button.css" rel="stylesheet" />
        <link href="${request.contextPath}/resources/index/plugins/horizontal_layout/float-button.css" rel="stylesheet" />
        <link href="${request.contextPath}/resources/index/plugins/horizontal_layout/main_layout.css" rel="stylesheet" />
    </head>
	<body>
		<div id="app">
			<input id="sidebar-show" type="checkbox" checked="checked" hidden="hidden" style="display: none;" />
			<div class="main LIGHT" v-bind:class="{'mfx-left':sidebar.isLeft,'mfx-right':!sidebar.isLeft,nofx: !sidebar.isFx }">
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
			<label v-if="sidebar.isFx==true" for="sidebar-show" id="fx-sidebar"></label>
			<!---->
			<div class="sidebar-button">
				<label for="sidebar-show" tabindex="0" class="mu-float-button mu-float-button-mini" style="-webkit-user-select: none; outline: none; cursor: pointer; -webkit-appearance: none;">
						<div> 
							<div class="mu-float-button-wrapper">
								<i class="icon-paragraph-justify3"></i>
							</div>
						</div>
				</label>
			</div>
			<!---->
			<div class="sidebar LIGHT" v-bind:class="{'bfx-left':sidebar.isLeft,'bfx-right':!sidebar.isLeft,'z-depth-2':sidebar.isLeft}">
				<div class="logo">
                    <img src="${request.contextPath}/resources/index/imgs/logo2.png" />
				</div>
				<div class="sidebar-user">
					<div class="category-content">
						<div class="media">
							<div class="media-body">
								<span class="media-heading text-semibold">电商产业园</span>
								<div class="text-size-mini text-muted">
									<i class="icon-pin text-size-small"></i> &nbsp;厦门
								</div>
							</div>

							<div class="media-right media-middle">
								<ul class="icons-list">
									<li>
										<a href="javascript:void(0)" v-on:click="settingShow=true" data-balloon="设置" v-bind:data-balloon-pos="(sidebar.isLeft?'right':'left')"><i class="icon-cog3"></i></a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<ul class="nav">
					<li style="margin-top: 6px;margin-bottom: 10px;">
						<span style="padding: 12px 15px;min-height: 44px;font-weight: 500;color: #eee;">菜单</span>
					</li>
                    <li v-for="(itm,index) in menuBar">
                        <a href=""><span>{{itm.menuname}}</span><i class="icon-chevron-right"></i></a>
                        <ul class="nav">
                            <li class="nav" v-for="(itm2,index2) in itm.chindren" v-if="itm2&&itm2.chindren">
                                <a href="javascript:void(0)" class="has-ul">{{itm2.menuname}}<i class="icon-chevron-right"></i></a>
                                <dropdown-menu v-bind:chindren="itm2.chindren"></dropdown-menu>
                            </li>
                            <li v-else>
                                <a onclick="addTab(this)" v-bind:data-id="itm2.menuid" v-bind:data-href="itm2.menuurl">{{itm2.menuname}}</a>
                            </li>
                        </ul>
                    </li>
                </ul>
				<div style="position: absolute; bottom: 0;left: 0;right: 0;padding: 12px 15px;text-align: center;" data-balloon="退出" v-bind:data-balloon-pos="sidebar.isLeft?'right':'left'">
					<i class="icon-exit3"></i>&nbsp;退出登录
				</div>
			</div>
			<!-- Modal -->
			<div class="modal fade" v-bind:class="{'in':settingShow}" v-bind:style="{'display':(settingShow?'block':'none')}">
				<div class="modal-backdrop fade in" v-on:click="settingShow=false"></div>
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header" style="border-bottom: 1px solid #f3f3f3;">
							<h4 class="modal-title" id="myModalLabel">设置</h4>
						</div>
						<div class="modal-body" style="text-align: center;border-bottom: 1px solid #f3f3f3;">
							<h4>布局设置</h4>
							<br />
							<!---->
							<label class="mu-switch">
		        		<input type="checkbox" hidden="hidden"  v-model="sidebar.isFx"> 
		        		<div class="mu-switch-wrapper"><!----> 
		        			<div class="mu-switch-container">
		        				<div class="mu-switch-track"></div> 
	        					<div class="mu-switch-thumb"> </div>
		        			</div> 
	        				<div class="mu-switch-label">主页面固定显示({{sidebar.isFx?'是':'否'}})</div>
		        		</div>
			        </label>
							<br />
							<label class="mu-switch">
		        		<input type="checkbox" hidden="hidden"  v-model="sidebar.isLeft"> 
		        		<div class="mu-switch-wrapper"><!----> 
		        			<div class="mu-switch-container">
		        				<div class="mu-switch-track"></div> 
	        					<div class="mu-switch-thumb"> </div>
		        			</div> 
	        				<div class="mu-switch-label">菜单居左边显示({{sidebar.isLeft?'是':'否'}})</div>
		        		</div>
			        </label>
							<br />
							<label class="mu-switch" onclick="window.location.href='verticalLayout'">
				        		<input type="checkbox" hidden="hidden"> 
				        		<div class="mu-switch-wrapper"><!----> 
				        			<div class="mu-switch-container">
				        				<div class="mu-switch-track"></div> 
			        					<div class="mu-switch-thumb"> </div>
				        			</div> 
			        				<div class="mu-switch-label">使用纵向布局?(否)</div>
				        		</div>
					        </label>
							<!---->
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" v-on:click="settingShow=false">关闭</button>
						</div>
					</div>
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
        <script src="${request.contextPath}/resources/index/plugins/horizontal_layout/main_layout.js"></script>
        <script>
            $(function() {
                store.set('layout', "horizontal_layout");
                layout.menuBar = transData(${userData}, "menuid", "parentid", "chindren"); //---加载菜单
            })
        </script>
	</body>

</html>