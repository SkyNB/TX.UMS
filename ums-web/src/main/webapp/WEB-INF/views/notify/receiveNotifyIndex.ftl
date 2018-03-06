<#import "/WEB-INF/layouts/master.ftl" as layout/>

<#assign bodyEnd>

</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="ReceiveNotificationController">
    <div class="action-bar">

    </div>

    <div class="search-bar">
        <form id="fromSearchReceiveNotification" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3">主题</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="subject" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">发送人</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="sender" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="read">状态</label>
                <div class="col-sm-9">
                    <select kendo-drop-down-list id="read" name="read">
                        <option value="">请选择...</option>
                        <option value="1">已读</option>
                        <option value="0">未读</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" k-data-bound='dataBound'
         id="gridNotification"></div>
</div>
</@layout.master>