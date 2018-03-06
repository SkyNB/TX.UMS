<#import "/WEB-INF/layouts/master.ftl" as layout>
<@layout.master >
<div ui-view="content" ng-controller="RoleController">
    <div class="action-bar">
        <a href="#/create" data-target="#addRole" data-toggle="modal" kendo-button>
            <i class="fa fa-plus"></i> 增加 </a>

        <a href="#" kendo-button ng-click="delete()">
            <i class="fa fa-remove"></i> 删除 </a>
        <a kendo-button ng-click="inactivate()">
            <i class="fa fa-ban"></i> 禁用 </a>
        <a href="#" kendo-button ng-click="activate()">
            <i class="fa fa-check"></i> 启用 </a>
    </div>
    <div class="search-bar">
        <form id="fromSearchRole" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3" for="codeSearch">编码</label>

                <div class="col-sm-9">
                    <input type="text" class="k-textbox" id="codeSearch"
                           name="code" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="nameSearch">名称</label>

                <div class="col-sm-9">
                    <input class="k-textbox" id="nameSearch" name="name"
                           autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridRole"></div>
</div>
</@layout.master>