<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="updateBusinessGroup" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="updateBusinessGroupForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;修改业务组</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Text id="code" label="编码" ngModel="businessGroup.code" required="required" pattern="[A-Za-z0-9_-]+" readonly="readonly"/>
                        </div>
                        <div class="form-group col-sm-6">
                        <@form.Text id="name" label="名称" ngModel="businessGroup.name" required="required"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                        <@form.Textarea id="remark" label="备注" ngModel="businessGroup.remark"/>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-12">
                            <div><h4>客户列表</h4></div>
                            <div class="table-scrollable">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>编码</th>
                                        <th>名称</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <#list cusForBiz as item>
                                    <tr>
                                        <td>${item_index+1}</td>
                                        <td>${item.code}</td>
                                        <td>${item.name}</td>
                                    </tr>
                                    </#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>
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
<script type="text/x-kendo-angular-template" id="template"></script>