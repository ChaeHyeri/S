<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true"%>


<!DOCTYPE html>
<html>
<head>
    <title>냥냥.com</title>
    <style>
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Noto Sans KR", sans-serif;
        }
        .container {
            width : 50%;
            margin : auto;
        }
        .writing-header {
            position: relative;
            margin: 20px 0 0 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #323232;
        }
        
        .frm {
            width:100%;
        }
        .btn {
            background-color: rgb(236, 236, 236); /* Blue background */
            border: none; /* Remove borders */
            color: black; /* White text */
            padding: 6px 12px; /* Some padding */
            font-size: 16px; /* Set a font size */
            cursor: pointer; /* Mouse pointer on hover */
            border-radius: 5px;
        }
        .btn:hover {
            text-decoration: underline;
        }

        #commentList {
            width : 50%;
            margin : auto;
            color: #c5c5c5;
        }

        .update {
            color: rgba(197,197,197,0.5);
        }

        .comment {
            color: #eeeeee;
        }

        .commenter {
            color: #00bbdd;
            font-size: 20px;
        }

        #commentWrite {
            text-align:center;
            margin: 70px 0px 10px 0px;
        }

        #commentWrite > input {
            width: 50%;
            height: 100px;
            margin: 5px 0px 10px 0px;
            border: 1px solid #e9e8e8;
            padding: 8px;
            background: #f8f8f8;
            outline-color: #e6e6e6;
        }
    </style>
</head>

<body>
<%@ include file="./header.jsp"%>

    <div class="well">
        <input type="text" id="message" value="소켓연습용" class="form-control"/>
        <button id="btnSend" class="btn btn-primary">-</button>
    </div>

    <script>
        let msg = "${msg}";
        if(msg=="WRT_ERR") alert("게시물 등록에 실패하였습니다. 다시 시도해 주세요.");
        if(msg=="MOD_ERR") alert("게시물 수정에 실패하였습니다. 다시 시도해 주세요.");
    </script>
    <div class="container">
        <h2 class="writing-header"> 게시판 ${mode=="new" ? "글쓰기" : ''}</h2>
        ${mode=="new" ? ''  : "조회수 : "} ${boardDto.view_cnt}
        ${mode=="new" ? ''  : "작성자 : "} ${boardDto.writer}
        <form id="form" class="frm" action="" method="post">
            <input type="hidden" name="bno" value="${boardDto.bno}">

            <input name="title" type="text" value="${boardDto.title}" placeholder="  제목을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
            <textarea name="content" rows="20" placeholder=" 내용을 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}>${boardDto.content}</textarea><br>

            <c:if test="${mode eq 'new'}">
                <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록</button>
            </c:if>
            <c:if test="${mode ne 'new'}">
                <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 글쓰기</button>
            </c:if>
            <c:if test="${boardDto.writer eq loginId}">
                <button type="button" id="modifyBtn" class="btn btn-modify"><i class="fa fa-edit"></i> 수정</button>
                <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
            </c:if>
            <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
        </form>
    </div>

    <div id="commentWrite">
    <input type="text" name="comment" placeholder="댓글을 남겨주세요."><br>
    <button id="sendBtn" type="button" class="btn btn-primary">확인</button>
    <button id="modBtn" type="button" style="display: none">수정</button>
    </div>

    <div id="commentList"></div> <!-- 댓글내용 불러옴-->

    <%--답글form은 숨겨놨다가, "답글"버튼을 클릭하면 해당 댓글 바로 아래로 이동하도록 한다.--%>
    <div id="replyForm" style="display:none">
        <input type="text" name="replyComment">
        <button id="wrtRepBtn" type="button">등록</button>
    </div>

    <script>
        let bno = ${boardDto.bno};
        let writer = "${boardDto.writer}";
        let showList = function(bno) {
            $.ajax({
                type:'GET',       // 요청 메서드
                url: '/comments?bno='+bno,  // 요청 URI
                headers : { "content-type": "application/json"}, // 요청 헤더
                success : function(result){
                    // $("#commentList").html(result);
                    $("#commentList").html(toHtml(result));
                },
                error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        }

        $(document).ready(function(){
            showList(bno);

            $('#btnSend').on('click', function(evt) {
                evt.preventDefault();

                if (socket.readyState !== 1) return;
                let message = $('input#message').val(); // input 박스의 message를 소켓에 전송(handlerTextMessage)
                console.log(message);
                socket.send(message);

            });


        });
  </script>


    <script>
            let formCheck = function() {

                //getElementByID - html 태그 내에 해당 id를 가지고 있는 요소를 찾음
                let form = document.getElementById("form");
                if(form.title.value=="") {
                    alert("제목을 입력해 주세요.");
                    form.title.focus();
                    return false;
                }
                if(form.content.value=="") {
                    alert("내용을 입력해 주세요.");
                    form.content.focus();
                    return false;
                }
                return true;
            }
            // 글쓰기 버튼 클릭 시 나타나는 화면(GET)
            $("#writeNewBtn").on("click", function(){
                location.href="<c:url value='/board/write'/>?page=${page}&pageSize=${pageSize}";
            });
            // 작성한 게시물을 저장(POST)
            $("#writeBtn").on("click", function(){
                let form = $("#form");
                form.attr("action", "<c:url value='/board/write'/>");
                form.attr("method", "post");
                if(formCheck())
                    form.submit();
            });

            // 수정
            $("#modifyBtn").on("click", function(){
                let form = $("#form");
                let isReadonly = $("input[name=title]").attr('readonly');

                // 1. 읽기 상태이면, 수정 상태로 변경
                if(isReadonly=='readonly') {
                    $(".writing-header").html("게시판 수정");
                    $("input[name=title]").attr('readonly', false);
                    $("textarea").attr('readonly', false);
                    $("#modifyBtn").html("<i class='fa fa-pencil'></i> 등록");
                    return;
                }
                // 2. 수정 상태이면, 수정된 내용을 서버로 전송
                form.attr("action", "<c:url value='/board/modify'/>?page=${page}&pageSize=${pageSize}");
                form.attr("method", "post");
                if(formCheck())
                    form.submit();
            });

            // 삭제
            $("#removeBtn").on("click", function(){
                if(!confirm("삭제하시겠습니까?")) return;
                let form = $("#form");
                form.attr("action", "<c:url value='/board/remove'/>?page=${page}&pageSize=${pageSize}");
                form.attr("method", "post");
                form.submit();
            });
            //. 목록버튼
            $("#listBtn").on("click", function(){
                location.href="<c:url value='/board'/>?page=${page}&pageSize=${pageSize}";
            });

    </script>
<%-- ★★★★★★★★ 댓글 영역 ★★★★★★★★★--%>
<script src="/reply.js">

</script>

<%@ include file="./footer.jsp"%>
</body>
</html>