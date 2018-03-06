<div id="updateOrganization" class="modal fade " role="basic" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateOrganizationForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;修改组织架构</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="code">编码
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="organization.code" required="required" id="code" name="code" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="name">名称
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="organization.name" required="required" id="name" name="name"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="type">类型
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <select kendo-drop-down-list ng-model="organization.type" required="required" id="type" name="type">
                                    <option value="">请选择...</option>
                                <#list types as item>
                                    <option value="${item}">${item.text}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="parentId">上级组织
                            </label>
                            <div class="col-sm-8">
                                <input kendo-drop-down-tree-view id="parentId" name="parentId" k-options="dropdownTreeview" ng-model="organization.parentId" >
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="remark">备注
                            </label>
                            <div class="col-sm-8" >
                                <textarea  class="k-textbox" id="remark" name="remark" ng-model="organization.remark" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="active">是否启用
                            </label>
                            <div class="col-sm-8 form-control-static">
                                <input type="checkbox"  id="active" name="active" ng-model="organization.active" checked disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="btnConfirmCreateOrganization" type="submit" class="btn btn-primary">
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