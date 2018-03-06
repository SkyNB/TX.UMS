'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/vehicle/create',
            controller: "VehicleCreateController",
            resolve: getDeps([contextPath + '/resources/pages/vehicle/create.js'])
        })
        .state('update/:id', {
            url: '/update/:id',
            reload: true,
            templateUrl: contextPath + '/vehicle/edit',
            controller: "VehicleUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/vehicle/update.js'])
        })
        .state('import', {
            url: '/import',
            reload: true,
            templateUrl: contextPath + '/vehicle/import',
            controller: "VehicleImportController",
            resolve: getDeps([contextPath + '/resources/pages/vehicle/import.js'])
        })
});

app.controller('VehicleController', function ($scope, $rootScope) {
    $scope.dataBound = function () {
        $scope.grid = $("#gridVehicle").data("kendoExGrid");
    }

    $scope.search = function () {
        $scope.data.filter($("#fromSearchVehicle").serializeArray());
    };

    $scope.forbidden = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/vehicle/inactivate/" + data.vehicleNo,
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
            url: contextPath + "/vehicle/activate/" + data.vehicleNo,
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

    $rootScope.data = getDataSource("vehicleId", contextPath + "/vehicle/search");

    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $scope.data,
        pageable: false,
        columns: [{
            field: "driver",
            title: "司机",
            width: 80
        }, {
            field: "vehicleNo",
            title: "车牌号",
            width: 85,
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.vehicleId + "' data-target='#updateVehicle' data-toggle='modal'>" + dataItem.vehicleNo + "</a>";
            }
        }, {
            field: "driverMobile",
            title: "联系电话",
            width: 130
        }, {
            field: "leaseType.text",
            title: "租赁方式",
            width: 80
        }, {
            field: "status.text",
            title: "状态",
            width: 50
        }, {
            field: "isActive",
            title: "是否启用",
            width: 80,
            template: "#= isActive?'是':'否'#"
        }, {
            field: "vehicleTypeName",
            title: "车型"
        }, {
            field: "length",
            title: "长(m)"
        }, {
            field: "width",
            title: "宽(m)"
        }, {
            field: "height",
            title: "高(m)"
        }, {
            field: "branchName",
            title: "分公司"
        }, {
            field: "siteName",
            title: "站点"
        }]
    });
});