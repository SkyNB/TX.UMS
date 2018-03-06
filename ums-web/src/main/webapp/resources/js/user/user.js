/**
 * 新易泰 万江
 *2017年1月17日17:42:38
 */

//全局变量区
/*var app;

$(document).ready(function() {
    YX.tableOption.columns=[{ //列头数据
        field: "username", //绑定的ID
        title: "用户名", //绑定的文字
        width: 240 //宽度
    }, {
        columnMenu: false, //是否允许列头菜单
        filterable: false, //是否允许筛选
        field: "realname",
        title: "真实姓名"
    }, {
        field: "usersex",
        title: "用户性别",
    },{
        field: "userqq",
        title: "qq号码",
    },{
        field: "userid",
        title: "主键",
        hidden:true
    }, {
        field: "phone",
        title: "手机号码",
    },{
        field: "updatetime",
        title: "修改时间",
        format: "{0:dd/MM/yyyy}", //日期格式化
        width: 150
    }];
    //以下是Vue区
    app = new Vue({
        el: "#app",//绑定id使用
        data: {
            searchBar: { /!*------搜索栏*!/
                show: true,//显示隐藏搜索栏
                data: {//搜索栏绑定数据
                    text: "111",
                    date: null,
                    dpd: null,
                    dpd2:""
                },
                tag:{//附加数据,下拉列的数据
                    testList: [{//比如这是 下拉列的数据
                        text: "Black",
                        value: "1"
                    }, {
                        text: "Orange",
                        value: "2"
                    }, {
                        text: "Grey",
                        value: "3"
                    }]
                }
            },
            grid: $("#grid"),
            tableOption: YX.tableOption,
            selectIndex: 0,
        },
        methods: {
            getTableData: function(url, data) {
                if(data==undefined){
                    data={};
                }
                $.ajax({
                    type: "GET",
                    url: url,
                    data: data,
                    dataType: "json",
                    success: function(data) {
                        app.tableOption.dataSource.data = data;
                    }
                });
            },
            //---打开/关闭搜索栏
            toggleSearchBar: function() {
                this.searchBar.show = !this.searchBar.show;
                setTimeout(function() {
                    app.grid.height(getMainH());
                    app.grid.data('kendoGrid').refresh();
                }, 310);
            },
            //---表格事件
            del:function(){
                $.ajax({
                    type: "GET",
                    url: "deleteUser",
                    dataType: "text",
                    success: function(data) {
                        app.tableOption.dataSource.data.splice( app.selectIndex, 1)
                        // app.toast.text="删除用户成功";
                        // app.showToast();
                    }
                });
            },
            updateUser:function(){
                console.log(this.tableOption.dataSource.data[ this.selectIndex],this.selectIndex);
                $.get("updateUser",{userId:this.tableOption.dataSource.data[ this.selectIndex].userid},function(data){

                })
            }
        },
        watch: { //观察者模式
            tableOption: {
                handler: function(val, oldVal) {
                    var tbp=objCopy(app.tableOption);
                    tbp.change = function(arg) {
                        var selectedIndex = $.map(this.select(), function(item) {
                            return $(item).context.rowIndex;
                        });
                        app.selectIndex=selectedIndex[0];
                    };
                    $("#grid").kendoGrid(tbp);
                },
                deep: true
            },
            pageSize: function(val, oldVal) {
                app.grid.data("kendoGrid").dataSource.pageSize(val); //分页
            }
        }
    });

    //$(window).resize(YX.window.resize($("#grid")));   //小龙解决

    //下拉列选项初始化
    $("#size,#dropd").kendoDropDownList();
    $("#fabric").kendoComboBox();
    //日期选择器
    $("#datepicker").kendoDatePicker({
        format: "yyyy-MM-dd"
    });
    //---
    if($("#main_loading").length) {
        $("#main_loading").fadeOut(300);
    }
});*/
