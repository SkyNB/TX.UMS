'use strict';
var app = angular.module('RoadnetApp');

app.controller('VehicleTypeCreateController', function ($scope, $rootScope) {
    $("#addVehicleType").modal();

    $scope.vehicleType = {};

    $scope.reset = function () {
        $scope.vehicleType = {};
    }

    $scope.validate = $("#addVehicleTypeForm").validate();

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/vehicleType/create",
            data: JSON.stringify($scope.vehicleType)
        }).done(function (result) {
            if (result.success) {
                $("#addVehicleType").modal("hide");
                $scope.reset();
                $rootScope.data.query();
            } else {
                App.toastr(result.message, "error");
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
});