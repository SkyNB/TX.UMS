<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="addGoodsArchives" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="addGoodsArchivesForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;创建商品档案</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.ComboBox id="customer" label="客户" ngModel="goodsArchives.customerCode"<#--ngName="goodsArchives.customerName"--> listItem=null options="customOptions" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id="code" label="编码" ngModel="goodsArchives.code" pattern="[a-zA-Z0-9_+]+" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="name" label="名称" ngModel="goodsArchives.name" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id="model" label="型号" ngModel="goodsArchives.model" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Dropdown id="category" label="分类" listItem=categoryEnum ngModel="goodsArchives.category" required="required" valueField=""/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="length" label="长" ngModel="goodsArchives.length" decimals=4 format="0.0000m" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="width" label="宽" ngModel="goodsArchives.width" decimals=4 format="0.0000m" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="height" label="高" ngModel="goodsArchives.height" decimals=4 format="0.0000m" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="weight" label="重量" ngModel="goodsArchives.weight" decimals=4 format="0.0000kg" required="required"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.NumberBox id="price" label="价格" ngModel="goodsArchives.price" decimals=2 format="￥0.00"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="brand" label="品牌" ngModel="goodsArchives.brand" />
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id="color" label="颜色" ngModel="goodsArchives.color"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="habitat" label="产地" ngModel="goodsArchives.habitat" />
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Textarea id="describe" label="描述" ngModel="goodsArchives.describe"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Textarea id="remark" label="备注" ngModel="goodsArchives.remark" />
                        </div>
                    </div>

                    <div class="row">
                        <div><h4>识别码</h4></div>
                    </div>
                    <div class="row">
                        <div kendo-grid k-data-source="codesDataSource" k-editable="true" k-toolbar="codesToolbar"
                             k-columns="codesColumns" id="identificationCodes" name="identificationCodes"></div>
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