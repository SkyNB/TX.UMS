/**
 * 业务组数据列表显示
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
        title: "编码"
    }, {
        field: "name",
        title: "名称",
        template: function (dataItem) {
            return "<a  title='修改' onclick=\"app.page( '/getOrganization/"+ dataItem.code +"')\" >"+ dataItem.name+"</a>"
        }
    }, {
        field: "createUserName",
        title: "创建人"
    }, {
        field: "createTime",
        title: "创建时间"
    }, {
        field: "modifyUserName",
        title: "修改人"
    }, {
        field: "modifyTime",
        title: "修改时间"
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
                username: "",
            },
            grid: null,//表格
            gridOptions: gridOptions2,
            dataSource: null,
        },
        methods: {
            page:function(name) {
                //加载一个新页面
                YM.KendoModal.modal(contextPath + "/businessGroup/" + name)
            },
            onSearch:function() {
                if(this.dataSource == null){
                    this.gridOptions.dataSource = this.dataSource = YM.Kendo.getDataSource("businessGroupId", contextPath + "/businessGroup/search");
                    this.grid = $("#gridBusinessGroup").kendoGrid(this.gridOptions);//数据的填充
                    YM.Kendo.resizeGrid();//表格的自适应
                }else{
                    console.log("搜索", this.search, " ");
                    var f = this.search.searchText;
                    this.gridOptions.dataSource.filter(YM.Kendo.toGridFilter({code: f, name: f}));
                }
            },
            selectAll:function(){
                var tmpGrid = this.grid.data("kendoGrid");
                for(var i=0;i<this.dataSource.data().length;i++){
                    tmpGrid.select(tmpGrid.tbody.find(">tr:not(.k-grouping-row)").eq(i));
                }
            },
        }
    });
    $(window).resize(function(){YM.Kendo.resizeGrid()} );
    app.onSearch();
});