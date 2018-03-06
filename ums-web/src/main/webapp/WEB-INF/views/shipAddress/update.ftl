<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updateShipAddress" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateShipAddressForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;修改发运地址</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="code" label="编码" ngModel="shipAddress.code" required="required" readonly="readonly" pattern="[A-Za-z0-9_-]+"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id="name" label="名称" ngModel="shipAddress.name" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="contactMan" label="联系人" ngModel="shipAddress.contactMan" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.MaskText id="telephoneNo" label="电话号码" ngModel="shipAddress.telephoneNo" pattern="\\d{3}-\\d{4}" mask="000-0000"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.MaskText id="mobilePhoneNo" label="手机号码" ngModel="shipAddress.mobilePhoneNo" required="required" pattern="1[35678]\\d{1}-\\d{4}-\\d{4}" mask="000-0000-0000"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="carrierCodeForUpdate" label="所属承运商" ngModel="shipAddress.carrierCode" listItem=carriers required="required"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="districtCode">地址
                            </label>
                            <div class="col-sm-10">
                                <input type="text" kendo-district-select k-province="'#province'" k-city="'#city'"
                                       k-area="'#area'" k-street="'#street'" class="k-textbox"
                                       ng-model="shipAddress.districtCode"
                                       name="districtCode" id="districtCode"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="province">省
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" id="province" ng-model="shipAddress.province"
                                       name="province"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="city">市
                            </label>
                            <div class="col-sm-8 form-control-static">
                                <input type="text" class="k-textbox" ng-model="shipAddress.city" id="city" name="city"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="area">区/县</label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="shipAddress.area" id="area"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="street">街道</label>
                            <div class="col-sm-8 form-control-static">
                                <input type="text" class="k-textbox" id="street" ng-model="shipAddress.street"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2">详细地址</label>
                            <div class="col-sm-10">
                                <input type="text" class="k-textbox" id="address" ng-model="shipAddress.address"
                                       name="address"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                        <@form.Textarea id="remark" label="备注" ngModel="shipAddress.remark"/>
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