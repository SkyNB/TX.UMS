'use strict';

var app = angular.module('RoadnetApp');
app.controller('VehicleUpdateController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {
        $.ajax({
            url: contextPath + "/vehicle/get/" + $stateParams.id,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.vehicle = result.body;
                $scope.vehicle.leaseType = result.body.leaseType.name;
                if ($scope.vehicle) {
                    $("#updateVehicle").modal();
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

    $scope.getVehicleType = function () {
        if ($("#vehicleType").val()) {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: contextPath + "/vehicleType/get/" + $("#vehicleType").val(),
                data: JSON.stringify($scope.vehicle)
            }).done(function (result) {
                if (result.success) {
                    var body = result.body;
                    $scope.vehicle.length = body.length;
                    $scope.vehicle.width = body.width;
                    $scope.vehicle.height = body.height;
                    $scope.vehicle.loadVolume = body.loadVolume;
                    $scope.vehicle.loadWeight = body.loadWeight;
                    var num = $("#length").data("kendoNumericTextBox");
                    num.value(body.length);
                    num = $("#width").data("kendoNumericTextBox");
                    num.value(body.width);
                    num = $("#height").data("kendoNumericTextBox");
                    num.value(body.height);
                    num = $("#loadVolume").data("kendoNumericTextBox");
                    num.value(body.loadVolume);
                    num = $("#loadWeight").data("kendoNumericTextBox");
                    num.value(body.loadWeight);
                } else {
                    App.toastr(result.message, "error");
                }
            }).fail(function () {
                App.toastr("提交失败", "error");
            });
        } else {
            $scope.vehicle.length = 0;
            $scope.vehicle.width = 0;
            $scope.vehicle.height = 0;
            $scope.vehicle.loadVolume = 0;
            $scope.vehicle.loadWeight = 0;
            var num = $("#length").data("kendoNumericTextBox");
            num.value(0);
            num = $("#width").data("kendoNumericTextBox");
            num.value(0);
            num = $("#height").data("kendoNumericTextBox");
            num.value(0);
            num = $("#loadVolume").data("kendoNumericTextBox");
            num.value(0);
            num = $("#loadWeight").data("kendoNumericTextBox");
            num.value(0)
        }
    }

    $scope.validate = $("#updateVehicleForm").validate({
        messages: {
            identityCardNo: {
                pattern: "请输入正确的身份证号码"
            },
            emergencyContactMobile: {
                pattern: "请输入正确的手机号"
            },
            vehicleNoForUpdate: {
                pattern: "请输入正确的车牌号"
            },
            driverMobileForUpdate: {
                pattern: "请输入正确的手机号"
            }
        }
    });

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            url: contextPath + "/vehicle/edit",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.vehicle)
        }).done(function (result) {
            if (result.success) {
                $("#updateVehicle").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error(result.message);
            }
        });
    };

    $scope.init();
});