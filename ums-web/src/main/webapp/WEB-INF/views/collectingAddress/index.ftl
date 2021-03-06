<#import "/WEB-INF/layouts/master.ftl" as layout>
<#import "/WEB-INF/layouts/form.ftl" as form/>

<#assign bodyEnd>
</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="CollectingAddressController">
    <div class="action-bar">
        <a href="#/create" ng-click="create();" kendo-button id="add">
            <i class="fa fa-plus"></i> 增加 </a>

        <a href="#" kendo-button ng-click="forbidden()">
            <i class="fa fa-ban"></i> 禁用 </a>

        <a href="#" kendo-button ng-click="invocation()">
            <i class="fa fa-check"></i> 启用
        </a>
    </div>
    <div class="search-bar">
        <form id="fromSearchCollectingAddress" class="form-inline" ng-submit="search()">
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
                <label class="control-label col-sm-3">联系人</label>
                <div class="col-sm-9">
                    <input class="k-textbox" name="contactMan" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">省</label>
                <div class="col-sm-9">
                    <input class="k-textbox" name="province" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">城市</label>
                <div class="col-sm-9">
                    <input class="k-textbox" name="city" autocomplete="off">
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
                <label class="control-label col-sm-3" for="typeForSearch">类型&nbsp;&nbsp;&nbsp;</label>
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
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridCollectingAddress"></div>
</div>
</@layout.master>