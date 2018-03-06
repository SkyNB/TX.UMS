<#import "/WEB-INF/layouts/master.ftl" as layout>

<#assign bodyEnd>
</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="DeliveryAddressController">
    <div class="action-bar">
        <a href="#/create" ng-click="create();" kendo-button id="add">
            <i class="fa fa-plus"></i> 增加 </a>

        <button href="#" class="k-button" ng-click="forbidden()">
            <i class="fa fa-ban"></i> 禁用 </button>

        <button href="#" class="k-button" ng-click="invocation()">
            <i class="fa fa-check"></i> 启用
        </button>
    </div>
    <div class="search-bar">
        <form id="fromSearchDeliveryAddress" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3">单位</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox"
                           name="companyName" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">联系人</label>
                <div class="col-sm-9">
                    <input class="k-textbox" name="contactMan"
                           autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">省</label>
                <div class="col-sm-9">
                    <input class="k-textbox" name="province"
                           autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">市</label>
                <div class="col-sm-9">
                    <input class="k-textbox" name="city"
                           autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="customerForSearch">客户</label>
                <div class="col-sm-9">
                    <select kendo-drop-down-list id="customerForSearch" name="ownerCode">
                        <option value="">-------请选择-------</option>
                        <#list customers as item>
                            <option value="${item.value}">${item.text}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridDeliveryAddress"></div>
</div>
</@layout.master>