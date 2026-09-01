<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>결제 실패</title>
    <link type="text/css" rel="stylesheet" href="/css/result.css" />
  </head>
  <body>
  <%@ include file="/WEB-INF/views/common/header.jsp"%>
  <div class="wrapper">
      <div class="header fail">
        <h1>결제 실패</h1>
      </div>
      <div class="content-box">
        <div class="icon-circle fail">✕</div>
        <p class="message">결제 처리 중 문제가 발생했습니다.</p>
        <a href="/" class="btn">처음으로 돌아가기</a>
      </div>
    </div>
  </body>
</html>
