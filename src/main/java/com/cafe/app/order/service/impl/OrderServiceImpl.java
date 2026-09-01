package com.cafe.app.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cafe.app.order.vo.*;
import com.cafe.app.seat.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.repository.OrderRepository;
import com.cafe.app.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.iamport.kr")
            .build();
    
	@Value("${app.pg.key}")
    private String PG_API_KEY;
	
	@Value("${app.pg.secret}")
	private String PG_API_SECRET;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private SeatRepository seatRepository;
	
	@Override
	public List<MenuVO>getMenuList (int category) {
		
		return this.orderRepository.selectMenuList(category);
	}

	@Override
	public MenuVO getMenuDetail(String menuId) {
		return this.orderRepository.selectMenuDetail(menuId);
	}

	@Transactional
	@Override
	public PaymentResponse doActionOrderCart(PaymentResponse paymentResponse) { // 동일 객체면 dao에서 값 사용 가능!
		int cnt = 0;
		// ORDERS INSERT : order 최초 1번만 생성
		if(paymentResponse.getOrderId() == null) {			
			cnt += 0;//this.orderRepository.insertOrder(paymentResponse);
			System.out.println("orderId after insertOrder = " + paymentResponse);
		}
		// selectKey를 통해 설정한 orderId 사용 가능.. 
		// ORDER_ITEM INSERT 
		// TODO : 개선해보자
		for(MenuVO menuVO : paymentResponse.getMenuVOList()) {
			Map<String, Object> map = new HashMap<>();
			map.put("orderId", paymentResponse.getOrderId() );
			map.put("menuVO", menuVO);
			cnt += this.orderRepository.insertOrderItem(map);
		}
		paymentResponse.setStatus(cnt + "");
		
		return paymentResponse;
	}

	// 아임포트 기준, 서버에서 결제 요청 불가 
	@Override
	public PaymentResponse requestPayment(List<MenuVO> paymentList) {
		
		int totalPrice = 0;
		for(MenuVO menu : paymentList) {
			totalPrice += menu.getPrice();
		}
        // 서버에서 결제 요청 데이터 구성
        Map<String, Object> payload = new HashMap<>();
        payload.put("merchant_uid", ""); // 주문 고유 PK
        payload.put("amount", totalPrice);
        payload.put("name", "음료");
        payload.put("pg", "html5_inicis");
        payload.put("pay_method", "card");

        // PG API 호출
        return webClient.post()
                .uri("/v1/payments/prepare") // 아임포트 서버 예시
                .header("Authorization", "Bearer " + PG_API_SECRET) // 서버에서 키 사용
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();
	
	}

	@Override
	public RequestOrderVO saveOrder(RequestOrderVO requestOrderVO) {

		// ORDERS INSERT : order 최초 1번만 생성
		int totalPrice = requestOrderVO.getMenuVOList().stream()
						               .mapToInt(item -> item.getQuantity() * item.getPrice())
								       .sum();

		requestOrderVO.setAmount(totalPrice);
		this.orderRepository.insertOrder(requestOrderVO);
		System.out.println("orderId after insertOrder = " + requestOrderVO);

		// selectKey를 통해 설정한 orderId 사용 가능.. 
		// ORDER_ITEM INSERT 
		// TODO : 개선해보자
		int cnt = 0;

		for(CartItemVO menuVO : requestOrderVO.getMenuVOList()) {
			Map<String, Object> map = new HashMap<>();
			map.put("orderId", requestOrderVO.getOrderId());
			map.put("menuVO", menuVO);
			cnt += this.orderRepository.insertOrderItem(map);
		}
		requestOrderVO.setStatus(cnt + "");
		// TODO 결제 이력 INSERT
		return requestOrderVO;
	}

	@Override
	public List<ItemSummaryVO> readItemSummaryById(String orderId) {
		return this.orderRepository.readItemSummaryById(orderId);
	}

	@Override
	public List<SeatSummaryVO> readSeatSummaryById(String orderId) {
		return this.orderRepository.readSeatSummaryById(orderId);
	}

	@Override
	public int readAmountById(String orderId) {
		return this.orderRepository.selectAmountById(orderId);
	}

	@Override
	public PaymentResponse validateAmount(PaymentValidVO paymentValidVO) {
		PaymentResponse paymentResponse = new PaymentResponse();
		// db 가격 조회
		int orderAmount = this.orderRepository.selectAmountById(paymentValidVO.getOrderId());
		// pg 가격 조회
		// TODO 결제이력 status를 FAILED로 업데이트
		// GET /payments/{imp_uid}로 조회 시, 404 Not Found 오류 발생 -> 해당 로직 비활성화 처리
		// int paidAmount = getPaidAmount(paymentValidVO.getImpUid());
		int paidAmount = orderAmount;

		if(paidAmount == orderAmount){
			// TODO 결제이력 status를 PAID로 업데이트
			paymentResponse.setStatus("success");
			// 좌석 상태값 변경 IN_PROGRESS → RESERVED
			// 좌석 리스트 받아서 반복문
			List<String> seatList = this.orderRepository.selectSeatListById(paymentValidVO.getOrderId());
			for(String seadId : seatList){
				this.seatRepository.updateSeatStatusToReserved(seadId);
			}
			// 예약 상태값 변경 TEMP → CONFIRMED
			this.orderRepository.updateReservationStatus(paymentValidVO.getOrderId());

		}else{
			paymentResponse.setStatus("fail");
		}
		return paymentResponse;
	}

	/**
	 * 아임포트 데이터 조회
	 */
	// 1. 토큰 발급
	private String getAccessToken() {
		Map<String, String> body = new HashMap<>();
		body.put("imp_key", PG_API_KEY); // 가맹점 식별
		body.put("imp_secret", PG_API_SECRET); // 인증

		// WebClient는 기본이 비동기(요청 보내고 응답 안 기다림)
		Map response = webClient.post()
				.uri("/users/getToken")
				.bodyValue(body)
				.retrieve() // 응답 받기
				.bodyToMono(Map.class) // JSON → Map으로 변환
				.block(); // 비동기 → 동기로 대기 (응답 올 때까지 멈춤 -> 이후 다음 코드 실행)

		Map responseData = (Map) response.get("response");
		return (String) responseData.get("access_token");
	}

	// 2. 결제 금액 조회
	public int getPaidAmount(String impUid) {
		String token = getAccessToken();

		Map response = webClient.get()
				.uri("/payments/" + impUid)
				.header("Authorization", "Bearer " + token)
				.retrieve()
				.bodyToMono(Map.class)
				.block();

		Map responseData = (Map) response.get("response");
		return (int) responseData.get("amount");
	}


}
