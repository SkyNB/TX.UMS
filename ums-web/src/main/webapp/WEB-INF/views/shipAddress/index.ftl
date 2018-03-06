<#import "/WEB-INF/layouts/master.ftl" as layout/>

<#assign bodyEnd>

</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="ShipAddressController">
    <div class="action-bar">
        <a href="#/create" data-target="#addVehicle" data-toggle="modal" class="k-button">
            <i class="fa fa-plus"></i> 增加
        </a>
        <a href="#" kendo-button ng-click="inactivate()">
            <i class="fa fa-ban"></i> 禁用 </a>

        <a href="#" kendo-button ng-click="activate()">
            <i class="fa fa-check"></i> 启用
        </a>
    </div>
    <div class="search-bar">
        <form id="fromSearchShipAddress" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3">编码</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="code" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">名称</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="name" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">联系人</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="contactMan" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="carrierCode">承运商</label>
                <div class="col-sm-9">
                    <select kendo-drop-down-list id="carrierCode" name="carrierCode">
                        <option value="">-------请选择-------</option>
                        <#list carriers as item>
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
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridShipAddress" k-data-bound='dataBound'></div>
</div>
</@layout.master>