//定义验证
var   v_user= {
    name: { "required": "名称不能为空"},
    code: {"required": "编码不能为空",},
    type: { "required": "不能为空", }
};

var create = new Vue({
    el:"#create",
    data:{
        organization:organization
    },
    methods: {
        //获取数据，提交ajax
        sbmit:function(){
            //获取用户organizationId
            var organizationId = create.organization.organizationId;
            console.log("用户id："+ organizationId);
            if(organizationId != null && organizationId != ""){
                //数据更新
                $.ajax({
                    type: "POST",
                    url: contextPath + "/organization/update",
                    data: JSON.stringify(create.organization),
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
                    url: contextPath + "/organization/create",
                    data: JSON.stringify(create.organization),
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
            v = Validate.vdata(this.organization[v],v_user[v]).msg;
            return v != undefined&&v.length>0?"-<span style='color:red'>"+v+"</span>":"";
        }

    },
    watch:{
//            user:{
//                handler:function(curVal,oldVal){
//                    this.validate.uNameMsg=notNull(curVal.uName)?"":"不能为空";
//                    this.validate.rNameMsg=notNull(curVal.rName)?"":"不能为空";
//                },
//                deep:true
//            }
    }
});
