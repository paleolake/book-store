<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>注册</title>
</head>
<body>
<h3>注册</h3>
<form action="/register.htm" method="post">
    手机号码: <input type="text" name="mobileNo" value=""/><br>
    密码: <input type="password" name="password" value=""/><br>
    确认密码: <input type="confirmPsw" name="confirmPsw" value=""/><br>
    <input type="submit" value="注册"/>
</form>
<br>
<c:if test="${error != null}">
    <span style="color: red;">${error}</span>
</c:if>
</body>
</html>