'use strict';

var app = angular.module('RoadnetApp');

app.controller('DeliveryAddressCreateController', function ($scope, $rootScope) {
    $scope.deliveryAddress = {};

    $scope.innit = function () {
        $("#addDeliveryAddress").modal("show");
    }

    $scope.validate = $("#addDeliveryAddressForm").validate({
        messages: {
            mobilePhoneNo: {
                pattern: "输入正确的手机号格式"
            },
            telephoneNo: {
                pattern: "输入正确的电话号格式"
            }
        }
    });

    $scope.reset = function () {
        $scope.deliveryAddress = {};
    }

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            url: contextPath + "/deliveryAddress/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.deliveryAddress)
        }).done(function (result) {
            if (result.success) {
                $("#addDeliveryAddress").modal("hide");
                $scope.reset();
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };

    $scope.innit();
});