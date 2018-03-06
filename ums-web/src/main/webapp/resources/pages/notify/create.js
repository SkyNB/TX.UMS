'use strict';
var app = angular.module('RoadnetApp');

app.controller('NotifyCreateController', function ($scope, $rootScope) {
    $("#addNotification").modal();

    $scope.notification = {};

    $scope.reset = function () {
        $scope.notification = {};
    }

    $scope.validate = $("#addNotificationForm").validate();

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/notify/create",
            data: JSON.stringify($scope.notification)
        }).done(function (result) {
            if (result.success) {
                $("#addNotification").modal("hide");
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