<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>网上书城首页</title>
</head>
<body>
<jsp:include page="../common/hender.jsp"></jsp:include>
<br/><br/>
${book.bookName}&nbsp;￥${book.price}<br/>
作者：${book.author}<br/>
出版社：${book.publisherName}<br/>
出版日期：<fmt:formatDate value="${book.publishDate}" pattern="yyyy/MM/dd"/><br/><br/>
<a href="/card/addBook.htm?bookId=${book.id}&price=${book.price}">加入购物车</a>&nbsp;
<a href="/card/buy.htm?bookId=${book.id}&price=${book.price}">马上购买</a>
</body>
</html>