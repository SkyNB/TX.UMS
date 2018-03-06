'use strict';

var app = angular.module('RoadnetApp');
app.controller('VehicleTypeUpdateController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {
        $.ajax({
            url: contextPath + "/vehicleType/get/" + $stateParams.id,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.vehicleType = result.body;

                if ($scope.vehicleType) {
                    $("#updateVehicleType").modal();
                    setTimeout(function () {
                        $scope.$apply();
                    }, 200)
                }
            } else {
                toastr.error(result.message);
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };

    $scope.validate = $("#updateVehicleTypeForm").validate();

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            url: contextPath + "/vehicleType/edit",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.vehicleType)
        }).done(function (result) {
            if (result.success) {
                $("#updateVehicleType").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error(result.message);
            }
        });
    };

    $scope.init();
});