'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/shipAddress/create',
            controller: "ShipAddressCreateController",
            resolve: getDeps([contextPath + '/resources/pages/shipAddress/create.js'])
        })
        .state('update/:id', {
            url: '/update/:id',
            reload: true,
            templateUrl: contextPath + '/shipAddress/edit',
            controller: "ShipAddressUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/shipAddress/update.js'])
        })
});

app.controller('ShipAddressController', function ($scope, $rootScope) {
    $scope.dataBound = function () {
        $scope.grid = $("#gridShipAddress").data("kendoExGrid");
    }

    $scope.search = function () {
        $scope.data.filter($("#fromSearchShipAddress").serializeArray());
    };

    $scope.inactivate = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/shipAddress/inactivate/" + data.code,
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
            url: contextPath + "/shipAddress/activate/" + data.code,
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

    $rootScope.data = getDataSource("shipAddressId", contextPath + "/shipAddress/search");

    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $scope.data,
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
                return "<a href='#/update/" + dataItem.shipAddressId + "' data-target='#updateShipAddress' data-toggle='modal'>" + dataItem.name + "</a>";
            }
        }, {
            field: "contactMan",
            title: "联系人",
            width: 90
        },  {
            field: "mobilePhoneNo",
            title: "手机号",
            width: 130
        }, {
            field: "telephoneNo",
            title: "电话号",
            width: 85
        }, {
            field: "isActive",
            title: "是否启用",
            width: 80,
            template: "#=isActive?'是':'否'#"
        }, {
            field: "carrierName",
            title: "承运商",
            width: 100
        }, {
            field: "province",
            title: "省",
            width: 80
        }, {
            field: "city",
            title: "市",
            width: 80
        }, {
            field: "address",
            title: "详细地址",
            width: 250,
            template: function(dataItem) {
                var ad = "";
                if(dataItem.area)
                    ad += dataItem.area;
                if(dataItem.street)
                    ad += dataItem.street;
                if(dataItem.address)
                    ad += dataItem.address;
                return ad;
            }
        }, {
            field: "remark",
            title: "备注"
        }]
    });
});