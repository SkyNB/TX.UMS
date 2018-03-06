var app = angular.module('RoadnetApp');
app.controller("VehicleImportController", function ($scope, $rootScope) {
    $("#importVehicle").modal();
    $scope.submit = function () {
        $("#importVehicleForm").ajaxSubmit({
            url: contextPath + "/vehicle/import",
            type:"POST",
            success: function (result) {
                if (result.success) {
                    $rootScope.data.query();
                    if(result.body){
                        $scope.resultData.data(result.body);
                    }
                    else{
                        $("#importVehicle").modal("hide");
                    }
                    App.toastr(result.message, "success");
                } else {
                    $scope.resultData.data(result.body || []);
                    App.toastr(result.message || "导入失败", "error");
                }
            }
        })
    };

    $scope.resultData = new kendo.data.DataSource({data: []});
    $scope.errorOptions = {
        dataSource: $scope.resultData,
        columns: [
            {
                title: "结果",
                field: "success",
                template: "#=success?'成功':'失败'#",
                width: 50
            },
            {
                title: "原因",
                field: "message",
                width: 200
            }
        ]
    };
});