angular.module('RoadnetApp').controller('SiteCreateController', ['$rootScope', '$scope','$filter', SiteCreateController]);
function SiteCreateController($rootScope, $scope,$filter) {
    $("#addSite").modal();

    $scope.site = {};
    $scope.reset = function () {
        $scope.site = {};
    };
    $scope.validate = $("#addSiteForm").validate({
        messages: {
            contactPhone: {
                pattern: "手机号格式不正确"
            }
        }
    });

    $scope.branchDataSource = new kendo.data.DataSource({
        serverFiltering: false,
        transport: {
            read: {
                dataType: "json",
                url: contextPath + "/organization/getBranchesForSelect",
            }
        }
    });

    /*$scope.branchOptions = {
        dataSource: $scope.branchDataSource,
        filter: "contains",
        dataTextField: "text",
        dataValueField: "value",
        change: function (e) {
            var value = this.value();
            var exists = $.grep($scope.branchDataSource.data(), function (v) {
                return v.value === value;
            });
            if (exists.length <= 0) {
                this.text('');
                this.value('');
            }
        },
        filtering: function (e) {
            var filter = e.filter;
            var originData = $scope.branchDataSource.data();
            if (!filter.value) {
                e.preventDefault();
            } else {
                var filterObj = $filter('filter')(originData, {value: filter.value});
                if (filterObj.length <= 0) {
                    this.text('');
                    this.value('');
                    this.setDataSource(originData);
                    //this.refresh();
                } else {
                    this.setDataSource(filterObj);
                }
            }
        }
    };*/
    
    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        $.ajax({
            url: contextPath + "/site/create",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.site)
        }).done(function (result) {
            if (result.success) {
                $("#addSite").modal("hide");
                toastr.success("保存成功!");
                $scope.reset();
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        }).fail(function () {
            App.toastr("提交失败", "error");
        });
    };
}