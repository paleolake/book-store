<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>网上书城首页</title>
</head>
<body>
<jsp:include page="../common/hender.jsp"></jsp:include>
<br/><br/>
${book.bookName}&nbsp;${book.price}<br/>
${book.author}&nbsp;${book.publisherName}&nbsp;${book.publishDate}<br/>
<a href="/card/addBook.htm?bookId=${book.id}&price=${book.price}">加入购物车</a>&nbsp;
<a href="/card/buy.htm?bookId=${book.id}&price=${book.price}">马上购买</a>
</body>
</html>