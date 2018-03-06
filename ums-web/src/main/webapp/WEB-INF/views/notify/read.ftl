<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="readInfo" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="readInfoForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;阅读通知</h4>
                </div>

                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="sender">发送者：
                            </label>
                            <div class="col-sm-8">
                                <div id="sender" ng-bind="notification.sender"></div>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="senderTime">发送时间：
                            </label>
                            <div class="col-sm-8">
                                <div id="senderTime" ng-bind="notification.sentTime"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="subject">主题：
                            </label>
                            <div class="col-sm-8">
                                <div id="subject" ng-bind="notification.subject"></div>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="body">内容：
                            </label>
                            <div class="col-sm-8">
                                <div id="body" ng-bind="notification.body"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" tabindex="-1" class="btn btn-primary" data-dismiss="modal">
                        <i class="fa fa-check"></i>&nbsp;确定
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
