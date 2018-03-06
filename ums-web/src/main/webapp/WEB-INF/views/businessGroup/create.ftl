<div id="create">
    <div class="content-group tab-content-bordered navbar-component">
        <div class="navbar navbar-inverse bg-teal-400" style="position: relative; z-index: 99;">
            <div class="navbar-header">
                <a class="navbar-brand">
                    <b>
                        {{businessGroup.businessGroupId.length>0?'修改':'新增'}}业务组
                    </b>
                </a>
            </div>
        </div>
        <div class="tab-content" style="padding: 0 10px;">
            <div class="tab-pane fade has-padding active in">
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12">
                        <sb-input type="text" label="编码" v-model="businessGroup.code" :msg="sbV('code')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12">
                        <sb-input  label="名称" type="text" v-model="businessGroup.name" :msg="sbV('name')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12" >
                        <p>备注:</p>
                        <div class="form-group">
                            <textarea class="form-control" rows="5" v-model="businessGroup.remark"  placeholder="可填写备注信息...." style="border: 1px;"></textarea>
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
    var businessGroup = {
        businessGroupId:"${businessGroup.body.businessGroupId}",
        code:"${businessGroup.body.code}",
        name:"${businessGroup.body.name}",
        remark:"${businessGroup.body.remark}",
    };

</script>
<script src="${request.contextPath}/resources/pages/businessGroup/create.js"></script>
