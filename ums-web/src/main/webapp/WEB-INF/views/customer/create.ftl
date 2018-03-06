<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="addCustomer" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="addCustomerForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;添加客户</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="code">编码
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="customer.code" required="required"
                                       name="code" pattern="[A-Za-z0-9_-]+"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="name">名称
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="customer.name" required="required"
                                       name="name"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <@form.Dropdown id="ratingCode" label="级别" listItem=ratingCodes ngModel="customer.ratingCode" textField="" valueField="" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="bizGroup">业务组
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <select kendo-drop-down-list ng-model="customer.bizGroupCode" id="bizGroup"
                                        required="required" name="bizGroup">
                                    <option value="">请选择...</option>
                                <#list businessGroups as item>
                                    <option value="${item.code}">${item.name}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4">联系人
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="customer.contactMan" name="contactMan"
                                       required="required"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <@form.MaskText id="contactPhone" name="contactPhone" label="联系电话" ngModel="customer.contactPhone" required="required" mask="000-0000-0000" pattern="1[35678]\\d{1}-\\d{4}-\\d{4}" />
                            <#--<label class="control-label col-sm-4">联系电话
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="customer.contactPhone"
                                       name="contactPhone" required="required"/>
                            </div>-->
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4">全名</label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="customer.fullName" name="fullName"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2">联系地址
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-10">
                                <input type="text" class="k-textbox" required="required"
                                       ng-model="customer.contactAddress" name="contactAddress"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group  col-sm-12">
                            <label class="control-label col-sm-2">备注</label>
                            <div class="col-sm-10">
                                <textarea class="k-textbox" ng-model="customer.remark" name="remark"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div><h4>品牌</h4></div>
                    </div>
                    <div class="row">
                        <div kendo-grid k-data-source="dataSource" k-editable="true" k-toolbar="toolbar"
                             k-columns="columns" id="brands" name="brands"></div>
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

