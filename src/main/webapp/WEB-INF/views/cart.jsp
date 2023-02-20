<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>장바구니</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 10px;
        }
    </style>
</head>
<body>
<%@ include file="./header.jsp"%>

<h1>장바구니</h1>
<table>
    <tr>
        <th>상품번호</th>
        <th>상품명</th>
        <th>가격</th>
        <th>수량</th>
        <th>재고</th>
        <th></th>
    </tr>
    <c:forEach var="cartItem" items="${cartList}">
        <tr>
            <td>${cartItem.product_id}</td>
            <td>${cartItem.productDTO.name}</td>
            <td>${cartItem.productDTO.price}</td>
            <td>${cartItem.quantity}</td>
            <td>${cartItem.productDTO.stock}</td>
            <td>
                <form action="<c:url value='/cart/delete'/>" method="post">
                    <input type="hidden" name="productId" value="${cartItem.product_id}"/>
                    <input type="hidden" name="loginId" value="${pageContext.request.session.getAttribute('id')}"/>
                    <input type="submit" value="X"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="<c:url value='/order'/>" method="post">
    <input type="submit" value="주문하기"/>
</form>
<form action="<c:url value='/shop'/>" method="get">
    <input type="submit" value="상품목록으로 돌아가기"/>
</form>

<%@ include file="./footer.jsp"%>
</body>
</html>