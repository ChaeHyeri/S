<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>상품 목록</title>

    <style>
        h1 {
            text-align: center;
        }
        #productContainer {
            display: flex;
            justify-content: center;
        }

    </style>
</head>
<body>
<%@ include file="./header.jsp"%>

<h1>상품 목록</h1>
<div id="productContainer">
    <table>
        <tr>
            <th>No.  </th>
            <th>상품명  </th>
            <th>가격  </th>
            <th>재고  </th>
            <th>상세</th>
        </tr>
        <c:forEach var="product" items="${productList}">
            <tr>
                <td>${product.product_id}</td>
                <td><a href="<c:url value='/productDetail?id=${product.product_id}'/>">${product.name}</a></td>
                <td>${product.price}</td>
                <td>${product.stock}</td>
                <td>${product.description}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<%@ include file="./footer.jsp"%>
</body>
</html>