<#import "/WEB-INF/layouts/form.ftl" as form>

<!--弹出遮罩层 -->
<div id="modal_theme_primary" class="modal fade" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h6 class="modal-title">添加用户</h6>
            </div>

            <div class="modal-body">
                <form action="${request.contextPath}/xinUser/addUser" method="post">
                    <p>登录名<input type="text" name="username" value="" id="username"></p>
                    <p>用户密码<input type="text" name="userpwd" value="" id="userpwd"></p>
                <#--<p>确认密码<input type="text" name="confirmUserpwd" value="confirmUserpwd" id=""></p>-->
                    <p>真实姓名<input type="text" name="realname" value="" id="realname"></p>
                    <p>用户性别<input type="text" name="usersex" value="" id="usersex"></p>
                    <p>特殊用户<input type="text" name="isvip" value="" id="isvip"></p>
                    <p>用户类型<input type="text" name="usertype" value="" id="usertype"></p>
                    <p>手机号码<input type="text" name="phone" value="" id="phone"></p>
                    <p>电子邮箱<input type="text" name="email" value="" id="email"></p>
                    <input type="submit" value="添加用户">
                </form>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="">确定</button>
            </div>
        </div>
    </div>
</div>

<div id="addUser" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="addUserForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;添加用户</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <@form.Text id="username" label="用户名" ngModel="user.username" required="required" maxLength="20" pattern="[A-Za-z0-9_-]+"/>
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
                            <label class="control-label col-sm-4" for="password">密码
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="password" class="k-textbox" ng-model="user.password" required="required" minlength="6" id="password" name="password"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="rePassword">确认密码
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="password" class="k-textbox" required="required"  ng-model="user.rePassword" minlength="6" equalTo="#password" id="rePassword" name="rePassword"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="emailCreate">邮箱
                            </label>
                            <div class="col-sm-8">
                                <input type="email" class="k-textbox" ng-model="user.email" id="emailCreate" name="email"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="orgCode">分支机构
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input kendo-drop-down-tree-view id="orgCode" name="orgCode" k-options="dropdownTreeview" ng-model="user.orgCode" required="required" ng-change="getSites();">
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