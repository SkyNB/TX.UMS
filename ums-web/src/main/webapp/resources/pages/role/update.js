'use strict';
angular.module('RoadnetApp').controller('RoleUpdateController', function ($rootScope, $scope,$stateParams, $http, $timeout) {
        $scope.init = function () {
            $.ajax({
                url: contextPath + "/role/get/" + $stateParams.roleId,
                type: "POST",
                contentType: "application/json"
            }).done(function (result) {
                $scope.role = result.body;
                console.info(JSON.stringify($scope.role));
                if ($scope.role) {
                    $("#updateRole").modal();
                    $scope.$apply();
                }
            });
        };
        $scope.validate = $("#addRoleForm").validate();
        $scope.submit = function () {
            if (!$scope.validate.valid())return;
            $.ajax({
                url: contextPath+'/role/update',
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify($scope.role)
            }).done(function (result) {
                if (result.success) {
                    $("#updateRole").modal("hide");
                    $rootScope.data.query();
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
        $scope.init();
    });