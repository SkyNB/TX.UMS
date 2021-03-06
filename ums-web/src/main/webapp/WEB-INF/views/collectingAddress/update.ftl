<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updateCollectingAddress" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateCollectingAddressForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;修改提货地址</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <@form.ComboBox id="customser" label="客户" listItem=customers ngModel="collectingAddress.ownerCode" required="required" readonly="readonly"/>
                        </div>
                        <div class="form-group col-sm-6">
                            <@form.Dropdown id="type" label="类型" listItem=type ngModel="collectingAddress.type" required="required" valueField=""/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <@form.Text id="code" label="编码" ngModel="collectingAddress.code" required="required" pattern="[A-Za-z0-9_-]+" readonly="readonly"/>
                        </div>
                        <div class="form-group col-sm-6">
                            <@form.Text id="name" label="名称" ngModel="collectingAddress.name" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <@form.Text id="contactMan" label="联系人" ngModel="collectingAddress.contactMan" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                            <@form.MaskText id="telephoneNo" label="电话号" ngModel="collectingAddress.telephoneNo" pattern="\\d{3}-\\d{4}" mask="000-0000"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <@form.MaskText id="mobilePhoneNo" label="手机号" ngModel="collectingAddress.mobilePhoneNo" required="required" pattern="1[3|4|5|7|8]\\d{1}-\\d{4}-\\d{4}" mask="000-0000-0000"/>
                        </div>
                        <div class="form-group col-sm-6">
                            <@form.CheckBox id="active" label="是否启用" ngModel="collectingAddress.active" disabled="disabled"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-12">
                        <@form.DistrictSelect label="地址" id="districtCode" required="required" province="#province" city="#city" area="#area" street="#street" ngModel="collectingAddress.districtCode" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <@form.Text id="province" label="省" ngModel="collectingAddress.province" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                            <@form.Text id="city" label="市" ngModel="collectingAddress.city" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <@form.Text id="area" label="区/县" ngModel="collectingAddress.area"/>
                        </div>
                        <div class="form-group col-sm-6">
                            <@form.Text id="street" label="街道" ngModel="collectingAddress.street"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2">地址行
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-10">
                                <input type="text" class="k-textbox" required="required" ng-model="collectingAddress.address"
                                       name="address"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group  col-sm-12">
                            <label class="control-label col-sm-2">备注</label>
                            <div class="col-sm-10">
                                <textarea class="k-textbox" ng-model="collectingAddress.remark"/>
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