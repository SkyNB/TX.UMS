<#import "/WEB-INF/layouts/master.ftl" as layout>
<#import "/WEB-INF/layouts/form.ftl" as form>

<#assign bodyEnd >
<script>
    var transportTypes = ${jsonMapper.writeValueAsString(transportTypes)};
    var orderTypes = ${jsonMapper.writeValueAsString(orderTypes)};
    var exaccts = ${jsonMapper.writeValueAsString(exaccts)};
</script>
</#assign>
<@layout.master bodyEnd=bodyEnd>
<div ui-view="content" ng-controller="PriceController">
    <div class="action-bar">
        <a href="#/create" data-target="#addPrice" data-toggle="modal" class="k-button" id="add">
            <i class="fa fa-plus"></i> 增加 </a>
        <a href="#/calculate" data-target="#calculate" data-toggle="modal" class="k-button" id="calc">
            <i class="fa fa-plus"></i> 报价计算器 </a>

        <a class="k-button" href="#/import" data-target="#importPrice" data-toggle="modal">
            <i class="fa fa-file-excel-o"></i> 导入 </a>
    <#--<button class="k-button" ng-click="delete()">-->
    <#--<i class="fa fa-remove"></i> 删除 </button>-->
    </div>
    <div class="search-bar">
        <form id="fromSearchPrice" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-4" for="ownerCodeSearch">来源</label>

                <div class="col-sm-4">
                    <input kendo-combo-box id="ownerCodeSearch"
                           name="ownerCode" k-options="ownerOptions">
                </div>
            </div>
            <div class="form-group">
                <@form.DistrictSelect id="orginSearch" name="orgin" label="起始地"/>
            </div>
            <div class="form-group">
                <@form.DistrictSelect id="destSearch" name="destination" label="目的地"/>
            </div>
            <div class="form-group">
                <button kendo-button type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridPrice"></div>
</div>
</@layout.master>