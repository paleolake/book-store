<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title> 操作失败</title>
</head>
<body>
<jsp:include page="../common/hender.jsp"></jsp:include>
<br/><br/>
 操作失败！！
<br>
错误原因：<c:out value="${error}"/>
<c:forEach var="constraintViolation" items="${constraintViolations}">
    <c:out value="${constraintViolation.message}"/><br/>
</c:forEach>
</body>
</html>