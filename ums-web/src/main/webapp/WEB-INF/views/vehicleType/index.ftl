<#import "/WEB-INF/layouts/master.ftl" as layout/>

<#assign bodyEnd>

</#assign>

<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="VehicleTypeController">
    <div class="action-bar">
        <a href="#/create" data-target="#addVehicleType" data-toggle="modal" class="k-button">
            <i class="fa fa-plus"></i> 增加
        </a>
    </div>
    <div class="search-bar">
        <form id="fromSearchVehicleType" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3">名称</label>
                <div class="col-sm-9">
                    <input type="text" class="k-textbox" name="name" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridVehicleType" k-data-bound='dataBound'></div>
</div>
</@layout.master>