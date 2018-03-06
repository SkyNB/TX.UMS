<#import "/WEB-INF/layouts/master.ftl" as layout/>

<#assign bodyEnd>

</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="NotificationController">
    <div class="action-bar">
        <a href="#/create" data-target="#addNotification" data-toggle="modal" class="k-button">
            <i class="fa fa-plus"></i> 增加 </a>

        <#--<a href="#" kendo-button ng-click="recall()">
            <i class="fa fa-ban"></i> 撤销 </a>-->
    </div>

    <div class="search-bar">
        <form id="fromSearchNotification" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3">主题</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="subject" autocomplete="off">
                </div>
            </div>
            <#--<div class="form-group">
                <label class="control-label col-sm-3">发送人</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="sender" autocomplete="off">
                </div>
            </div>-->
            <div class="form-group">
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" k-data-bound='dataBound' id="gridNotification"></div>
</div>
</@layout.master>