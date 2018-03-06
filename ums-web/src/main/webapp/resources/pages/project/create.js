'use strict';

var app = angular.module('RoadnetApp');

app.controller('ProjectCreateController', function ($scope, $rootScope) {
    $("#addProject").modal();
    $scope.project = {
        settleCycle: "PROMPTLY_PAY",
        paymentType: "GIRO_BACK",
        calculateType: "VOLUME",
        handoverType: "DOOR_TO_DOOR",
        receivableDataSource: "PACK_DATA",
        packCoefficient: 1.1,
        lightGoods: 250,
        lightThrowGoods: 255,
        commonGoods: 260,
        heavyThrowGoods: 265,
        heavyGoods: 270
    };

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
                    volume: {type: "string", validation: {pattern: "^\\d+(\\.\\d{0,6})?$", required: {message: "体积为必填项"}, min: 0},},
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
        title: "体积"
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

    $.validator.addMethod("bigger", function (value, element, param) {
        if(parseFloat(value) > parseFloat($(param).val()))
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

    $scope.validate = $("#addProjectForm").validate($.extend({}, $scope.rules));

    $scope.reset = function () {
        $scope.project = {};
    }

    $scope.submit = function () {
        if (!$scope.validate.form())
            return;

        $scope.project.customerName = $("#customer").find("option:selected").text();
        $scope.project.branchName = $("#branch").find("option:selected").text()
        $scope.project.businessTypes = $("#businessType").data("kendoGrid").dataSource.data();
        $scope.project.boxTypes = $("#boxType").data("kendoGrid").dataSource.data();
        $scope.project.cargoTypes = $("#cargoType").data("kendoGrid").dataSource.data();

        if (!($scope.project.heavyGoods > $scope.project.heavyThrowGoods && $scope.project.heavyThrowGoods > $scope.project.commonGoods && $scope.project.commonGoods > $scope.project.lightThrowGoods && $scope.project.lightThrowGoods > $scope.project.lightGoods)) {
            App.toastr("重货>重抛货>普通货>轻抛货>轻货", "warning");
            return;
        }

        $.ajax({
            url: contextPath + "/project/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.project)
        }).done(function (result) {
            if (result.success) {
                $("#addProject").modal("hide");
                $scope.reset();
                $rootScope.data.query();
            } else {
                toastr.error(result.message);
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };

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
});