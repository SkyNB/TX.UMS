<html>
<body>

<h1>成功1</h1>
<table>
    <th>用户名</th>
    <th> 手机号</th>
<#list list as user>
<tr>
    <td>${user.userName}</td>
   <td>${user.phone}</td>
    </br>
</tr>
</table>
<#--用户名：${user.userName}
密  码：${user.userPassword}-->
</#list>

</body>
</html>