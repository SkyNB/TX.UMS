//配置区
kendo.culture("zh-CN");
const defaultGridOptions = { //gridOptions
    dataSource: {},
    selectable: "row",
    sortable: true,
    groupable: false,
    editable: false,
    resizable: true,
    reorderable: true,
    pageable: { refresh: false, pageSize: 20, pageSizes: [20, 50, 100, 200, 500] }
}; //表格默认配置

 $(function () {
     $.ajaxSetup({
         dataType: "json",       //下载格式
         type: "POST",           //上传数据方式
         cache: false,           //是否启用浏览器缓存
         contentType: "application/json",
         complete: function (xhr, status) {
             $(".main_loading").fadeOut(300);
         },
         beforeSend: function () {
             $(".main_loading").fadeIn(300);
         },
         error: function (textStatus, jqXHR) {
             $(".main_loading").fadeOut(300);
             if (jqXHR.statusText !== "OK") {
                 var html = $(jqXHR.responseText);
                 if (html.length > 2) {
                     var title = html.eq(1).text();
                     YM.Message.toast({ title: "系统出错：", message: title + "，请联系系统管理员！", type: "danger"});
                 }
                 else {
                     YM.Message.toast({ title: "系统出错：", message: "请联系系统管理员！", type: "danger"});
                 }
             }
         }
     });
 });

var YM = {};
YM.Kendo = {
    getContainerH: function () {//获取内容主体高度
        var outerHeight = 0;
        $(".nav-bar").each(function (i, v) {
            outerHeight += $(v).outerHeight();
        });
        return window.innerHeight - outerHeight - 1;
    },
    resizeGrid: function (containerHeight) {//设置表内容高度
        containerHeight = (containerHeight == undefined ? this.getContainerH() : containerHeight);
        $(".k-grid.dynamicHeight,.dynamic-height.k-grid").each(function () {
            var grid = $(this),
                h1 = grid.find("div.k-grid-toolbar").outerHeight() || 0,
                h2 = grid.find("div.k-grouping-header").outerHeight() || 0,
                h3 = grid.find("div.k-grid-header").outerHeight() || 0,
                h4 = grid.find("div.k-grid-pager").outerHeight() || 0,
                ch = containerHeight - 2 - h1 - h2 - h3 - h4;

            if (ch > 0) {
                grid.find('div.k-grid-content').css({"maxHeight": ch + 'px',"minHeight": (ch-1) + 'px'});
                grid.find('div.k-grid-content-locked').css({"maxHeight": ch + 'px',"minHeight": (ch-1) + 'px'});
            }
        });
    },
    getDataSourceConfig: function (idField, readUrl, filter) {//表格配置初始化
        return {
            schema: {
                model: { id: idField }, data: function (response) {
                    return response.list || response.body || response.data || response.rows || response; //<========根据字段源
                }, total: "total" || "totalItemCount"//<======根据字段源
            }, transport: {
                parameterMap: function (options) {
                    return JSON.stringify(options);
                }, read: this.getTransport(readUrl)
            }, serverPaging: true, serverFiltering: true, serverSorting: true, filter: filter, refresh: true, pageSize: 20
        }
    },
    getDataSource: function (idField, readUrl, filter) {
        return new kendo.data.DataSource(YM.Kendo.getDataSourceConfig(idField, readUrl, filter));
    },
    getTransport: function (url, type, dataType, contentType) {
        return {
            contentType: contentType || "application/json",
            dataType: dataType || "json",
            type: type || "POST",
            async: false,
            url: url
        };
    },
    toGridFilter: function (o) { //转为表过滤对象
        var filter = {
            logic: o.logic || "and",
            filters: []
        };
        for (var i in o) {
            var m = o[i];
            if (i === "logic") {
                continue;
            }
            if ($.trim(m) && (typeof m === "string" || typeof m === "number")) {
                filter.filters.push({
                    name: i,
                    value: $.trim(m),
                    Wildcard: "eq"
                });
            } else if (typeof m === "object" && $.trim(m.value)) {
                if (m.wildcard === "in") {
                    var value = m.value.split("/");
                    filter.filters.push({
                        name: i,
                        value: value,
                        Wildcard: m.Wildcard || "in"
                    });
                } else {
                    filter.filters.push({
                        name: i,
                        value: $.trim(m.value),
                        Wildcard: m.Wildcard || "eq"
                    });
                }
            } else if (typeof m === "object" && m.type === "range") {
                if (m.start) filter.filters.push({
                    name: i,
                    value: m.start,
                    Wildcard: "gte"
                });
                if (m.end) {
                    filter.filters.push({
                        name: i,
                        value: m.end,
                        Wildcard: "lte"
                    });
                }
            }
        }
        return filter;
    },
    getKendoGridId:function (kendoGrid,key) {

        var ids = [];
        var rows = kendoGrid.select();
        if(!rows.length > 0) {
            YM.Message.toast({ title: "警告", message: "请选择数据！", type: "warning" });
            return;
        }
        rows.each(function(i, row) {
            var dataItem = kendoGrid.dataItem(row);
            if(dataItem) {
                ids.push(dataItem[key]);
            }
        });
        return ids;
    }
}

