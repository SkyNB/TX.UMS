angular.module('RoadnetApp').config(function ($stateProvider, $urlRouterProvider) {
//        $urlRouterProvider.otherwise('home');
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/user/create',
            controller: "UserCreateController",
            resolve: getDeps([contextPath + '/resources/pages/user/create.js'])
        })
        .state('update/:userId', {
            url: '/update/:userId',
            reload: true,
            templateUrl: contextPath + '/user/update',
            controller: "UserUpdateController",
            resolve: getDeps([contextPath + '/resources/pages/user/update.js'])
        })
        .state('resetPassword', {
            url: '/resetPassword',
            reload: true,
            templateUrl: contextPath + '/user/resetPassword',
            controller: "UpdatePasswordController",
            resolve: getDeps([contextPath + '/resources/pages/user/resetPassword.js'])
        })
});


angular.module('RoadnetApp').controller('UserController', function UserController($rootScope, $scope, $state) {

    $scope.dataBound = function () {
        $scope.grid = $("#gridUser").data("kendoExGrid");
    };
    $scope.enable = function () {
        var userIds = $scope.getSelectIds();
        if (userIds == null || userIds.length < 1) return;
        $.ajax({
            url: contextPath + "/user/enable",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(userIds)
        }).done(function (result) {
            if (result.success) {
                $rootScope.data.query();
                toastr.success("启用成功！" + result.message, "");
            } else {
                toastr.error("启用失败！" + result.message, "", {
                    positionClass: "toast-top-center", closeButton: true, timeOut: 3000
                });
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
    $scope.getSelectIds = function () {
        var row = $scope.grid.select();
        if (row.length <= 0) {
            App.toastr("请选择数据", "warning");
            return null;
        }
        var userIds = [];
        var rows = $scope.grid.select();
        rows.each(function (i, row) {
            var dataItem = $scope.grid.dataItem(row);
            if (dataItem) {
                userIds.push(dataItem.userId);
            }
        });
        return userIds;
    };
    $scope.unEnable = function () {
        var userIds = $scope.getSelectIds();
        if (userIds == null || userIds.length < 1) return;
        $.ajax({
            url: contextPath + "/user/unEnable",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(userIds)
        }).done(function (result) {
            if (result.success) {
                toastr.success("禁用成功！" + result.message, "");
                $rootScope.data.query();
            } else {
                App.toastr("禁用失败！" + result.message, "error");
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
    $scope.delete = function () {

        var userIds = $scope.getSelectIds();
        if (userIds == null || userIds.length < 1) return;
        $.ajax({
            url: contextPath + "/user/delete",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(userIds)
        }).done(function (result) {
            if (result.success) {
                toastr.success(result.message, "");
                $rootScope.data.query();
            } else {
                App.toastr("删除失败！" + result.message, "error");
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };

    $scope.resetPassword = function () {
        var row = $scope.grid.select();
        if (row.length <= 0) {
            App.toastr("请选择数据", "warning");
            return;
        }
        var userIds = [];
        var rows = $scope.grid.select();
        rows.each(function (i, row) {
            var dataItem = $scope.grid.dataItem(row);
            if (dataItem) {
                userIds.push(dataItem.userId);
            }
        });
        $rootScope.userIds = userIds;
        $state.go("resetPassword");
        $("#updatePassword").modal("show");
    };
    $scope.search = function () {
        $rootScope.data.filter($("#fromSearchUser").serializeArray());
    };

    $rootScope.data = getDataSource("userId", contextPath + "/company/selectCompanyList");
    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $rootScope.data,
        change: $scope.onChange,
        dataBinding: $scope.dataBinding,
        columns: [{
            field: "username",
            title: "用户名",
            width: 140
        }, {
            field: "fullName",
            title: "姓名",
            width: 140,
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.userId + "' data-target='#updateUser' data-toggle='modal'>" + dataItem.fullName + "</a>";
            }
        }, {
            field: "email",
            title: "邮箱",
            width: 170
        }, {
            field: "orgName",
            title: "所属组织",
            width: 130
        }, {
            field: "type.text",
            title: "用户类型",
            width: 130
        }, {
            field: "active",
            title: "是否启用",
            width: 80,
            template: "#= active?'是':'否' #"
        }, {
            field: "createTime",
            title: "创建时间",
            width: 170
        }, {
            field: "remark",
            title: "备注",
            width: 250
        }]
    });
});
