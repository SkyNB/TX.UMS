<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updateVehicleType" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateVehicleTypeForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;修改车辆类型</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="name" label="名称" ngModel="vehicleType.name" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="length" label="长(m)" ngModel="vehicleType.length" required="required" min="0"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="width" label="宽(m)" ngModel="vehicleType.width" required="required" min="0"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="height" label="高(m)" ngModel="vehicleType.height" required="required" min="0"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="loadVolume" label="荷载体积" ngModel="vehicleType.loadVolume" min="0" format="0.000000m³"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="loadWeight" label="荷载重量" ngModel="vehicleType.loadWeight" min="0" format="0.0000t"/>
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