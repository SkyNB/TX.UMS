'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/notify/create',
            controller: "NotifyCreateController",
            resolve: getDeps([contextPath + '/assets/pages/notify/create.js'])
        })
        .state('detail', {
            url: '/detail/:id',
            reload: true,
            templateUrl: contextPath + '/notify/detail',
            controller: "NotifyDetailController",
            resolve: getDeps([contextPath + '/assets/pages/notify/detail.js'])
        })
});

app.controller('NotificationController', function UserController($rootScope, $scope, $state) {
    $scope.dataBound = function () {
        $scope.grid = $("#gridNotification").data("kendoExGrid");
    }

    $scope.recall = function () {
        var row = $scope.grid.select();
        if (row.length != 1) {
            App.toastr("能且只能选择一条数据！", "warning");
            return;
        }
        var id = $scope.grid.dataItem(row).id;
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: contextPath + "/notify/recall/" + id
        }).done(function (result) {
            if (result.success) {
                location.reload();
            } else {
                App.toastr(result.message, "error");
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    }

    $scope.search = function () {
        $scope.data.filter($("#fromSearchNotification").serializeArray());
    };

    $rootScope.data = getDataSource("id", contextPath + "/notify/search");/*new kendo.data.DataSource({
        schema: {
            model: {
                id: 'id'
            },
            data: function (response) {
                return response;
            },
            total: 'totalItemCount'
        },
        transport: {
            parameterMap: function (options) {
                return kendo.stringify(options);
            },
            read: {
                contentType: 'application/json',
                dataType: 'json',
                type: 'POST',
                url: contextPath + "/notify/search"
            }
        },
        pageSize: 20,
        serverPaging: true,
        serverFiltering: true,
        serverSorting: true,
        filter: []
    });*/

    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $scope.data,
        columns: [{
            field: "subject",
            title: "主题",
            template: function (dataItem) {
                return "<a href='#/detail/" + dataItem.id + "' data-target='#notificationDetail' data-toggle='modal'>" + dataItem.subject + "</a>";
            }
        }, {
            field: "recalled",
            title: "是否已撤销",
            template: "#= recalled ? '是':'否'#"
        }/*, {
            field: "sender",
            title: "发送人"
        }*/, {
            field: "sentTime",
            title: "发送时间"
        }]
    });
});