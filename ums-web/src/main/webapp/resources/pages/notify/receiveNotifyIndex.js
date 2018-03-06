'use strict';
var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('read', {
            url: '/read/:id',
            reload: true,
            templateUrl: contextPath + '/notify/read',
            controller: "ReadController",
            resolve: getDeps([contextPath + '/assets/pages/notify/read.js'])
        })
});

app.controller('ReceiveNotificationController', function UserController($rootScope, $scope) {
    $scope.active = true;

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
        $scope.data.filter($("#fromSearchReceiveNotification").serializeArray());
    };

    $rootScope.data = getDataSource("id", contextPath + "/notify/queryNotifyForReceive");

    $scope.gridOptions = $.extend(getGridOptions(), {
        dataSource: $scope.data,
        columns: [{
            field: "subject",
            title: "主题",
            template: function (dataItem) {
                return "<a href='#/read/" + dataItem.id + "' data-target='#readInfo' data-toggle='modal'>" + dataItem.subject + "</a>";
            }
        }, {
            field: "read",
            title: "是否已读",
            template: "#= read ? '是':'否'#"
        }, {
            field: "sender",
            title: "发送人"
        }, {
            field: "sentTime",
            title: "发送时间"
        }]
    });
});