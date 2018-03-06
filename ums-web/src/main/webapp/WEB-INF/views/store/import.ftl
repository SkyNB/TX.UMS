<#--
<div id="importStore" class="modal fade " role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="importStoreForm" class="form-horizontal" ng-submit="submit()" role="form"
                  enctype="multipart/form-data" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-file-text-o"></i>&nbsp;导入门店信息</h4>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <h4 align="center"><a href="${request.contextPath}/store/downLoad?fileName=store.xlsx">下载导入模板</a>
                            </h4>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label class="control-label col-sm-3">选择数据文件
                                <span class="required" aria-required="true"> * </span>
                            </label>
                            <div class="col-sm-9">
                                <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                                    <div class="form-control" data-trigger="fileinput">
                                        <i class="glyphicon glyphicon-file fileinput-exists"></i> <span
                                            class="fileinput-filename"></span></div>
                            <span class="input-group-addon btn btn-default btn-file">
                                <span class="fileinput-new">选择文件</span>
                                <span class="fileinput-exists">替换</span>
                                <input type="file" name="storeFile"
                                       accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/msexcel"
                                       required="required">
                            </span>
                                    <a href="#" class="input-group-addon btn btn-default fileinput-exists"
                                       data-dismiss="fileinput">删除</a>
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

<link href="${request.contextPath}/resources/global/plugins/bootstrap/css/bootstrap-fileinput.css" rel="stylesheet"
      type="text/css"/>
<script src="${request.contextPath}/resources/global/plugins/bootstrap/js/bootstrap-fileinput.js"
        type="text/javascript"></script>
<script src="${request.contextPath}/resources/global/scripts/jquery.form.min.js" type="text/javascript"></script>
-->
<div id="importStore" class="modal fade" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="importStoreForm" class="form-horizontal" ng-submit="submit()" role="form"
                  enctype="multipart/form-data" method="post">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-file-text-o"></i>&nbsp;导入门店信息</h4>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <h4 align="center"><a href="${request.contextPath}/store/downLoad?fileName=store.xlsx">下载导入模板</a>
                            </h4>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-sm-12">
                            <labe class="control-label col-sm-3">选择数据文件
                                <span class="required" aria-required="true"> * </span>
                            </labe>
                            <div class="col-sm-9">
                                <div class="fileinput fileinput-new input-group" data-provides="fileinput">
                                    <div class="form-control" data-trigger="fileinput">
                                        <i class="glyphicon glyphicon-file fileinput-exists"></i> <span
                                            class="fileinput-filename"></span></div>
                            <span class="input-group-addon  btn-default btn-file">
                                <span class="fileinput-new">选择文件</span>
                            <#--     <span class="fileinput-exists">替换</span>-->
                                <input type="file" name="storeFile" required="required">
                            </span>
                                <#--   <a href="#" class="input-group-addon btn btn-default fileinput-exists"
                                      data-dismiss="fileinput">删除</a>-->
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div kendo-grid k-options="errorOptions"></div>
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

<link href="${request.contextPath}/resources/global/plugins/bootstrap/css/bootstrap-fileinput.css" rel="stylesheet"
      type="text/css"/>
<script src="${request.contextPath}/resources/global/plugins/bootstrap/js/bootstrap-fileinput.js" type="text/javascript"></script>
<script src="${request.contextPath}/resources/global/scripts/jquery.form.min.js" type="text/javascript"></script>
