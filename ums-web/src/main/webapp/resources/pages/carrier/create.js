'use strict';
var app = angular.module('RoadnetApp');

app.controller('CarrierCreateController', function ($scope, $rootScope) {
    $("#addCarrier").modal();

    $scope.carrier = {
        settleCycle: 'MONTY_PAY',
        paymentType: 'GIRO_BACK',
        calculateType: 'VOLUME',
        transportType: 'HIGHWAY_LTL',
        type: 'SPECIAL_LINE'
    };

    $scope.reset = function () {
        $scope.carrier = {};
    }

    $scope.validate = $("#addCarrierForm").validate({
        messages: {
            mobilephoneNo: {
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
            url: contextPath + "/carrier/create",
            data: JSON.stringify($scope.carrier)
        }).done(function (result) {
            if (result.success) {
                $("#addCarrier").modal("hide");
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