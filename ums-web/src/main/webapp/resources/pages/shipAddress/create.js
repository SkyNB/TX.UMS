'use strict';
var app = angular.module('RoadnetApp');

app.controller('ShipAddressCreateController', function ($scope, $rootScope) {
    $("#addShipAddress").modal();

    $scope.shipAddress = {};

    $scope.reset = function () {
        $scope.shipAddress = {};
    }

    $scope.validate = $("#addShipAddressForm").validate({
        messages: {
            mobilePhoneNo: {
                pattern: "手机号格式不正确"
            }
        }
    });

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/shipAddress/create",
            data: JSON.stringify($scope.shipAddress)
        }).done(function (result) {
            if (result.success) {
                $("#addShipAddress").modal("hide");
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