<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>网上书城首页</title>
</head>
<body>
<h3>购物车</h3>
<form action="/account/buy.htm" method="post">
    <c:forEach var="cardDetail" items="${cardDetails}">
        <input type="checkbox" name="bookId" value="${cardDetail.book.id}"/>
        ${cardDetail.book.bookName}&nbsp;￥${cardDetail.book.price}&nbsp;
        ${cardDetail.book.author}&nbsp;${cardDetail.book.publisherName}&nbsp;${cardDetail.count}本<br/>
    </c:forEach>
    <br/>
    <input type="button" value="立即购买" id="buy"/>
</form>
</body>
<script src="https://code.jquery.com/jquery-2.2.4.js" type="application/javascript"></script>
<script type="application/javascript">
    $(document).ready(function () {
        $("#buy").click(function(){
            var length = $("input[name='bookId']:checked").length;
            if (length <= 0) {
                alert("请选择需要购买的商品！")
                return;
            }
            document.forms[0].submit();
        });
    });
</script>
</html>