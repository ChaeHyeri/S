<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <title>footer</title>
  <link href="/css/style.css" rel="stylesheet">
</head>
<body>
<footer>
  <div class="wrapper">
    <p><small>&copy; 2022 Nyangnyang.com</small></p>
  </div>
</footer>

<script>
    let socket = null; // 어느 페이지에서든 알림을 보낼 수 있도록 전역변수로 선언
    $(document).ready(function () {
        connectWS();

    });

    function connectWS() {
        console.log("mm")
        // servlet-context.xml
        var ws = new WebSocket("ws://localhost:8080" + "/replyEcho?bno=1234");
        socket = ws;

        // 소켓 연결 시
        ws.onopen = function () {
            console.log('Info: connection opened.');
        };

        ws.onmessage = function (event) {
            console.log("Handler로부터 받은 메세지 : ", event.data + '\n');

            // 실시간 알림창(6초동안 팝업)
            let $alarm = $('div#alarm');
            $alarm.html(event.data);
            $alarm.css('display','block');
            setTimeout(function () {
               $alarm.css('display','none');
            },6000);
        };

        ws.onclose = function (event) {
            console.log('Info: connection closed.');
            // setTimeout(function () { connect(); }, 1000); // retry connection!!
        };
        ws.onerror = function (err) {
            console.log('Info: connection error.');
        };
    }

</script>
</body>
</html>
