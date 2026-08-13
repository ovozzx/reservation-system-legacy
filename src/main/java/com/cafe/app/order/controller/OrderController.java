package com.cafe.app.order.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cafe.app.order.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.service.OrderService;

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
	public String viewOrderPage(Model model, HttpSession session) {
		List<MenuVO> menuList = orderService.getMenuList(1);
 		model.addAttribute("menuList", menuList);
		System.out.println("출력 : " + menuList);
		// 남은 시간 확인
		Long currentTime =  System.currentTimeMillis();
		Long orderStartTime = (Long) session.getAttribute("orderStartTime");
		if (orderStartTime == null || currentTime - orderStartTime > 300000){ // ms 단위, 5분 초과
			// 팝업 출력
			return "redirect:/";
		}
		long remainingSeconds = 300 - (currentTime - orderStartTime) / 1000;
		model.addAttribute("remainingSeconds", remainingSeconds);
		return "order/list";
	}
	
	// 음료 주문 필터
	@ResponseBody
	@GetMapping("/order/filter")
	public List<MenuVO> viewOrderFilter(@RequestParam int categoryId) {
		return this.orderService.getMenuList(categoryId);
	}

	// 먹고가기 / 포장하기 구분 저장
	@GetMapping("/order/start")
	public String checkOrderType(@RequestParam String type, HttpSession session) {
		session.setAttribute("orderType", type);
		// 시간 설정
		session.setAttribute("orderStartTime", System.currentTimeMillis());
		return "IN".equals(type) ? "redirect:/login" : "redirect:/order";
	}
	
	// 음료 상세보기 화면
	@GetMapping("/order/detail/{menuId}")
	public String viewOrderDetailPage(@PathVariable String menuId, Model model) {
		MenuVO menuVO = this.orderService.getMenuDetail(menuId);
		model.addAttribute("menu", menuVO);
		System.out.println("출력 : " + menuVO);
		return "order/detail";
	}

	// 장바구니 담기 (세션)
	@PostMapping("/order/cart")
	public String doOrderCart(MenuVO menuVO, HttpSession session) {
		CartItemVO cartItemVO = new CartItemVO();
		cartItemVO.setMenuId(menuVO.getMenuId());
		cartItemVO.setName(menuVO.getName());
		cartItemVO.setQuantity(1);
		cartItemVO.setPrice(menuVO.getPrice());

		List<CartItemVO> cartList;
		if(session.getAttribute("cart") == null) { // 담은 메뉴 없을 때 
			cartList = new ArrayList<>();
		} else { // 담은 메뉴 있을 때
			cartList = (List<CartItemVO>) session.getAttribute("cart");
		}
		// TODO 동일 메뉴 수량 추가
		// 동일 메뉴 확인
		CartItemVO existingItem = cartList.stream()
				.filter(item -> item.getMenuId().equals(cartItemVO.getMenuId()))
				.findFirst()
				.orElse(null);

		if(existingItem != null){
			// 수량 증가
			existingItem.setQuantity(existingItem.getQuantity() + 1);
		}else{
			// 담은 메뉴 추가
			cartList.add(cartItemVO);
		}
		// 담은 메뉴 취소 
		// 세션에서 같은 key로 하면 덮어쓰기 됨
		session.setAttribute("cart", cartList);
		System.out.println("세션 정보 : " + session.getAttribute("cart"));
	
		return "redirect:/order";
	}

	// '좌석 예약' 클릭 (장바구니 담기 완료)
	@PostMapping("/order")
	public String doActionSaveOrder(HttpSession session, RedirectAttributes redirectAttributes) {
		// 세션에서 가지고 있다가 ORDER 테이블에 한번에 넣기
		List<CartItemVO> orderList = (List<CartItemVO>) session.getAttribute("cart");
		String userId = (String) session.getAttribute("userId");
		RequestOrderVO requestOrderVO = new RequestOrderVO();
		requestOrderVO.setUserId(userId);

		requestOrderVO.setMenuVOList(orderList);
		this.orderService.saveOrder(requestOrderVO);
		// redirect 후, 페이지 이동 시 아래 값은 사라진다
		redirectAttributes.addAttribute("orderId", requestOrderVO.getOrderId()); // URL로 붙어서 감
		return "redirect:/seat"; // redirect => 다시 get 요청
	}

	// 포장하기 > 결제하기
	@PostMapping("/order/takeout")
	public String saveOrderForTakeOut(HttpSession session, RedirectAttributes redirectAttributes){
		List<CartItemVO> orderList = (List<CartItemVO>) session.getAttribute("cart");
		RequestOrderVO requestOrderVO = new RequestOrderVO();
		requestOrderVO.setUserId("GUEST"); // 포장하기 고정 USER_ID

		requestOrderVO.setMenuVOList(orderList);
		this.orderService.saveOrder(requestOrderVO);

		return "redirect:/order/summary/" +  requestOrderVO.getOrderId();
	}

	// 결제 
	@ResponseBody
	@GetMapping("/payment/{orderId}")
	public PaymentResponse doClickPayment(@PathVariable String orderId,HttpSession session) {

		PaymentResponse paymentResponse = new PaymentResponse();
		int totalPrice = this.orderService.readAmountById(orderId);
		paymentResponse.setOrderId(orderId);
		paymentResponse.setAmount(totalPrice);
		return paymentResponse;
	}	
	
	// 결제 검증
	@ResponseBody
	@PostMapping("/payment/valid")
	public PaymentResponse confirmValidPayment(@RequestBody PaymentValidVO paymentValidVO, HttpSession session) {
		return this.orderService.validateAmount(paymentValidVO);
	}	
	
	// 결제 성공
	@GetMapping("/payment/success/{orderId}")
	public String viewPaymentSuccess(@PathVariable String orderId, HttpSession session, Model model) {
		model.addAttribute("orderId", orderId);
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

	@GetMapping("/order/summary/{orderId}")
	public String readOrderSummary(@PathVariable String orderId, Model model, HttpSession session) {
		// 음료 조회
		List<ItemSummaryVO> itemList = this.orderService.readItemSummaryById(orderId);
		List<SeatSummaryVO> seatList = this.orderService.readSeatSummaryById(orderId);
		int totalPrice = this.orderService.readAmountById(orderId);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("itemList", itemList);
		model.addAttribute("seatList", seatList);
		model.addAttribute("orderId", orderId); // js에서 사용
		// 좌석 조회
		// this.orderService.readSeatSummaryById(orderId);
		return "order/summary";
	}

	// 수량 감소
	@ResponseBody
	@PostMapping("/order/cart/minus/{menuId}")
	public String cartItemMinus(@PathVariable String menuId, HttpSession session) {
		List<CartItemVO> orderList = (List<CartItemVO>) session.getAttribute("cart");
		CartItemVO cartItem = orderList.stream()
				.filter(item -> item.getMenuId().equals(menuId))
				.findFirst()
				.orElse(null);
		System.out.println("수량 증가 대상 : " + cartItem);
		if(cartItem != null){
			if(cartItem.getQuantity() > 1){
				cartItem.setQuantity(cartItem.getQuantity() - 1);
				System.out.println("수량 변경 후 : " + cartItem.getQuantity());
			}
		}
		// AJAX 호출하면 redirect 응답을 브라우저가 자동으로 따라가지 않음
		return "success";
	}

	// 수량 증가
	@ResponseBody
	@PostMapping("/order/cart/plus/{menuId}")
	public String cartItemPlus(@PathVariable String menuId, HttpSession session) {
		List<CartItemVO> orderList = (List<CartItemVO>) session.getAttribute("cart");
		CartItemVO cartItem = orderList.stream()
				.filter(item -> item.getMenuId().equals(menuId))
				.findFirst()
				.orElse(null);
		if(cartItem != null){
			cartItem.setQuantity(cartItem.getQuantity() + 1);
		}
		return "success";
	}

	// 삭제
	@ResponseBody
	@PostMapping("/order/cart/remove/{menuId}")
	public String cartItemRemove(@PathVariable String menuId, HttpSession session) {
		List<CartItemVO> orderList = (List<CartItemVO>) session.getAttribute("cart");
		List<CartItemVO> filteredList = orderList.stream()
				.filter(item -> !item.getMenuId().equals(menuId))
				.collect(Collectors.toList());
		session.setAttribute("cart", filteredList); // 원본 수정

		return "success";
	}

}
