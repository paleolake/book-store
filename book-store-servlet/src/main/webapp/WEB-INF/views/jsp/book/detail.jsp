<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>网上书城首页</title>
</head>
<body>
<c:if test="${SESSION_CUSTOMER == null}">
<a href="login.htm">登录</a>
<a href="register.htm">注册</a>
</c:if>
<a href="card.htm">购物车</a>
<a href="../">首页</a>
<br/><br/>
${book.bookName}&nbsp;${book.price}<br/>
${book.author}&nbsp;${book.publisherName}&nbsp;${book.publishDate}<br/>
<a href="card/addBook.htm?bookId=${book.id}">加入购物车</a>&nbsp;
<a href="buy.htm?bookId=${book.id}">马上购买</a>
</body>
</html>