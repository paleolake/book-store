<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h3>Login</h3>
<form action="/login.htm" method="post">
    手机号码: <input type="text" name="mobileNo" value="" maxlength="11"/><br>
    密码: <input type="password" name="password" value="" maxlength="16"/><br>
    <input type="button" value="登录" id="login"/>
</form>
<br>
<c:if test="${error != null}">
    <span style="color: red;" id="error">${error}</span>
</c:if>
</body>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script>
    $(document).ready(function () {
        $("#login").click(function () {
            var mobileNo = $("input[name='mobileNo']").val();
            if (mobileNo == null || mobileNo == '') {
                $("#error").text("手机号码不能为空");
                return;
            }
            var password = $("input[name='password']").val();
            if (password == null || password == '') {
                $("#error").text("密码不能为空");
                return;
            }
            document.forms[0].submit();
        });
    });
</script>
</html>