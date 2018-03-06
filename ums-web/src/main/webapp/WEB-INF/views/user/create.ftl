<div id="create">
    <div class="content-group tab-content-bordered navbar-component">
        <div class="navbar navbar-inverse bg-teal-400" style="position: relative; z-index: 99;">
            <div class="navbar-header">
                <a class="navbar-brand">
                    <b>
                        {{user.userId.length>0?'修改':'新增'}}用户
                    </b>
                </a>

                <ul class="nav navbar-nav pull-right visible-xs-block">
                    <li>
                        <a data-toggle="collapse" data-target="#demo1"><i class="icon-tree5"></i></a>
                    </li>
                </ul>
            </div>

            <div class="navbar-collapse collapse" id="demo1">
                <ul class="nav navbar-nav">
                    <li class="active">
                        <a href="#tab-demo1" data-toggle="tab" aria-expanded="true">
                            <i class="icon-file-picture2 position-left"></i> 基本信息
                        </a>
                    </li>
                    <li>
                        <a href="#tab-demo2" data-toggle="tab" aria-expanded="false">
                            <i class="icon-file-music2 position-left"></i> 角色配置
                        </a>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="javascript:void(0)" data-dismiss="modal"><i class="icon-cross2"></i></a></li>
                </ul>
            </div>
        </div>
        <div class="tab-content" style="padding: 0 10px;">
            <div class="tab-pane fade has-padding active in" id="tab-demo1">
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-4">
                        <sb-input type="text" label="用户名" v-model="user.username" :msg="sbV('username')"></sb-input>
                    </div>
                    <div class="col-sm-4">
                        <sb-input type="text" label="密码" v-model="user.password" :msg="sbV('password')"></sb-input>
                    </div>
                    <div class="col-sm-4">
                        <sb-input type="text" label="确认密码" ></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-4">
                        <sb-input type="text" label="邮箱" v-model="user.email" :msg="sbV('email')"></sb-input>
                    </div>
                    <div class="col-sm-4">
                        <sb-input type="text" label="分支机构" v-model="user.orgCode" ></sb-input>
                    </div>
                </div>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-12" >
                        <p>备注:</p>
                        <div class="form-group">
                            <textarea class="form-control" rows="5" v-model="user.remark" placeholder="可填写备注信息...." style="border: 1px;"></textarea>
                            <i class="form-group__bar"></i>
                        </div>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade has-padding" id="tab-demo2">Music tab content</div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" @click="sbmit()">确定</button>
    </div>
</div>

<script>
    var initData = {
        userId:"${user.body.userId}",
        username:"${user.body.username}",
        password:"${user.body.password}",
        email:"${user.body.email}",
        remark:"${user.body.remark}",
        orgCode:"${user.body.orgCode}",
    };

</script>
<script src="${request.contextPath}/resources/pages/user/create.js"></script>
