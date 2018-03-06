'use strict';

angular.module('RoadnetApp').controller('GoodsArchivesCreateController', ['$rootScope', '$scope', GoodsArchivesCreateController]);
function GoodsArchivesCreateController($rootScope, $scope) {

    $("#addGoodsArchives").modal();

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
    }, {
        field: "identificationCode",
        title: "识别码"
    }, {
        command: "destroy"
    }];


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
    $scope.goodsArchives = {};
    $scope.reset = function () {
        $scope.goodsArchives = {};
        //other operation...
    };

    $scope.validate = $("#addGoodsArchivesForm").validate();
    $scope.submit = function () {
        if (!$scope.validate.valid()) return;
        if($("#identificationCodes").data("kendoGrid").dataSource.data().length < 1){
            toastr.error("识别码不能为空！");
            return ;
        }
        $scope.goodsArchives.identificationCodes = $("#identificationCodes").data("kendoGrid").dataSource.data();
        $.ajax({
            url: contextPath + "/goodsArchives/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.goodsArchives)
        }).done(function (result) {
            if (result.success) {
                $("#addGoodsArchives").modal("hide");
                toastr.success("保存成功!");
                $scope.reset();
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        }).fail(function () {
            App.toastr("数据提交失败!", "error");
        });
    };
}