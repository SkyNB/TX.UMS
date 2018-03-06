/**
 * 用户数据列表显示
 *
 */
var app;
var gridOptions2 = Object.assign(defaultGridOptions, {
    selectable: "multiple",//行多选
    columns: [{
        field: "rownumber",
        title: " ",
        template: function(dataItem){return app.dataSource.data().indexOf(dataItem) + 1},
        width: 45
    },  {
        title: "用户名",
        field: "username",
        template: "<a target='_modal' title='修改' onclick=\"app.page( '/get/#= userId #')\" >#= username#</a>",
        width: 200
    }, {
        field: "fullName",
        title: "姓名",
        width: 140,
    },  {
    field: "email",
        title: "邮箱",
        width: 170
    }, {
        field: "orgName",
        title: "所属组织",
        width: 130
    },  {
        field: "active",
        title: "是否启用",
        width: 80,
        template: "#= active?'是':'否' #"
    }, {
        field: "createTime",
        title: "创建时间",
        width: 170
    }, {
        field: "remark",
        title: "备注",
        width: 250
    }]
});

$(document).ready(function() {
    app = new Vue({
        el: "#app",
        data: {
            search: {
                username: ""
            },
            grid: null,//表格
            gridOptions: gridOptions2,
            dataSource: null,
        },
        methods: {
            page:function(name) {
                //加载一个新页面
                YM.KendoModal.modal(contextPath + "/user/" + name)
            },
            onSearch:function() {
                if(this.dataSource == null){
                    this.gridOptions.dataSource = this.dataSource = YM.Kendo.getDataSource("userId", contextPath + "/user/search");
                    this.grid = $("#grid").kendoGrid(this.gridOptions);//数据的填充
                    YM.Kendo.resizeGrid();//表格的自适应
                }else{
                    this.gridOptions.dataSource.filter(YM.Kendo.toGridFilter(this.search));
                }
            },
            onDelete:function() {
                if(!confirm("你是否需要删除？")) return;
                //调用通用方法，获取userIds
                var kendoType = this.grid.data("kendoGrid");//kendoGrid:数据格式
                var ids = YM.Kendo.getKendoGridId(kendoType,"userId");
                if(!ids) return;
                $.ajax({
                    type: "POST",
                    url: contextPath + "/user/delete",
                    data: JSON.stringify(ids),
                    //dataType: "json",       //下载格式
                    success: function(data) {
                        YM.Message.toast({ message: "删除成功！", type: "success" });
                        app.dataSource.query();
                    }
                });
            },
            selectAll:function(){
                var tmpGrid = this.grid.data("kendoGrid");
                for(var i=0;i<this.dataSource.data().length;i++){
                    tmpGrid.select(tmpGrid.tbody.find(">tr:not(.k-grouping-row)").eq(i));
                }
            },

            //是否启用调用
            active:function(isOrNo){
                //url定义
                var activeUr;
                if(isOrNo){
                    //启用url
                    activeUr = contextPath + "/user/enable";
                    if(!confirm("确定要启用吗？")) return;
                }else {
                    //禁用url
                    activeUr = contextPath + "/user/unEnable";
                    if(!confirm("确定要停用吗？")) return;
                }
                //调用通用方法，获取userIds
                var kendoType = this.grid.data("kendoGrid");//kendoGrid:数据格式
                var userIds = YM.Kendo.getKendoGridId(kendoType,"userId");
                if(!userIds) return;
                $.ajax({
                    type: "POST",
                    url: activeUr,
                    data: JSON.stringify(userIds),
                    contentType: "application/json",
                    success: function(data) {
                        if(isOrNo){
                            YM.Message.toast({ message: "启用成功！", type: "success" });
                        }else {
                            YM.Message.toast({ message: "禁用成功！", type: "success" });
                        }
                        app.dataSource.query();
                    }
                });

            }
        }
    });
    $(window).resize(function(){YM.Kendo.resizeGrid()} );
    app.onSearch();
});