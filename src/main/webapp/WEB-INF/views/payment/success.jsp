<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>결제 성공</title>
    <link type="text/css" rel="stylesheet" href="/css/success.css" />
  </head>
  <body>
    <div class="wrapper">
      <div class="header">
      	<h1>주문 완료</h1>
      </div>
	  <div class="content-box">
        <div id="order-number">${orderId} 번</div>
        <a href="/" class="back">처음으로 돌아가기</a>
      </div>
    </div>
  </body>
</html>
