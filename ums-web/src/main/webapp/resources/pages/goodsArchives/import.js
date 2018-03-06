'use strict';
var app = angular.module('RoadnetApp');
app.controller("GoodsArchivesImportController", function ($scope, $rootScope) {
    $("#goodsArchivesImport").modal();
    $scope.submit = function () {
        $("#goodsArchivesImportForm").ajaxSubmit({
            url: contextPath + "/goodsArchives/import",
            success: function (result) {
                if (result.success) {
                    $("#goodsArchivesImport").modal("hide");
                    $rootScope.data.query();
                    toastr.success("导入成功");
                } else {
                    toastr.error("导入失败！", result.message);
                }
            }
        })
    };
});