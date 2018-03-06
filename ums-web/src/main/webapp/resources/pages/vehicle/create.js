var app = angular.module('RoadnetApp');

app.controller('VehicleCreateController', function ($scope, $rootScope) {
    $("#addVehicle").modal();

    $scope.vehicle = {
        length: 0,
        width: 0,
        height: 0,
        loadVolume: 0,
        loadWeight: 0
    };

    $scope.reset = function () {
        $scope.vehicle = {};
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
                    $scope.$apply();
                    /*var num = $("#length").data("kendoNumericTextBox");
                    num.value(body.length);
                    num = $("#width").data("kendoNumericTextBox");
                    num.value(body.width);
                    num = $("#height").data("kendoNumericTextBox");
                    num.value(body.height);
                    num = $("#loadVolume").data("kendoNumericTextBox");
                    num.value(body.loadVolume);
                    num = $("#loadWeight").data("kendoNumericTextBox");
                    num.value(body.loadWeight);*/
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
            $scope.$apply();
            /*var num = $("#length").data("kendoNumericTextBox");
            num.value(0);
            num = $("#width").data("kendoNumericTextBox");
            num.value(0);
            num = $("#height").data("kendoNumericTextBox");
            num.value(0);
            num = $("#loadVolume").data("kendoNumericTextBox");
            num.value(0);
            num = $("#loadWeight").data("kendoNumericTextBox");
            num.value(0)*/
        }
    };

    /*
    $scope.getSite = function () {
        var sites = $("#siteCode").data("kendoComboBox");
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath + "/site/getByBranchCode/" + $("#branchCode").val()
        }).done(function (result) {
            if (result) {
                var dataSource = new kendo.data.DataSource({
                    data: result,
                    serverFiltering: true
                });
                sites.setDataSource(dataSource);
            } else {
                var dataSource = new kendo.data.DataSource({
                    data: [],
                    serverFiltering: true
                });
                sites.setDataSource(dataSource);
                App.toastr(result.message, "error");
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
    */

    $scope.rules = {
        messages: {
            identityCardNo: {
                pattern: "请输入正确的身份证号码"
            },
            emergencyContactMobile: {
                pattern: "请输入正确的手机号"
            },
            vehicleNoForCreate: {
                pattern: "请输入正确的车牌号"
            },
            driverMobileForCreate: {
                pattern: "请输入正确的手机号"
            }
        }
    };

    $scope.validate = $("#addVehicleForm").validate($.extend({}, $scope.rules));

    $("#rootwizard").bootstrapWizard({
        onTabClick: function (tab, navigation, index) {
            return false;
        },
        'tabClass': 'nav nav-pills',
        'onNext': function (tab, navigation, index) {
            if (!$("#tab" + index).validate($.extend({}, $scope.rules)).form())return false;
            var totalTab = navigation.find('li').length;
            if (index == (totalTab - 1)) {
                $scope.showSubmitBtn = true;
            } else {
                $scope.showSubmitBtn = false;
            }
        }
    });

    $scope.submit = function () {
        if (!$scope.validate.form())
            return;
        $scope.vehicle.branchCode = $("#topBranch").val();
        $scope.vehicle.siteCode = $("#topSite").val();
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/vehicle/create",
            data: JSON.stringify($scope.vehicle)
        }).done(function (result) {
            if (result.success) {
                $("#addVehicle").modal("hide");
                $scope.reset();
                $rootScope.data.query();
            } else {
                App.toastr(result.message, "error");
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
});