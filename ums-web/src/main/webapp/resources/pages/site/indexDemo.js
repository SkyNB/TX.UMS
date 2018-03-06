var app = angular.module('RoadnetApp');

app.config(function ($stateProvider) {
    $stateProvider
        .state('create', {
            url: '/create',
            reload: true,
            templateUrl: contextPath + '/site/create',
            controller: "SiteCreateController",
            resolve: getDeps([
                contextPath + '/resources/pages/site/create.js',
            ])
        })
        .state('update/:siteId', {
            url: '/update/:siteId', reload: true,
            templateUrl: contextPath + '/site/update',
            controller: "SiteUpdateController",
            resolve: getDeps([
                contextPath + '/resources/pages/site/update.js',
            ])
        })
});
app.controller('SiteController', ['$scope', '$rootScope', SiteController]);

function SiteController($scope, $rootScope) {
    $scope.search = function () {
        $scope.data.filter($("#fromSearchSite").serializeArray());
    };

    $scope.delete = function () {
        var row = $scope.grid.select();
        if (row.length <= 0) return;

        if (row.length > 1) {
            toastr.warning("一次只能删除一条数据！");
            return;
        }

        App.confirm("提示", "确定要删除吗？").on(function (e) {
            if (e) {
                var data = $scope.grid.dataItem(row);
                $.ajax({
                    url: contextPath + "/site/delete/" + data.siteId,
                    contentType: "application/json"
                }).done(function (result) {
                    if (result.success) {
                        $rootScope.data.query();
                    } else {
                        toastr.error("删除失败！" + result.message);
                    }
                }).fail(function () {
                    App.toastr("提交失败", "error");
                });
            }
        });
    };

    $rootScope.data =getDataSource("siteId",contextPath+"/site/search");
    $scope.gridOptions = {
        pageable: false,
        dataSource: $scope.data,
        dataBound: function () {
            $scope.grid = $("#gridSite").data("kendoExGrid");
        },
        columns: [{
            field: "code",
            title: "编码"
        }, {
            field: "name",
            title: "站点名称",
            template: function (dataItem) {
                return "<a href='#/update/" + dataItem.siteId + "' data-target='#updateSite' data-toggle='modal'>" + dataItem.name + "</a>";
            }
        }, {
            field: "contacts",
            title: "联系人"
        }, {
            field: "contactPhone",
            title: "联系人电话"
        }, {
            field: "province",
            title: "所在城市",
            template: function (dataItem) {
                return dataItem.province+""+dataItem.city+""+dataItem.district;
            }
        },{
            field: "address",
            title: "详细地址"
        }, {
            field: "remark",
            title: "备注"
        }]
    };
}
