var app;
var gridOptions2 = Object.assign(defaultGridOptions, {
    dataSource: {},
    selectable: true,
    columns: [{
        field: "code", title: "编号"
    }, {
        field: "name", title: "站点名称"
        //, template: function (dataItem) {
        //     return "<a href='#/update/" + dataItem.code + "' data-target='#updateExacct' data-toggle='modal'>" + dataItem.name + "</a>";
        // }
    }, {
        field: "remark", title: "备注"
    }]
});
$(document).ready(function() {
    app = new Vue({
        el: "#app",
        data: {
            search: {
                // code: "",
                name:"",
                logic:"or"
            },
            grid: null,
            gridOptions: gridOptions2,
            dataSource: null
        },
        methods: {
            page:function(name) {
                YM.KendoModal.modal(contextPath + "/expenseAccount/" + name,false)
            },
            onSearch:function() {
                if(this.dataSource == null){
                    this.gridOptions.dataSource = this.dataSource =YM.KendoTreeGrid.createTreeListDataSource("code", contextPath + "/expenseAccount/search", "superiorCode");
                    this.grid = $("#gridExpenseAccount").kendoTreeList(this.gridOptions);
                    YM.Kendo.resizeGrid();
                }else{
                    this.gridOptions.dataSource.filter(YM.Kendo.toGridFilter({code: this.search.name, name: this.search.name,logic:"or"}));
                }
            },
            onDelete: function () {
                if (!confirm("你是否需要删除？")) return;
                //调用通用方法，获取userIds
                var kendoType = this.grid.data("kendoTreeList");//kendoGrid:数据格式
                var code = YM.Kendo.getKendoGridId(kendoType,"code")[0];
                if (!code) return;
                $.ajax({
                    type: "POST",
                    url: contextPath + "/expenseAccount/delete/"+code,
                    data: JSON.stringify(code),
                    contentType: "application/json",
                    success: function (data) {
                        if(data.success){
                            YM.Message.toast({message: "删除成功！", type: "success"});
                        }else{
                            YM.Message.toast({message: "删除失败！", type: "success"});
                        }
                        app.dataSource.query();
                    },
                    error: function () {
                        YM.Message.toast({title: "错误", message: "删除失败！", type: "danger"});
                    }
                });
            },
            sbV:function(v) {
                v = Validate.vdata(this.expenseAccount[v],v_user[v]).msg;
                return v != undefined&&v.length>0?"-<span style='color:red'>"+v+"</span>":"";
            }
        }
    });
    $(window).resize(function(){YM.Kendo.resizeGrid()});
    app.onSearch();
});