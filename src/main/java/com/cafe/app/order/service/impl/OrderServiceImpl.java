package com.cafe.app.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.repository.OrderRepository;
import com.cafe.app.order.service.OrderService;
import com.cafe.app.order.vo.OrderItemVO;
import com.cafe.app.order.vo.PaymentResponse;

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
	
	@Override
	public List<MenuVO> getMenuList(String category) {
		
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
			cnt += this.orderRepository.insertOrder(paymentResponse);
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


}
