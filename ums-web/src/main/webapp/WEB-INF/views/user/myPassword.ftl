<div id="myPasswordModal">
    <form id="myPasswordForm" class="form-horizontal" data-role="form">
        <div class="modal-header">
            <h4><i class="fa fa-edit"></i>&nbsp;修改密码</h4>
        </div>
        <div class="modal-body">
            <div class="form-group">
                <label class="control-label col-sm-3" for="name">
                    用户名
                    <span class="required" aria-required="true"> * </span>
                </label>
                <div class="col-sm-7">
                    <input id="userName" class="form-control" name="name" type="text" value="${userName}"
                           required="required">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="">
                    原密码
                    <span class="required" aria-required="true"> * </span>
                </label>
                <div class="col-sm-7">
                    <input id="oldPassword" class="form-control" value="" required="required"
                           name="oldPassword" aria-required="true" type="password">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="">
                    新密码
                    <span class="required" aria-required="true"> * </span>
                </label>
                <div class="col-sm-7">
                    <input id="newPassword" class="form-control" value="" required="required"
                           name="newPassword" aria-required="true" type="password">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="">确认密码 </label>
                <div class="col-sm-7">
                    <input id="confirmPassword" class="form-control" name="confirmPassword" value=""
                           aria-invalid="false"
                           type="password" equalTo="#newPassword" required="required">
                </div>
            </div>
            <input id="userId" class="form-control" type="hidden" value="${userPrincipal.getUserId()}">
        </div>
        <div class="modal-footer">
            <button type="submit" tabindex="-1" class="btn btn-primary" id="submit">
                <i class="fa fa-check"></i>&nbsp;确定
            </button>
            <button class="btn btn-danger" type="button" tabindex="-1" data-dismiss="modal">
                <i class="fa fa-close"></i>
                取消
            </button>
        </div>
    </form>
</div>

<script>
    var validate = $("#myPasswordForm").validate();

    $("#submit").click(function (e) {
        e.preventDefault();
        if (!validate.valid()) return;
        $.ajax({
            url: contextPath + '/user/modifyPassword',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                userName: $("#userName").val(),
                oldPassword: $("#oldPassword").val(),
                newPassword: $("#newPassword").val()
            })
        }).done(function (result) {
            if (result.success) {
                $("#myPasswordModal,.modal-backdrop").hide();
            } else {
                App.toastr(result.message, "error");
            }
        }).fail(function () {
            App.toastr("请求失败！");
        })
    });
</script>


