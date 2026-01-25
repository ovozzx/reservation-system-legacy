 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>회원가입 화면</title>
    <link type="text/css" rel="stylesheet" href="/css/login.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/order/order.js"></script>
  </head>
  <body>
    <div class="wrapper">
      <div class="login-container">
        <h2>회원가입</h2>
        <form action="/register" method="post">
          <div class="form-group">
            <label for="userId">아이디:</label>
            <input type="text" id="userId" name="userId" required />
          </div>
          <div class="form-group">
            <label for="password">비밀번호:</label>
            <input type="password" id="password" name="password" required />
            <label for="confirmPassword">비밀번호 확인:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required />
          </div>
          <div class="form-group">
            <label for="email">이메일:</label>
            <input type="email" id="email" name="email" required />
          </div>
          <button type="submit">회원가입</button>
        </form>
      </div>
    </div>
  </body>
</html>
