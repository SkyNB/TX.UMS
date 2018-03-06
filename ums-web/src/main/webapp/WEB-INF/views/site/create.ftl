<div id="create">
    <div class="content-group tab-content-bordered navbar-component">
        <div class="navbar navbar-inverse bg-teal-400" style="position: relative; z-index: 99;">
            <div class="navbar-header">
                <a class="navbar-brand">
                    <b>
                        {{site.siteId.length>0?'修改':'新增'}}站点
                    </b>
                </a>
            </div>
            <div class="navbar-collapse collapse" id="demo1">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="javascript:void(0)" data-dismiss="modal"><i class="icon-cross2"></i></a></li>
                </ul>
            </div>
        </div>
        <div class="tab-content" style="padding: 0 10px;">
            <div class="tab-pane fade has-padding active in">
                <div class="row" style="margin-top: 20px;" v-if="site.siteId">
                    <div class="col-sm-4">
                        <sb-input type="text" label="分公司" disabled="true" v-model="site.branchCode" :msg="sbV('branchCode')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-4">
                        <sb-input type="text" label="编码" v-model="site.code" :msg="sbV('code')"></sb-input>
                    </div>
                    <div class="col-sm-4">
                        <sb-input  label="名称" type="text" v-model="site.name" :msg="sbV('name')"></sb-input>
                    </div>
                    <div class="col-sm-4">
                        <sb-input type="text" label="联系人" v-model="site.contacts" :msg="sbV('contacts')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-4">
                        <sb-input type="text" label="手机号码" v-model="site.contactPhone" :msg="sbV('contactPhone')"></sb-input>
                    </div>
                    <div class="col-sm-8">
                        <sb-input type="text" label="地址" v-model="site.city" :msg="sbV('city')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12">
                        <sb-input type="text" label="详细地址" v-model="site.address" :msg="sbV('address')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-4">
                        <sb-input type="text" label="省" v-model="site.province" :msg="sbV('province')"></sb-input>
                    </div>
                    <div class="col-sm-4"></div>
                    <div class="col-sm-4">
                        <sb-input type="text" label="市" v-model="site.city" :msg="sbV('city')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-4">
                        <sb-input type="text" label="区" v-model="site.district" :msg="sbV('district')"></sb-input>
                    </div>
                    <div class="col-sm-4"></div>
                    <div class="col-sm-4">
                        <sb-input type="text" label="街" v-model="site.street" :msg="sbV('street')"></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12" >
                        <p>备注:</p>
                        <div class="form-group">
                            <textarea class="form-control" rows="5" v-model="site.remark" placeholder="可填写备注信息...." style="border: 1px;"></textarea>
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
    var site = {
        siteId:"${site.body.siteId}",
        code:"${site.body.code}",
        name:"${site.body.name}",
        contacts:"${site.body.contacts}",
        contactPhone:"${site.body.contactPhone}",
        city:"${site.body.city}",
        address:"${site.body.address}",
        remark:"${site.body.remark}",
        province:"${site.body.province}",
        district:"${site.body.district}",
        street:"${site.body.street}",
        branchCode:"${site.body.branchCode}",
    };
</script>
<script src="${request.contextPath}/resources/pages/site/create.js"></script>