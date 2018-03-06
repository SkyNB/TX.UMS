<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="addVehicle" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="addVehicleForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;添加车辆信息</h4>
                </div>
                <div class="modal-body">
                    <div id="rootwizard">
                        <ul class="nav nav-pills nav-justified steps">
                            <li>
                                <a data-toggle="tab" href="#tab1">
                                    <span class="number"> 1 </span>车辆信息
                                </a>
                            </li>
                            <li>
                                <a data-toggle="tab" href="#tab2">
                                    <span class="number"> 2 </span>司机信息
                                </a>
                            </li>
                            <li>
                                <a data-toggle="tab" href="#tab3">
                                    <span class="number"> 3 </span>其它信息
                                </a>
                            </li>
                        </ul>

                        <div class="tab-content">
                            <hr>

                            <div class="tab-pane" id="tab1">
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.Text id="vehicleNoForCreate" label="车牌号" ngModel="vehicle.vehicleNo" required="required" pattern="[\\u4e00-\\u9fa5][A-Z][A-Z_0-9]{5}"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.Dropdown id="vehicleType" label="车辆类型" ngModel="vehicle.vehicleTypeId" listItem=vehicleTypes ngChange="getVehicleType()" placeholder="-------请选择-------"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="length" label="长" ngModel="vehicle.length" min="0" format="0.00m"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="width" label="宽" ngModel="vehicle.width" min="0" format="0.00m"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="height" label="高" ngModel="vehicle.height" min="0" format="0.00m"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="loadVolume" label="荷载体积" ngModel="vehicle.loadVolume" min="0" format="0.000000m³"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="loadWeight" label="荷载重量" ngModel="vehicle.loadWeight" min="0" format="0.0000t"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.DatePicker id="buyTime" label="购车日期" ngModel="vehicle.buyTime" required="required"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.DatePicker id="contractEffectiveDate" label="合同生效日期" ngModel="vehicle.contractEffectiveDate" required="required"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.DatePicker id="contractTerminationDate" label="合同终止日期" ngModel="vehicle.contractTerminationDate" required="required"/>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" id="tab2">
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.Text id="driverForCreate" label="司机" ngModel="vehicle.driver" required="required"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.MaskText id="identityCardNo" label="身份证号码" ngModel="vehicle.identityCardNo" required="required" mask="000000-00000000-0000" pattern="\\d{6}-\\d{8}-\\d{4}"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.MaskText id="driverMobileForCreate" label="手机号" ngModel="vehicle.driverMobile" required="required" mask="000-0000-0000" pattern="1[3|5|6|7|8]\\d-\\d{4}-\\d{4}"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.Text id="drivingLicence" label="驾驶证" ngModel="vehicle.drivingLicence" required="required"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.Text id="vehicleRegistractionNo" label="行驶证" ngModel="vehicle.vehicleRegistractionNo" required="required"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.Text id="emergencyContact" label="紧急联系人" ngModel="vehicle.emergencyContact" required="required"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.MaskText id="emergencyContactMobile" label="紧急联系人电话" ngModel="vehicle.emergencyContactMobile" required="required" mask="000-0000-0000" pattern="1[35678]\\d-\\d{4}-\\d{4}"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-12">
                                        <label class="control-label col-sm-2" for="districtCode">地址
                                        </label>
                                        <div class="col-sm-10">
                                            <input type="text" kendo-district-select k-province="'#province'" k-city="'#city'"
                                                   k-area="'#area'" k-street="'#street'" class="k-textbox"
                                                   ng-model="vehicle.districtNo"
                                                   name="districtNo" id="districtNo"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                        <label class="control-label col-sm-4" for="province">省
                                        </label>
                                        <div class="col-sm-8">
                                            <input type="text" class="k-textbox" id="province" ng-model="vehicle.province" name="province"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-6">
                                        <label class="control-label col-sm-4" for="city">市
                                        </label>
                                        <div class="col-sm-8 form-control-static">
                                            <input type="text" class="k-textbox" ng-model="vehicle.city" id="city" name="city"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                        <label class="control-label col-sm-4" for="area">区/县</label>
                                        <div class="col-sm-8">
                                            <input type="text" class="k-textbox" ng-model="vehicle.area" id="area"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-6">
                                        <label class="control-label col-sm-4" for="street">街道</label>
                                        <div class="col-sm-8 form-control-static">
                                            <input type="text" class="k-textbox" id="street" ng-model="vehicle.street"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="form-group col-sm-12">
                                        <label class="control-label col-sm-2">详细地址</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="k-textbox" id="address" ng-model="vehicle.address"
                                                   name="address"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" id="tab3">
                                <!--
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <#--<@form.ComboBox id="branchCode" label="分公司" ngModel="vehicle.branchCode" required="required" listItem=branches ngChange="getSite()"/>-->
                                    </div>
                                    <div class="form-group col-sm-6">
                                        <label class="control-label col-sm-4" for="sites">站点
                                            <span class="required" aria-required="true"> * </span>
                                        </label>
                                        <div class="col-sm-8">
                                            <select kendo-combo-box ng-model="vehicle.siteCode" id="siteCode" name="siteCode"
                                                    k-placeholder="'选择站点'"
                                                    k-data-text-field="'name'"
                                                    k-data-value-field="'code'"
                                                    k-filter="'contains'"
                                                    k-auto-bind="true"
                                                    k-data-source="sitesDataSource" required="required">
                                            </select>
                                        </div>
                                    <#--<@form.ComboBox id="siteCode" label="站点" ngModel="vehicle.siteCode" required="required" listItem=null textField="name" valueField="code"/>-->
                                    </div>
                                </div>
                                -->

                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.Dropdown id="leaseTypeForCreate" label="租赁方式" ngModel="vehicle.leaseType" required="required" listItem=leaseType textField="text" valueField=""/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.Dropdown id="paymentType" label="结算方式" ngModel="vehicle.paymentType" required="required" listItem=paymentTypes textField="text" valueField=""/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <ul class="pager wizard">
                        <#--<li class="previous first"><a href="#">首页</a></li>-->
                            <li class="previous"><a href="#"><i class="fa fa-angle-left"></i>上一页</a></li>
                        <#--<li class="next last"><a href="#">末页</a></li>-->
                            <li class="next"><a href="#">下一页<i class="fa fa-angle-right"></i></a></li>

                            <li><button type="button" class="btn btn-primary" ng-click="submit();" ng-show="showSubmitBtn">
                                <i class="fa fa-check"></i>&nbsp;确定
                            </button></li>
                        </ul>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>