<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updateStore" class="modal fade " role="basic" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateStoreForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;修改门店</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="owner">客户
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <select id="owner" kendo-combo-box k-filter="'contains'" ng-model="store.ownerCode"
                                        required="required" name="owner" readonly="readonly">
                                    <option value="">请选择...</option>
                                <#list customer as item>
                                    <option value="${item.value}">${item.text}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4">编码
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="store.code" required="required"
                                       readonly="readonly" name="code" pattern="[A-Za-z0-9_-]+"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4">名称
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="store.name" required="required"
                                       name="name"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4">联系人
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="store.contactMan" required="required"
                                       name="contactMan"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.MaskText id="telephoneNo" label="电话号" ngModel="store.telephoneNo" pattern="\\d{3}-\\d{4}" mask="000-0000"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.MaskText id="mobilePhoneNo" label="手机号" ngModel="store.mobilePhoneNo" required="required" pattern="1[3|4|5|7|8]\\d{1}-\\d{4}-\\d{4}" mask="000-0000-0000"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="brandsFoUpdate">品牌
                            </label>
                            <div class="col-sm-8">
                                <#--<select kendo-multi-select multiple="multiple" k-auto-close="false"
                                        ng-model="store.brandCodes" k-options="selectOptions" id="brandsFoUpdate"
                                        name="brands"/>-->
                                <select kendo-combo-box ng-model="store.brandCode" id="brandsFoUpdate" name="brands"
                                        k-placeholder="'选择品牌'"
                                        k-data-text-field="'text'"
                                        k-data-value-field="'value'"
                                        k-filter="'contains'"
                                        k-auto-bind="true"
                                        k-data-source="brandsDataSource">
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="districtCode">地址
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-10">
                                <input type="text" kendo-district-select k-province="'#province'" k-city="'#city'" k-area="'#area'" k-street="'#street'" class="k-textbox" ng-model="store.districtCode" required="required"
                                       name="districtCode" id="districtCode" />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="province">省
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" id="province" ng-model="store.province" required="required"
                                       name="province"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="city">市
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8 form-control-static">
                                <input type="text" class="k-textbox" ng-model="store.city" id="city" required="required"
                                       name="city"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="area">区/县</label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="store.area" id="area"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="street">街道</label>
                            <div class="col-sm-8 form-control-static">
                                <input type="text" class="k-textbox" id="street" ng-model="store.street"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2">地址行
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-10">
                                <input type="text" class="k-textbox" required="required" ng-model="store.address"
                                       name="address"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4">是否启用</label>
                            <div class="col-sm-8 form-control-static">
                                <input type="checkbox" ng-model="store.active" checked="checked" disabled="disabled"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group  col-sm-12">
                            <label class="control-label col-sm-2">备注</label>
                            <div class="col-sm-10">
                                <textarea class="k-textbox" ng-model="store.remark"/>
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