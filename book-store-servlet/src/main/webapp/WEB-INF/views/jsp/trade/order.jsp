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
<table border="1">
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:forEach var="detail" items="${order.orderDetails}">
                    ${detail.bookName}<br/>
                    ￥${detail.bookAmt}<br/>
                    ${detail.bookCount}本<br/>
                </c:forEach>
            </td>
            <td>
                订单编号：${order.orderCode}<br/>
                ￥${order.orderAmt}<br/>
                订单日期：<fmt:formatDate value="${order.createTime}" pattern="yyyy/MM/dd"></fmt:formatDate>
                状态：${order.orderState.label()}
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>