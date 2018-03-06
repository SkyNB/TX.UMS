<div id="create">
    <div class="content-group tab-content-bordered navbar-component">
        <div class="navbar navbar-inverse bg-teal-400" style="position: relative; z-index: 99;">
            <div class="navbar-header">
                <a class="navbar-brand">
                    <b>     新增费用科目
                    </b>
                </a>
            </div>
        </div>
        <div class="tab-content" style="padding: 0 10px;">
            <div class="tab-pane fade has-padding active in">
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12">
                        <sb-input type="text" label="编码" v-model="exacct.code" :msg="sbV('code')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12">
                        <sb-input  label="名称" type="text" v-model="exacct.name" :msg="sbV('name')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12">
                        <sb-input  label="上级费用" type="text" v-model="exacct.superiorCode"  >
                            <#--<select name="groupid" id="groupidId" class="select01">
                                <option value ="">--请选择--</option>
                            <#if dataMap?exists>
                                <#list dataMap?keys as key>
                                    <option value="${key}">${dataMap.get(key)}</option>
                                </#list>
                            </#if>
                            </select>-->
                        </sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12" >
                        <p>备注:</p>
                        <div class="form-group">
                            <textarea class="form-control" rows="5" v-model="exacct.remark" :msg="sbV('remark')" placeholder="可填写备注信息...." style="border: 1px;"></textarea>
                            <i class="form-group__bar"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" @click="addData">确定</button>
    </div>
</div>
<script>
    var exacct = {
        expenseAccountId:"${exacct.body.expenseAccountId}",
        code:"${exacct.body.code}",
        name:"${exacct.body.name}",
        superiorCode:"${exacct.body.superiorCode}",
        remark:"${exacct.body.remark}",
    };

</script>
<script src="${request.contextPath}/resources/pages/expenseAccount/create.js"></script>