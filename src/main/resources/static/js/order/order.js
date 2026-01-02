$().ready(function(){
	
	loadMenus("1");
	
	// 메인 이동
	$("#main-title").on("click", function () { 
		window.location.href = "/order"
	});
	
	// 카테고리 필터링
	$("button.category").on("click", function() {
	   $("button.category").removeClass("selected");
	   $(this).addClass("selected");
	   loadMenus($(this).data("category"));
	});
	
	// 상세 이동 
	// 동적 요소 이벤트 할당 => 정적으로 존재하는 부모 요소 이용하여 동적 자식에게 이벤트 위임
	// $(부모).on("이벤트", "자식 선택자", 콜백)
	$(".menus").on("click", "li.menu", function(){
		window.location.href = "/order/detail/" + $(this).data("id");	
	});
	
	// 삭제 클릭 
	$("#remove").on("click", function () { 
		$.get("/payment/remove", function(response) {
			if(response.status === "success") {
				//alert("삭제 완료!");
				window.location.href = "/order"
			}
		});
	});
	
	// 좌석예약 이동 
	$("#seat").on("click", function(){
		window.location.href = "/seat";
	});
	
	// 결제하기 클릭
	$("#payment").on("click", function () { // 결제하기 버튼 클릭
	    //alert("!");
		IMP.init('imp26301316'); // 발급받은 가맹점 식별코드
		
		$.get("/payment", function(response){
										
			console.log(response);
			console.log(response.orderId);
			console.log(response.amount);
			
			// TODO : 주문 id 

			IMP.request_pay(
			              {
			            	pg: "html5_inicis",
			                pay_method: "card",
							//merchant_uid: "order_" + new Date().getTime(),
			                merchant_uid: response.orderId, //"order_" + new Date().getTime(),//merchantUid, // “서비스 예약 슬롯” PK 
							// 주문 고유 번호 => 요청시마다 바뀌어야 함
			                name: "음료",
							amount: response.amount,
			              },
			              function (response) {
			                // 결제 종료 시 호출되는 콜백 함수
			                // response.imp_uid 값으로 결제 단건조회 API를 호출하여 결제 결과를 확인하고,
			                // 결제 결과를 처리하는 로직을 작성		        
							var impUid = response.imp_uid;
							if(response.success == true){
								alert("결제 완료");
							}else{
								alert("결제 실패");
							}
							// 서버에 결제 완료 저장
							
							$.ajax({
								url: "/payment/valid",
								type: "POST",
								contentType : "application/json", 
								data: JSON.stringify({
									orderId : merchant_uid,
									ordrDt : resOrdrDt,
									ordrStts : resStatus,
									resSuccess : resSuccess,
									pymnt : "P001", // 카드
									ttltyAmnt : resAmount,
									resErrorCd : resErrorCd,
									impUid : impUid
								}),
								//dataType: "json",
								success: function(response){
									// alert("결제가 완료되었습니다. 예약 확정!");
									// 결제 취소해도 여기로 온다!
									console.log("서버 응답 완료")
									console.log("~~ : ", $(".title").data("success"));
									if(resSuccess == true
										&& resStatus === "paid"){
										window.location.href = "/payment/success";	
										// 서버에서 redirect 안 먹음 
									}
									else{
										window.location.href = "/payment/fail";	
									}
								},
								error: function(xhr, status, err){
									console.error("서버 에러:", xhr.status, xhr.responseText);
									alert("서버 요청 실패");
									window.location.href = "/payment/fail";	
								}
							});
							
							
			              },
			            );
				
		});
		
		
	 });
	 
	 /**
	  * detail 화면 
	  */
	 $("#cancle").on("click", function(){
		window.location.href = "/order"
	 });
	 
	 function loadMenus(category){
		$.get("/order/filter?category=" + category, function(response){
				// 읽은 값 렌더링
				var html = "";
				response.forEach(menu => {
					html += `<li class="menu" data-id="${menu.menuId}">
							 <div>${menu.imagePath}</div>
							 <div>${menu.name}</div>
							 <div>${menu.price} 원</div>
						     </li>`;
				})
				$(".menus").html(html); // 자식 전체를 새로 바꿈
		   });
	 }

})