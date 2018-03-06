<#import "/WEB-INF/layouts/form.ftl" as form/>
<div id="addNotification" class="modal fade in" role="basic" aria-hidden="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form id="addNotificationForm" class="form-horizontal" ng-submit="submit()" data-role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title"><i class="fa fa-plus"></i>&nbsp;添加通知</h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <div class="col-sm-12">
                            <@form.Text label="主题" id="subject" ngModel="notification.subject" maxLength="50" required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <div class="col-sm-12">
                            <@form.Textarea label="内容" id="body" ngModel="notification.body" maxLength="1000" required="required"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <div class="col-sm-12">
                            <@form.MultiSelect label="接收人" id="receivers" ngModel="notification.receivers" required="required" listItem=users/>
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