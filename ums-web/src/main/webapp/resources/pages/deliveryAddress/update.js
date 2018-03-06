'use strict';

var app = angular.module('RoadnetApp');

app.controller('DeliveryAddressUpdateController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {
        $.ajax({
            url: contextPath + "/deliveryAddress/get/" + $stateParams.id,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.deliveryAddress = result.body;

                if ($scope.deliveryAddress) {
                    $("#updateDeliveryAddress").modal();
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

    $scope.validate = $("#updateDeliveryAddressForm").validate({
        messages: {
            mobilePhoneNo: {
                pattern: "输入正确的手机号格式"
            },
            telephoneNo: {
                pattern: "输入正确的电话号格式"
            }
        }
    });

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            url: contextPath + "/deliveryAddress/edit",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.deliveryAddress)
        }).done(function (result) {
            if (result.success) {
                $("#updateDeliveryAddress").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        });
    };

    $scope.init();
});



