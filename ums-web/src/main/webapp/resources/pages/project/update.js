'use strict';

var app = angular.module('RoadnetApp');

app.controller('ProjectUpdateController', function ($scope, $rootScope, $stateParams) {
    //业务类型
    $scope.toolbar = [{name: "create"}];
    $scope.businessTypeDataSource = new kendo.data.DataSource({
        data: [],
        schema: {
            model: {
                fields: {
                    code: {type: "string", validation: {required: true}},
                    name: {type: "string", validation: {required: true}},
                    remark: {type: "string"}
                }
            }
        }
    });

    $scope.businessTypeColumns = [{
        field: "code",
        title: "编码"
    }, {
        field: "name",
        title: "名称"
    }, {
        field: "remark",
        title: "备注"
    }, {
        command: "destroy"
    }];

    //箱型
    $scope.toolbar = [{name: "create"}];
    $scope.boxTypeDataSource = new kendo.data.DataSource({
        data: [],
        schema: {
            model: {
                fields: {
                    code: {type: "string", validation: {required: true}},
                    name: {type: "string", validation: {required: true}},
                    volume: {type: "string", validation: {pattern: "^\\d+(\\.\\d{0,6})?$", required: {message: "体积为必填项"}, min: 0}},
                    remark: {type: "string"}
                }
            }
        }
    });

    $scope.boxTypeColumns = [{
        field: "code",
        title: "编码"
    }, {
        field: "name",
        title: "名称"
    }, {
        field: "volume",
        title: "体积",
        template: function (dataItem) {
            return kendo.toString(dataItem.volume, "n6");
        }
    }, {
        field: "remark",
        title: "备注"
    }, {
        command: "destroy"
    }];

    //货物类型
    $scope.toolbar = [{name: "create"}];
    $scope.cargoTypeDataSource = new kendo.data.DataSource({
        data: [],
        schema: {
            model: {
                fields: {
                    code: {type: "string", validation: {required: true}},
                    name: {type: "string", validation: {required: true}},
                    remark: {type: "string"}
                }
            }
        }
    });

    $scope.carGoTypeColumns = [{
        field: "code",
        title: "编码"
    }, {
        field: "name",
        title: "名称"
    }, {
        field: "remark",
        title: "备注"
    }, {
        command: "destroy"
    }];

    $scope.init = function () {
        $.ajax({
            url: contextPath + "/project/getByCode/" + $stateParams.code,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            $scope.project = result.body;
            if (result.body.settleCycle)
                $scope.project.settleCycle = result.body.settleCycle.name;
            if (result.body.paymentType)
                $scope.project.paymentType = result.body.paymentType.name;
            if (result.body.calculateType)
                $scope.project.calculateType = result.body.calculateType.name;
            if (result.body.handoverType)
                $scope.project.handoverType = result.body.handoverType.name;
            if (result.body.receivableDataSource)
                $scope.project.receivableDataSource = result.body.receivableDataSource.name;
            $scope.businessTypeGrid = $("#businessTypeF").data("kendoGrid");
            $scope.boxTypeGrid = $("#boxTypeF").data("kendoGrid");
            $scope.cargoTypeGrid = $("#cargoTypeF").data("kendoGrid");

            //初始化业务类型数据源
            if ($scope.project.businessTypes) {
                var dataSource = new kendo.data.DataSource({
                    data: $scope.project.businessTypes
                });
                $scope.businessTypeGrid.setDataSource(dataSource);
            }

            //初始化箱型数据源
            if ($scope.project.boxTypes) {
                var dataSource = new kendo.data.DataSource({
                    data: $scope.project.boxTypes
                });
                $scope.boxTypeGrid.setDataSource(dataSource);
            }

            //初始化货物类型数据源
            if ($scope.project.cargoTypes) {
                var dataSource = new kendo.data.DataSource({
                    data: $scope.project.cargoTypes
                });
                $scope.cargoTypeGrid.setDataSource(dataSource);
            }

            if ($scope.project) {
                $("#updateProject").modal();
                $scope.$apply();
            }
        });
    };

    $.validator.addMethod("bigger", function (value, element, param) {
        if (parseFloat(value) > parseFloat($(param).val()))
            return true;
        return false;
    }, "");

    $scope.rules = {
        rules: {
            lightThrowGoods: {
                bigger: "#lightGoods"
            },
            commonGoods: {
                bigger: "#lightThrowGoods"
            },
            heavyThrowGoods: {
                bigger: "#commonGoods"
            },
            heavyGoods: {
                bigger: "#heavyThrowGoods"
            }
        },
        messages: {
            lightThrowGoods: {
                bigger: "应大于轻货"
            },
            commonGoods: {
                bigger: "应大于轻抛货"
            },
            heavyThrowGoods: {
                bigger: "应大于普通货"
            },
            heavyGoods: {
                bigger: "应大于重抛货"
            }
        }
    }

    $scope.validate = $("#updateProjectForm").validate($.extend({}, $scope.rules));

    $scope.submit = function () {
        if (!$scope.validate.valid())
            return;

        $scope.project.businessTypes = $scope.businessTypeGrid.dataSource.data();
        $scope.project.boxTypes = $scope.boxTypeGrid.dataSource.data();
        $scope.project.cargoTypes = $scope.cargoTypeGrid.dataSource.data();

        if (!($scope.project.heavyGoods > $scope.project.heavyThrowGoods && $scope.project.heavyThrowGoods > $scope.project.commonGoods && $scope.project.commonGoods > $scope.project.lightThrowGoods && $scope.project.lightThrowGoods > $scope.project.lightGoods)) {
            App.toastr("重货>重抛货>普通货>轻抛货>轻货", "warning");
            return;
        }

        $.ajax({
            url: contextPath+'/project/edit',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.project)
        }).done(function (result) {
            if (result.success) {
                $("#updateProject").modal("hide");
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



