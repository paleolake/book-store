<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h3>Login</h3>
<form action="/login.htm" method="post">
    手机号码: <input type="text" name="mobileNo" value=""/><br>
    密码: <input type="password" name="password" value=""/><br>
    <input type="submit" value="submit"/>
</form>
<br>
<c:if test="${error != null}">
    <span style="color: red;">${error}</span>
</c:if>
</body>
</html>