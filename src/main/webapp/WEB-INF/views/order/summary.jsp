<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>주문 내용 (음료/좌석)</title>
    <link type="text/css" rel="stylesheet" href="/css/summary.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script>
      var orderId = ${orderId};
    </script>
    <script type="text/javascript" src="/js/order/order.js"></script>
  </head>
  <body>
  <%@ include file="/WEB-INF/views/common/header.jsp"%>
  <div class="wrapper">
      <div class="header">
        <h1>주문 내용</h1>
        <p class="user-id">${sessionScope.userId}</p>
      </div>
      <div class="content-box">
        <div class="section">
          <h2>음료</h2>
          <ul class="item-list">
            <c:forEach var="item" items="${itemList}">
              <li>
                <span class="item-name">${item.name}</span>
                <span class="item-qty">${item.quantity}개</span>
                <span class="item-price">${item.price}원</span>
              </li>
            </c:forEach>
          </ul>
        </div>
        <c:if test="${sessionScope.orderType == 'IN'}">
          <div class="section">
            <h2>좌석</h2>
            <ul class="item-list">
              <c:forEach var="seat" items="${seatList}">
                <li>
                  <span class="item-name">${seat.seatNumber}번</span>
                  <span class="item-price">${seat.startTime} ~ ${seat.endTime}</span>
                </li>
              </c:forEach>
            </ul>
          </div>
        </c:if>
      </div>
      <div class="bottom">
        <div class="total-row">
          <span class="total-label">합계</span>
          <span class="total-amount">
            <fmt:formatNumber value="${totalPrice}" pattern="#,###" />원
          </span>
        </div>
        <button id="payment">결제하기</button>
      </div>
    </div>
  </body>
</html>
