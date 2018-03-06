<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="addProject" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="addProjectForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;添加项目</h4>
                </div>
                <div class="modal-body">
                    <div id="rootwizard">
                        <ul class="nav nav-pills nav-justified steps">
                            <li>
                                <a data-toggle="tab" href="#tab1">
                                    <span class="number"> 1 </span>基础信息
                                </a>
                            </li>
                            <li>
                                <a data-toggle="tab" href="#tab2">
                                    <span class="number"> 2 </span>计费信息
                                </a>
                            </li>
                            <li>
                                <a data-toggle="tab" href="#tab3">
                                    <span class="number"> 3 </span>轻重货比
                                </a>
                            </li>
                            <li>
                                <a data-toggle="tab" href="#tab4">
                                    <span class="number"> 4 </span>其它
                                </a>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <hr/>
                            <div class="tab-pane" id="tab1">
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.Text id="code" label="编码" ngModel="project.code" required="required" pattern="[A-Za-z0-9_-]+"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.Text id="name" label="名称" ngModel="project.name" required="required"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.ComboBox id="customer" label="客户" ngModel="project.customerCode" listItem=customers textField="text" valueField="value" required="required"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-12">
                                        <label class="control-label col-sm-2">备注</label>
                                        <div class="col-sm-10">
                                            <textarea name="remark" class="k-textbox" ng-model="project.remark" rows="3"/>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" id="tab2">
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.Dropdown id="settleCycle" label="结算周期" listItem=settleCycle valueField="" ngModel="project.settleCycle"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.Dropdown id="paymentType" label="支付方式" listItem=paymentType valueField="" ngModel="project.paymentType"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.Dropdown id="calculateType" label="计费方式" listItem=calculateType valueField="" ngModel="project.calculateType"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.Dropdown id="handoverType" label="交接方式" listItem=handoverType valueField="" ngModel="project.handoverType"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.Dropdown id="receivableDataSource" label="应收重量体积来源" listItem=receivableDataSource valueField="" ngModel="project.receivableDataSource"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="packCoefficient" label="打包系数"  ngModel="project.packCoefficient" min="1" format="0.000"/>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" id="tab3">
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="lightGoods" label="轻货(kg/m³)"  ngModel="project.lightGoods" min="250" format="0.000"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="lightThrowGoods" label="轻抛货(kg/m³)"  ngModel="project.lightThrowGoods" min="250" format="0.000"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="commonGoods" label="普通货(kg/m³)"  ngModel="project.commonGoods" min="250" format="0.000"/>
                                    </div>
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="heavyThrowGoods" label="重抛货(kg/m³)"  ngModel="project.heavyThrowGoods" min="250" format="0.000"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-sm-6">
                                    <@form.NumberBox id="heavyGoods" label="重货(kg/m³)"  ngModel="project.heavyGoods" min="250" format="0.000"/>
                                    </div>
                                </div>
                            </div>

                            <div class="tab-pane" id="tab4">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="portlet light bordered">
                                            <div class="portlet-title tabbable-line">
                                                <ul class="nav nav-tabs">
                                                    <li class="active">
                                                        <a href="#businessTypeTab" data-toggle="tab">业务类型</a>
                                                    </li>
                                                    <li>
                                                        <a href="#boxTypeTab" data-toggle="tab">箱型</a>
                                                    </li>
                                                    <li>
                                                        <a href="#cargoTypeTab" data-toggle="tab">货物类型</a>
                                                    </li>
                                                </ul>
                                            </div>

                                            <div class="portlet-body">
                                                <div class="tab-content">
                                                    <div class="tab-pane active" id="businessTypeTab">
                                                        <div kendo-grid k-data-source="businessTypeDataSource"
                                                             k-editable="true"
                                                             k-toolbar="toolbar"
                                                             k-columns="businessTypeColumns" id="businessType"
                                                             name="businessType"></div>
                                                    </div>
                                                    <div class="tab-pane" id="boxTypeTab">
                                                        <div kendo-grid k-data-source="boxTypeDataSource" k-editable="true"
                                                             k-toolbar="toolbar"
                                                             k-columns="boxTypeColumns" id="boxType" name="boxType"></div>
                                                    </div>
                                                    <div class="tab-pane" id="cargoTypeTab">
                                                        <div kendo-grid k-data-source="cargoTypeDataSource"
                                                             k-editable="true"
                                                             k-toolbar="toolbar"
                                                             k-columns="carGoTypeColumns" id="cargoType"
                                                             name="cargoType"></div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <ul class="pager wizard">
                            <#--<li class="previous first"><a href="#">首页</a></li>-->
                                <li class="previous"><a href="#"><i class="fa fa-angle-left"></i>上一页</a></li>
                            <#--<li class="next last"><a href="#">末页</a></li>-->
                                <li class="next"><a href="#">下一页<i class="fa fa-angle-right"></i></a></li>

                                <li><a href="#" class="btn btn-primary" ng-click="submit();" ng-show="showSubmitBtn">
                                    <i class="fa fa-check"></i>&nbsp;确定
                                </a></li>
                            </ul>
                            <#--<ul class="pager wizard">
                                <li class="previous first" style="display:none;"><a href="#">First</a></li>
                                <li class="previous"><a href="#">上一步</a></li>
                                <li class="next last" style="display:none;"><a href="#">Last</a></li>
                                <li class="next"><a href="#">下一步</a></li>
                            </ul>-->
                        </div>
                    </div>
                </div>

                <#--<div>
                    <div class="modal-footer">
                        <button type="button" tabindex="-1" class="k-button" data-dismiss="modal">
                            <i class="fa fa-close"></i>&nbsp;取消
                        </button>
                        <button type="submit" id="submit" class="k-button k-primary">
                            <i class="fa fa-check"></i>&nbsp;确定
                        </button>
                    </div>
                </div>-->
            </form>
        </div>
    </div>
</div>

