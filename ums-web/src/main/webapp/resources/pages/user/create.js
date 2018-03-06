//定义验证
var v_user = {
    username: { "required": {} },
    password: {
        "required": "密码不能为空",
        minlength: { msg: "最小长度为3", param: 3 },
        maxlength: { msg: "最大长度为6", param: 6 }
    },
    email: { "required": "不能为空", 'email': "邮箱错误" }
};

var create = new Vue({
    el:"#create",
    data:{
        user:initData
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
                    url: contextPath + "/user/edit",
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
                    url: contextPath + "/user/create",
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
            v = Validate.vdata(this.user[v], v_user[v]).msg;
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
