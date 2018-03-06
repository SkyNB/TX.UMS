<div id="notificationInfo">
    <div class="modal-header">
        <h4><i class="fa fa-edit"></i>&nbsp;通知</h4>
    </div>
    <div class="modal-body">
        <div class="row">
            <div class="form-group col-sm-6">
                <label class="control-label col-sm-4" for="sender">发送者：
                </label>
                <div class="col-sm-8">
                    <div id="sender">${notificationDto.sender}</div>
                </div>
            </div>
            <div class="form-group col-sm-6">
                <label class="control-label col-sm-4" for="senderTime">发送时间：
                </label>
                <div class="col-sm-8">
                    <div id="senderTime">${notificationDto.sentTime}</div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="form-group col-sm-6">
                <label class="control-label col-sm-4" for="subject">主题：
                </label>
                <div class="col-sm-8">
                    <div id="subject">${notificationDto.subject}</div>
                </div>
            </div>
            <div class="form-group col-sm-6">
                <label class="control-label col-sm-4" for="body">内容：
                </label>
                <div class="col-sm-8">
                    <div id="body">${notificationDto.body}</div>
                </div>
            </div>
        </div>
        <div>
            <input type="text" id="readId" style="visibility:hidden" value="${notificationDto.id}"/>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" tabindex="-1" class="btn btn-primary" data-dismiss="modal">
            <i class="fa fa-check"></i>&nbsp;确定
        </button>
    </div>
</div>

<script type="text/javascript">
    $("#notificationInfo").closest(".modal").on('hide.bs.modal', function () {
        $.ajax({
            url: contextPath + "/notify/read/" + $("#readId").val(),
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                location.reload();
            } else {
                App.toastr("重置通知状态失败！", "error");
            }
        }).fail(function () {
            App.toastr("重置通知状态失败！", "error");
        });
    });
</script>