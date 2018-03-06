<div id="updateUser" class="modal fade " role="basic" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateUserForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;修改用户</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="userName">用户名
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="user.userName" required="required" id="userName" name="name" pattern="[A-Za-z0-9_-]+" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="fullName">姓名
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="user.fullName" required="required" id="fullName" name="fullName"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="type">用户类型
                            </label>
                            <div class="col-sm-8">
                                <select kendo-drop-down-list ng-model="user.type" id="type" readonly="readonly">
                                    <option value="">请选择...</option>
                                <#list types as item>
                                    <option value="${item}">${item.text}</option>
                                </#list>
                                </select>
                            </div>
                        </div>

                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="emailCreate">邮箱
                            </label>
                            <div class="col-sm-8">
                                <input type="email" class="k-textbox" ng-model="user.email" id="emailCreate" name="email"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="orgCode">分支机构
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input kendo-drop-down-tree-view id="orgCode" name="orgCode" k-options="dropdownTreeview" ng-model="user.orgCode" required="required" ng-change="getSites();"/>
                            </div>
                        </div>

                    </div>
                    <div class="row" id="site">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-4">站点
                            </label>
                            <div class="col-sm-8">
                                <select kendo-multi-select k-options="siteOptions" k-auto-close="false" ng-model="user.siteCodes" id="siteMulti"></select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2">备注</label>
                            <div class="col-sm-10">
                                <textarea class="k-textbox" ng-model="user.remark" rows="3"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="roleCodes">角色
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <select kendo-multi-select multiple="multiple" required="required" k-auto-close="false" ng-model="user.roleCodes" name="roleCodes" id="roleCodes">
                                <#list roles as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button id="btnConfirmCreateUser" type="submit" class="btn btn-primary">
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

<script>
    var types = ${jsonMapper.writeValueAsString(types)};
</script>