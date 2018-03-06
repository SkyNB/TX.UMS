'use strict';

var app = angular.module('RoadnetApp');

app.controller('StoreUpdateController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {
        /*$scope.selectOptions = {
            dataTextField: "text",
            dataValueField: "value",
            autoBind: true,
            dataSource: {
                data: []
            }
        }*/

        $.ajax({
            url: contextPath + "/store/getByCode/" + $stateParams.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.store = result.body;
                $scope.bindBrand(result.body.ownerCode, $scope.store.brandCodes[0]);

                if ($scope.store) {
                    $("#updateStore").modal();
                    setTimeout(function () {
                        $scope.$apply();
                    }, 200)
                }
            } else {
                toastr.error(result.message);
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };

    $scope.bindBrand = function (code, values) {
        var ownerCode = code || $("#owner").val();
        var brands = $("#brandsFoUpdate").data("kendoComboBox");

        if (ownerCode) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                async: false,
                url: contextPath + "/customer/getBrands/" + ownerCode
            }).done(function (result) {
                if (result) {
                    var dataSource = new kendo.data.DataSource({
                        data: result,
                    });
                    brands.setDataSource(dataSource);
                    brands.value(values);
                    brands.trigger("change");
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

    $scope.validate = $("#updateStoreForm").validate({
        messages: {
            mobilePhoneNo: {
                pattern: "输入正确的手机号格式"
            },
            telephoneNo: {
                pattern: "输入正确的电话号格式"
            }
        }
    });

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        if($scope.store.brandCode)
            $scope.store.brandCodes = [$scope.store.brandCode];
        $.ajax({
            url: contextPath + "/store/edit",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.store)
        }).done(function (result) {
            if (result.success) {
                $("#updateStore").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        });
    };

    $scope.init();
});



