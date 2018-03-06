<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="notificationDetail" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="notificationDetailForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;通知信息</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <div class="col-sm-12">
                            <@form.Text label="主题" id="subject" ngModel="notification.subject" maxLength="50" required="required" readonly="readonly"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <div class="col-sm-12">
                            <@form.Textarea label="内容" id="body" ngModel="notification.body" maxLength="1000" required="required" readonly="readonly"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <div class="col-sm-12">
                            <@form.MultiSelect label="接收人" id="receivers" ngModel="notification.receivers" required="required" readonly="readonly" listItem=users/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="sender">发送人</label>
                            <div class="col-sm-8">
                                <span id="sender">{{notification.sender}}</span>
                            </div>
                        </div>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4" for="sentTime">发送时间</label>
                            <div class="col-sm-8">
                                <span id="sentTime">{{notification.sentTime}}</span>
                            </div>
                        </div>
                    </div>
                    <div style="visibility:hidden">
                        <input type="text" id="readId" ng-model="notification.id"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" id="submit" class="btn btn-danger">
                        <i class="fa fa-close"></i>&nbsp;撤销
                    </button>
                    <button type="button" tabindex="-1" class="btn btn-primary" data-dismiss="modal">
                        <i class="fa fa-check"></i>&nbsp;确定
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>