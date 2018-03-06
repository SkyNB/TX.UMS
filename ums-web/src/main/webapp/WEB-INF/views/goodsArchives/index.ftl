<#import "/WEB-INF/layouts/master.ftl" as layout/>
<#import "/WEB-INF/layouts/form.ftl" as form/>

<#assign bodyEnd>
</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="GoodsArchivesController">
    <div class="action-bar">
        <a href="#/create" data-target="#addGoodsArchives" data-toggle="modal" kendo-button id="add">
            <i class="fa fa-plus"></i> 增加 </a>
        <a href="#/import" class="k-button" data-target="#import" data-toggle="modal">
            <i class="fa fa-plus"></i> 导入 </a>
        <button class="k-button" ng-confirm-message="确定要删除吗？" ng-confirm-click="delete()" >
            <i class="fa fa-remove"></i> 删除 </button>
    </div>
    <div class="search-bar">
        <form id="fromSearchGoodsArchives" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3" for="searchName">名称</label>

                <div class="col-sm-9">
                    <input type="text" class="k-textbox" id="searchName"
                           name="name" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="searchCode">编码</label>

                <div class="col-sm-9">
                    <input class="k-textbox" id="searchCode" name="code"
                           autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <div class="form-group">
                    <label class="control-label col-sm-3" for="customerCode">客户</label>
                    <div class="col-sm-9">
                        <select kendo-drop-down-list id="customerCode" name="customerCode">
                            <option value="">-------请选择-------</option>
                            <#list customers as item>
                                <option value="${item.value}">${item.text}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <#--<@form.ComboBox listItem=customers ngModel="customer" id="customerCode" label="客户"/>-->
            </div>
            <div class="form-group">
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" k-data-bound='dataBound' id="gridGoodsArchives"></div>
    <#--<div class="selected-data">
        <span ng-repeat="item in selectedData" class="label label-primary">{{item.code}}    <i class="fa fa-remove"></i></span>
    </div>-->
</div>
</@layout.master>