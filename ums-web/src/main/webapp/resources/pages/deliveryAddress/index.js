'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/deliveryAddress/create',
            controller: "DeliveryAddressCreateController",
            resolve: getDeps([contextPath + '/resources/pages/deliveryAddress/create.js'])
        })
        .state('update/:id', {
            url: '/update/:id',
            reload: true,
            templateUrl: contextPath + '/deliveryAddress/edit',
            controller: "DeliveryAddressUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/deliveryAddress/update.js'])
        })
});

app.controller('DeliveryAddressController', function ($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function () {
        App.resizeGrid();
        $scope.grid = $("#gridDeliveryAddress").data("kendoExGrid");
    });

    $scope.create = function () {
        $("#addDeliveryAddress").modal("show");
    }

    $scope.forbidden = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/deliveryAddress/inactivate/" + data.deliveryAddressId,
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
            url: contextPath + "/deliveryAddress/activate/" + data.deliveryAddressId,
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

    $scope.search = function () {
        $scope.data.filter($("#fromSearchDeliveryAddress").serializeArray());
    };

    $rootScope.data = getDataSource("deliveryAddressId", contextPath + "/deliveryAddress/search");

    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $scope.data,
        columns: [{
            field: "ownerName",
            title: "客户",
            width: 100
        }, {
            field: "companyName",
            title: "单位名称",
            width: 120
        }, {
            field: "contactMan",
            title: "联系人",
            width: 90,
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.deliveryAddressId + "' data-target='#updateDeliveryAddress' data-toggle='modal'>" + dataItem.contactMan + "</a>";
            }
        }, {
            field: "mobilePhoneNo",
            title: "手机号",
            width: 130
        }, {
            field: "telephoneNo",
            title: "电话号",
            width: 90
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
            field: "active",
            title: "是否启用",
            width: 80,
            template: "#= active?'是':'否' #"
        }, {
            field: "remark",
            title: "备注"
        }]
    });
});
