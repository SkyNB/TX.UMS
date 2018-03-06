<html>
<body>

<h1>成功</h1>

<#--查询-->
<#--<form action="${request.contextPath}/ums/sestctUms" method="get">
    <input type="submit"  value="查询"></form>-->

<#--修改密码-->
<#--<form action="${request.contextPath}/myPassword?userId=1051611061407073267" method="get">

    用户名<input type="text" name="" id="" value="">
    旧密码<input type="password" name="" id="" value="">
    新密码<input type="password" name="" id="" value="">
    确认新密码<input type="password" name="" id="" value="">
    <input type="submit"  value="修改密码">
</form>-->

<#--跳转修改密码页面-->
<form action="${request.contextPath}/user/myPassword?userId=1051611061407073267" method="get">
    <input type="submit"  value="跳转修改密码页面">
</form>

<a href="http://7070/REST/user/login"><button value="">本-REST/user/login</button></a>
<a href="http://192.168.1.119:7070/UMS/ums/home"><button value="">查home</button></a>
<a href="http://localhost:7070/UMS/ums/list"><button value="">查list</button></a>
<a href="http://www.baidu.com"><button value="">查百度</button></a>
<a href="${request.contextPath}/ums/selectUserTen"><button value="">本-UMS/ums/selectUserTen</button></a>
<a href="${request.contextPath}/user/myPassword?userId=1051611061407073267"><button value="">跳转修改密码页面1"</button></a>
<a href="WEB-INF/views/user/myPassword.ftl"><button value="">跳转修改密码页面2"</button></a>
<a href="${request.contextPath}/ums/selectUserBy"><button value="">本-UMS/ums/selectUserBy-账户密码查用户</button></a>
<a href="${request.contextPath}/ums/selectByUser"><button value="">本-UMS/ums/selectByUser-账户密码查用户</button></a>
<a href="${request.contextPath}/ums/selectByPrimaryKey"><button value="">本-UMS/ums/selectByPrimaryKey-账户密码查用户</button></a>
<a href="${request.contextPath}/ums/selectUserOne"><button value="">本-UMS/ums/selectUserOne-账户密码查用户</button></a>
<a href="${request.contextPath}/ums/selectUserPwd"><button value="">本-UMS/ums/selectUserPwd-用户名查密码</button></a>
<a href="${request.contextPath}/ums/selectByNamePass"><button value="">本-UMS/ums/selectByNamePass-用户名+密码查找用户</button></a>
<a href="index.html">s</a>
</body>
</html>