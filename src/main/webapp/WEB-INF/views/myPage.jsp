<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원정보 수정</title>
<body>
<%@ include file="./header.jsp"%>
<form action="<c:url value='/me'/>" method="post">
  <div>
    <label for="">아이디</label>
    <input type="text" name="id" value="${userInfo.id}" readonly="readonly"/>
  </div>
  <div>
    <label for="">비밀번호</label>
    <input type="password" name="pwd" value="${userInfo.pwd}">
  </div>
  <div>
    <label for="">이메일</label>
    <input type="email" name="email" value="${userInfo.email}">
  </div>
  <div>
    <label for="">이름</label>
    <input type="text" name="name" value="${userInfo.name}" readonly="readonly"/>
  </div>
  <div>
    <label for="">생년월일</label>
    <fmt:formatDate value="${userInfo.birth}" pattern="yyyy/MM/dd" var="birth" />
    <input type="text" name="birth" value="${birth}" readonly="readonly"/>
  </div>
  <div>
    <label for="">SNS</label>
    <input type="text" name="sns" value="${userInfo.sns}">
  </div>
  <input type="submit" value="수정">
</form>
<%@ include file="./footer.jsp"%>

</body>
</html>