<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>
<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? '' : pageContext.request.session.getAttribute('id')}"/>

<c:set var="loginOutLink" value="${loginId=='' ? '/loginpage' : '/logout'}"/>
<c:set var="loginOut" value="${loginId=='' ? 'login' : 'logout'}"/>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="고양이가 좋아서 만든 사이트">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSS -->
    <link rel="stylesheet" href="https://unpkg.com/ress/dist/ress.min.css">
    <link href="https://fonts.googleapis.com/css?family=Philosopher" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/0f2c8c1913.js" crossorigin="anonymous"></script>
    <style>
        * { box-sizing:border-box; }
        a { text-decoration: none; }
        form {
            width:400px;
            height:500px;
            display : flex;
            flex-direction: column;
            align-items:center;
            position : absolute;
            top:50%;
            left:50%;
            transform: translate(-50%, -50%) ;
            border: 1px solid rgb(27, 56, 77);
            border-radius: 10px;
        }
        input[type='text'], input[type='password'] {
            width: 300px;
            height: 40px;
            border : 1px solid rgb(19, 19, 19);
            border-radius:5px;
            padding: 0 10px;
            margin-bottom: 10px;
            background-color: rgb(75, 98, 121,0.8);
        }

        button {
            background-color: rgb(45, 75, 96);
            color : #cecece;
            width:300px;
            height:50px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 20px 0 30px 0;
        }
        #title {
            color: rgb(19, 53, 77);
            font-size: 50px;
            margin: 40px 0 30px 0;
        }
        #msg {

            height: 30px;
            text-align:center;
            font-size:16px;
            color: #3e84b7;
            margin-bottom: 20px;
        }
    </style>

</head>
<body>

<div id="home" class="big-bg">
    <header class="page-header wrapper">
        <h1><a href="<c:url value='/'/>"><img class="logo" src="images/logo.png" alt="냥냥닷컴 홈"></a></h1>
        <nav>
            <ul class="main-nav">
                <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
                <li><a href="<c:url value='/'/>">Shop</a></li>
                <li><a href="<c:url value='/board'/>">Post</a></li>
            </ul>
        </nav>
    </header>

<form action="<c:url value="/loginpage/login"/>" method="post" onsubmit="return formCheck(this);">
    <h3 id="title">login</h3>
    <div id="msg">
        <c:if test="${not empty param.msg}">
            <i class="fa fa-exclamation-circle"> ${param.msg}</i>
        </c:if>
    </div>
    <input type="text" name="id" value="${cookie.id.value}" placeholder="email" autofocus>
    <input type="password" name="pwd" placeholder="password">
    <input type="hidden" name="toURL" value="${param.toURL}">
    <input type="hidden" name="regDate" value="${param.regDate}">

    <button>login</button>
    <div>
        <label><input type="checkbox" name="rememberId" value="on" ${empty cookie.id.value ? "":"checked"}> id 저장</label>
        <a href="">id 찾기</a>
        <a href="">비밀번호 찾기</a>
        <a href="<c:url value='/register'/>">회원가입</a>
    </div>
    <script>

        function formCheck(frm) {
            let msg = '';
            if(frm.id.value.length==0) {
                setMessage('id를 입력해주세요.', frm.id);
                return false;
            }
            if(frm.pwd.value.length==0) {
                setMessage('비밀번호를 입력해주세요.', frm.pwd);
                return false;
            }


            return true;
        }


        function setMessage(msg, element){
            document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;
            if(element) {
                element.select();
            }
        }
    </script>

</form>
</body>
</html>