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
        field: "code",
        title: "编码",
        width: 200
    }, {
        field: "name",
        title: "站点名称",
        template: "<a target='_modal' title='修改' onclick=\"app.page( '/get/#= siteId #')\" >#= name#</a>",
        width: 140
    },  {
        field: "contacts",
        title: "联系人",
        width: 170
    }, {
        field: "contactPhone",
        title: "联系人电话",
        width: 130
    },  {
        field: "city",
        title: "所在城市",
        width: 80
    }, {
        field: "address",
        title: "详细地址",
        width: 170
    }, {
        field: "remark",
        title: "备注",
        width: 250
    }]
});


//定义所有的url
var site = {
    enableUrl : contextPath + "/site/enable",
    unEnableUrl : contextPath + "/site/unEnable",
    deleteUrl : contextPath + "/site/delete"
}

$(document).ready(function() {
    app = new Vue({
        el: "#app",
        data: {
            search: {
                name: ""
            },
            grid: null,//表格
            gridOptions: gridOptions2,
            dataSource: null,
        },
        methods: {
            page:function(name) {
                //加载一个新页面
                YM.KendoModal.modal(contextPath + "/site/" + name)
            },
            onSearch:function() {
                if(this.dataSource == null){
                    this.gridOptions.dataSource = this.dataSource = YM.Kendo.getDataSource("siteId", contextPath + "/site/search");
                    this.grid = $("#grid").kendoGrid(this.gridOptions);//数据的填充
                    YM.Kendo.resizeGrid();//表格的自适应
                }else{
                    console.log("搜索", this.search, " ");
                    this.gridOptions.dataSource.filter(YM.Kendo.toGridFilter(this.search));
                }
            },
            onDelete:function() {
                if(!confirm("你是否需要删除？")) return;
                //调用通用方法，获取siteIds
                var kendoType = this.grid.data("kendoGrid");//kendoGrid:数据格式
                var ids = YM.Kendo.getKendoGridId(kendoType,"siteId");
                if(!ids) return;
                $.ajax({
                    type: "POST",
                    url: contextPath + "/site/delete",
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
                console.log("isOrNo==>" + isOrNo);
                //url定义
                var activeUr;
                if(isOrNo){启用url
                    //
                    activeUr = contextPath + "/site/enable";
                    if(!confirm("确定要启用吗？")) return;
                }else {
                    //禁用url
                    activeUr = contextPath + "/site/unEnable";
                    if(!confirm("确定要停用吗？")) return;
                }
                //调用通用方法，获取siteIds
                var kendoType = this.grid.data("kendoGrid");//kendoGrid:数据格式
                var siteIds = YM.Kendo.getKendoGridId(kendoType,"siteId");
                if(!siteIds) return;
                $.ajax({
                    type: "POST",
                    url: activeUr,
                    data: JSON.stringify(siteIds),
                    contentType: "application/json",
                    success: function(data) {
                        if(isOrNo){
                            YM.Message.toast({ message: "启用成功！", type: "success" });
                        }else {
                            YM.Message.toast({ message: "禁用成功！", type: "success" });
                        }
                        app.dataSource.query();
                    },
                    error: function() {
                        if(isOrNo){
                            YM.Message.toast({ message: "启用失败！", type: "danger" });
                        }else {
                            YM.Message.toast({ message: "禁用失败！", type: "success" });
                        }
                    }
                });

            }
        }
    });
    $(window).resize(function(){YM.Kendo.resizeGrid()} );
    app.onSearch();
});