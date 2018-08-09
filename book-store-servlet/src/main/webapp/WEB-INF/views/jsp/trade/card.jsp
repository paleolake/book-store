<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>网上书城首页</title>
</head>
<body>
<h3>购物车</h3>
<br/><br/>
<form action="/buy.htm" method="post">
    <c:forEach var="book" items="${books}">
        <input type="checkbox" name="bookId" value="${book.id}"/>
        <a href="book/detail.htm?bookId=${book.id}">${book.bookName}</a>&nbsp;${book.price}<br/>
    </c:forEach>
    <input type="button" value="立即购买"/>
</form>
</body>
<script src="https://code.jquery.com/jquery-2.2.4.js" type="application/javascript"/>
<script type="application/javascript">
    $(document).ready(function () {
        function buy() {
            var length = $("input[name='bookId']:checked").length;
            if (length <= 0) {
                alert("请选择需要购买的商品！")
                return;
            }
            document.forms[0].submit();
        }
    });
</script>
</html>