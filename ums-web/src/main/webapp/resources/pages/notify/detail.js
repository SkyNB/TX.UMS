'use strict';

var app = angular.module('RoadnetApp');

app.controller('NotifyDetailController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/notify/detail/" + $stateParams.id
        }).done(function (result) {
            if (result.success) {
                $scope.notification = result.body;
                if ($scope.notification) {
                    $("#notificationDetail").modal();
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
    }

    $scope.submit = function () {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/notify/recall/" + $("#readId").val()
        }).done(function (result) {
            if (result.success) {
                $("#notificationDetail").modal("hide");
                $rootScope.data.query();
                //location.reload();
            }
            else
                App.toastr(result.message, "error");
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    }

    $scope.init();
});