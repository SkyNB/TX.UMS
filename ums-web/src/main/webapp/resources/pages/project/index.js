'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/project/create',
            controller: "ProjectCreateController",
            resolve: getDeps([contextPath + '/resources/pages/project/create.js'])
        })
        .state('update/:code', {
            url: '/update/:code',
            reload: true,
            templateUrl: contextPath + '/project/edit',
            controller: "ProjectUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/project/update.js'])
        })
});

app.controller('ProjectController', function ($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function () {
        App.resizeGrid();
        $scope.grid = $("#gridProject").data("kendoExGrid");
    });

    $scope.search = function () {
        $scope.data.filter($("#fromSearchProject").serializeArray());
    };


    $scope.forbidden = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/project/forbidden/" + data.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.data.query();
            } else {
                App.toastr(result.message, "error");
            }
        });
    };

    $scope.invocation = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/project/invocation/" + data.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.data.query();
            } else {
                App.toastr(result.message, "error");
            }
        });
    };

    $rootScope.data = getDataSource("projectId", contextPath + "/project/search"); /*new kendo.data.DataSource({
        schema: {
            model: {
                id: "projectId"
            },
            data: function (response) {
                return response.list;
            },
            total: 'total'
        },
        transport: {
            parameterMap: function (options) {
                return kendo.stringify(options);
            },
            read: {
                contentType: 'application/json',
                dataType: 'json',
                type: 'POST',
                url: contextPath + "/project/search"
            }
        },
        pageSize: 20,
        serverPaging: true,
        serverFiltering: true,
        serverSorting: true
    });*/

    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $scope.data,
        columns: [{
            field: "code",
            title: "编码",
            width: 100
        }, {
            field: "name",
            title: "名称",
            width: 120,
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.code + "' data-target='#updateProject' data-toggle='modal'>" + dataItem.name + "</a>";
            }
        }, {
            field: "customerName",
            title: "客户",
            width: 100
        }, {
            field: "branchName",
            title: "分公司",
            width: 100
        }, {
            field: "settleCycle.text",
            title: "结算周期",
            width: 100
        }, {
            field: "paymentType.text",
            title: "支付方式",
            width: 100
        }, {
            field: "calculateType.text",
            title: "计费方式",
            width: 100
        }, {
            field: "handoverType.text",
            title: "交接方式",
            width: 100
        }, {
            field: "receivableDataSource.text",
            title: "计费来源",
            width: 100
        }, {
            field: "packCoefficient",
            title: "打包系数",
            width: 100
        }, /*{
            field: "lightGoods",
            title: "轻货(kg/m³)",
            width: 100
        }, {
            field: "lightThrowGoods",
            title: "轻抛货(kg/m³)",
            width: 100
        }, {
            field: "commonGoods",
            title: "普通货(kg/m³)",
            width: 100
        }, {
            field: "heavyThrowGoods",
            title: "重抛货(kg/m³)",
            width: 100
        }, {
            field: "heavyGoods",
            title: "重货(kg/m³)",
            width: 100
        },*/ {
            field: "active",
            title: "是否启用",
            width: 80,
            template: "#= active?'是':'否' #"
        }/*, {
         field: "createUserName",
         title: "创建人",
         width: 100
         }, {
         field: "createTime",
         title: "创建时间",
         width: 160
         }, {
         field: "modifyUserName",
         title: "修改人",
         width: 100
         }, {
         field: "modifyTime",
         title: "修改时间",
         width: 160
         }*/, {
            field: "remark",
            title: "备注"
        }]
    });
});
