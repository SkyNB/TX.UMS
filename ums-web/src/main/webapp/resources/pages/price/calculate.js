angular.module('RoadnetApp').controller('CalculateController', ['$rootScope', '$scope', '$filter', CalculateController]);
function CalculateController($rootScope, $scope, $filter) {
    $("#calculate").modal();

    $scope.price = {};
    $scope.reset = function () {
        $scope.price = {};
        $scope.price.rangeDto = [{}];
    };
    $scope.validate = $("#calculateForm").validate();
    $scope.exacctData = new kendo.data.DataSource({data: []});
    $scope.ownerOptions = {
        dataSource: getDataListSource("id", contextPath + "/price/getOwnerCode"),
        filter: "contains",
        dataTextField: "text",
        dataValueField: "value"
    };
    $scope.itemsColumns = [{
        field: "", title: "单号", width: 150
    }, {
        field: "customerOrderNo", title: "客户单号", width: 100
    }];
    $scope.result = "";
    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        $.ajax({
            url: contextPath + "/price/calculate",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.price)
        }).done(function (result) {
            if (result.success) {
                $scope.result = JSON.stringify(result.body);
                $scope.$apply();
            } else {
                toastr.error("计算失败！" + result.message);
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
}