angular.module('RoadnetApp').controller('UserUpdateController', function ($rootScope, $scope, $stateParams) {
    $scope.siteOptions = {
        placeholder: "选择站点...",
        dataTextField: "name",
        dataValueField: "code"
    };

    $scope.init = function () {
        $("#site").hide();
        $.ajax({
            url: contextPath + "/user/get/" + $stateParams.userId,
            type: "POST",
            contentType: "application/json"
        }).done(function (result) {
            $scope.user = result.body;
            console.info(JSON.stringify($scope.user));
            $scope.user.type = result.body.type.name;
            $scope.getSites(result.body.orgCode, result.body.siteCodes);

            if ($scope.user) {
                $("#updateUser").modal();
                $scope.$apply();
            }
        });
    };
    $scope.dropdownTreeview = {
        treeView: {
            dataSource: getHierarchicalDataSource("code", contextPath + '/organization/getHierarchical', null,
                "items"),
            dataTextField: "name"
        }
        , valueField: "code", textField: "name"
    };

    $scope.getSites = function(branchCode, selectSiteCodes) {
        $scope.user.siteCodes = [];
        var code = branchCode || $("#topSite").val();
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: contextPath + "/site/getByBranchCode/" + code
        }).done(function(result) {
            if(result.length > 0) {
                $("#site").show();
                var dataSource = new kendo.data.DataSource({
                    data: result
                });
                var multiSelect = $("#siteMulti").data("kendoMultiSelect");
                multiSelect.setDataSource(dataSource);
                if(selectSiteCodes) {
                    multiSelect.value(selectSiteCodes);
                    multiSelect.trigger("change");
                }
            } else {
                $("#site").hide();
            }

        }).fail(function() {
            App.toastr("提交失败","error");
        }) ;
    };

    $scope.validate = $("#updateUserForm").validate();
    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        $.ajax({
            url: contextPath + '/user/edit',
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.user)
        }).done(function (result) {
            if (result.success) {
                $("#updateUser").modal("hide");
                toastr.success("保存成功!");
                $rootScope.data.query();
            } else {
                toastr.error("保存失败！" + result.message);
            }
        }).fail(function(){
            App.toastr("提交失败","error");
        });
    };
    $scope.init();
});