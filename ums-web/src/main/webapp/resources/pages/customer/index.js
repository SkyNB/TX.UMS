'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/customer/create',
            controller: "CustomerCreateController",
            resolve: getDeps([contextPath + '/resources/pages/customer/create.js'])
        })
        .state('update/:code', {
            url: '/update/:code', reload: true,
            templateUrl: contextPath + '/customer/edit',
            controller: "CustomerUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/customer/update.js'])
        })
        .state('import', {
            url: '/import', reload: true,
            templateUrl: contextPath + '/customer/import',
            controller: "CustomerImportController",
            resolve: getDeps([contextPath + '/resources/pages/customer/import.js'])
        })
});

app.controller('CustomerController', function ($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function () {
        App.resizeGrid();
        $scope.grid = $("#gridCustomer").data("kendoExGrid");
    });

    $scope.forbidden = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/customer/forbidden/" + data.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.data.query();
            } else {
                App.toastr("禁用失败！" + result.message, "error");
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
            url: contextPath + "/customer/invocation/" + data.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.data.query();
            } else {
                App.toastr("禁用失败！" + result.message, "error");
            }
        });
    };

    $scope.search = function () {
        $scope.data.filter($("#fromSearchCustomer").serializeArray());
    };

    $rootScope.data = getDataSource("customerId", contextPath + "/customer/search");

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
                return "<a href='#/update/" + dataItem.code + "' data-target='#updateCustomer' data-toggle='modal'>" + dataItem.name + "</a>";
            }
        }, {
            field: "ratingCode",
            title: "级别",
            width: 50
        }, {
            field: "contactMan",
            title: "联系人",
            width: 120
        }, {
            field: "contactPhone",
            title: "联系电话",
            width: 130
        }, {
            field: "active",
            title: "是否启用",
            width: 80,
            template: "#= active?'是':'否' #"
        }, {
            field: "createTime",
            title: "创建时间",
            width: 180
        }, {
            field: "remark",
            title: "备注"
        }]
    });
});
