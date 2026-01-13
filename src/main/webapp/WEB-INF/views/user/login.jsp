<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
          <button type="button">비회원 주문</button>
          <button type="button" onclick="location.href='/register'">
            회원가입
          </button>
          <button type="button">ID 찾기</button>
          <button type="button">비밀번호 찾기</button>
          <button type="submit">로그인</button>
        </form>
      </div>
    </div>
  </body>
</html>
