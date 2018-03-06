angular.module('RoadnetApp').controller('PriceUpdateController', ['$rootScope', '$scope','$stateParams', PriceUpdateController]);
function PriceUpdateController($rootScope, $scope,$stateParams) {
    $scope.price = {};
    $.ajax({
        url: contextPath + "/price/get/" + $stateParams.priceId,
        contentType: "application/json"
    }).done(function (result) {
        $scope.price = result.body;
        if ($scope.price) {
            $scope.price.calcFormula = result.body.calcFormula.name;
            $scope.price.ownerType = result.body.ownerType.name;
            $scope.price.calcAttr = result.body.calcAttr.name;
            $scope.typeChange();
            $("#updatePrice").modal();
            $scope.$apply();
        }
    });
    $scope.reset = function () {
        $scope.price = {};
        $scope.price.rangeDto=[{}];
    };
    $scope.validate = $("#updatePriceForm").validate();

    $scope.ownerData = new kendo.data.DataSource({data:[]});
    $scope.exacctData = new kendo.data.DataSource({data:[]});
    $scope.typeChange = function(){
        var exacctCode = "120100";
        if($scope.price.ownerType==='CUSTOMER'){
            $.get(contextPath + "/customer/getAvailable").done(function(result){
                $scope.ownerData.data(result);
            });
            exacctCode ="110000";
        }else {
            $.get(contextPath + "/carrier/getBranchAvailable").done(function(result){
                if(result&&result.success){
                    $scope.ownerData.data(result.body);
                }
            });
        }
        $.get(contextPath+"/expenseAccount/findChildren/"+exacctCode).done(function(result){
            if(result&&result.success){
                $scope.exacctData.data(result.body);
            }
        });
    };
    $scope.price.rangeDto =[{}];
    $scope.addRow = function(){
      $scope.price.rangeDto.push({});
    };
    $scope.removeRow =function(i){
        $scope.price.rangeDto.splice(i,1);
    };
    $scope.ownerOptions = {
        dataSource: $scope.ownerData,
        filter: "contains",
        dataTextField: "name",
        dataValueField: "code"
    };
    $scope.exacctOptions = {
        dataSource: $scope.exacctData,
        filter: "contains",
        dataTextField: "name",
        dataValueField: "code"
    };
    
    $scope.submit = function () {
        if (!$scope.validate.valid())return;
        if($scope.price.rangeDto.length<1){
            App.toastr("请添加区间","error");
            return;
        }
        for(var i = 0;i<$scope.price.rangeDto.length;i++){
            var range =$scope.price.rangeDto[i];
            if(range.rangeEnd!=null&&range.rangeEnd!=undefined&&range.rangeEnd!=''&&range.rangeStart>=range.rangeEnd){
                App.toastr("第"+(i+1)+"行,区间最大值必须大于最小值","error");
                return;
            }
            for(var j=0;j<$scope.price.rangeDto.length;j++){
                var r = $scope.price.rangeDto[j];
                if(i===j) continue;//跳过当前
                if(range.rangeStart>r.rangeStart&&((r.rangeEnd==null||r.rangeEnd==undefined||r.rangeEnd=="")||range.rangeStart<r.rangeEnd)){
                    App.toastr("第"+(i+1)+"行,区间已包含在第"+(j+1)+"行","error");
                    return;
                }
                if((range.rangeEnd==null||range.rangeEnd==undefined||range.rangeEnd=="")&&range.rangeEnd>r.rangeStart&&((r.rangeEnd==null||r.rangeEnd==undefined||r.rangeEnd=="")||range.rangeEnd<r.rangeEnd)){
                    App.toastr("第"+(i+1)+"行,区间已包含在第"+(j+1)+"行","error");
                    return;
                }
            }
        }
        $.ajax({
            url: contextPath + "/price/update",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify($scope.price)
        }).done(function (result) {
            if (result.success) {
                $("#updatePrice").modal("hide");
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