'use strict';

var app = angular.module('RoadnetApp');

app.controller('CollectingUpdateController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {

        $.ajax({
            url: contextPath + "/collectingAddress/getByCode/" + $stateParams.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.collectingAddress = result.body;
                $scope.collectingAddress.type = result.body.type.name;
                if ($scope.collectingAddress) {
                    $("#updateCollectingAddress").modal();
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


    $scope.validate = $("#updateCollectingAddressForm").validate({
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
            url: contextPath + "/collectingAddress/edit",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.collectingAddress)
        }).done(function (result) {
            if (result.success) {
                $("#updateCollectingAddress").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        });
    };

    $scope.init();
});



