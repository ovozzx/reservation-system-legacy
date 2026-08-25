 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>회원가입 화면</title>
    <link type="text/css" rel="stylesheet" href="/css/login.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/register/register.js"></script>
  </head>
  <body>
    <c:if test="${not empty msg}">
      <script>
        alert("${msg}");
      </script>
    </c:if>
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
          </div>
          <div class="form-group">
            <label for="confirmPassword">비밀번호 확인:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required />
          </div>
          <span id="passwordMsg" style="color: red; font-size: 12px;"></span>
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
