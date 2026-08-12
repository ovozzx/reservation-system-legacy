<%@ page contentType="text/html;charset=UTF-8" language="java" %> <%@ taglib
prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>음료 주문</title>
    <link type="text/css" rel="stylesheet" href="/css/order.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script type="text/javascript" src="/js/order/order.js"></script>
  </head>
  <body>
    <div class="wrapper">
      <div class="header">
        <h1 id="main-title">음료 주문</h1>
        <p id="login-id">${sessionScope.userId}</p>
        <p id="back-start">처음으로 돌아가기</p>
      </div>
      <div class="category-box">
        <button class="category selected" data-category="1">커피 (Hot)</button>
        <button class="category" data-category="2">커피 (Ice)</button>
        <button class="category" data-category="3">논커피 (Hot)</button>
        <button class="category" data-category="4">논커피 (Ice)</button>
      </div>
      <div class="content-box">
        <ul class="menus">
          <c:forEach var="menu" items="${menuList}">
					<li class="menu" data-id="${menu.menuId}">
						<div><img src="${menu.imagePath}"></div>
						<div>${menu.name}</div>
						<div>${menu.price} 원</div>
					</li>
          </c:forEach>
        </ul>
      </div>
      <div class="bottom">
        <div class="cart">
          <div class="cart-header">
            <span>담은 내용</span>
          </div>
          <ul class="cart-list">
            <c:forEach var="item" items="${sessionScope.cart}">
              <li class="cart-item">
                <span class="cart-name">${item.name}</span>
                <div class="cart-quantity">
                  <button class="minus-btn" data-menu-id="${item.menuId}">-</button>
                  <span class="cart-qty">${item.quantity}</span>
                  <button class="plus-btn" data-menu-id="${item.menuId}">+</button>
                </div>
                <span class="cart-price">${item.price * item.quantity}원</span>
                <button class="remove-btn" data-menu-id="${item.menuId}">✕</button>
              </li>
            </c:forEach>
          </ul>
        </div>
        <div class="button-container">
          <div>남은 시간</div>
          <div id="remainingSeconds" data-seconds="${remainingSeconds}">${remainingSeconds}</div>
          <button id="remove">삭제</button>
          <c:if test="${not empty sessionScope.cart}">
            <c:choose>
              <c:when test="${sessionScope.orderType == 'IN'}">
                <form action="/order" method="post">
                  <!-- 먹고가기 -->
                  <button id="seat">좌석예약</button>
                </form>
              </c:when>
              <c:otherwise>
                <!-- 포장하기 -->
                <form action="/order/takeout" method="post">
                  <button id="pay">결제하기</button>
                </form>
              </c:otherwise>
            </c:choose>
          </c:if>
        </div>
      </div>
    </div>
  </body>
  <script>
    var seconds = $("#remainingSeconds").data("seconds");
    var timer = setInterval(function(){
      seconds--;
      $("#remainingSeconds").text(seconds);
      if(seconds <= 0){
        clearInterval(timer); // setInterval 반복 실행 멈춤 
        alert("주문 시간 초과");
        window.location.href = "/";
      }
    }, 1000);
  </script>
</html>
