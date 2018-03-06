<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
    <meta charset="utf-8" />
    <title>${siteMap.rootNode.title} | 登录</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <meta content="${title}" name="description" />
    <meta content="新易泰信息中心" name="author" />
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <link href="${request.contextPath}/resources/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${request.contextPath}/resources/layouts/login.css" rel="stylesheet" type="text/css"/>
    <link href="${request.contextPath}/resources/global/plugins/fontawesome/css/font-awesome.min.css" rel="stylesheet">
    <link rel="shortcut icon" href="${request.contextPath}/resources/global/favicon.ico"/>
</head>
<!-- END HEAD -->

<body class=" login">
<div class="backdrop"></div>
<!-- BEGIN LOGIN -->
<div class="content">
    <!-- BEGIN LOGIN FORM -->
    <form class="form-horizontal" action="${request.contextPath}/oauthlogin.do" method="post">
    <#--<form class="form-horizontal" action="http://192.168.1.119:7070/REST/user/userLogin" method="post">-->
      <div class="form-title">
        <span class="formcolme"> 欢迎|请登录</span>
        </div>
       <#-- <div class="alert alert-danger display-hide">
            <button class="close" data-close="alert"></button>
            <span> 请输入用户名和密码 </span>
        </div>-->
        <div class="form-group">
            <div class="col-md-2 "></div>
            <div class="col-md-8">
            <input class="form-control " type="text" autocomplete="off" placeholder="用户名" name="userName" id="userName" value="${userName}" />
                </div>
            <div class="col-md-2 "></div>
        </div>
        <div class="form-group">
            <div class="col-md-2 "></div>
            <div class="col-md-8">
            <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" placeholder="密码" name="password" />
                </div >
            <div class="col-md-2 "></div>
        </div>

    <#if message?has_content>
        <div class="alert alert-warning">
            <button class="close" data-close="alert"></button>
            <span> ${message} </span>
        </div>
    </#if>
        <div class="form-group">
            <#--<button type="submit" class="btn red btn-block uppercase">登录</button>-->
                <label class="col-md-2 "></label>
                <div class="col-md-9">
                    <button type="submit" class=" btnprimary">登&nbsp;&nbsp;录</button>
                </div>
        </div>
        <div class="form-actions">
            <p>
            <div class="pull-left">
                <label class="rememberme check">
                    <input type="checkbox" name="remember" value="1" /> 记住我 </label>
            </div>
            <div class="pull-right forget-password-block">
                <a href="javascript:;" id="forget-password" class="forget-password">忘记密码?</a>
            </div>
            </p>
        </div>
    </form>
    <!-- END LOGIN FORM -->
    <!-- BEGIN FORGOT PASSWORD FORM -->
    <form class="forget-form" action="${request.contextPath}/resetPwd" method="post">
        <div class="form-title forgetpas">
          忘记密码 ?请输入你的邮箱重置密码.
        </div>
        <div class="form-group">
            <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="邮箱" name="email" />
        </div>
        <div class="form-actions">
            <button type="button" id="back-btn" class="btn btn-default">返回</button>
            <button type="submit" class="btn btn-primary uppercase pull-right">提交</button>
        </div>
    </form>
    <!-- END FORGOT PASSWORD FORM -->
</div>

<script src="${request.contextPath}/resources/global/kendoui/js/jquery.min.js"></script>
<script src="${request.contextPath}/resources/global/kendoui/js/jquery.validate.min.js"></script>

<script src="${request.contextPath}/resources/global/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${request.contextPath}/resources/layouts/login.js" type="text/javascript"></script>
<!--[if lt IE 9]>
<script src="${request.contextPath}/resources/global/plugins/respond.min.js"></script>
<script src="${request.contextPath}/resources/global/plugins/excanvas.min.js"></script>
<![endif]-->
<script>
    var contextPath = "${request.contextPath}";
</script>
</body>

</html>