<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<!--全局-->
		<link href="${request.contextPath}/resources/static/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
		<script src="${request.contextPath}/resources/static/public/js/vue.min.2.1.10.js"></script>
		<script src="${request.contextPath}/resources/static/public/js/jquery.3.1.1.min.js"></script>
		<link href="${request.contextPath}/resources/static/plugins/bootstrap/css/icons/icomoon/styles.css" rel="stylesheet" />
		<link href="${request.contextPath}/resources/static/public/css/loading_xbox.css" rel="stylesheet" />
		<link href="${request.contextPath}/resources/static/public/css/main_layout.css" rel="stylesheet" />
		<!--kendoui-->
		<link href="${request.contextPath}/resources/static/plugins/kendoui/styles/kendo.common-material.min.css" rel="stylesheet" />
		<link href="${request.contextPath}/resources/static/plugins/kendoui/styles/kendo.material.min.css" rel="stylesheet" />
		<script src="${request.contextPath}/resources/static/plugins/kendoui/js/kendo.all.min.js"></script>

		<script src="${request.contextPath}/resources/static/public/js/store.min.js"></script>
		<script src="${request.contextPath}/resources/static/public/js/js-css-load.js"></script>
		<!--mian-->
	</head>

	<body>
		<div id="app">
			<input id="sidebar-show" type="checkbox" checked="checked"  style="display: none;overflow: hidden;" />
			<div class="main LIGHT" v-bind:class="[{'mfx-left':sidebar.isLeft,'mfx-right':!sidebar.isLeft,nofx: !sidebar.isFx },sidebar.themeClass]">
				<div id="tabstrip">
					<ul class="">
						<li class="k-state-active ">
							<i class="icon-home" style="font-size: 12px;"></i>&nbsp;主页
						</li>
					</ul>
					<div>
						<iframe src=""></iframe>
					</div>
				</div>
			</div>
			<label v-if="sidebar.isFx==true" for="sidebar-show" id="fx-sidebar"></label>
			<div class="sidebar LIGHT" v-bind:class="[{'bfx-left':sidebar.isLeft,'bfx-right':!sidebar.isLeft,'z-depth-2':sidebar.isLeft},sidebar.themeClass]" style="z-index: 2;">
				<div class="logo">
					<img src="${request.contextPath}/resources/static/imgs/logo2.png" />
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
										<a href="javascript:void(0)"  data-balloon="设置" v-bind:data-balloon-pos="(sidebar.isLeft?'right':'left')"><i class="icon-cogs"></i></a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<ul class="nav">
					<li style="margin-top: 6px;margin-bottom: 10px;">
						<span  class="menu-sign-text" >菜单</span>
					</li>
                    <li v-for="(itm,index) in menuBar">
                        <a href="javascript:void(0)"><span>{{itm.menuname}}</span><i class="icon-chevron-right"></i></a>
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
				<div style="position: absolute;bottom: 0px;left: 0px;right: 0px;text-align: center;padding: 12px 0;">
					<div class="row" style="margin-left: 0;margin-right: 0;width: 100%;">
						<div class="col-sm-3">
							<span v-on:click="settingShow=true" style="cursor: pointer;" data-balloon="布局" v-bind:data-balloon-pos="sidebar.isLeft?'right':'left'">
								<i class="icon-magic-wand" ></i>
							</span>
						</div> 
						<div class="col-sm-3">
							<span style="cursor: pointer;" data-balloon="聊天" v-bind:data-balloon-pos="sidebar.isLeft?'right':'left'" onclick="window.location.href='./login/lockscreen.html'">
								<i class="icon-bubbles" ></i>
							</span>
						</div>
						<div class="col-sm-3">
							<span style="cursor: pointer;" data-balloon="锁屏" v-bind:data-balloon-pos="sidebar.isLeft?'right':'left'" onclick="window.location.href='./login/lockscreen.html'">
								<i class="icon-lock2" ></i>
							</span>
						</div>
						<div class="col-sm-3">
							<span style="cursor: pointer;" data-balloon="注销" v-bind:data-balloon-pos="sidebar.isLeft?'right':'left'">
								<i class="icon-exit3" ></i>
							</span>
						</div>
					</div>
				</div>
			</div>
			<!--按钮-->
			<div class="sidebar-button" v-bind:style="{bottom:sidebar.floatButtonH.value+'%'}">
				<label for="sidebar-show" tabindex="0" class="mu-float-button mu-float-button-mini" style="-webkit-user-select: none; outline: none; cursor: pointer; -webkit-appearance: none;" v-bind:style="{'border-radius':(sidebar.floatButtonH.round?'50%':'0')}">
						<div> 
							<div class="mu-float-button-wrapper">
								<i class="icon-paragraph-justify3"></i>
							</div>
						</div>
				</label> 
			</div>
			<!-- Modal -->
			<div class="modal fade" v-bind:class="{'in':settingShow}" v-bind:style="{'display':(settingShow?'block':'none')}">
				<div class="modal-backdrop fade in" v-on:click="settingShow=false"></div>
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header" style="border-bottom: 1px solid #f3f3f3;">
							<h4 class="modal-title" id="myModalLabel">设置</h4>
						</div>
						<div class="modal-body" style="border-bottom: 1px solid #f3f3f3;"> 
							<!---->
							<div class="row"> 
								<div class="col-sm-12"><h4>布局设置</h4></div>
								<div class="col-sm-4">
									<label class="mu-switch">
						        		<input type="checkbox" hidden="hidden"  v-model="sidebar.isFx"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">内容面固定显示({{sidebar.isFx?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-4">
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
								</div>
								<div class="col-sm-4">
									<label class="mu-switch" onclick="window.location.href='vertical_layout.html'">
						        		<input type="checkbox" hidden="hidden"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">切换到纵向布局(否)</div>
						        		</div>
							        </label>
								</div>
							</div> 
							<!---->
							<div class="row"> 
								<div class="col-sm-12"><h4>颜色主题</h4></div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="LIGHT-df"  v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">默认({{sidebar.themeClass=="LIGHT-df"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="DARK"  v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">DARK({{sidebar.themeClass=="DARK"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="CARBON" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">CARBON({{sidebar.themeClass=="CARBON"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="TEAL" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">TEAL({{sidebar.themeClass=="TEAL"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="KIT1" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">KIT1({{sidebar.themeClass=="KIT1"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="KIT2" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">KIT2({{sidebar.themeClass=="KIT2"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="TRACK" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">TRACK({{sidebar.themeClass=="TRACK"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="SPRFLAT" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">SPRFLAT({{sidebar.themeClass=="SPRFLAT"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="Z-LIGHT" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">Z-LIGHT({{sidebar.themeClass=="Z-LIGHT"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="Material-RED" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">MaterialR({{sidebar.themeClass=="Material-RED"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="Material-GREEN" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">MaterialG({{sidebar.themeClass=="Material-GREEN"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
								<div class="col-sm-3">
									<label class="mu-switch">
						        		<input type="radio" hidden="hidden" value="Material-BLUE" v-model="sidebar.themeClass"> 
						        		<div class="mu-switch-wrapper"><!----> 
						        			<div class="mu-switch-container">
						        				<div class="mu-switch-track"></div> 
					        					<div class="mu-switch-thumb"> </div>
						        			</div> 
					        				<div class="mu-switch-label">MaterialB({{sidebar.themeClass=="Material-BLUE"?'是':'否'}})</div>
						        		</div>
							        </label>
								</div>
							</div> 
							<!---->
							<div class="row"> 
								<div class="col-sm-12"><h4>浮动按钮高度</h4></div>
									<div class="col-sm-10" data-balloon-pos="top" v-bind:data-balloon="sidebar.floatButtonH.value+'%'" >
										<input class="range-box" style="margin-top: 9px;" type="range" min="0" max="95" v-model="sidebar.floatButtonH.value">
									</div>  
									<div class="col-sm-1" >
										<span  data-balloon-pos="top"  data-balloon="重置" style="cursor: pointer;">
											<i class="icon-reset" v-on:click="sidebar.floatButtonH.value=sidebar.floatButtonH.default" ></i>
										</span>
									</div> 
									<div class="col-sm-1"  data-balloon-pos="top"  data-balloon="风格" style="cursor: pointer;">
										<i v-bind:class="{'icon-radio-unchecked':sidebar.floatButtonH.round,'icon-checkbox-unchecked2':!sidebar.floatButtonH.round}" v-on:click="sidebar.floatButtonH.round=!sidebar.floatButtonH.round" ></i>
									</div> 
							</div> 
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
		<div id="main_loading" class="main_loading" style="background-color: #f5f5f5;position: fixed;top: 0;left: 0;right: 0;bottom: 0;">
			<div class="loading_xbox center-display">
				<div>加载中</div>
				<div>&nbsp;</div>
				<div class="loading_xbox_xs">
					<div class="pace_activity"></div>
				</div>
			</div>
		</div>
		<!--<script src="./plugins/horizontal_layout/main_layout.js"></script>--> 
		<script>
			$(function() { 
				addNodes({
					parent:"head",
					src:["${request.contextPath}/resources/static/plugins/horizontal_layout/switch1.css",
					"${request.contextPath}/resources/static/plugins/horizontal_layout/balloon.min.css",
					"${request.contextPath}/resources/static/plugins/horizontal_layout/float-button.css",
					"${request.contextPath}/resources/static/plugins/horizontal_layout/main_layout.css",
					"${request.contextPath}/resources/static/plugins/horizontal_layout/layout-color.css",
                    "${request.contextPath}/resources/static/public/js/to_treeJson.js",
					"${request.contextPath}/resources/static/plugins/horizontal_layout/main_layout.js"],
					load (){
                        setTimeout(function() {
                            $(".main_loading").fadeOut();
                            store.set('layout', "horizontal_layout");
                            layout.menuBar = transData(${userData}, "menuid", "parentid", "chindren"); //---加载菜单}
                        } ,500);
					}
				});
				
			});
		</script>
	</body>

</html>