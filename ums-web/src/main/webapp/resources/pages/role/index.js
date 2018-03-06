var app;
var gridOptions2 = Object.assign(defaultGridOptions, {
    dataSource: {},
    columns:[{
        title: "角色编号",
        field: "code",
        // template: "<a  title='修改'  onclick=\"app.page('create?roleId=#= roleId #')\" >#= code# </a>",
        template: "<a target='_modal' title='修改' onclick=\"app.page( '/get/#= roleId #')\" >#= name#</a>",
        width:350
    }, {
        title: "角色名称",
        field: "name",
        width: 300
    }, {
        field: "active",
        title: "是否启用",
        width: 80,
        template: "#= active?'是':'否' #"
    },
        {
        title: "是否系统定义",
        field: "sysRole",
        // template: '<input type="checkbox" #= isSystemRole ? "checked=checked" : "" # disabled="disabled" />',
            template: "#= sysRole?'是':'否' #",
        width: 210
    },
        {
        title: "备注",
        field: "remark",
        width: 210
    }]
});
$(document).ready(function() {
    app = new Vue({
        el: "#app",
        data: {
            search: {
                code: "",
            },
            grid: null,
            gridOptions: gridOptions2,
            dataSource: null
        },
        methods: {
            page: function (name) {
                // modal("/sysRole/" + name);
                //加载一个新页面
                YM.KendoModal.modal(contextPath + "/role/" + name)
            },
            onSearch: function () {
                if(this.dataSource == null){
                    this.gridOptions.dataSource = this.dataSource = YM.Kendo.getDataSource("roleId", contextPath + "/role/search");
                    this.grid = $("#grid").kendoGrid(this.gridOptions);//数据的填充
                    YM.Kendo.resizeGrid();//表格的自适应
                }else{
                    this.gridOptions.dataSource.filter(YM.Kendo.toGridFilter(this.search));
                }
            },
            onDelete: function () {
                if (!confirm("你是否需要删除？")) return;
                //调用通用方法，获取userIds
                var kendoType = this.grid.data("kendoGrid");//kendoGrid:数据格式
                var ids = YM.Kendo.getKendoGridId(kendoType,"roleId");
                if (!ids) return;
                $.ajax({
                    type: "POST",
                    url: contextPath + "/role/delete",
                    data: JSON.stringify(ids),
                    // contentType: "application/json",
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
                //url定义
                var activeUr;
                if(isOrNo){
                    //启用url
                    activeUr = contextPath + "/role/activate";
                    if(!confirm("确定要启用吗？")) return;
                }else {
                    //禁用url
                    activeUr = contextPath + "/role/inactivate";
                    if(!confirm("确定要停用吗？")) return;
                }
                //调用通用方法，获取userIds
                var kendoType = this.grid.data("kendoGrid");//kendoGrid:数据格式
                var codes = YM.Kendo.getKendoGridId(kendoType,"code");
                if(!codes) return;
                $.ajax({
                    type: "POST",
                    url: activeUr,
                    data: JSON.stringify(codes),
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
            },
            common:function (kendoType) {
                var tmpGrid = kendoType;
                var userIds = [];
                var rows = tmpGrid.select();
                if(!rows.length > 0) {
                    YM.Message.toast({ title: "警告", message: "请选择数据！", type: "warning" });
                    return;
                }
                rows.each(function(i, row) {
                    var dataItem = tmpGrid.dataItem(row);
                    if(dataItem) {
                        userIds.push(dataItem.roleId);
                    }
                });
                return userIds;
            }
        }
    });
    $(window).resize(function(){YM.Kendo.resizeGrid()} );
    app.onSearch();
});