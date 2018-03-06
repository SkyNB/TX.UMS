<#import "/WEB-INF/layouts/master.ftl" as layout>
<@layout.master >
<div ui-view="content" ng-controller="RoleController">
    <div class="action-bar">
    <#--<a href="#/create" data-target="#addRole" data-toggle="modal" class="k-button" id="add">-->
    <#--<i class="fa fa-plus"></i> 增加 </a>-->
        <a data-target="#addRole" data-toggle="modal" class="k-button" id="add">
            <i class="fa fa-plus"></i> 增加 </a>

        <a href="#" class="k-button" ng-click="delete()">
            <i class="fa fa-remove"></i> 删除 </a>
        <a class="k-button" ng-click="inactivate()">
            <i class="fa fa-ban"></i> 禁用 </a>
        <a href="#" class="k-button" ng-click="activate()">
            <i class="fa fa-check"></i> 启用 </a>
    </div>
    <div class="search-bar">
        <form id="fromSearchRole" class="form-inline" ng-submit="search()">
            <div class="form-group">
                <label class="control-label col-sm-3" for="codeSearch">编码</label>

                <div class="col-sm-9">
                    <input type="text" class="k-textbox" id="codeSearch"
                           name="code" autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="nameSearch">名称</label>

                <div class="col-sm-9">
                    <input class="k-textbox" id="nameSearch" name="name"
                           autocomplete="off">
                </div>
            </div>
            <div class="form-group">
                <button class="k-button" type="submit" class="k-button"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
        </form>
    </div>
    <div class="dynamic-height" kendo-ex-grid k-options="gridOptions" id="gridRole"></div>
</div>

<div id="addRole" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="addRoleForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;添加角色</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="code">编码
                                <span class="required" aria-required="true"> * </span>
                            </label>

                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="role.code" required="required" id="role"
                                       name="code" pattern="[A-Za-z0-9_-]+"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="name">名称
                                <span class="required" aria-required="true"> * </span>
                            </label>

                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="role.name" required="required" id="name"
                                       name="name"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="sysRole">预设角色
                            </label>

                            <div class="col-sm-8 form-control-static">
                                <input type="checkbox" id="sysRole" ng-model="role.sysRole" checked/>
                            </div>
                        </div>
                    <#-- <div class="form-group col-sm-6">
                         <label class="control-label col-sm-4" for="active">是否启用</label>

                         <div class="col-sm-8 form-control-static">
                             <input type="checkbox" id="active" ng-model="role.active" checked/>
                         </div>
                     </div>-->
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="remark">备注</label>
                            <div class="col-sm-10">
                                <textarea class="k-textbox" id="remark" name="remark" ng-model="role.remark"
                                          rows="3"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="permissionsLists">权限</label>
                            <div class="col-sm-10">
                                <select kendo-multi-select multiple="multiple" required="required" k-auto-close="false"
                                        ng-model="role.permissionsLists" name="permissionsLists" id="permissionsLists">
                                    <#list permissions as item>
                                        <option value="${item.code}">${item.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" id="submit" class="btn btn-primary">
                        <i class="fa fa-check"></i>&nbsp;确定
                    </button>
                    <button type="button" tabindex="-1" class="btn btn-danger" data-dismiss="modal">
                        <i class="fa fa-close"></i>&nbsp;取消
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

</@layout.master>