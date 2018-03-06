<#import "/WEB-INF/layouts/master.ftl" as layout/>

<#assign bodyEnd>

</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="VehicleController">
    <div class="action-bar">
        <a href="#/create" data-target="#addVehicle" data-toggle="modal" class="k-button">
            <i class="fa fa-plus"></i> 增加
        </a>

        <a href="#/import" class="k-button" data-target="#importVehicle" data-toggle="modal">
            <i class="fa fa-reply"></i> 导入 </a>

        <a href="#" kendo-button ng-click="forbidden()">
            <i class="fa fa-ban"></i> 禁用 </a>

        <a href="#" kendo-button ng-click="invocation()">
            <i class="fa fa-check"></i> 启用
        </a>
    </div>
    <div class="search-bar">
        <form id="fromSearchVehicle" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3">司机</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="driver" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">车牌号</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="vehicleNo" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">手机号</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="driverMobile" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">租赁方式</label>
                <div class="col-sm-9">
                    <select kendo-drop-down-list name="leaseType">
                        <option value="">-------请选择-------</option>
                        <#list leaseType as item>
                            <option value="${item}">${item.text}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3">状态</label>
                <div class="col-sm-9">
                    <select kendo-drop-down-list name="status">
                        <option value="">-------请选择-------</option>
                        <#list status as item>
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
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridVehicle" k-data-bound='dataBound'></div>
</div>
</@layout.master>