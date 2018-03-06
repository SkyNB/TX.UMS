<#macro iframe_layout title="" headEnd="" bodyEnd="" >
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>新易泰物流应用平台 | ${title}</title>
    <link href="${request.contextPath}/resources/static/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/static/plugins/kendoui/styles/kendo.common-material.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/static/plugins/kendoui/styles/kendo.material.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/static/public/css/loading_xbox.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/static/public/css/main_layout.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/static/public/css/covered.css" rel="stylesheet" />
    <script src="${request.contextPath}/resources/static/public/js/js-css-load.js"></script>
    <script src="${request.contextPath}/resources/static/public/js/vue.min.2.2.1.js"></script>
    <script src="${request.contextPath}/resources/static/public/js/jquery.3.1.1.min.js"></script>
    <script src="${request.contextPath}/resources/static/plugins/kendoui/js/kendo.all.min.js"></script>
    <script src="${request.contextPath}/resources/static/plugins/kendoui/js/messages/kendo.messages.zh-CN.min.js"></script>
    <script src="${request.contextPath}/resources/static/plugins/kendoui/js/cultures/kendo.culture.zh-CN.min.js"></script>
    <script src="${request.contextPath}/resources/static/public/js/common.js"></script>
    <script src="${request.contextPath}/resources/static/public/js/vue.component.js"></script>
    <script src="${request.contextPath}/resources/static/public/js/data.validate.js"></script>

    <#if headEnd?has_content> ${headEnd}</#if>

    <script>
        /*定义全局变量url*/
        var contextPath = "${request.contextPath}";
    </script>
</head>

<body style="background-color: #fff;">
    <#nested />
<div class="main_loading" style="background-color: #f5f5f5;position: fixed;top: 0;left: 0;right: 0;bottom: 0;z-index:11111;">
    <div class="loading_xbox center-display">
        <div>加载中</div>
        <div>&nbsp;</div>
        <div class="loading_xbox_xs">
            <div class="pace_activity"></div>
        </div>
    </div>
</div>
<script src="${request.contextPath}/resources/static/plugins/bootstrap/js/bootstrap.min.js" async="async"></script>
<!--<script src="/public/js/object-copy.js"></script>-->
<script>
    $(function() {
        addNodes({
            parent:"head",
            src:["${request.contextPath}/resources/static/public/js/to_treeJson.js",
                "${request.contextPath}/resources/static/public/js/object-copy.js",
                "${request.contextPath}/resources/static/plugins/bootstrap-notify/bootstrap-notify.min.js",
                "${request.contextPath}/resources/static/public/css/animate.min.css",
                "${request.contextPath}/resources/static/plugins/bootstrap/css/icons/icomoon/styles.css"],
            load:function(){
                $(".main_loading").fadeOut();
            }
        });
    });
</script>
    <#if bodyEnd?has_content>${bodyEnd}</#if>
</body>

</html>
</#macro>