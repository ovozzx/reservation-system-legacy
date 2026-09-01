<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>결제 성공</title>
    <link type="text/css" rel="stylesheet" href="/css/result.css" />
  </head>
  <body>
  <%@ include file="/WEB-INF/views/common/header.jsp"%>
  <div class="wrapper">
      <div class="header success">
        <h1>주문 완료</h1>
      </div>
      <div class="content-box">
        <div class="icon-circle success">✓</div>
        <p class="message">주문번호 <strong>${orderId}</strong> 번</p>
        <a href="/" class="btn">처음으로 돌아가기</a>
      </div>
    </div>
  </body>
</html>
