<#import "/WEB-INF/layouts/master.ftl" as layout>


<#assign bodyEnd>
<#--<script src="${request.contextPath}/resources/pages/user/indexDemo.js" type="text/javascript"></script>-->
</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="UserController">
    <div class="action-bar">
        <a href="#/create" data-target="#addUser" data-toggle="modal" class="k-button">
            <i class="fa fa-plus"></i> 增加 </a>
        <button class="k-button" ng-confirm-message="确定要删除吗？" ng-confirm-click="delete()" >
            <i class="fa fa-remove"></i> 删除 </button>
        <button ng-click="resetPassword()" class="k-button">
            <i class="fa fa-edit"></i> 修改密码 </button>
        <button ng-click="unEnable()" class="k-button">
            <i class="fa fa-ban"></i> 禁用 </button>
        <button ng-click="enable()" class="k-button">
            <i class="fa fa-check"></i> 启用 </button>
    </div>

    <div class="search-bar">
        <form id="fromSearchUser" class="form-inline" ng-submit="search()">
            <div class="form-group ">
                <label class="control-label col-sm-3" for="usernameSearch">用户名</label>

                <div class="col-sm-9">
                    <input type="text" class="k-textbox" ng-model="filter.userName" id="usernameSearch"
                           name="userName" autocomplete="off">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-sm-3">姓名</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="fullName" autocomplete="off">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-sm-3" for="emailSearch">邮箱</label>

                <div class="col-sm-9">
                    <input class="k-textbox" id="emailSearch" ng-model="filter.email" name="email"
                           autocomplete="off">
                </div>
            </div>
            <div class="form-group ">
                <label class="control-label col-sm-3" id="typeForSearch">类型</label>
                <div class="col-sm-9">
                    <select kendo-drop-down-list id="typeForSearch" name="type">
                        <option value="">-------请选择-------</option>
                        <#list types as item>
                            <option value="${item}">${item.text}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i> 搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" k-data-bound='dataBound' id="gridUser"></div>
</div>

</@layout.master>