var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/price/create',
            controller: "PriceCreateController",
            resolve: getDeps([contextPath + '/resources/pages/price/create.js'])
        })
        .state('import', {
            url: '/import',
            reload: true,
            templateUrl: contextPath + '/price/import',
            controller: "PriceImportController",
            resolve: getDeps([contextPath + '/resources/pages/price/import.js'])
        })
        .state('calculate', {
            url: '/calculate',
            reload: true,
            templateUrl: contextPath + '/price/calculate',
            controller: "CalculateController",
            resolve: getDeps([contextPath + '/resources/pages/price/calculate.js'])
        })
        .state('update/:priceId', {
            url: '/update/:priceId',
            reload: true,
            templateUrl: contextPath + '/price/update',
            controller: "PriceUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/price/update.js'])
        })
});
app.controller('PriceController', ['$scope', '$rootScope', PriceController]);

function PriceController($scope, $rootScope) {
    $scope.search = function () {
        $scope.data.filter($("#fromSearchPrice").serializeArray());
    };

    $scope.ownerOptions = {
        dataSource: getDataListSource("id", contextPath + "/price/getOwnerCode"),
        filter: "contains",
        dataTextField: "text",
        dataValueField: "value"
    };
    $rootScope.data = getDataSource("priceId", contextPath + "/price/search");
    $scope.gridOptions = {
        dataSource: $scope.data, dataBound: function () {
            $scope.grid = $("#gridPrice").data("kendoExGrid");
        }, detailInit: function (e) {
            $("<div/>").appendTo(e.detailCell).kendoGrid({
                dataSource: getDataListSource("rangeId", contextPath + "/price/findRanges/" + e.data.priceId),
                sortable: true,
                columns: [{
                    field: "minAmount", title: "最低收费", width: 120
                }, {
                    field: "rangeStart", title: "区间最小值", width: 120
                }, {
                    field: "rangeEnd", title: "区间最大值", width: 120
                }, {
                    field: "unitPrice", title: "单价", width: 80
                }]
            });
        }, columns: [{
            field: "ownerCode", title: "客户编码", template: function (dataItem) {
                return "<a href='#/update/" + dataItem.priceId + "' data-target='#updatePrice' data-toggle='modal'>" + dataItem.ownerName + "</a>";
            }
        }, {
            field: "transportType", title: "运输方式", values: transportTypes
        }, {
            field: "orgin", title: "起始地", template: "#= orginName#"
        }, {
            field: "destination", title: "目的地", template: "#= destinationName#"
        }, {
            field: "productCategory", title: "产品分类"
        }, {
            field: "expenseAccount", title: "费用科目", template: "#= expenseName#"
        }, {
            field: "vehicleType", title: "车型", template: "#= vehicleTypeName#"
        }, {
            field: "calcFormula.text", title: "计费公式"
        }, {
            field: "calcAttr.text", title: "计费属性"
        }]
    };
}
