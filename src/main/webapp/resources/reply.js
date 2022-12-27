let toHtml = function(comments){
    let tmp = "<ul>";
    comments.forEach(function (comment) {
        tmp += '<li data-cno=' + comment.cno
        tmp += ' data-pcno=' + comment.pcno
        tmp += ' data-bno=' + comment.bno + '>'
        if(comment.cno!=comment.pcno)
            tmp += 'ㄴ'
        // 추후 해당 부분만 가져오기 쉽도록 span함.
        tmp += ' <span class="commenter">' + comment.commenter + '</span> '
        tmp += '<br>'
        tmp += ' <span class="comment">' + comment.comment + '</span> '
        tmp += ' 등록일 <span class="update">' + displayTime(comment.up_date) + '</span> '
        tmp += '<button class="delBtn">삭제</button>'
        tmp += '<button class="modBtn">수정</button>'
        tmp += '<button class="replyBtn">답글</button>'
        tmp += '</li>'
    })
    return tmp + "</ul>";
}

// 댓글 날짜 포맷
function displayTime(timeValue){
    var today = new Date(); // 현재 시간
    var gap = today.getTime() - timeValue;
    // 시간차이 연산.
    var dateObj = new Date(timeValue);
    // 덧글이 등록된 시간을 변수에 할당.
    var str = "";

    if(gap<(1000*60*60*24)){
        // 시간차이가 24시간 미만이라면,
        var hh = dateObj.getHours();
        var mi = dateObj.getMinutes();
        var ss = dateObj.getSeconds();

        return [ (hh>9?'':'0')+hh, ':'
            ,(mi>9?'':'0')+mi
            ,':',(ss>9?'':'0')+ss].join('');

        // 배열 요소를 문자열로 변환. join
        // 시간에 포맷을 맞추기 위해서
        // 0~9 까지는 앞에 0을 추가 표시.
    }else{
        var yy = dateObj.getFullYear();
        var mm = dateObj.getMonth()+1;
        var dd = dateObj.getDate();

        return [yy, '/', (mm>9?'':'0')+mm, '/',
            (dd>9?'':'0')+dd].join('');
    }
}

// ★ 댓글 작성
// 댓글 내용 작성 후 send 버튼을 눌렀을때
$("#sendBtn").click(function () {
    let comment = $("input[name=comment]").val();
    // 입력한 comment의 값을 json에 전송


    if(comment.trim()==''){  // trim:공백없애기
        alert("댓글을 입력해주세요.");
        $("input[name=comment]").focus()
        return;
    }

    $.ajax({
        type:'POST',       // 요청 메서드
        url: '/comments?bno='+bno,  // 요청 URI        /comments?bno=1 POST
        headers : { "content-type": "application/json"},
        data : JSON.stringify({bno:bno, comment:comment, writer}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success : function(result){
            alert(result);
            showList(bno);

            console.debug("reply.js::socket>>",socket)
            // 소켓이 연결된 상태에만 알림을 보냄
            if(socket) { // (reply,글번호)

                let socketMsg = "reply"+","+bno+","+writer;
                console.debug("sssssmsg>>",socketMsg);
                socket.send(socketMsg);


            }
        },
        error   : function(){ alert("error") } // 에러가 발생했을 때, 호출될 함수
    }); // $.ajax()
    showList(bno);
    $("input[name=comment]").val('');
});

// ★ 댓글 수정
// 댓글 수정한 내용 입력 후 '수정' 버튼 눌렀을 때
$("#modBtn").click(function () {
    let cno = $(this).attr("data-cno");
    let comment = $("input[name=comment]").val();

    if(comment.trim()==''){  // trim:공백없애기
        alert("수정할 내용을 입력해주세요.");
        $("input[name=comment]").focus()
        return;
    }

    $.ajax({
        type:'PATCH',
        url: '/comments/'+cno,  // 요청 URI
        headers : { "content-type": "application/json"},
        data : JSON.stringify({cno:cno, comment:comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success : function(result){
            alert(result);
            showList(bno);
        },
        error   : function(){ alert("error") }
    }); // $.ajax()
    showList(bno);
    $("input[name=comment]").val('');
    $("#sendBtn").css("display","block");
    $("#modBtn").css("display","none");
});


// ★ 댓글 '수정' 버튼 클릭 시
$("#commentList").on("click",".modBtn",function() {
    let cno = $(this).parent().attr("data-cno");

    // 전체가 아닌 클릭된 li의 span.comment만 선택
    let comment = $("span.comment", $(this).parent()).text();

    // 1. comment의 내용을 input에 그대로 뿌려주기
    $("input[name=comment]").val(comment);
    // 2. 수정할 cno 전달하기
    $("#modBtn").attr("data-cno", cno);
    // 3. send 버튼 숨기기
    $("#sendBtn").css("display","none");
    // 4. 숨겨진 '수정'버튼 노출
    $("#modBtn").css("display","block");
});

// ★ 댓글 삭제
// $(".delBtn").click(function(){
// 댓글기능은 비동기 방식으로, 요청이 오기 전에 바로 실행됨. 고정된 요소인 commentList에 이벤트를 걸어야 정상 작동함.
$("#commentList").on("click",".delBtn",function(){

    // li는 button의 부모. 부모의 "data-cno/bno" 속성을 가져와야 함.
    let cno = $(this).parent().attr("data-cno");
    let bno = $(this).parent().attr("data-bno");

    $.ajax({
        type:'DELETE',
        url: '/comments/'+cno+'?bno='+bno,  // /comments/1?bno=1
        success : function(result){
            alert(result)
            showList(bno); // 삭제 성공 후 목록 갱신.
        },
        error   : function(){ alert("error") }
    }); // $.ajax()
});



//        대댓글
// 답글 버튼 클릭했을 때
$("#commentList").on("click",".replyBtn",function(){

    // 1. 해당 댓글 바로 아래에 replyForm이 나오도록 위치를 옮겨줌. (부모태그인 li 아래에 위치)
    // A.appendTo B : A요소를 B요소 안으로 이동함.
    $("#replyForm").appendTo($(this).parent());

    // 2. 숨겨놨던 replyForm이 보일 수 있도록함.
    $("#replyForm").css("display","block");

});

// 답글-등록 버튼 눌렀을 때
$("#wrtRepBtn").click(function () {
    let comment = $("input[name=replyComment]").val();
    // 답글의 부모 li 찾기
    let pcno = $("#replyForm").parent().attr("data-pcno");

    if (comment.trim() == '') {
        alert("댓글을 입력해주세요.");
        $("input[name=replyComment]").focus()
        return;
    }

    $.ajax({
        type: 'POST',       // 요청 메서드
        url: '/comments?bno=' + bno,  // 요청 URI
        headers: {"content-type": "application/json"},
        data: JSON.stringify({pcno: pcno, bno:bno, comment: comment}),  // 서버로 전송할 데이터. stringify()로 직렬화 필요.
        success: function (result) {
            alert(result);
            showList(bno);

            if (socket) { // (reply,글번호)
                let socketMsg = "reply" + "," + bno + "," + writer;
                console.debug("sssssmsg>>", socketMsg);
                socket.send(socketMsg);
            }
        },
        error: function () {
            alert("error")
        }
    });
    // 답글 등록 후, replyForm을 숨김 처리 / input박스의 내용을 삭제 / 위치를 특정 댓글 아래가 아닌 원래의 위치로 되돌림.
    $("#replyForm").css("display","none");
    $("input[name=replyComment]").val('');
    $("#replyForm").appendTo("body");
});