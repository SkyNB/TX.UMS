<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="calculate" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="calculateForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;计算报价</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.ComboBox label="客户/承运商" id='customer' ngModel='price.ownerCode' required="required" listItem=null options="ownerOptions"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Dropdown label="运输方式" id='transportType' valueField="" ngModel='price.transportType' required="required" listItem=transportTypes/>
                        </div>
                    </div>
                    <div class="row">
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
                        <@form.Dropdown label="计费属性" id='calcAttr' valueField="" ngModel='price.calcAttr' required="required" listItem=calcAttrs/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.NumberBox label="计算值" id='value'ngModel='price.remark' required="required" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id='vehicleType' listItem=vehicleTypes placeholder="请选择..." valueField="vehicleTypeId" textField="name" ngModel="price.vehicleType" label="车型" />
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id='productCategory' ngModel="price.productCategory" label="产品分类" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                        <@form.Textarea id='result'  ngModel="result" label="计算结果" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">
                        <i class="fa fa-check"></i>&nbsp;计算费用
                    </button>
                    <button type="button" tabindex="-1" class="btn btn-danger" data-dismiss="modal">
                        <i class="fa fa-close"></i>&nbsp;取消
                    </button>

                </div>
            </form>
        </div>
    </div>
</div>