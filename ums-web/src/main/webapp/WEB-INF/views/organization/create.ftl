<div id="create">
    <div class="content-group tab-content-bordered navbar-component">
        <div class="navbar navbar-inverse bg-teal-400" style="position: relative; z-index: 99;">
            <div class="navbar-header">
                <a class="navbar-brand">
                    <b>
                        {{organization.code.length>0?'修改':'新增'}}组织
                    </b>
                </a>
            </div>
        </div>
        <div class="tab-content" style="padding: 0 10px;">
            <div class="tab-pane fade has-padding active in">
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-4">
                        <sb-input type="text" label="编码" v-model="organization.code" :msg="sbV('code')"></sb-input>
                    </div>
                    <div class="col-sm-4">
                        <sb-input  label="名称" type="text" v-model="organization.name" :msg="sbV('name')"></sb-input>
                    </div>
                    <div class="col-sm-4">
                        <sb-input type="text" label="类型 " v-model="organization.type" :msg="sbV('type')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12">
                        <sb-input type="text" label="上级组织" v-model="organization.parentId" ></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12" >
                        <p>备注:</p>
                        <div class="form-group">
                            <textarea class="form-control" rows="5" placeholder="可填写备注信息...." style="border: 1px;" v-model="organization.remark" ></textarea>
                            <i class="form-group__bar"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" @click="sbmit()">确定</button>
    </div>
</div>
<script>
    var organization = {
        organizationId:"${organization.body.organizationId}",
        code:"${organization.body.code}",
        name:"${organization.body.name}",
        type:"${organization.body.type}",
        parentId:"${organization.body.parentId}",
        remark:"${organization.body.remark}",
    };

</script>
<script src="${request.contextPath}/resources/pages/organization/create.js"></script>