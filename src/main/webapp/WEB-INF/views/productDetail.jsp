<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
  <%@ include file="./header.jsp"%>

    <title>상세페이지</title>
</head>
<body>

  <table>
    <tr>
      <th>상품명</th>
      <td>${product.name}</td>
    </tr>
    <tr>
      <th>가격</th>
      <td>${product.price}</td>
    </tr>
    <tr>
      <th>재고</th>
      <td>${product.stock}</td>
    </tr>
    <tr>
      <th>상세 설명</th>
      <td>${product.description}</td>
    </tr>
  </table>

  <form method="post" action="${pageContext.request.contextPath}/cart">
    <input type="hidden" name="productId" value="${product.product_id}">
    <label for="quantity">수량</label>
    <select name="quantity" id="quantity">
      <c:forEach var="i" begin="1" end="${product.stock}">
        <option value="${i}">${i}</option>
      </c:forEach>
    </select>
    <button type="submit">장바구니에 추가</button>
  </form>

  <%@ include file="./footer.jsp"%>
</body>
</html>
