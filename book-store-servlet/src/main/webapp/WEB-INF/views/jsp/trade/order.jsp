<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>订单列表</title>
</head>
<body>
<h3>订单列表</h3>
<jsp:include page="../common/hender.jsp"></jsp:include>
<br/><br/>
<c:forEach var="order" items="${orders}">
    订单总金额：￥${order.orderAmt}
    订单日期：<fmt:formatDate value="${order.createTime}" pattern="yyyy/MM/dd"></fmt:formatDate>&nbsp;
    状态：${order.orderState.label()}&nbsp;
    订单编号：${order.orderCode}<br/>
    <c:forEach var="detail" items="${order.orderDetails}">
        >><a href="../book/detail.htm?bookId=${detail.bookId}">${detail.bookName}</a>&nbsp;￥${detail.bookAmt}&nbsp;${detail.bookCount}本<br/>
    </c:forEach>
    <br/>
</c:forEach>
</body>
</html>