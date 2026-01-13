package com.cafe.app.order.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.service.OrderService;
import com.cafe.app.order.vo.OrderItemVO;
import com.cafe.app.order.vo.PaymentResponse;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
//    public OrderController(OrderService orderService) {
//        this.orderService = orderService;
//    }

    // 시작 화면 : 먹고가기 or 포장하기
	@GetMapping("/")
	public String viewStartPage() {
		// return "index";
		return "order/start";
	}
	
	// 음료 주문 화면 
	@GetMapping("/order")
	public String viewOrderPage() {
 		// model.addAttribute("menuList", menuList);
		// System.out.println("출력 : " + menuList);
		return "order/list";
	}
	
	// 음료 주문 필터
	@ResponseBody
	@GetMapping("/order/filter")
	public List<MenuVO> viewOrderFilter(@RequestParam(required = false) String category, Model model) {

//		if(category == null) {			
//			category = "1";
//		} 
		List<MenuVO> menuList = this.orderService.getMenuList(category);
 		// model.addAttribute("menuList", menuList);
		// System.out.println("출력 : " + menuList);
		return menuList;
	}
	
	// 음료 상세보기 화면
	@GetMapping("/order/detail/{menuId}")
	public String viewOrderDetailPage(@PathVariable String menuId, Model model) {
		MenuVO menuVO = this.orderService.getMenuDetail(menuId);
		model.addAttribute("menu", menuVO);
		System.out.println("출력 : " + menuVO);
		return "order/detail";
	}	
	
	// 장바구니 담기
	@PostMapping("/order/cart")
	public String doActionOrderCart(MenuVO menuVO, HttpSession session) { // 결제 전까지 세션에서 관리
		//session.removeAttribute("cart");
		// session.invalidate();
		// 기존 값 READ
		List<MenuVO> cartList;
		if(session.getAttribute("cart") == null) { // 담은 메뉴 없을 때 
			cartList = new ArrayList<>();
		} else { // 담은 메뉴 있을 때
			cartList = (List<MenuVO>) session.getAttribute("cart");
		}
		
		// 담은 메뉴 추가 
		cartList.add(menuVO);	
		// 담은 메뉴 취소 
		// 세션에서 같은 key로 하면 덮어쓰기 됨
		session.setAttribute("cart", cartList);
		System.out.println("세션 정보 : " + session.getAttribute("cart"));
		// redirect : url 변경 (다른 엔드 포인트 다시 요청) 
		return "redirect:/order";
	}
	
	// 결제 
	@ResponseBody
	@GetMapping("/payment")
	public PaymentResponse doClickPayment(HttpSession session) {
		
		if(session.getAttribute("cart") == null) {
			throw new IllegalArgumentException("결제할 메뉴가 없습니다.");
		}
		
		List<MenuVO> paymentList = (List<MenuVO>) session.getAttribute("cart");
		PaymentResponse paymentResponse = new PaymentResponse();
		
		paymentResponse.setMenuVOList(paymentList);
		// 주문 + 주문 상세 테이블 INSERT => 읽은 값 넣기 
		paymentResponse = this.orderService.doActionOrderCart(paymentResponse);

		int totalPrice = 0;
		for(MenuVO menu : paymentList) {
			totalPrice += menu.getPrice();
		}
		
		paymentResponse.setAmount(totalPrice);
		
		return paymentResponse;
	}	
	
	// 결제 검증
	@ResponseBody
	@GetMapping("/payment/valid")
	public PaymentResponse confirmValidPayment(HttpSession session) {
	
		PaymentResponse paymentResponse = new PaymentResponse();
		// TODO
		// 프론트 요청 값 == 우리 요청 값, 미적재 건 복구 
		return paymentResponse;
	}	
	
	// 결제 성공
	@GetMapping("/payment/success")
	public String viewPaymentSuccess(HttpSession session) {
		
		return "payment/success";
	}

	// 결제 실패
	@GetMapping("/payment/fail")
	public String viewPaymentFail(HttpSession session) {
		return "payment/fail";
	}
	
	// 장바구니 삭제
	@ResponseBody
	@GetMapping("/payment/remove")
	public PaymentResponse doRemoveCart(HttpSession session) {
		session.removeAttribute("cart");
		PaymentResponse paymentResponse = new PaymentResponse();
		paymentResponse.setStatus("success");
		return paymentResponse;
	}

	

}
