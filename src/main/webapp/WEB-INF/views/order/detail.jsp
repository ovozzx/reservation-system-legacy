<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>음료 주문</title>
	<link type="text/css" rel="stylesheet" href="/css/order.css" />
	<script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
	<script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script type='text/javascript' src='/js/order/order.js'></script>
  </head>
  <body>
    <div class="wrapper">
	      <div class="header">
	      	<h1>음료 상세보기</h1>
	      </div>
		  <form action="/order/cart" method="post"><!--div는 name 달아도 전송안-->
		      <div class="menu-content">
				<ul>
					<li>
						<input type="hidden" name="menuId" value="${menu.menuId}" />
						<input type="hidden" name="name" value="${menu.name}" />
						<input type="hidden" name="price" value="${menu.price}" />
						<div class="img">${menu.imagePath}</div>
						<div class="name">${menu.name}</div>
						<div class="category">${menu.categoryId}</div>
						<div class="price">${menu.price}</div>
						<div class="desc">${menu.description}</div>
					</li>
			    </ul>
		      </div>
	          <div class="bottom">     
					<button type="submit">장바구니 담기</button>
					<button type="button" id="cancle">취소</button>
				  </div>
	        </div>
		</form>
	
    </div>
  </body>
</html>
