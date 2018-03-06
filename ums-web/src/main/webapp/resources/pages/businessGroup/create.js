//定义验证
var   v_user= {
    name: { "required": "名称不能为空"},
    code: {"required": "编码不能为空"},
};

var create = new Vue({
    el:"#create",
    data:{
        businessGroup:businessGroup
    },
    methods: {
        //获取数据，提交ajax
        sbmit:function(){
            //获取用户businessGroupId
            var businessGroupId = create.businessGroup.businessGroupId;
            console.log("用户组id："+ businessGroupId);
            if(businessGroupId != null && businessGroupId != ""){
                //数据更新
                $.ajax({
                    type: "POST",
                    url: contextPath + "/businessGroup/edit",
                    data: JSON.stringify(create.businessGroup),
                    contentType: "application/json",
                    success: function(data) {
                        YM.Message.toast({ message: "用户更新成功！", type: "success" });
                        //刷新
                        app.dataSource.query();
                        //删除弹出框，关闭
                        YM.CloseDialog.closeDialog();
                    },
                    error: function() {
                        YM.Message.toast({ message: "用户更新失败！", type: "danger" });
                        //删除弹出框，关闭
                        YM.CloseDialog.closeDialog();
                    }
                });
            }else{
                //数据保存
                $.ajax({
                    type: "POST",
                    url: contextPath + "/businessGroup/create",
                    data: JSON.stringify(create.businessGroup),
                    contentType: "application/json",
                    success: function(data) {
                        YM.Message.toast({ message: "用户保存成功！", type: "success" });
                        //刷新
                        app.dataSource.query();
                        //删除弹出框，关闭
                        YM.CloseDialog.closeDialog();
                    },
                    error: function() {
                        YM.Message.toast({ message: "用户保存失败！", type: "danger" });
                        //删除弹出框，关闭
                        YM.CloseDialog.closeDialog();
                    }
                });
            }

        },
        sbV:function(v) {
            v = Validate.vdata(this.businessGroup[v],v_user[v]).msg;
            return v != undefined&&v.length>0?"-<span style='color:red'>"+v+"</span>":"";
        }

    },
    watch:{
    }
});
