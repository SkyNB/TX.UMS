'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/store/create',
            controller: "StoreCreateController",
            resolve: getDeps([contextPath + '/resources/pages/store/create.js'])
        })
        .state('update/:code', {
            url: '/update/:code',
            reload: true,
            templateUrl: contextPath + '/store/update',
            controller: "StoreUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/store/update.js'])
        })
        .state('import', {
            url: '/import',
            reload: true,
            templateUrl: contextPath + '/store/import',
            controller: "StoreImportController",
            resolve: getDeps([contextPath + '/resources/pages/store/import.js'])
        })
});

app.controller('StoreController', function ($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function () {
        App.resizeGrid();
        $scope.grid = $("#gridStore").data("kendoExGrid");
    });

    $scope.create = function () {
        $("#addStore").modal("show");
    }

    $scope.forbidden = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/store/forbidden/" + data.code,
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
            url: contextPath + "/store/invocation/" + data.code,
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
        $scope.data.filter($("#fromSearchStore").serializeArray());
    };

    $rootScope.data = getDataSource("storeId", contextPath + "/store/search");

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
                return "<a href='#/update/" + dataItem.code + "' data-target='#updateStore' data-toggle='modal'>" + dataItem.name + "</a>";
            }
        }, {
            field: "ownerName",
            title: "客户",
            width: 70
        }, {
            field: "brands",
            title: "品牌",
            width: 100
        }, {
            field: "contactMan",
            title: "联系人",
            width: 90
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
