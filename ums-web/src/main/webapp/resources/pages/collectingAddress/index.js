'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/collectingAddress/create',
            controller: "CollectingCreateController",
            resolve: getDeps([contextPath + '/resources/pages/collectingAddress/create.js'])
        })
        .state('update/:code', {
            url: '/update/:code',
            reload: true,
            templateUrl: contextPath + '/collectingAddress/edit',
            controller: "CollectingUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/collectingAddress/update.js'])
        })
});

app.controller('CollectingAddressController', function ($scope, $rootScope) {
    $scope.$on('$viewContentLoaded', function () {
        App.resizeGrid();
        $scope.grid = $("#gridCollectingAddress").data("kendoExGrid");
    });

    $scope.create = function () {
        $("#addCollectingAddress").modal("show");
    }

    $scope.forbidden = function () {
        var row = $scope.grid.select();

        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }

        var data = $scope.grid.dataItem(row);

        $.ajax({
            url: contextPath + "/collectingAddress/inactivate/" + data.code,
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
            url: contextPath + "/collectingAddress/activate/" + data.code,
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
        $scope.data.filter($("#fromSearchCollectingAddress").serializeArray());
    };

    $rootScope.data = getDataSource("collectingAddressId", contextPath + "/collectingAddress/search"); /*new kendo.data.DataSource({
        schema: {
            model: {
                id: "collectingAddressId"
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
                url: contextPath + "/collectingAddress/search"
            }
        },
        pageSize: 20,
        serverPaging: true,
        serverFiltering: true,
        serverSorting: true
    });*/

    $scope.gridOptions = {
        dataSource: $scope.data,
        sortable: true,
        resizable: true,
        selectable: true,
        reorderable: true,
        pageable: {refresh: true, pageSize: 20, pageSizes: [20, 50, 100, 200, 500]},
        columns: [{
            field: "code",
            title: "编码",
            width: 100
        }, {
            field: "name",
            title: "名称",
            width: 120,
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.code + "' data-target='#updateCollectingAddress' data-toggle='modal'>" + dataItem.name + "</a>";
            }
        }, {
            field: "ownerName",
            title: "客户",
            width: 70
        }, {
            field: "type.text",
            title: "类型",
            width: 60
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
    };
});
