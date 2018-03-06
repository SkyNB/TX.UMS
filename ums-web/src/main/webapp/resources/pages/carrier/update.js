'use strict';

var app = angular.module('RoadnetApp');
app.controller('CarrierUpdateController', function ($scope, $rootScope, $stateParams) {
    $scope.init = function () {
        $.ajax({
            url: contextPath + "/carrier/get/" + $stateParams.id,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $scope.carrier = result.body;
                $scope.carrier.type = result.body.type.name;
                $scope.carrier.settleCycle = result.body.settleCycle.name;
                $scope.carrier.paymentType = result.body.paymentType.name;
                $scope.carrier.calculateType = result.body.calculateType.name;
                $scope.carrier.transportType = result.body.transportType.name;
                if ($scope.carrier) {
                    $("#updateCarrier").modal();
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

    $scope.validate = $("#updateCarrierForm").validate({

    });

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $.ajax({
            url: contextPath + "/carrier/edit",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.carrier)
        }).done(function (result) {
            if (result.success) {
                $("#updateCarrier").modal("hide");
                $rootScope.data.query();
            } else {
                toastr.error(result.message);
            }
        });
    };

    $scope.init();
});