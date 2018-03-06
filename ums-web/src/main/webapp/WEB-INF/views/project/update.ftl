<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updateProject" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateProjectForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;修改项目</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.ComboBox id="customer" label="客户" ngModel="project.customerCode" listItem=customers textField="text" valueField="value" readonly="readonly" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.ComboBox id="branch" label="分公司" ngModel="project.branchCode" listItem=branches textField="text" valueField="value" readonly="readonly" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="code" label="编码" ngModel="project.code" readonly="readonly" required="required" pattern="[A-Za-z0-9_-]+"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id="name" label="名称" ngModel="project.name" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="settleCycle" label="结算周期" listItem=settleCycle valueField="" ngModel="project.settleCycle" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="paymentType" label="支付方式" listItem=paymentType valueField="" ngModel="project.paymentType" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="calculateType" label="计费方式" listItem=calculateType valueField="" ngModel="project.calculateType" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="handoverType" label="交接方式" listItem=handoverType valueField="" ngModel="project.handoverType" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="receivableDataSource" label="应收重量体积来源" listItem=receivableDataSource valueField="" ngModel="project.receivableDataSource" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="packCoefficient" label="打包系数"  ngModel="project.packCoefficient" required="required" min="1" format="0.000"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="lightGoods" label="轻货(kg/m³)"  ngModel="project.lightGoods" required="required" min="250" format="0.000"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="lightThrowGoods" label="轻抛货(kg/m³)"  ngModel="project.lightThrowGoods" required="required" min="250" format="0.000"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="commonGoods" label="普通货(kg/m³)"  ngModel="project.commonGoods" required="required" min="250" format="0.000"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="heavyThrowGoods" label="重抛货(kg/m³)"  ngModel="project.heavyThrowGoods" required="required" min="250" format="0.000"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="heavyGoods" label="重货(kg/m³)"  ngModel="project.heavyGoods" required="required" min="250" format="0.000"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.CheckBox id="active" label="是否启用" ngModel="project.active" disabled="disabled"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="createUserName">创建人</label>
                            <div class="col-sm-8">
                                <span id="createUserName">{{project.createUserName}}</span>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="createTime">创建时间</label>
                            <div class="col-sm-8">
                                <span id="createTime">{{project.createTime}}</span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="modifyUserName">修改人</label>
                            <div class="col-sm-8">
                                <span id="modifyUserName">{{project.modifyUserName}}</span>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="modifyTime">修改时间</label>
                            <div class="col-sm-8">
                                <span id="modifyTime">{{project.modifyTime}}</span>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-2">备注</label>
                            <div class="col-sm-10">
                                <textarea class="k-textbox" ng-model="project.remark" rows="3"/>
                            </div>
                        </div>
                    </div>
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
                            </div>
                            <div class="portlet-body">
                                <div class="tab-content">
                                    <div class="tab-pane active" id="businessTypeTab">
                                        <div kendo-grid k-data-source="businessTypeDataSource" k-editable="true"
                                             k-toolbar="toolbar"
                                             k-columns="businessTypeColumns" id="businessTypeF"
                                             name="businessType"></div>
                                    </div>
                                    <div class="tab-pane" id="boxTypeTab">
                                        <div kendo-grid k-data-source="boxTypeDataSource" k-editable="true"
                                             k-toolbar="toolbar"
                                             k-columns="boxTypeColumns" id="boxTypeF" name="boxType"></div>
                                    </div>
                                    <div class="tab-pane" id="cargoTypeTab">
                                        <div kendo-grid k-data-source="cargoTypeDataSource" k-editable="true"
                                             k-toolbar="toolbar"

                                             k-columns="carGoTypeColumns" id="cargoTypeF" name="cargoType"></div>
                                    </div>
                                </div>
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

