<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updatePrice" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updatePriceForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;添加运输报价</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown label="报价类型" ngChange="typeChange()" id='ownerType' valueField="" ngModel='price.ownerType' listItem=ownerTypes required="required" />
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.ComboBox label="报价对象" id='customer' ngModel='price.ownerCode' required="required" listItem=null options="ownerOptions"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.ComboBox id='expenseAccount' listItem=null options="exacctOptions" required="required" ngModel="price.expenseAccount" label="费用科目" />
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown label="运输方式" id='transportType' valueField="" ngModel='price.transportType' required="required" listItem=transportTypes/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                        <@form.DistrictSelect  id="orgin" ngModel="price.orgin" label="起始地" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                        <@form.DistrictSelect id="destination" ngModel="price.destination" label="目的地" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown label="订单类型" id='orderType' valueField="" ngModel='price.orderType' listItem=orderTypes/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id='productCategory' ngModel="price.productCategory" label="产品分类" />
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown label="计费公式" id='calcFormula' valueField="" ngModel='price.calcFormula' required="required" listItem=calcFormulas/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown label="计费属性" id='calcAttr' valueField="" ngModel='price.calcAttr' required="required" listItem=calcAttrs/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id='vehicleType' listItem=vehicleTypes valueField="vehicleTypeId" textField="name" ngModel="price.vehicleType" label="车型" />
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Textarea id='remark' ngModel="price.remark" label="备注" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6" style="padding-left: 20px;">
                            <button class="k-button k-primary" type="button" ng-click="addRow()"><i class="fa fa-plus"></i>增加</button>
                        </div>
                    </div>
                    <div class="table-scrollable">
                        <table class="table table-striped table-hover table-bordered dataTable no-footer">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>最低收费</th>
                                <th>区间最小值</th>
                                <th>区间最大值</th>
                                <th>单价</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="item in price.rangeDto">
                                <td>{{$index + 1}}</td>
                                <td><input type="number" name="minAmount{{$index}}" required="required" min="0" k-decimals="'2'" k-format="'0.00'"
                                           ng-model="item.minAmount" kendo-numeric-text-box></td>
                                <td><input type="number" name="rangeStart{{$index}}" required="required" min="0" k-decimals="'2'" k-format="'0.00'"
                                           ng-model="item.rangeStart" kendo-numeric-text-box></td>
                                <td><input type="number" name="rangeEnd{{$index}}" placeholder="'不填表示没有上限'" min="0" k-decimals="'2'" k-format="'0.00'"
                                           ng-model="item.rangeEnd" kendo-numeric-text-box></td>
                                <td><input type="number" name="unitPrice{{$index}}" required="required" min="0" k-decimals="'2'" k-format="'0.00'"
                                           ng-model="item.unitPrice" kendo-numeric-text-box></td>
                                <td><button class="k-button" ng-click="removeRow($index)"><i class="fa fa-remove"></i>删除</button></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">
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