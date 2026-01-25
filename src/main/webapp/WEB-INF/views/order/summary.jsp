<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>주문 내용 (음료/좌석)</title>
    <link type="text/css" rel="stylesheet" href="/css/order.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script type="text/javascript" src="/js/order/order.js"></script>
  </head>
  <body>
    <div class="wrapper">
      <div class="header">
        <h1 id="main-title">주문 내용</h1>
        <p id="login-id">${sessionScope.userId}</p>
      </div>
      <div class="drink">
        <h2 id="main-title">음료</h2>
        <c:forEach var="item" items="${itemList}">
          <li>${item.name} ${item.quantity} ${item.price}</li>
        </c:forEach>
      </div>
      <div class="seat">
        <h2 id="main-title">좌석</h2>
      </div>
      <button>결제하기</button>
    </div>
  </body>
</html>
