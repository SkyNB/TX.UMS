'use strict';

var app = angular.module('RoadnetApp');
app.controller('ShipAddressUpdateController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {
        $.ajax({
            url: contextPath + "/shipAddress/get/" + $stateParams.id,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.shipAddress = result.body;
                if ($scope.shipAddress) {
                    $("#updateShipAddress").modal();
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

    $scope.validate = $("#updateShipAddressForm").validate({

    });

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            url: contextPath + "/shipAddress/edit",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.shipAddress)
        }).done(function (result) {
            if (result.success) {
                $("#updateShipAddress").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error(result.message);
            }
        });
    };

    $scope.init();
});