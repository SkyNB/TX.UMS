var app = angular.module('RoadnetApp');
app.controller("PriceImportController", function ($scope, $rootScope) {
    $("#importPrice").modal();
    $scope.submit = function () {
        $("#importPriceForm").ajaxSubmit({
            url: contextPath + "/price/importPrice",
            success: function (result) {
                if (result.success) {
                    $scope.resultData.data(result.body);
                    $("#importPrice").modal("hide");
                    $rootScope.data.query();
                    $("#importPriceForm")[0].reset();
                    App.toastr("导入成功", "success");
                } else {
                    $scope.resultData.data(result.body||[]);
                    App.toastr(result.message, "error");
                }
            }
        })
    };
    $scope.resultData =new kendo.data.DataSource({data:[]});
    $scope.errorOptions = {
        dataSource:$scope.resultData,
        columns:[
            {title:"结果",field:"success",width:50},
            {title:"原因",field:"message",width:200}
        ]
    };

});