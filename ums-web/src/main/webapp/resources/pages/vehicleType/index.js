'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/vehicleType/create',
            controller: "VehicleTypeCreateController",
            resolve: getDeps([contextPath + '/resources/pages/vehicleType/create.js'])
        })
        .state('update/:id', {
            url: '/update/:id',
            reload: true,
            templateUrl: contextPath + '/vehicleType/edit',
            controller: "VehicleTypeUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/vehicleType/update.js'])
        })
});

app.controller('VehicleTypeController', function ($scope, $rootScope) {
    $scope.dataBound = function () {
        $scope.grid = $("#gridVehicleType").data("kendoExGrid");
    }

    $scope.search = function () {
        $scope.data.filter($("#fromSearchVehicleType").serializeArray());
    };

    $rootScope.data = getDataSource("vehicleTypeId", contextPath + "/vehicleType/search");

    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $scope.data,
        pageable: false,
        columns: [{
            field: "name",
            title: "名称",
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.vehicleTypeId + "' data-target='#updateVehicleType' data-toggle='modal'>" + dataItem.name + "</a>";
            }
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
            field: "loadVolume",
            title: "荷载体积(m³)"
        }, {
            field: "loadWeight",
            title: "荷载重量(t)"
        }]
    });
});