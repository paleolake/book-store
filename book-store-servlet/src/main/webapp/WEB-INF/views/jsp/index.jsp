<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>网上书城首页</title>
</head>
<body>
<jsp:include page="common/hender.jsp"></jsp:include>
<br/><br/>
<c:forEach var="book" items="${books}">
    <a href="book/detail.htm?bookId=${book.id}">${book.bookName}</a>&nbsp;￥${book.price}<br/>
    <a href="card/addBook.htm?bookId=${book.id}&price=${book.price}">加入购物车</a>&nbsp;
    <a href="/card/buy.htm?bookId=${book.id}&price=${book.price}">立即购买</a>
    <br/><br/>
</c:forEach>
</body>
</html>