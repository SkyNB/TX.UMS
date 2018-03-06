
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <link href="${request.contextPath}/resources/index/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/index/plugins/bootstrap/css/icons/icomoon/styles.css" rel="stylesheet" />
    <link href="${request.contextPath}/resources/index/plugins/login/stars.css" rel="stylesheet" />
    <script src="http://cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <style>
        .full-content-center {
            width: 100%;
            padding: 5px 0px;
            max-width: 500px;
            margin: 6% auto;
            text-align: center;
        }

        .full-content-center h1 {
            font-size: 150px;
            font-family: "Open Sans";
            line-height: 150px;
            font-weight: 700;
            color: #252932;
        }

        .not-logged-avatar {
            width: 100px;
            margin: 0px auto;
            display: block;
            margin-bottom: 20px;
            text-align: center;
            box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
        }

        .btn-default {
            background-color: #ABB7B7;
            border-color: #ABB7B7;
            color: #fff;
        }

        .btn-default:hover,
        .btn-default:focus,
        .btn-default:active,
        .btn-default.active,
        .open .dropdown-toggle.btn-default {
            background-color: #98A3A3;
            border-color: #98A3A3;
            color: #fff;
        }

        .login-wrap {
            margin: 20px 10%;
            text-align: left;
            background: rgba(250, 250, 2250, 0.1);
            padding: 20px 20px;
            color: #fff;
        }

        .login-wrap a {
            color: #fff;
        }

        .login-wrap i {
            margin-right: 5px;
        }

        .login-wrap .checkbox {
            margin-left: 0;
            padding-left: 0;
        }

        .login-wrap .btn-block {
            margin: 5px 0;
        }

        .login-wrap .login-input {
            position: relative;
        }

        .login-wrap .login-input .text-input {
            padding-left: 30px;
        }

        .login-wrap .login-input i.overlay {
            position: absolute;
            left: 10px;
            top: 10px;
            color: #aaa;
        }

    </style>
</head>

<body>
<div class="prod-fallback-edoc" style="position: fixed;top: 0;left: 0;right: 0;bottom: 0;">
    <div id="stars"></div>
    <div id="stars2"></div>

    <!-- container -->
    <div class="container">
        <div class="full-content-center">
            <p class="text-center">
                <a href="#"><img src="${request.contextPath}/resources/index/plugins/login/logo2.png" alt="Logo" style="height: 36px;"></a>
            </p>
            <div class="login-wrap animated flipInX">
                <div class="login-block">
                    <img src="${request.contextPath}/resources/index/plugins/login/default-user.png" class="img-circle not-logged-avatar">
                    <#--<form role="form" action="${request.contextPath}/oauthlogin.do"  method="post">-->
                    <form role="form" action="${request.contextPath}/login"  method="post">
                        <div class="form-group login-input">
                            <i class="glyphicon glyphicon-user overlay"></i>
                            <input type="text" class="form-control text-input" placeholder="用户名" name="userName" id="userName" value="${userName}" >
                        </div>
                        <div class="form-group login-input">
                            <i class="glyphicon glyphicon-eye-close overlay"></i>
                            <input type="passWord" name="passWord" class="form-control text-input" placeholder="********">
                        </div>

                        <div class="row">
                            <#--<div style="display:none;" class="col-sm-6">-->
                                <#--<a href="register.html" class="btn btn-default btn-block">注册</a>-->
                            <#--</div>-->
                            <div class="col-sm-12">
                                <button type="submit" class="btn btn-success btn-block">登录</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>

</div>
<script>
    function rd(begin,end){
        return Math.floor(Math.random()*(end-begin))+begin;
    }
    $(".prod-fallback-edoc").css("background-image","url('${request.contextPath}/resources/index/plugins/login/bg/"+rd(1,10)+".jpg')");
    var contextPath = "${request.contextPath}";
</script>
</body>

</html>