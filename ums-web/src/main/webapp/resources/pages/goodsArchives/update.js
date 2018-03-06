'use strict';

angular.module('RoadnetApp').controller('GoodsArchivesUpdateController', ['$rootScope', '$scope','$stateParams',
    GoodsArchivesUpdateController]);
function GoodsArchivesUpdateController($rootScope, $scope,$stateParams) {
    $scope.codesToolbar = [{name: "create"}];
    $scope.codesDataSource = new kendo.data.DataSource({
        data: [],
        schema: {
            model: {
                fields: {
                    identificationCode: {type: "string", validation: {required: true}}
                }
            }
        }
    });
    $scope.codesColumns = [{
        field: "identificationName",
        title: "识别码名称"
    },{
        field: "identificationCode",
        title: "识别码"
    }, {
        command: "destroy"
    }];

    $.ajax({
        url: contextPath + "/goodsArchives/get/" + $stateParams.archivesId,
        type: "POST",
        contentType: "application/json"
    }).done(function (result) {
        $scope.goodsArchives = result.body;
        if ($scope.goodsArchives) {
            if ($scope.goodsArchives.identificationCodes) {
                var dataSource = new kendo.data.DataSource({
                    data: $scope.goodsArchives.identificationCodes
                });
                $("#identificationCodes").data("kendoGrid").setDataSource(dataSource);
            }
            $scope.goodsArchives.category = $scope.goodsArchives.category.name;
            $("#updateGoodsArchives").modal();
            $scope.$apply();
        }
    });
    $scope.customersDataSource = new kendo.data.DataSource({
        serverFiltering: false,
        transport: {
            read: {
                dataType: "json",
                url: contextPath + "/customer/getAvailableForSelect"
            }
        }
    });
    $scope.customOptions = {
        dataSource: $scope.customersDataSource,
        filter: "contains",
        dataTextField: "text",
        dataValueField: "value",
        change: function (e) {//填一个不存在的值，
            var value = this.value();
            var exists = $.grep($scope.customersDataSource.data(), function (v) {
                return v.value === value;
            });
            if (exists.length <= 0) {
                this.text('');
                this.value('');
            }
        }
    };

    $scope.validate = $("#updateGoodsArchivesForm").validate();
    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        if($("#identificationCodes").data("kendoGrid").dataSource.data().length < 1){
            toastr.error("识别码不能为空！");
            return ;
        }
        $.ajax({
            url: contextPath + "/goodsArchives/update",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.goodsArchives)
        }).done(function (result) {
            if (result.success) {
                $("#updateGoodsArchives").modal("hide");
                toastr.success("保存成功!");
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        }).fail(function () {
            App.toastr("数据提交失败!", "error");
        });
    };
}