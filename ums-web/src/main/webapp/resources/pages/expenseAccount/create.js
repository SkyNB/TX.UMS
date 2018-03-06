//定义验证
var v_exacct={
    code: { "required": "编码是必填项" },
    name: { "required": "名称不能为空"},
};

var create = new Vue({
    el:"#create",
    data:{
        exacct:exacct
    },
    methods: {
        addData:function(){
            var vinfo=Validate.vobj(this.exacct, v_exacct);//获取验证信息
            if(vinfo.result==true){//<===判断表单验证返回值
                $.ajax({
                    type: "POST",
                    url: contextPath + "/expenseAccount/create",
                    data: JSON.stringify(create.exacct),
                    success: function(data) {
                        YM.Message.toast({ message: "保存成功！", type: "success" });
                        //刷新
                        app.dataSource.query();
                        //删除弹出框，关闭
                        YM.CloseDialog.closeDialog();
                    }
                });
            }else{
                YM.Message.toast({ message: vinfo.msg, type: "warning" });//<====提示错误信息
            }
        },
        sbV:function(v) {//<====及时验证
            v = Validate.vdata(this.exacct[v],v_exacct[v]).msg;
            return v != undefined&&v.length>0?"-<span style='color:red;font-size: 11px;'>"+v+"</span>":"";
        },
    },
    watch:{
    }
});
