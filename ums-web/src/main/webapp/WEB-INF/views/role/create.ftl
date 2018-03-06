<div id="create">
	<div class="content-group tab-content-bordered navbar-component">
		<div class="navbar navbar-inverse bg-teal-400" style="position: relative; z-index: 99;">
			<div class="navbar-header">
				<a class="navbar-brand">
					<b>
                        {{role.roleId.length>0?'修改':'新增'}}角色
					</b>
				</a>
			</div>
		</div>
		<div class="tab-content" style="padding: 0 10px;">
			<div class="tab-pane fade has-padding active in">
				<div class="row" style="margin-top: 20px;">
					<div class="col-sm-4">
						<sb-input type="text" label="角色编码" v-model="role.code"></sb-input>
					</div>
					<div class="col-sm-4">
						<sb-input type="text" label="角色名称 " v-model="role.name"></sb-input>
					</div>
					<div class="col-sm-2"> 
						<sb-checkbox label="是否启用" v-model="role.active"></sb-checkbox>
					</div>
					<div class="col-sm-2">
						<sb-checkbox label="是否系统定义" v-model="role.sysRole"></sb-checkbox>
					</div>
				</div>
				<div class="row" style="margin-top: 20px;">
					<div class="col-sm-12" >
						<p>授权:</p>
						<div class="form-group">
	                        <textarea class="form-control" rows="5" placeholder="可填写备注信息...." style="border: 1px;"></textarea>
	                        <i class="form-group__bar"></i>
	                    </div>
	                </div>
				</div>
				<div class="row" style="margin-top: 20px;">
					<div class="col-sm-12" >
						<p>备注:</p>
						<div class="form-group">
                            <textarea class="form-control" rows="5" placeholder="可填写备注信息...." style="border: 1px;"></textarea>
                            <i class="form-group__bar"></i>
                        </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
		<button type="button" class="btn btn-primary">确定</button>
	</div>
</div>
<script>
    var role = {
        roleId:"${role.body.roleId}",
        name:"${role.body.name}",
        code:"${role.body.code}",
        active:"${role.body.active}",
        sysRole:"${role.body.sysRole}",
        remark:"${role.body.remark}",
    };
    //定义验证
    var   v_role= {
        roleId: { "required": {} },
    };

    var create = new Vue({
        el:"#create",
        data:{
            role:role
        },
        methods: {
            //获取数据，提交ajax
            sbmit:function(){
                //获取用户userId
                var userId = create.user.userId;
                console.log("用户id："+ userId);
                if(userId != null && userId != ""){
                    //数据更新
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/role/update",
                        data: JSON.stringify(create.user),
                        success: function(data) {
                            YM.Message.toast({ message: "用户更新成功！", type: "success" });
                            //刷新
                            app.dataSource.query();
                            //删除弹出框，关闭
                            YM.CloseDialog.closeDialog();
                        }
                    });
                }else{
                    //数据保存
                    $.ajax({
                        type: "POST",
                        url: contextPath + "/role/create",
                        data: JSON.stringify(create.user),
                        success: function(data) {
                            YM.Message.toast({ message: "用户保存成功！", type: "success" });
                            //刷新
                            app.dataSource.query();
                            //删除弹出框，关闭
                            YM.CloseDialog.closeDialog();
                        }
                    });
                }

            },
            sbV:function(v) {
                v = Validate.vdata(this.role[v],v_role[v]).msg;
                return v != undefined&&v.length>0?"-<span style='color:red'>"+v+"</span>":"";
            }

        }
    });

</script>