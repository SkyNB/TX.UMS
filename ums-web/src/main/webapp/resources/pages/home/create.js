angular.module('RoadnetApp').controller('RoleCreateController', function ($rootScope, $scope, $http, $timeout) {
    $("#addRole").modal();

    $scope.role = {};
    $scope.reset = function () {
        $scope.role = {};
    };
    $scope.validate = $("#addRoleForm").validate();
    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        $.ajax({
            url: contextPath + "/role/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.role)
        }).done(function (result) {
            if (result.success) {
                $("#addRole").modal("hide");
                $scope.reset();
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
});