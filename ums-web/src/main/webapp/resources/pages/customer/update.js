'use strict';

var app = angular.module('RoadnetApp');

app.controller('CustomerUpdateController', ['$scope', '$rootScope', '$stateParams',CustomerUpdateController]);
 function CustomerUpdateController($scope, $rootScope, $stateParams) {
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
        title: "是否启用"
        //template: "<input type='checkbox' #= active ? 'checked=checked':''# />"
    }, {
        field: "remark",
        title: "备注"
    }, {
        command: "destroy"
    }];

    $scope.init = function () {
        $.ajax({
            url: contextPath + "/customer/getByCode/" + $stateParams.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            $scope.customer = result.body;
            if ($scope.customer.brands) {
                var dataSource = new kendo.data.DataSource({
                    data: $scope.customer.brands
                });
                $scope.brandsGrid = $("#brandsForUpdate").data("kendoGrid");
                $scope.brandsGrid.setDataSource(dataSource);
            }
            if ($scope.customer) {
                $("#updateCustomer").modal();
                $scope.$apply();
            }
        });
    };

    $scope.validate = $("#updateCustomerForm").validate({
        messages: {
            contactPhone: {
                pattern : "手机号格式不正确"
            }
        }
    });

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;
        console.info($scope.brandsGrid.dataSource.data());
        $scope.customer.brands = $scope.brandsGrid.dataSource.data();
        $.ajax({
            url: contextPath+'/customer/edit',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.customer)
        }).done(function (result) {
            if (result.success) {
                $("#updateCustomer").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        }).fail(function(){
            App.toastr("提交失败","error");
        });
    };

    $scope.init();
}



