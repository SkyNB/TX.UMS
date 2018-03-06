//定义验证
var vali = {
    name: { "required": {} },
    code: {
        "required": "密码不能为空",
        minlength: { msg: "最小长度为3", param: 3 },
        maxlength: { msg: "最大长度为20", param: 20 }
    },
    contacts: { "required": "不能为空" },
    address: { "required": "不能为空" },
    contactPhone: { "required": "不能为空" }
    //city: { "required": "不能为空" }

};

var create = new Vue({
    el:"#create",
    data:{
        site:site
    },
    methods: {
        //获取数据，提交ajax
        sbmit:function(){
            //获取siteId
            var siteId = create.site.siteId;
            alert(siteId);
            if(siteId != null && siteId != ""){
                //数据更新
                $.ajax({
                    type: "POST",
                    url: contextPath + "/site/update",
                    data: JSON.stringify(create.site),
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
                    url: contextPath + "/site/create",
                    data: JSON.stringify(create.site),
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
            v = Validate.vdata(this.site[v],vali[v]).msg;
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
