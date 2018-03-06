'use strict';

var app = angular.module('RoadnetApp');

app.controller('CollectingCreateController', function ($scope, $rootScope) {
    $scope.collectingAddress = {};

    $scope.innit = function () {
        $("#addCollectingAddress").modal("show");
    }

    $scope.validate = $("#addCollectingAddressForm").validate({
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
        $scope.collectingAddress = {};
    }

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            url: contextPath + "/collectingAddress/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.collectingAddress)
        }).done(function (result) {
            if (result.success) {
                $("#addCollectingAddress").modal("hide");
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