package com.cafe.app.order.service;

import java.util.List;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.vo.OrderItemVO;
import com.cafe.app.order.vo.PaymentResponse;

public interface OrderService {

	List<MenuVO> getMenuList(String category);

	MenuVO getMenuDetail(String menuId);

	PaymentResponse doActionOrderCart(PaymentResponse paymentResponse);

	PaymentResponse requestPayment(List<MenuVO> paymentList);

}
