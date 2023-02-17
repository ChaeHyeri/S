<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
    <title>냥냥.com</title>
    <style>

        a {
            text-decoration: none;
            color: black;
        }
        .userinfo {
            text-align: center;
        }
        button,
        input {
            border: none;
            outline: none;
        }
        .board-container {
            width: 60%;
            height: 1000px;
            margin: 0 auto;
            /* border: 1px solid black; */
        }
        .search-container {
            background-color: rgba(253, 253, 250,0.1);
            width: 100%;
            height: 110px;
            border: 1px solid #ddd;
            margin-top : 10px;
            margin-bottom: 30px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .search-form {
            height: 37px;
            display: flex;
        }
        .search-option {
            width: 100px;
            height: 100%;
            outline: none;
            margin-right: 5px;
            border: 1px solid #ccc;
            color: gray;
        }
        .search-option > option {
            text-align: center;
        }
        .search-input {
            color: gray;
            background-color: white;
            border: 1px solid #ccc;
            height: 100%;
            width: 300px;
            font-size: 15px;
            padding: 5px 7px;
        }
        .search-input::placeholder {
            color: gray;
        }
        .search-button {
            /* 메뉴바의 검색 버튼 아이콘  */
            width: 20%;
            height: 100%;
            background-color: rgb(22, 22, 22);
            color: rgb(209, 209, 209);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 15px;
        }
        .search-button:hover {
            color: rgb(165, 165, 165);
        }
        table {
            border-collapse: collapse;
            width: 100%;
            border-top: 2px solid rgb(39, 39, 39);
        }

        th,
        td {
            width:300px;
            text-align: center;
            padding: 10px 12px;
            border-bottom: 1px solid #ddd;
            background-color: rgba(255,255,255,0.5);
        }
        td {
            color: rgb(0, 0, 0);
        }
        .no      { width:150px;}
        .title   { width:50%;  }
        td.title   { text-align: left;  }
        td.writer  { text-align: left;  }
        td.viewcnt { text-align: right; }
        td.title:hover {
            text-decoration: underline;
        }
        .paging {
            color: black;
            width: 100%;
            align-items: center;
        }
        .page {
            color: black;
            padding: 6px;
            margin-right: 10px;
        }
        .paging-active {
            background-color: rgb(216, 216, 216);
            border-radius: 5px;
            color: rgb(24, 24, 24);
        }
        .paging-container {
            width:100%;
            height: 70px;
            display: flex;
            margin-top: 50px;
            margin : auto;
        }
        .btn-write {
            background-color: rgb(236, 236, 236); /* Blue background */
            border: none; /* Remove borders */
            color: black; /* White text */
            padding: 6px 12px; /* Some padding */
            font-size: 16px; /* Set a font size */
            cursor: pointer; /* Mouse pointer on hover */
            border-radius: 5px;
            margin-left: 30px;
        }
        .btn-write:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<%@ include file="./header.jsp"%>

    <div class="userinfo">${userid}님, 환영합니다.</div>

    <script>
        let msg = "${msg}";
        if(msg=="LIST_ERR")  alert("게시물 목록을 가져오는데 실패했습니다. 다시 시도해 주세요.");
        if(msg=="READ_ERR")  alert("삭제되었거나 없는 게시물입니다.");
        if(msg=="DEL_ERR")   alert("삭제되었거나 없는 게시물입니다.");
        if(msg=="DEL_OK")    alert("성공적으로 삭제되었습니다.");
        if(msg=="WRT_OK")    alert("성공적으로 등록되었습니다.");
        if(msg=="MOD_OK")    alert("성공적으로 수정되었습니다.");
    </script>

    <script>$(".title").on("click", function(){
        let vcnt = ${boardDto.view_cnt+1};
    }); </script>

    <div style="text-align:center">
        <div class="board-container">
            <div class="search-container">
<%--  검색창   --%>
               <%--  검색조건 선택박스   --%>
                    <form action="<c:url value="/board"/>" class="search-form" method="get">
                        <select class="search-option" name="option">
                        <option value="A" ${ph.sc.option=='A' || ph.sc.option=='' ? "selected" : ""}>제목+내용</option>
                        <option value="T" ${ph.sc.option=='T' ? "selected" : ""}>제목</option>
                        <option value="W" ${ph.sc.option=='W' ? "selected" : ""}>글쓴이</option>
                    </select>

                    <input type="text" name="keyword" class="search-input" type="text" value="${ph.sc.keyword}" placeholder="검색어를 입력해주세요">
                    <input type="submit" class="search-button" value="검색">
                </form>
                <button id="writeBtn" class="btn-write" onclick=location.href="<c:url value='/board/write'/>?page=${page}&pageSize=${pageSize}"><i class="fa fa-pencil"></i> 글쓰기</button>
            </div>


<%--    게시판 리스트     --%>
            <table>
                <tr>
                    <th class="no">번호</th>
                    <th class="title">제목</th>
                    <th class="writer">이름</th>
                    <th class="reg_date">등록일</th>
                    <th class="view_cnt">조회수</th>
                </tr>
                <c:forEach var="boardDto" items="${list}">
                    <tr>
                        <td class="no">${boardDto.bno}</td>
                        <%--   제목을 클릭하면 게시물을 읽음      --%>
                        <td class="title"><a href="<c:url value="/board/read${ph.sc.queryString}&bno=${boardDto.bno}"/>">${boardDto.title}</a></td>

                        <td class="writer">${boardDto.writer}</td>
                        <c:choose>
                            <%-- 게시글 작성 시간이 오늘이면 시간만 표기 --%>
                            <c:when test="${boardDto.reg_date.time >= startOfToday}">
                                <td class="reg_date"><fmt:formatDate value="${boardDto.reg_date}" pattern="HH:mm" type="time"/></td>
                            </c:when>
                            <c:otherwise>
                                <%-- 게시글 작성 시간이 오늘 이전이면 년/월/일로 표기 --%>
                                <td class="reg_date"><fmt:formatDate value="${boardDto.reg_date}" pattern="yyyy-MM-dd" type="date"/></td>
                            </c:otherwise>
                        </c:choose>
                        <td class="view_cnt">${boardDto.view_cnt}</td>
                    </tr>
                </c:forEach>
            </table>
            <br>

 <%--    페이징     --%>
            <div class="paging-container">
                    <div class="paging">
                        <c:if test="${totalCnt==null || totalCnt==0}">
                            <div> 게시물이 없습니다. </div>
                        </c:if>
                        <c:if test="${totalCnt!=null && totalCnt!=0}">
                            <c:if test="${ph.showPrev}">
                                <a class="page" href="<c:url value="/board${ph.sc.getQueryString(ph.beginPage-1)}"/>">&lt;</a>
                            </c:if>
                            <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
                                <a class="page ${i==ph.sc.page? "paging-active" : ""}" href="<c:url value="/board${ph.sc.getQueryString(i)}"/>">${i}</a>
                            </c:forEach>
                            <c:if test="${ph.showNext}">
                                <a class="page" href="<c:url value="/board${ph.sc.getQueryString(ph.endPage+1)}"/>">&gt;</a>
                        </c:if>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
<%@ include file="./footer.jsp"%>
</body>
</html>