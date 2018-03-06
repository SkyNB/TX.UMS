'use strict';
angular.module('RoadnetApp').controller('UpdatePasswordController', function ($rootScope, $scope, $http, $timeout) {

    if($rootScope.userIds)
    $("#updatePassword").modal("show");
    $scope.validate = $("#updatePasswordForm").validate();

    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        $scope.user.userIds = $rootScope.userIds;
        $.ajax({
            url: contextPath + '/user/resetPassword',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.user)
        }).done(function (result) {
            if (result.success) {
                $("#updatePassword").modal("hide");
                $scope.user.password = "";
                $scope.user.userIds =[];
            } else {
                toastr.error("保存失败！" + result.message, "", {
                    positionClass: "toast-top-center",
                    closeButton: true,
                    timeOut: 3000
                });
            }
        }).fail(function(){
            App.toastr("提交失败","error");
        });
    };
});