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

<%--  <form method="post" action="${pageContext.request.contextPath}/cart">--%>
<%--    <input type="hidden" name="productId" value="${product.product_id}">--%>
<%--    <label for="quantity">수량</label>--%>
<%--    <select name="quantity" id="quantity">--%>
<%--      <c:forEach var="i" begin="1" end="${product.stock}">--%>
<%--        <option value="${i}">${i}</option>--%>
<%--      </c:forEach>--%>
<%--    </select>--%>
<%--    <button type="submit">장바구니에 추가</button>--%>
<%--  </form>--%>

<%--  <form method="post" action="${pageContext.request.contextPath}/order">--%>
<%--    <input type="hidden" name="productId" value="${product.product_id}">--%>
<%--    <label for="quantity">수량</label>--%>
<%--    <select name="order-quantity" id="order-quantity">--%>
<%--      <c:forEach var="i" begin="1" end="${product.stock}">--%>
<%--        <option value="${i}">${i}</option>--%>
<%--      </c:forEach>--%>
<%--    </select>--%>
<%--    <button type="submit">주문하기</button>--%>
<%--  </form>--%>

  <form id="cart-form" method="post" action="${pageContext.request.contextPath}/cart">
    <input type="hidden" name="productId" value="${product.product_id}">
    <button type="submit">장바구니에 추가</button>
  </form>

  <form id="order-form" method="post" action="${pageContext.request.contextPath}/order">
    <input type="hidden" name="productId" value="${product.product_id}">
    <button type="submit">주문하기</button>
  </form>

<%-- 수량 선택 1번만 출력되도록 form 바깥에 선언 --%>
  <select name="quantity" id="quantity">
    <c:forEach var="i" begin="1" end="${product.stock}">
      <option value="${i}">${i}</option>
    </c:forEach>
  </select>

  <script>
    // 장바구니 form submit 시 수량 선택 값을 함께 전송
    document.getElementById('cart-form').addEventListener('submit', function(event) {
      event.preventDefault(); // 기본 form submit 방지
      const quantity = document.getElementById('quantity').value;
      const form = this;
      const input = document.createElement('input');
      input.setAttribute('type', 'hidden');
      input.setAttribute('name', 'quantity');
      input.setAttribute('value', quantity);
      form.appendChild(input);
      form.submit();
    });

    // 주문하기 form submit 시 수량 선택 값을 함께 전송
    document.getElementById('order-form').addEventListener('submit', function(event) {
      event.preventDefault();
      const quantity = document.getElementById('quantity').value;
      const form = this;
      const input = document.createElement('input');
      input.setAttribute('type', 'hidden');
      input.setAttribute('name', 'quantity');
      input.setAttribute('value', quantity);
      form.appendChild(input);
      form.submit();
    });
  </script>



  <%@ include file="./footer.jsp"%>
</body>
</html>
