'use strict';
angular.module('RoadnetApp').controller('OrganizationUpdateController', function ($rootScope, $scope, $stateParams, $http, $timeout) {

    $scope.dropdownTreeview = {
        treeView: {
            dataSource: getHierarchicalDataSource("organizationId", contextPath + '/organization/getHierarchical', null,
                "items"),
            dataTextField: "name"
        }
        , valueField: "organizationId", textField: "name"
    };
    $scope.init = function () {
        $.ajax({
            url: contextPath + "/organization/get/" + $stateParams.organizationId,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            $scope.organization = result;
            if ($scope.organization) {
                $scope.organization.type = result.type.name;
                $("#updateOrganization").modal();
                $scope.$apply();
            }
        }).fail(function(){
            App.toastr("提交失败","error");
        });
    };
    $scope.branches = [{code: "GZ", name: "广州分公司"}, {code: "SZ", name: "深圳分公司"}];
    $scope.validate = $("#updateOrganizationForm").validate();
    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        $.ajax({
            url: contextPath+'/organization/update',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.organization)
        }).done(function (result) {
            if (result.success) {
                $("#updateOrganization").modal("hide");
                toastr.success("保存成功!");
                $rootScope.treeData.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        });
    };
    $scope.init();
});