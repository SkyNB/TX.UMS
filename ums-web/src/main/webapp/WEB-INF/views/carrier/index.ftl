<#import "/WEB-INF/layouts/master.ftl" as layout/> <#--as layout 取得别名-->

<#assign bodyEnd>
    <#-- 自己特定的js都放到这里面引用 -->
</#assign>

<@layout.master bodyEnd=bodyEnd><#-- 第一行的别名-->
<div ui-view="content" ng-controller="CarrierController">
    <div class="action-bar">
        <a href="#/import" class="k-button" data-target="#importCarrier" data-toggle="modal">
            <i class="fa fa-mail-forward"></i> 导入 </a>
        <a href="#/create" data-target="#addCarrier" data-toggle="modal" class="k-button">
            <i class="fa fa-plus"></i> 增加
        </a>
        <a href="#" kendo-button ng-click="inactivate()">
            <i class="fa fa-ban"></i> 禁用 </a>

        <a href="#" kendo-button ng-click="activate()">
            <i class="fa fa-check"></i> 启用
        </a>
    </div>
    <div class="search-bar">
        <form id="fromSearchCarrier" class="form-inline" ng-submit="search()">
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
                <label class="control-label col-sm-3" for="carrierType">类型</label>
                <div class="col-sm-9">
                    <select kendo-drop-down-list id="carrierType" name="carrierType">
                        <option value="">-------请选择-------</option>
                        <#list carrierTypes as item>
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
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridCarrier" k-data-bound='dataBound'></div>
</div>
</@layout.master>