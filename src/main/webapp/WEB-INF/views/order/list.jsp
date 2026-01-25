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
      </div>
      <div class="category-box">
        <button class="category selected" data-category="1">커피 (Hot)</button>
        <button class="category" data-category="2">커피 (Ice)</button>
        <button class="category" data-category="3">논커피 (Hot)</button>
        <button class="category" data-category="4">논커피 (Ice)</button>
      </div>
      <div class="content-box">
        <ul class="menus">
          <!--<c:forEach var="menu" items="${menuList}">
					<li class="menu" data-id="${menu.menuId}"
						<div>사진</div>
						<div>${menu.name}</div>
						<div>${menu.price} 원</div>
					</li>
				</c:forEach>-->
        </ul>
      </div>
      <div class="bottom">
        <div class="cart">
          <div>담은 내용</div>
          <ul>
            이름 / 수량 / 가격
            <c:forEach var="item" items="${sessionScope.cart}">
              <li>${item.name} 1 ${item.price}</li>
            </c:forEach>
          </ul>
        </div>
        <div class="button-container">
          <div>남은 시간</div>
          <button id="remove">삭제</button>
          <c:if test="${not empty sessionScope.cart}">
            <form action="/order" method="post">
              <button id="seat">좌석예약</button>
            </form>
          </c:if>
        </div>
      </div>
    </div>
  </body>
</html>
