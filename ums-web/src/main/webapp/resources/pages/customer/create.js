'use strict';

var app = angular.module('RoadnetApp');

app.controller('CustomerCreateController', function ($scope, $rootScope) {
    $("#addCustomer").modal();
    $scope.customer = {};

    $scope.toolbar = [{name: "create"}];
    $scope.dataSource = new kendo.data.DataSource({
        data: [],
        schema: {
            model: {
                fields: {
                    code: {type: "string", validation: {required: true}},
                    name: {type: "string", validation: {required: true}},
                    active: {type: "boolean", defaultValue: true},
                    remark: {type: "string"}
                }
            }
        }
    });

    $scope.columns = [{
        field: "code",
        title: "编码"
    }, {
        field: "name",
        title: "名称"
    }, {
        field: "active",
        title: "启用"
    }, {
        field: "remark",
        title: "备注"
    }, {
        command: "destroy"
    }];

    $scope.validate = $("#addCustomerForm").validate({
        messages: {
            contactPhone: {
                pattern : "手机号格式不正确"
            }
        }
    });

    $scope.reset = function () {
        $scope.customer = {};
    };

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $scope.customer.brands = $("#brands").data("kendoGrid").dataSource.data();

        $.ajax({
            url: contextPath + "/customer/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.customer)
        }).done(function (result) {
            if (result.success) {
                $("#addCustomer").modal("hide");
                $scope.reset();
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        }).fail(function(){
            App.toastr("提交失败","error");
        });
    };
});