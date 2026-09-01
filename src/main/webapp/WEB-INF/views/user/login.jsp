<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>로그인 화면</title>
    <link type="text/css" rel="stylesheet" href="/css/login.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/order/order.js"></script>
  </head>
  <body>
  <%@ include file="/WEB-INF/views/common/header.jsp"%>
  <c:if test="${not empty registerMsg}">
      <script>
        alert("${registerMsg}");
      </script>
    </c:if>
    <c:if test="${not empty loginMsg}">
      <script>
        alert("${loginMsg}");
      </script>
    </c:if>
    <div class="wrapper">
      <div class="login-container">
        <h2>로그인</h2>
        <form action="/login" method="post">
          <div class="form-group">
            <label for="userId">아이디:</label>
            <input type="text" id="userId" name="userId" required />
          </div>
          <div class="form-group">
            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required />
          </div>
          <button type="submit">로그인</button>
          <div class="sub-links">
            <a href="/order/guest">비회원 주문</a>
            <span class="divider">|</span>
            <a href="/register">회원가입</a>
            <span class="divider">|</span>
            <a href="#">ID 찾기</a>
            <span class="divider">|</span>
            <a href="#">비밀번호 찾기</a>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
