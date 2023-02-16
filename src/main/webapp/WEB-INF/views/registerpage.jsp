<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false"%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="고양이가 좋아서 만든 사이트">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
    <link href="https://fonts.googleapis.com/css?family=Philosopher" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <style>
        * { box-sizing:border-box;
            margin: 0;
            padding: 0;}
        a { text-decoration: none; }
        form {
            width:400px;
            height:700px;
            display : flex;
            flex-direction: column;
            align-items:center;
            position : absolute;
            top:50%;
            left:50%;
            transform: translate(-50%, -50%) ;
            border: 1px solid rgb(27, 56, 77);
            border-radius: 10px;
            background-color: rgb(75, 98, 121,0.8);
        }
        .input-field {
            width: 300px;
            height: 50px;
            border : 1px solid rgb(0, 0, 0);
            border-radius:10px;
            padding: 4px;
            margin-bottom: 10px;
        }

        label {
            width:300px;
            height:30px;
        }
        button {
            background-color: rgb(45, 75, 96);
            color : #d5d5d5;
            width:300px;
            height:50px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 20px 0 30px 0;
        }
        .title {
            color: #1B384DFF;
            font-size : 50px;
            margin: 40px 0 30px 0;
            background-color: rgb(75, 98, 121,0.1);
        }
        .msg {
            height: 30px;
            text-align:center;
            font-size:16px;
            color: #3e84b7;
            margin-bottom: 20px;
        }

        .sns-chk {
            margin-top : 5px;
        }

    </style>
    <title>Register</title>
</head>
<body>
<div id="home" class="big-bg">
    <header class="page-header wrapper">
        <h1><a href="<c:url value='/'/>"><img class="logo" src="images/logo.png" alt="냥냥닷컴 홈"></a></h1>
        <nav>
            <ul class="main-nav">
                <li><a href="<c:url value='/'/>">Post</a></li>
                <li><a href="<c:url value='/'/>">Contact</a></li>
            </ul>
        </nav>
    </header>

<form action="<c:url value="/register/save"/>" method="POST" onsubmit="return formCheck(this);">
    <div class="title">Register</div>
    <div id="msg" class="msg"><form:errors path="id"/></div>
    <label for="">아이디</label>
    <input class="input-field" type="text" name="id" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <label for="">비밀번호</label>
    <input class="input-field" type="password" name="pwd" placeholder="8~12자리의 영대소문자와 숫자 조합">
    <label for="">이름</label>
    <input class="input-field" type="text" name="name" placeholder="홍길동">
    <label for="">이메일</label>
    <input class="input-field" type="text" name="email" placeholder="example@fastcampus.co.kr">
    <label for="">생일</label>
    <input class="input-field" type="date" name="birth" placeholder="2020-12-31">

    <div class="sns-chk">
        <label><input type="checkbox" name="sns" value="facebook"/>페이스북</label>
        <label><input type="checkbox" name="sns" value="kakaotalk"/>카카오톡</label>
        <label><input type="checkbox" name="sns" value="instagram"/>인스타그램</label>
    </div>
    <button>회원가입</button>

    <script>
    function formCheck(frm) {
        var msg ='';
        if(frm.id.value.length<3) {
            setMessage('id의 길이는 3이상이어야 합니다.', frm.id);
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
</div>
</body>
</html>