YM.KendoGrid = {
    getDataSourceConfig: function (idField, readUrl, filter) {//表格配置初始化
        return {
            schema: {
                model: { id: idField }, data: function (response) {
                    return response.list || response.body || response.data || response.rows || response; //<========根据字段源
                }, total:  "totalItemCount"||"total"//<======根据字段源
            }, transport: {
                parameterMap: function (options) {
                    return JSON.stringify(options);//后台接收参数必须使用“data”
                }, read: getTransport(readUrl)
            }, serverPaging: true, serverFiltering: true, serverSorting: true, filter: filter, refresh: true, pageSize: 20
        }
    },
    getDataSource: function (idField, readUrl, filter) {
        return new kendo.data.DataSource(getDataSourceConfig(idField, readUrl, filter));
    },
    getTransport: function (url, type, dataType, contentType) {
        return {
            contentType: contentType || "application/json",
            dataType: dataType || "json",
            type: type || "POST",
            async: false,
            url: url
        };
    },
    toGridFilter: function (o) { //转为表过滤对象
        var filter = {//创建filter对象
            filters: []//过滤条件
        };
        for (var i in o) {
            var m = o[i];
            if ($.trim(m) && (typeof m === "string" || typeof m === "number")) {
                filter.filters.push({
                    name: i,
                    value: $.trim(m),
                    Wildcard: "eq"
                });
            } else if (typeof m === "object" && $.trim(m.value)) {
                if (m.wildcard === "in") {
                    var value = m.value.split("/");
                    filter.filters.push({
                        name: i,
                        value: value,
                        Wildcard: m.Wildcard || "in"
                    });
                } else {
                    filter.filters.push({
                        name: i,
                        value: $.trim(m.value),
                        Wildcard: m.Wildcard || "eq"
                    });
                }
            } else if (typeof m === "object" && m.type === "range") {
                if (m.start) filter.filters.push({
                    name: i,
                    value: m.start,
                    Wildcard: "gte" 
                });
                if (m.end) {
                    filter.filters.push({
                        name: i,
                        value: m.end,
                        Wildcard: "lte"
                    });
                }
            }
        }
        filter.logic=(filter.logic||"and");
        return filter;
    }
}


YM.KendoTreeGrid = {
    createTreeListDataSource: function (idField, readUrl, parentField, expanded) {//树形表数据源处理 
        var datasource = {
            schema: {
                model: {
                    id: idField,
                    fields: {parentId: {field: parentField, nullable: true}},
                    expanded: expanded||true//<=====是否默认展开==//
                }, data: function (response) {
                    return response.list || response.body || response
                }
            }, transport: {
                parameterMap: function (options) {
                    return kendo.stringify(options)
                }, read: YM.KendoTreeGrid.createTransport(readUrl)
            }, serverFiltering: true, serverSorting: true
        };
        return new kendo.data.TreeListDataSource(datasource);
    },
    createTransport: function (url, type, dataType, contentType) {
        return url ? {
            contentType: contentType || "application/json",
            dataType: dataType || "json",
            type: type || "POST",
            url: url
        } : undefined;
    }
}

YM.KendoModal = {
    //setModal: function(html = "",lg=true, w = "auto") {//创建 设置 弹出框
    setModal: function (html, lg, w) {//创建 设置 弹出框
        if ($("#modal_theme_primary").length <= 0) {
            $("body").append('<div id="modal_theme_primary" class="modal fade" aria-hidden="false" style="display: none;"><div class="modal-dialog modal-lg"><div class="modal-content"></div></div></div>');
        }
        $("#modal_theme_primary>div.modal-dialog").css({
            "min-width": w,
            "max-width": w
        });
        if (!lg) { $("#modal_theme_primary>div.modal-dialog.modal-lg").removeClass("modal-lg"); }
        $("#modal_theme_primary>div.modal-dialog>div.modal-content").html(html);
        $("#modal_theme_primary").modal({
            backdrop: 'static',
            keyboard: false
        });
    },
    //modal: function(url, data = {},lg=true, w = "auto") { //弹出框 封装
    modal: function (url, data, lg, w) { //弹出框 封装  
        if (typeof (data) == "boolean") { [data, lg] = [{}, data]; }
        if (typeof (url) == "string") {
            $.ajax({
                type: "get",
                url: url,
                data: data,
                async: true,
                dataType: "html",
                success: function (data) {
                    lg = lg == undefined ? true : lg;
                    w = w == undefined ? "auto" : w;

                    YM.KendoModal.setModal(data, lg, w);
                }
            });
        }
    }

}


YM.Message = {
    notify: function (from, align, icon, type, animIn, animOut, title, message) {
        $.notify({
            icon: icon,
            title: title || ":",
            message: message || "",
            url: ""
        }, {
            element: "body",
            type: type,
            allow_dismiss: true,
            placement: {
                from: from,
                align: align
            },
            offset: {
                x: 30,
                y: 30
            },
            spacing: 10,
            z_index: 1999,
            delay: 2500,
            timer: 1000,
            url_target: "_blank",
            mouse_over: false,
            animate: {
                enter: animIn,
                exit: animOut
            },
            template: '<div data-notify="container" class="alert alert-dismissible alert-{0}" role="alert"  >' +
            '<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>' +
            '<span data-notify="icon"></span> ' +
            '<span data-notify="title">{1}</span> ' +
            '<span data-notify="message">{2}</span>' +
            '<div class="progress" data-notify="progressbar">' +
            '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
            "</div>" +
            '<a href="{3}" target="{4}" data-notify="url"></a>' +
            "</div>"
        });
    },
    toast: function (o) {//js调用消息框 封装
        if (typeof (o) == "object") {
            this.notify(o.from || "top", o.align || "right", o.icon, o.type || "-light", o.animIn || "animated fadeInUp", o.animOut || "animated fadeOutRight", o.title || "提示:", o.message || "无消息");
        }
    }
}

//关闭弹出框
YM.CloseDialog = {
    closeDialog:function () {
        $('#modal_theme_primary').modal('hide');
        $('#myModal').on('hide.bs.modal', function () {
            $("#modal_theme_primary>div.modal-dialog>div.modal-content").html('');
        });

    }
}
