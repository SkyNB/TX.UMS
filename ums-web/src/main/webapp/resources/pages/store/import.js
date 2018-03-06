'use strict';
var app = angular.module('RoadnetApp');
app.controller("StoreImportController", function ($scope, $rootScope) {
    $("#importStore").modal();
    $scope.submit = function () {
        $("#importStoreForm").ajaxSubmit({
            url: contextPath + "/store/import",
            success: function (result) {
                if (result.success) {
                    if (result.body && result.body.length > 0) {
                        $scope.resultData.data(result.body);
                    } else {
                        $("#importStore").modal("hide");
                        $rootScope.data.query();
                    }
                    App.toastr("导入成功", "success");
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