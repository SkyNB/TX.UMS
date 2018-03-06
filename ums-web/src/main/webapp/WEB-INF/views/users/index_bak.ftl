<#import "/WEB-INF/layouts/master.ftl" as layout/>

<#assign bodyEnd>

</#assign>

<@layout.master bodyEnd=bodyEnd>

<div id="app">
    <!--搜索栏  你只要负责这个节点就算完成了搜索栏功能 -->
    <nav class="navbar nav-bar search-bar" v-bind:class="{'search-bar-show':searchBar.show}">
        <div class="container-fluid">
            <div class="form-horizontal row">
                <!--<div class="col-md-2">
                    <div class="form-group ">
                        <label class="control-label col-sm-4">输入框示例</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control ">
                        </div>
                    </div>
                </div>-->
                <sb-input label="输入框" placeholder="提示文字" col="5" v-model="searchBar.data.text"></sb-input>
                <sb-input id="fabric" label="下拉输入框" placeholder="提示文字" col="5" v-model="searchBar.data.dpd"></sb-input>
                <!--<sb-select id="color"  label="下拉列表" placeholder="提示文字" v-bind:values="testList"  v-model="searchBar.data.dpd2" ></sb-select>-->
                <sb-input id="datepicker" label="日期" placeholder="提示文字" col="5" v-model="searchBar.data.date"></sb-input>

                <!--<div class="col-md-2">
                    <div class="form-group ">
                        <label class="control-label col-sm-4">下拉输入框</label>
                        <div class="col-sm-8">
                            <input id="fabric" placeholder="Select fabric..." style="width: 100%;" />
                        </div>
                    </div>
                </div>-->
                <!--<div class="col-md-2">
                    <div class="form-group ">
                        <label class="control-label col-sm-4">日期</label>
                        <div class="col-sm-8">
                            <input id="datepicker" value="" style="width: 100%" readonly="readonly" />
                        </div>
                    </div>
                </div>-->
                <!--<div class="col-md-2">
                    <div class="form-group ">
                        <label class="control-label col-sm-4">日期+时间</label>
                        <div class="col-sm-8">
                            <input id="datetimepicker" value="" style="width: 100%" />
                        </div>
                    </div>
                </div>-->
                <div class="col-md-2">
                    <div class="form-group ">
                        <label class="control-label col-sm-5">下拉列表</label>
                        <div class="col-sm-7">
                            <#--<select id="dropd" v-model="searchBar.data.dpd2">-->
                                <#--<option value="">请选择</option>-->
                                <#--<option v-for="i in searchBar.tag.testList" v-bind:value="i.value">{{i.text}}</option>-->
                            <#--</select>-->
                        </div>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="form-group ">
                        <label class="control-label col-sm-3"></label>
                        <div class="col-sm-9">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default"><i class="icon-reset"></i>重置</button>
                                <button type="button" class="btn btn-default"><i class="icon-search4"></i>搜索</button>
                                <button type="button" class="btn btn-default" v-on:click="toggleSearchBar()"><i class="icon-search4"></i>{{searchBar.show?"关闭":"打开"}}高级搜索</button>
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
                        <button type="button" class="btn btn-default" id="btn_create" data-toggle="modal" data-target="#modal_theme_primary"><i class="icon-plus2"></i>添加用户</button>
                        <button type="button" class="btn btn-default" v-on:click="updateUser()"><i class="icon-compose"></i>编辑用户</button>
                        <button type="button" class="btn btn-default" v-on:click="del()"><i class="icon-cross2"></i>删除用户</button>
                        <button type="button" class="btn btn-default"><i class="icon-add-to-list"></i>批量操作</button>
                        <div class="btn-group">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                其他操作<span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#"><i class="icon-reload-alt"></i>启用用户</a></li>
                                <li><a href="#"><i class="icon-diff-ignored"></i>停用用户</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>
    <div id="main-content">
        <div class="content-padding">
            <div id="grid" style="border: none;" class="dynamicHeight" data-bind="kendoExGrid: { data: dataSource, columns: columns,sortable:true, widget:grid,change:$root.onChange,groupable:false}"></div>
        </div>
    </div>
    <!--选中行数据:{{tableData[selectIndex]}}-->
    <!--  提醒 -->
    <sb-toast show="fasle" ></sb-toast>
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
                <h6 class="modal-title">添加用户</h6>
            </div>

            <div class="modal-body">
                <form action="${request.contextPath}/xinUser/addUser" method="post">
                <p>登录名<input type="text" name="userName" value="" id="userName"></p>
                <p>用户密码<input type="text" name="userPwd" value="" id="userPwd"></p>
                <#--<p>确认密码<input type="text" name="confirmUserpwd" value="confirmUserpwd" id=""></p>-->
                <p>真实姓名<input type="text" name="realName" value="" id="realName"></p>
                <p>用户性别<input type="text" name="userSex" value="" id="userSex"></p>
                <p>特殊用户<input type="text" name="isVIP" value="" id="isVIP"></p>
                <p>用户类型<input type="text" name="userType" value="" id="userType"></p>
                <p>手机号码<input type="text" name="phone" value="" id="phone"></p>
                <p>电子邮箱<input type="text" name="email" value="" id="email"></p>
                    <input type="submit" value="添加用户">
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="">确定</button>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function() {
        app.tableOption.dataSource.data=${ErpUser};
    });
</script>
</@layout.master>