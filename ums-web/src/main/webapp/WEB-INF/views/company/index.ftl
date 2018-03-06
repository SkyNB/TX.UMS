<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<#--<meta http-equiv="X-Frame-Options" content="ALLOW-FROM http://localhost:7070/">-->

    <title></title>
    <link href="${request.contextPath}/resources/index/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/index/plugins/kendoui/style/kendo.common-material.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/index/plugins/kendoui/style/kendo.material.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/index/plugins/bootstrap/css/icons/icomoon/styles.css" rel="stylesheet" />
    <script src="${request.contextPath}/resources/index/public/js/add_static_node.js"></script>
    <script src="${request.contextPath}/resources/home/homeZhu/bootstrap/js/vue.min.js"></script>
<#--    <script src="http://cdn.bootcss.com/vue/2.1.8/vue.min.js"></script>-->
    <script src="${request.contextPath}/resources/index/plugins/kendoui/jquery.min.js"></script>
    <!--<script src="./plugins/kendoui/js/kendo.all.min.js"></script>
    <script src="./public/js/object-copy.js"></script>-->
    <link href="${request.contextPath}/resources/index/public/css/loading_xbox.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/index/public/css/main_layout.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/index/demo.1.css" rel="stylesheet" />
</head>

<body style="background-color: #fff;">
<div id="app">
    <!--搜索栏  你只要负责这个节点就算完成了搜索栏功能 -->
    <nav class="navbar nav-bar search-bar" v-bind:class="{'search-bar-show':searchBar.show}">
        <div class="container-fluid">
            <div class="form-horizontal row">

                <sb-input label="输入框" placeholder="提示文字" col="5" v-model="searchBar.data.text"></sb-input>
                <sb-input id="fabric" label="下拉输入框" placeholder="提示文字" col="5" v-model="searchBar.data.dpd"></sb-input>
                <!--<sb-select id="color"  label="下拉列表" placeholder="提示文字" v-bind:values="testList"  v-model="searchBar.data.dpd2" ></sb-select>-->
                <sb-input id="datepicker" label="日期" placeholder="提示文字" col="5" v-model="searchBar.data.date"></sb-input>

                <div class="col-md-2">
                    <div class="form-group ">
                        <label class="control-label col-sm-5">下拉列表</label>
                        <div class="col-sm-7">
                            <select id="dropd" v-model="searchBar.data.dpd2">
                                <option value="">请选择</option>
                                <option v-for="i in searchBar.tag.testList" v-bind:value="i.value">{{i.text}}</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="col-md-2">
                    <div class="form-group ">
                        <label class="control-label col-sm-3"></label>
                        <div class="col-sm-9">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default"><i class="icon-reset"></i>重置</button>
                                <button type="button" class="btn btn-default"><i class="icon-search4"></i>搜索</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <nav class="navbar nav-bar btn-bar" role="navigation" style="border: none;">
        <div class="container-fluid">
            <div class="navbar-collapse collapse" style="padding-left: 1px;">
                <div class="nav navbar-nav">
                    <div class="btn-group">
                        <button type="button" class="btn btn-default" id="btn_create" data-toggle="modal" data-target="#modal_theme_primary"><i class="icon-plus2"></i>添加公司</button>
                        <button type="button" class="btn btn-default" v-on:click="updateCompany()"><i class="icon-compose"></i>编辑公司</button>
                        <button type="button" class="btn btn-default" v-on:click="deleteCompany()"><i class="icon-cross2"></i>删除公司</button>
                        <button type="button" class="btn btn-default"><i class="icon-add-to-list"></i>批量操作</button>
                        <div class="btn-group">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                其他操作<span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><button type="button" class="btn btn-default" v-on:click="startCompany()"><i class="icon-compose"></i>启用公司</button></li>
                                <li><button type="button" class="btn btn-default" v-on:click="disableCompany()"><i class="icon-cross2"></i>停用公司</button></li>
                            </ul>
                        </div>
                    </div>
                    <div class="btn-group">
                        <button type="button" class="btn btn-default" v-on:click="toggleSearchBar()"><i class="icon-search4"></i>{{searchBar.show?"关闭":"打开"}}高级搜索</button>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <div id="main-content">
        <div class="content-padding">
            <div id="grid" style="border: none;" class="dynamicHeight" data-bind="kendoExGrid: { data: dataSource, columns: columns, widget:grid,change:$root.onChange,groupable:true}"></div>
        </div>
    </div>
    <!--选中行数据:{{tableData[selectIndex]}}-->
    <!--  提醒 -->
    <div class="toast" style="z-index: 20141256;" v-bind:class="{'show':toast.show}">{{toast.text}}</div>
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
</div>
<!--弹出遮罩层 -->
<div id="modal_theme_primary" class="modal  fade"  aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h6 class="modal-title">添加公司</h6>
            </div>

            <div class="modal-body">
            <#--<form action="${request.contextPath}/xinUser/addUser" method="post">-->
                <form action="#" method="post">
                    <p>公司名<input type="text" name="companyname" value="" id="companyname"></p>
                    <p>公司类型<input type="text" name="companyclassid" value="" id="companyclassid"></p>
                <#--<p>用户密码<input type="text" name="userPwd" value="" id="userPwd"></p>-->
                <#--<p>确认密码<input type="text" name="confirmUserpwd" value="confirmUserpwd" id=""></p>-->
                <#--<p>真实姓名<input type="text" name="realName" value="" id="realName"></p>-->
                <#--<p>用户性别<input type="text" name="userSex" value="" id="userSex"></p>-->
                <#--<p>特殊用户<input type="text" name="isVIP" value="" id="isVIP"></p>-->
                <#--<p>用户类型<input type="text" name="userType" value="" id="userType"></p>-->
                <#--<p>手机号码<input type="text" name="phone" value="" id="phone"></p>-->
                <#--<p>电子邮箱<input type="text" name="email" value="" id="email"></p>-->
                    <#--<input type="submit" value="添加公司">-->
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="">确定</button>
            </div>
        </div>
    </div>
</div>
<#--用户js-->
<script src="${request.contextPath}/resources/js/user/user.js"></script>

<script src="${request.contextPath}/resources/index/plugins/bootstrap/js/bootstrap.min.js" async="async"></script>
<script src="${request.contextPath}/resources/index/plugins/kendoui/jquery.min.js"></script>
<script src="${request.contextPath}/resources/index/plugins/kendoui/js/kendo.all.min.js"></script>

<script src="${request.contextPath}/resources/index/plugins/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>
<script src="${request.contextPath}/resources/index/plugins/kendoui/js/cultures/kendo.culture.zh-CN.min.js"></script>
<script src="${request.contextPath}/resources/index/public/js/object-copy.js"></script>
<script src="${request.contextPath}/resources/index/demo.4.js"></script>
<script>
    $(document).ready(function() {
        app.tableOption.dataSource.data=${ErpCompany}
    });

</script>
</body>

</html>