'use strict';

/**
 * UserCreateController
 * @constructor
 */
angular.module('RoadnetApp').controller('UserCreateController', function ($rootScope, $scope, $http, $timeout) {
    $("#addUser").modal();
    $("#site").hide();
    $scope.dropdownTreeview = {
        treeView: {
            dataSource: getHierarchicalDataSource("organizationId",contextPath +'/organization/getHierarchical',null,
                "items"),
            dataTextField: "name"
        }
        , valueField: "code", textField: "name"
    };
    $scope.user = {};

    $scope.siteOptions = {
        placeholder: "选择站点...",
        dataTextField: "name",
        dataValueField: "code"
    }

    $scope.getSites = function() {
        $scope.user.siteCodes = [];
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath + "/site/getByBranchCode/" + $("#orgCode").val()
        }).done(function(result) {
            if(result.length > 0) {
                $("#site").show();
                var dataSource = new kendo.data.DataSource({
                    data: result
                });
                var multiSelect = $("#siteMulti").data("kendoMultiSelect");
                multiSelect.setDataSource(dataSource);
            } else {
                $("#site").hide();
            }

        }).fail(function() {
            App.toastr("提交失败","error");
        }) ;
    }

    $scope.validate = $("#addUserForm").validate();

    $scope.submit = function () {

        if (!$scope.validate.valid())return;
        $.ajax({
            url: contextPath + "/user/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.user)
        }).done(function (result) {
            if (result.success) {
                $("#addUser").modal("hide");
                $scope.user ={};
                $rootScope.data.query();
            } else {
                App.toastr("保存失败！" + result.message, "error");
            }
        }).fail(function(){
            App.toastr("提交失败","error");
        });
    };
});