<div id="updateRole" class="modal fade " role="basic" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
        <div class="modal-content">
            <form id="addRoleForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;修改角色</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="code">编码
                                <span class="required" aria-required="true"> * </span>
                            </label>

                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="role.code" required="required" id="role"
                                       name="code" readonly="readonly" pattern="[A-Za-z0-9_-]+"/>
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
                                <input type="checkbox" id="sysRole" ng-model="role.sysRole" checked disabled="disabled"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="active">是否启用</label>

                            <div class="col-sm-8 form-control-static">
                                <input type="checkbox" id="active" ng-model="role.active" checked disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="systemCode">系统编码
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="role.systemCode" required="required" id="systemCode"
                                       name="systemCode" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="remark">备注</label>

                            <div class="col-sm-8">
                                <textarea class="k-textbox" id="remark" name="remark" ng-model="role.remark"
                                          rows="3"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="permissionsLists">权限</label>
                            <div class="col-sm-10">
                                <select kendo-multi-select multiple="multiple" k-auto-close="false" ng-model="role.permissionsLists" name="permissionsLists" id="permissionsLists">
                                <#list permissions as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="btnConfirmCreateRole" type="submit" class="btn btn-primary">
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