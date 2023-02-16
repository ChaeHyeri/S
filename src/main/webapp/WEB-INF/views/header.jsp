<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? '' : pageContext.request.session.getAttribute('id')}"/>
<c:set var="loginOutLink" value="${not empty loginId=='' ? '/login' : '/logout'}"/>
<c:set var="loginOut" value="${not empty loginId=='' ? 'login' : 'logout'}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>냥냥.com</title>
    <meta name="description" content="고양이가 좋아서 만든 사이트">
    <%--  <link rel="icon" type="image/png" href="images/favicon.png">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- CSS -->
    <link rel="stylesheet" href="https://unpkg.com/ress/dist/ress.min.css">

    <link href="/css/style.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.4.1.js"></script>

    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
        }
        input {
            width: 50%;
            height: 35px;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }
        textarea {
            width: 100%;
            background: #f8f8f8;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            resize: none;
            padding: 8px;
            outline-color: #e6e6e6;
        }


        ul {
            list-style:none;
            color: #c5c5c5;
        }

        .main {
            list-style: none;
            width: 500px;
            margin: 10px;
        }
        .main-nav li {
            padding: 10px 0 0 10px;
            margin-bottom: 10px;
        }
        .main-nav li:first-child {
            border-top: 0;
        }
        .main-nav li a {
            height: 20px;
            line-height: 20px;
            display: block;
            text-decoration: none;
            color: #ffffff;
        }
        .main-nav li p {
            display: none;
            line-height: 2;
            padding-top: 10px;
            font-size: 13px;
        }

    </style>
</head>

<body>
<div id="alarm" class="alert alert-success" role="alert" style="display:none;"></div>

</div>

<div id="home" class="big-bg">
    <header class="page-header wrapper">
        <h1><a href="<c:url value='/'/>"><img class="logo" src="/images/logo.png" alt="냥냥닷컴 홈"></a></h1>
        <nav>
            <ul class="main-nav">
                <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
                <li><a href="<c:url value='/'/>">Shop</a></li>
                <li><a href="<c:url value='/board'/>">Post</a></li>
            </ul>
        </nav>
        <ul class="main-nav">
            <li><a href="#" >알림</a>
                <p class="content"></p>
            </li>
        </ul>
    </header>

<script>  // 알림내역
    let showAlarmList = function() {
        $.ajax({
            type:'GET',       // 요청 메서드
            url: '/events', // 요청 URI
            headers : { "content-type": "application/json"}, // 요청 헤더
            success : function(result){
                $(".content").html(ahtml(result));
            },
            error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
    }

    </script>

    <script>
    let ahtml = function(events){
        let tmp = "<ul>";
        events.forEach(function (data) {
            tmp += '<li data-writer=' + data.writer
            tmp += ' <span class="data_bno">' + data.bno + '번 게시물에 '+data.commenter+'님이 댓글을 달았습니다.'+ '</span> '
            tmp += "<br>"
            tmp += ' <span class="data.comment">' + data.comment + '</span> '
            tmp += '</li>'
        })
        return tmp + "</ul>";
    }

    $(function(){
        $(".main-nav>li>a").click(function(){
            showAlarmList();
            $(this).next("p").slideToggle(200);
            $(this).parent("li").siblings("li").children("p").slideUp(200);
        });
    });

</script>

</body>
</html>