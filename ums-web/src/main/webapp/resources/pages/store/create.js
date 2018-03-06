'use strict';

var app = angular.module('RoadnetApp');

app.controller('StoreCreateController', function ($scope, $rootScope) {
    $("#addStore").modal();
    $scope.store = {};

    /*$scope.innit = function () {
     $("#addStore").modal("show");
     $scope.selectOptions = {
     dataTextField: "text",
     dataValueField: "value",
     autoBind: true,
     dataSource: {
     data: []
     }
     }
     }*/

    $scope.validate = $("#addStoreForm").validate({
        messages: {
            mobilePhoneNo: {
                pattern: "输入正确的手机号格式"
            },
            telephoneNo: {
                pattern: "输入正确的电话号格式"
            }
        }
    });

    $scope.reset = function () {
        $scope.store = {};
    }

    $scope.bindBrand = function () {
        var code = $("#owner").val();
        var brands = $("#brands").data("kendoComboBox");

        if (code) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: contextPath + "/customer/getBrands/" + code
            }).done(function (result) {
                if (result) {
                    var dataSource = new kendo.data.DataSource({
                        data: result,
                        serverFiltering: true
                    });
                    brands.setDataSource(dataSource);
                } else {
                    var dataSource = new kendo.data.DataSource({
                        data: [],
                    });
                    brands.setDataSource(dataSource);
                }
            });
        } else {
            var dataSource = new kendo.data.DataSource({
                data: [],
            });
            brands.setDataSource(dataSource);
        }
    }

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        if($scope.store.brandCode)
            $scope.store.brandCodes = [$scope.store.brandCode];
        $.ajax({
            url: contextPath + "/store/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.store)
        }).done(function (result) {
            if (result.success) {
                $("#addStore").modal("hide");
                $scope.reset();
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };

    //$scope.innit();
});