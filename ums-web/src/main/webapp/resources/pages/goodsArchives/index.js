'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/goodsArchives/create',
            controller: "GoodsArchivesCreateController",
            resolve: getDeps([contextPath + '/resources/pages/goodsArchives/create.js'])
        })
        .state('update/:archivesId', {
            url: '/update/:archivesId',
            reload: true,
            templateUrl: contextPath + '/goodsArchives/update',
            controller: "GoodsArchivesUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/goodsArchives/update.js'])
        })
        .state('import', {
            url: '/import',
            reload: true,
            templateUrl: contextPath + '/goodsArchives/import',
            controller: "GoodsArchivesImportController",
            resolve: getDeps([contextPath + '/resources/pages/goodsArchives/import.js'])
        })
});

app.controller('GoodsArchivesController', function ($scope, $rootScope) {

    $scope.search = function () {
        $scope.data.filter($("#fromSearchGoodsArchives").serializeArray());
    };
    $rootScope.data = getDataSource("archivesId", contextPath + "/goodsArchives/search");

    $scope.getSelectIds = function () {
        var row = $scope.grid.select();
        if (row.length <= 0) {
            App.toastr("请选择数据", "warning");
            return null;
        }
        var archivesIds = [];
        var rows = $scope.grid.select();
        rows.each(function (i, row) {
            var dataItem = $scope.grid.dataItem(row);
            if (dataItem) {
                archivesIds.push(dataItem.archivesId);
            }
        });
        return archivesIds;
    };

    $scope.delete = function () {
        var archivesIds = $scope.getSelectIds();
        if (archivesIds == null || archivesIds.length < 1) return;
        $.ajax({
            url: contextPath + "/goodsArchives/delete",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(archivesIds)
        }).done(function (result) {
            if (result.success) {
                toastr.success("删除成功", "");
                $rootScope.data.query();
            } else {
                App.toastr("删除失败！" + result.message, "error");
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
    $scope.dataBound =function(){
        $scope.grid = $("#gridGoodsArchives").data("kendoExGrid");
    };
    $scope.gridOptions ={
        dataSource: $scope.data,
        columns: [{
            field: "code",
            title: "编码",
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.archivesId + "' data-target='#updateGoodsArchives' data-toggle='modal'>" + dataItem.code + "</a>";
            }
        }, {
            field: "name",
            title: "名称"
        },{
            field: "customerName",
            title: "客户"
        },  {
            field: "model",
            title: "型号"
        },  {
            field: "category.text",
            title: "分类"
        },  {
            field: "volume",
            title: "体积"
        },  {
            field: "weight",
            title: "重量"
        }]
    };
});
