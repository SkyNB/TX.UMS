<#import "/WEB-INF/layouts/master.ftl" as layout>
<@layout.master >
<div ui-view="content" ng-controller="ExacctController">
    <div class="action-bar">
        <a href="#/create" data-target="#addExacct" data-toggle="modal" kendo-button id="add">
            <i class="fa fa-plus"></i> 增加 </a>

        <a href="#" kendo-button ng-confirm-message="确定要删除吗？" ng-confirm-click="delete()">
            <i class="fa fa-remove"></i> 删除 </a>
    </div>
    <div class="search-bar">
        <form id="fromSearchExacct" class="form-inline" ng-submit="search()">
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
    <div class="dynamic-height" kendo-tree-list k-options="gridOptions" id="gridExacct"></div>
</div>
</@layout.master>