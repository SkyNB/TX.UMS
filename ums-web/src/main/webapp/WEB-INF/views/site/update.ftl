<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updateSite" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateSiteForm" class="form-horizontal" ng-submit="submit()" data-sitecodesite="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;添加站点</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="customerCode">分公司
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input kendo-combo-box k-options="branchOptions" ng-model="site.branchCode" required="required" name="branchCode" readonly="readonly">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="code">编码
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="site.code" required="required" pattern="[a-zA-Z0-9_-]+" name="code" readonly="readonly"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="name">名称
                                <span class="required" aria-required="true"> * </span>
                            </label>

                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="site.name" required="required" name="name"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="contacts">联系人
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="site.contacts" name="contacts" required="required"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.MaskText id="contactPhone" name="contactPhone" label="手机号码" ngModel="site.contactPhone" required="required" pattern="1[35678]\\d{1}-\\d{4}-\\d{4}" mask="000-0000-0000"/>
                            <#--<label class="control-label col-sm-4" for="contactPhone">联系电话
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="site.contactPhone" name="contactPhone" required="required"/>
                            </div>-->
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="districtCode">地址
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-10">
                                <input type="text" kendo-district-select k-province="'#province'" k-city="'#city'"
                                       k-area="'#district'" k-street="'#street'" class="k-textbox"
                                       ng-model="site.districtCode" required="required"
                                       name="districtCode" id="districtCode"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="address">详细地址
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-10">
                                <input type="text" class="k-textbox"
                                       ng-model="site.address" required="required"
                                       name="address" id="address"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="province">省
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="site.province" name="province" id="province"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="city">市
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="site.city" name="city" id="city"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="district">区
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="site.district" name="district" id="district"/>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="street">街
                            </label>
                            <div class="col-sm-8">
                                <input type="text" class="k-textbox" ng-model="site.street" name="street" id="street"/>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2" for="remark">备注</label>
                            <div class="col-sm-10">
                                <textarea class="k-textbox" name="remark" ng-model="site.remark" rows="3"></textarea>
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