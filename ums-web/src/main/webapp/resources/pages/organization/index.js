var app;
var gridOptions2 = Object.assign(defaultGridOptions, {
    dataSource: {},
    columns:[{
        field: "code",
        title: "编码"
    }, {
        field: "name",
        title: "名称",
        template: function (dataItem) {
            return "<a  title='修改' onclick=\"app.page( '/getOrganization/"+ dataItem.organizationId +"')\" >"+ dataItem.name+"</a>"
        }

    }, {
        field: "type.text",
        title: "类型"
    }, {
        field: "active",
        title: "启用",
        template: "#= active?'是':'否' #"
    }, {
        field: "remark",
        title: "备注"
    }]
});
$(document).ready(function() {
    app = new Vue({
        el: "#app",
        data: {
            search: {
                searchText: "",
            },
            grid: null,
            gridOptions: gridOptions2,
            dataSource: null
        },
        methods: {
            page: function (name) {
                // modal("/sysRole/" + name);
                //加载一个新页面
                YM.KendoModal.modal(contextPath + "/organization/" + name)
            },
            onSearch: function () {
                if (this.dataSource == null) {
                    this.gridOptions.dataSource = this.dataSource = YM.Kendo.getDataSource("organizationId", contextPath + "/organization/search", "parentId");
                    this.grid = $("#gridOrganization").kendoGrid(this.gridOptions);//数据的填充
                    YM.Kendo.resizeGrid();//表格的自适应
                } else {
                    console.log("搜索", this.search, " ");
                    var f = this.search.searchText;
                    this.gridOptions.dataSource.filter(YM.Kendo.toGridFilter({code: f, name: f}));
                }
            },
            onDelete: function () {
                if (!confirm("你是否需要删除？")) return;
                //调用通用方法，获取userIds
                var kendoType = this.grid.data("kendoGrid");//kendoGrid:数据格式
                var ids = YM.Kendo.getKendoGridId(kendoType,"organizationId");
                if (!ids) return;
                $.ajax({
                    type: "POST",
                    url: contextPath + "/organization/delete",
                    data: JSON.stringify(ids),
                    contentType: "application/json",
                    success: function (data) {
                        YM.Message.toast({message: "删除成功！", type: "success"});
                        app.dataSource.query();
                    },
                    error: function () {
                        YM.Message.toast({title: "错误", message: "删除失败！", type: "danger"});
                    }
                });
            },
            active: function (isOrNo) {
                console.log("isOrNo==>" + isOrNo);
                //url定义
                var activeUr;
                if (isOrNo) {
                    //启用url
                    activeUr = contextPath + "/organization/activate";
                    if (!confirm("确定要启用吗？")) return;
                } else {
                    //禁用url
                    activeUr = contextPath + "/organization/inactivate";
                    if (!confirm("确定要停用吗？")) return;
                }
                //调用通用方法，获取userIds
                var kendoType = this.grid.data("kendoGrid");//kendoGrid:数据格式
                var userIds = YM.Kendo.getKendoGridId(kendoType,"code");
                if (!userIds) return;
                $.ajax({
                    type: "POST",
                    url: activeUr,
                    data: JSON.stringify(userIds),
                    contentType: "application/json",
                    success: function (data) {
                        if (isOrNo) {
                            YM.Message.toast({message: "启用成功！", type: "success"});
                        } else {
                            YM.Message.toast({message: "禁用成功！", type: "success"});
                        }
                        app.dataSource.query();
                    },
                    error: function () {
                        if (isOrNo) {
                            YM.Message.toast({message: "启用失败！", type: "danger"});
                        } else {
                            YM.Message.toast({message: "禁用失败！", type: "success"});
                        }
                    }
                });
            },
        }
    });
    $(window).resize(function(){YM.Kendo.resizeGrid()} );
    app.onSearch();
});