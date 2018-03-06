angular.module('RoadnetApp').config(function ($stateProvider, $urlRouterProvider) {
//        $urlRouterProvider.otherwise('home');
    $stateProvider
        .state('create', {
            url: '/create', reload: true,
            templateUrl: contextPath + '/create',
            controller: "RoleCreateController",
            resolve: getDeps([
                contextPath + '/resources/pages/home/create.js'
            ])
        })
        .state('update/:id', {
            url: '/update/:id', reload: true,
            templateUrl: function (params) {
                return contextPath + '/update';
            },
            controller: "RoleUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/home/update.js'])
        })
})


angular.module('RoadnetApp').controller('RoleController', function ($rootScope, $scope, $http, $timeout) {
    $scope.$on('$viewContentLoaded', function () {
        App.resizeGrid();
        $scope.grid = $("#gridRole").data("kendoExGrid");
    });
    $scope.delete = function () {
        var row = $scope.grid.select();
        if (row.length <= 0) return;
        var data = $scope.grid.dataItem(row);
        $.ajax({
            url: contextPath + "/role/delete/" + data.id,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            if (result.success) {
                $rootScope.data.query();
            } else {
                toastr.error("删除失败！" + result.message, "", {
                    positionClass: "toast-top-center",
                    closeButton: true,
                    timeOut: 3000
                });
            }
        });
    };
    $scope.inactivate = function () {
        var rows = $scope.grid.select();
        if (rows.length <= 0) {
            App.toastr("请选择数据", "warning");
            return;
        }
        var codes = [];
        rows.each(function (i, row) {
            var dataItem = $scope.grid.dataItem(row);
            if (dataItem) {
                if (dataItem.active)
                    codes.push(dataItem.code);
            }
        });
        if (codes.length <= 0) {
            toastr.error("所选数据都是已禁用");
            return;
        }
        $.ajax({
            url: contextPath + "/role/inactivate",
            type: 'post',
            data: JSON.stringify(codes),
            contentType: 'application/json'
        })
            .done(function (result) {
                if (result.success) {
                    toastr.success("禁用成功!");
                    $rootScope.data.query();
                } else {
                    toastr.error(result.message);
                }
            })
            .fail(function () {
                toastr.error("提交失败！" + result.message);
            });
    };

    $scope.activate = function () {
        var rows = $scope.grid.select();
        if (rows.length <= 0) {
            App.toastr("请选择数据", "warning");
            return;
        }
        var codes = [];
        rows.each(function (i, row) {
            var dataItem = $scope.grid.dataItem(row);
            if (dataItem) {
                if (!dataItem.active)
                    codes.push(dataItem.code);
            }
        });
        if (codes.length <= 0) {
            toastr.error("所选数据都是已启用");
            return;
        }
        $.ajax({
            url: contextPath + "/role/activate",
            type: 'post',
            data: JSON.stringify(codes),
            contentType: 'application/json'
        })
            .done(function (result) {
                if (result.success) {
                    toastr.success("启用成功!");
                    $rootScope.data.query();
                } else {
                    toastr.error(result.message, "启用失败");
                }
            })
            .fail(function () {
                toastr.error("提交失败！");
            });
    };

    $scope.bindId = function (id) {
        console.info(id);
        $scope.updateId = id;
        $("#updateRole").modal();
    };
    $scope.search = function () {
        $scope.data.filter($("#fromSearchRole").serializeArray());
    };
    $rootScope.data = getDataSource("id", contextPath + "/search");
    $scope.gridOptions = {
        dataSource: $scope.data,
        columns: [{
            field: "id",
            title: "编码"
        }, {
            field: "name",
            title: "名称",
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.id + "' data-target='#updateRole' data-toggle='modal'>" + dataItem.name + "</a>";
                // return "<a ng-click='bindId(" + dataItem.id + ")' data-target='#updateRole' data-toggle='modal'>" + dataItem.name + "</a>";
            }
        }, /*{
         field: "active",
         title: "是否启用",
         template: "#= active?'是':'否' #"
         }, {
         field: "sysRole",
         title: "预设角色",
         template: "#= sysRole?'是':'否' #"
         },*/ {
            field: "age",
            title: "备注"
        }]
    };
});


angular.module('RoadnetApp').controller('RoleCreateController', function ($rootScope, $scope, $http, $timeout) {
    // $("#addRole").modal();

    $scope.role = {};
    $scope.reset = function () {
        $scope.role = {};
    };
    $scope.validate = $("#addRoleForm").validate();
    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        $.ajax({
            url: contextPath + "/role/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.role)
        }).done(function (result) {
            if (result.success) {
                $("#addRole").modal("hide");
                $scope.reset();
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message, "", {
                    positionClass: "toast-top-center",
                    closeButton: true,
                    timeOut: 3000
                });
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
});
