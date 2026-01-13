<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>시작 화면</title>
    <link type="text/css" rel="stylesheet" href="/css/order.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="/js/order/order.js"></script>
  </head>
  <body>
    <div class="wrapper">
      <div class="content">
        <div class="order-container">
          <button class="order-btn here" onclick="location.href='/login'">
            먹고가기
          </button>
          <button class="order-btn take-out" onclick="location.href='/order'">
            포장하기
          </button>
        </div>
      </div>
    </div>
  </body>
</html>
