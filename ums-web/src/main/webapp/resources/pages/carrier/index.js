'use strict';/*严格模式，错了，会报错*/
var app = angular.module('RoadnetApp');/*master中*/

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {/*create随意定义*/
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/carrier/create',
            controller: "CarrierCreateController",/*这个名字随意定义，在下面的create.js中使用*/
            resolve: getDeps([contextPath + '/resources/pages/carrier/create.js'])
        })
        .state('update/:id', {
            url: '/update/:id',
            reload: true,
            templateUrl: contextPath + '/carrier/edit',
            controller: "CarrierUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/carrier/update.js'])
        })
        .state('import', {
            url: '/import',
            reload: true,
            templateUrl: contextPath + '/carrier/import',
            controller: "CarrierImportController",
            resolve: getDeps([contextPath + '/resources/pages/carrier/import.js'])
        })
});

app.controller('CarrierController', function ($scope, $rootScope) {
    $scope.dataBound = function () {
        $scope.grid = $("#gridCarrier").data("kendoExGrid");
    }

    $scope.search = function () {
        $scope.data.filter($("#fromSearchCarrier").serializeArray());
    };

    $scope.inactivate = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/carrier/inactivate/" + data.code,
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

    $scope.activate = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/carrier/activate/" + data.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.data.query();
            } else {
                App.toastr(result.message, "error");
            }
        });
    }

    $rootScope.data = getDataSource("carrierId", contextPath + "/carrier/search");

    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $scope.data, /*数据源*/
        pageable: false,
        columns: [{
            field: "code",
            title: "编码",
            width: 80
        }, {
            field: "name",
            title: "名称",
            width: 100,
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.carrierId + "' data-target='#updateCarrier' data-toggle='modal'>" + dataItem.name + "</a>";
            }
        }, {
            field: "contactMan",
            title: "联系人",
            width: 90
        }, {
            field: "telephoneNo",
            title: "电话号",
            width: 85
        }, {
            field: "mobilephoneNo",
            title: "手机号",
            width: 130
        }, {
            field: "isActive",
            title: "是否启用",
            width: 80,
            template: "#=isActive?'是':'否'#"
        }, {
            field: "type.text",
            title: "类型",
            width: 70
        }, {
            field: "settleCycle.text",
            title: "结算周期"
        }, {
            field: "paymentType.text",
            title: "付费方式"
        }, {
            field: "calculateType.text",
            title: "计费方式"
        }, {
            field: "transportType.text",
            title: "运输方式"
        }, {
            field: "branchName",
            title: "分公司"
        }, {
            field: "province",
            title: "省"
        }, {
            field: "city",
            title: "市"
        }, {
            field: "remark",
            title: "备注"
        }]
    });
});