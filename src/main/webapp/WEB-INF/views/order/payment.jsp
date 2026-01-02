<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>결제하기</title>
    <link type="text/css" rel="stylesheet" href="/css/hello-spring.css" />
    <link type="text/css" rel="stylesheet" href="/css/payment.css" />
    <script type="text/javascript" src="/js/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <script src="https://cdn.iamport.kr/v1/iamport.js"></script>
    <script type='text/javascript' src='/js/payment/payment.js'></script>
  </head>
  <body>
    <div class="wrapper">
      <div class="grid-container">
	      <div class="left-blank-div"></div>
	      <div>
		      <div class="header">
		      	<img src="/img/header/back.png" class="back icon">결제하기
		      </div>
		      <div class="content-box">
		        <input class="id" type="hidden" data-id="${id}" />
		        <div class="provider-info margin">
		          <div class="name">${serviceViewVO.mmbrNm} (${serviceViewVO.cdNm})</div>
		          <div class="content">
		          	<div class="gray">예약 일정</div>
		          	<div class="date">${serviceViewVO.strtDt} ~ ${serviceViewVO.endDt}</div>
		          	<div class="time"></div>
		          </div>
		        </div>
		        <div class="payment-box margin">
		          <div>총 금액</div>
		          <div class="grow"></div>
		          <div class="amount content">
		          <fmt:formatNumber value="${serviceViewVO.ttlPrc}" type="number" /> 원
		          </div>
		        </div>
		        <button class="payment money">계좌 결제</button>
		        <button class="payment card">카드 결제</button>
		        <!--
		        <div class="check-list">
		          <div class="title">약관 동의</div>
		          <div class="content">
		            약관내용1 어쩌구-------------------------
		            -----------------------------------------
		          </div>
		        </div>
		        <div class="payment">
		          <button class="complete">결제완료</button>
		        </div>-->
		      </div>
		    </div>
		    <div class="right-blank-div"></div>
		</div>
    </div>
  </body>
</html>
