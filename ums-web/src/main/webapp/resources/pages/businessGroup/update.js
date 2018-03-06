'use strict';

var app = angular.module('RoadnetApp');

app.controller('BusinessGroupUpdateController', function ($scope, $rootScope, $stateParams) {
    $scope.customerOptions = {
        dataSource: [],
        sortable: true,
        resizable: true,
        columns: [{
            field: "code",
            title: "编码"
        }, {
            field: "name",
            title: "名称"
        }, {
            field: "remark",
            title: "备注"
        }]
    };

    $scope.init = function () {
        $.ajax({
            url: contextPath + "/businessGroup/getByCode/" + $stateParams.code,
            type: "POST",
        }).done(function (result) {
            $scope.businessGroup = result.body;
            if ($scope.businessGroup) {
                $("#updateBusinessGroup").modal();
                setTimeout(function () {
                    $scope.$apply();
                }, 200);
            }
        });
    };

    $scope.validate = $("#updateBusinessGroupForm").validate();

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            url: contextPath + '/businessGroup/edit',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.businessGroup)
        }).done(function (result) {
            if (result.success) {
                $("#updateBusinessGroup").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error(result.message);
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };

    $scope.init();
});



