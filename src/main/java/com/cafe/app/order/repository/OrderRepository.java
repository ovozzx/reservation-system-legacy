package com.cafe.app.order.repository;

import java.util.List;
import java.util.Map;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.vo.PaymentResponse;

public interface OrderRepository {

	List<MenuVO> selectMenuList(String category);

	MenuVO selectMenuDetail(String menuId);

	int insertOrder(PaymentResponse paymentResponse);

	int insertOrderItem(Map<String, Object> map);

}
