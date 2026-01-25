<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>좌석 예약</title>
    <link type="text/css" rel="stylesheet" href="/css/seat.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
	<script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script type='text/javascript' src='/js/seat/seat.js'></script>
  </head>
  <body>
	<c:if test="${not empty msg}">
		<script>
			alert("${msg}");
		</script>
	</c:if>
	<form id="seatForm" action="/seat" method="post">
	<div class="modal">
		<div class="modal-content">	
				<div>시간 설정</div>
				<input id="seatId" name="seatId" data-seat-id="" type="hidden">
				<input id="reserveTime" name="reserveTime" data-time="" type="hidden">
				<input value="${orderId}" name="orderId" type="hidden">
				<div class="time" id="one-hour" data-time="1">1 시간</div>
				<div class="time" id="two-hour" data-time="2">2 시간</div>
				<div class="time" id="three-hour" data-time="3">3 시간</div>
				<div class="modal-buttons">
					<button type="button" class="cancle">취소</button>
					<button type="button" class="complete" disabled>완료</button>
				</div>
		</div>
    </div> 
    <div class="wrapper">
	      <div class="header">
	      	<h1 id="main-title">좌석 예약</h1>
	      </div>
		  <div class="category">
			<ul> <!-- 카테고리도 DB로 관리 ? -->
				<li>1F</li>
				<li>2F</li>
				<li>3F</li>
			</ul>
		  </div>
	      <div class="content-box">
			<div class="left-area">
				<c:forEach var="seat" items="${leftSeatList}">
					<button class="seat" 
							type="button"
					        data-id="${seat.seatId}"
						    ${seat.isOccupied eq 'Y' ? 'disabled' : ''}> <!-- 사용 가능 좌석 N -->
						${seat.seatNumber}
					</button>
				</c:forEach>
			</div>
			<div class="aisle-area">
				
			</div>
			<div class="right-area">
				<c:forEach var="seat" items="${rightSeatList}">
					<button class="seat" 
					        data-id="${seat.seatId}"
						    ${seat.isOccupied eq 'Y' ? 'disabled' : ''}> <!-- 사용 가능 좌석 N -->
						${seat.seatNumber}
					</button>
				</c:forEach>
			</div>
			<div class="window-area">
				<c:forEach var="seat" items="${windowSeatList}">
					<button class="seat" 
							type="button"
					        data-id="${seat.seatId}"
						    ${seat.isOccupied eq 'Y' ? 'disabled' : ''}> <!-- 사용 가능 좌석 N -->
						${seat.seatNumber}
					</button>
				</c:forEach>
			</div>
		
	      </div>
          <div class="bottom">
	          <div class="info">
				<div>남은 좌석</div>
				<div>남은 시간</div>
			  </div>
	          <div class="button-container">	
					<button id="payment" type="submit">결제하기</button>				
			  </div>
        </div>
	
    </div>
	</form>
  </body>
</html>
