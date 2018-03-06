<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updateCarrier" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateCarrierForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;修改承运商</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="code" label="编码" ngModel="carrier.code" required="required" readonly="readonly" pattern="[A-Za-z0-9_-]+"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id="name" label="名称" ngModel="carrier.name" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="contactMan" label="联系人" ngModel="carrier.contactMan" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.MaskText id="telephoneNo" label="电话号码" ngModel="carrier.telephoneNo" pattern="\\d{3}-\\d{4}" mask="000-0000"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.MaskText id="mobilephoneNo" label="手机号码" ngModel="carrier.mobilephoneNo" required="required" pattern="1[35678]\\d{1}-\\d{4}-\\d{4}" mask="000-0000-0000"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="branchCode" label="所属分公司" ngModel="carrier.branchCode" listItem=branches required="required" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="settleCycle" label="结算周期" ngModel="carrier.settleCycle" listItem=settleCycle valueField="" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="paymentType" label="支付方式" ngModel="carrier.paymentType" listItem=paymentType valueField="" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="calculateType" label="计费方式" ngModel="carrier.calculateType" listItem=calculateType valueField="" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="transportType" label="运输方式" ngModel="carrier.transportType" listItem=transportType valueField="" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="type" label="承运商类型" ngModel="carrier.type" listItem=carrierType valueField="" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="districtCode">地址
                            </label>
                            <div class="col-sm-10">
                                <input type="text" kendo-district-select k-province="'#province'" k-city="'#city'"
                                       k-area="'#area'" k-street="'#street'" class="k-textbox"
                                       ng-model="carrier.districtCode"
                                       name="districtCode" id="districtCode"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="province">省
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" id="province" ng-model="carrier.province"
                                       name="province"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="city">市
                            </label>
                            <div class="col-sm-8 form-control-static">
                                <input type="text" class="k-textbox" ng-model="carrier.city" id="city" name="city"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="area">区/县</label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="carrier.area" id="area"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="street">街道</label>
                            <div class="col-sm-8 form-control-static">
                                <input type="text" class="k-textbox" id="street" ng-model="carrier.street"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2">详细地址</label>
                            <div class="col-sm-10">
                                <input type="text" class="k-textbox" id="address" ng-model="carrier.address"
                                       name="address"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                        <@form.Textarea id="remark" label="备注" ngModel="carrier.remark"/>
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