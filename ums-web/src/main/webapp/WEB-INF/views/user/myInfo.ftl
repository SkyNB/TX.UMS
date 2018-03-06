<div>
    <form id="myUserForm" class="form-horizontal" data-role="form">
        <div class="modal-header">
            <h4><i class="fa fa-edit"></i>&nbsp;我的信息</h4>
        </div>
        <div class="modal-body">
            <div class="form-group">
                <label class="control-label col-sm-3" for="name">
                    用户名
                    <span class="required" aria-required="true"> * </span>
                </label>
                <div class="col-sm-7">
                    <input id="name" class="form-control" name="name" type="text" value="${userInfo.userName}" readonly="readonly">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-3" for="fullname">
                    姓名
                    <span class="required" aria-required="true"> * </span>
                </label>
                <div class="col-sm-7">
                    <input id="fullname" class="form-control" value="${userInfo.fullName}" required="required"
                           name="fullname" aria-required="true" type="text" readonly="readonly">
                </div>
            </div>
            <#--<div class="form-group">
                <label class="control-label col-sm-3" for="org">分支机构 </label>
                <div class="col-sm-7">
                    <input id="orgName" class="form-control" name="orgCode" value="${userInfo.orgName}"
                           aria-invalid="false" readonly="readonly"
                           type="text">
                </div>
            </div>-->
            <div class="form-group">
                <label class="control-label col-sm-3" for="email">
                    邮箱
                    <span class="required" aria-required="true"> * </span>
                </label>
                <div class="col-sm-7">
                    <input id="email" class="form-control" required="required" value="${userInfo.email}" name="email"
                           aria-required="true" readonly="readonly"
                           type="email">
                </div>
            </div>
            <#--<div class="form-group">
                <label class="control-label col-sm-3" for="remark">备注 </label>
                <div class="col-sm-7">
                    <textarea id="remark" class="form-control" value="${userInfo.remark}" name="remark"
                              rows="3" readonly="readonly"></textarea>
                </div>
            </div>-->
            <div class="form-group ">
                <label class="control-label col-sm-3" for="roleCodes">角色
                    <span class="required" aria-required="true"> * </span>
                </label>
                <div class="col-sm-7">
                    <select id="multiselect" multiple="multiple" readonly="readonly">
                    <#if (myRoles??) && (myRoles?size gt 0)>
                        <#list myRoles as item>
                            <option value="${item.code}" selected="selected">${item.name}</option>
                        </#list>
                    </#if>
                    </select>
                </div>
            </div>
            <input id="userId" class="form-control" type="hidden" value="${userPrincipal.getUserId()}">
        </div>
        <div class="modal-footer">
            <button type="button" tabindex="-1" class="btn btn-primary" data-dismiss="modal">
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
    $("#multiselect").kendoMultiSelect();
</script>