package com.cafe.app.order.service;

import java.util.List;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.vo.*;

public interface OrderService {

	List<MenuVO> getMenuList(int category);

	MenuVO getMenuDetail(String menuId);

	PaymentResponse doActionOrderCart(PaymentResponse paymentResponse);

	PaymentResponse requestPayment(List<MenuVO> paymentList);

	RequestOrderVO saveOrder(RequestOrderVO requestOrderVO);

	List<ItemSummaryVO> readItemSummaryById(String orderId);

	List<SeatSummaryVO> readSeatSummaryById(String orderId);

	int readAmountById(String orderId);

	PaymentResponse validateAmount(PaymentValidVO paymentValidVO);
}
