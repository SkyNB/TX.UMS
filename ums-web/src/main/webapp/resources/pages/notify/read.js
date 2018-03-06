'use strict';
var app = angular.module('RoadnetApp');

app.controller('ReadController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/notify/detail/" + $stateParams.id
        }).done(function (result) {
            if (result.success) {
                $scope.notification = result.body;
                if ($scope.notification) {
                    $("#readInfo").modal();
                    setTimeout(function () {
                        $scope.$apply();
                    }, 200)
                }
            } else {
                App.toastr(result.message, "error");
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        })
    };

    $("#readInfo").closest(".modal").on('hide.bs.modal', function () {
        $.ajax({
            url: contextPath + "/notify/read/" + $scope.notification.id,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $rootScope.data.query();
            } else {
                App.toastr("重置通知状态失败！", "error");
            }
        }).fail(function () {
            App.toastr("重置通知状态失败！", "error");
        });
    });

    $scope.init();
});