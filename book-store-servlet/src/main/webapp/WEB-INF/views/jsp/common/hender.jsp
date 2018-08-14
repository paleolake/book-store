<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="book.store.model.Customer" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<a href="../../">首页</a>&nbsp;
<a href="card.htm">购物车</a>&nbsp;
<c:choose>
    <c:when test="${SESSION_CUSTOMER == null}">
        <a href="../../login.htm">登录</a>&nbsp;
        <a href="../../register.htm">注册</a>&nbsp;
    </c:when>
    <c:otherwise>
        <%
            Customer customer = (Customer) session.getAttribute("SESSION_CUSTOMER");
            String mobileNo = StringUtils.replace(customer.getMobileNo(), StringUtils.substring(customer.getMobileNo(), 3, 7), "****");
        %>
        <a href="../../account/order.htm">我的订单</a>&nbsp;
        ${mobileNo}&nbsp;
        <a href="../../logout.htm">登出</a>
    </c:otherwise>
</c:choose>