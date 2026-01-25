package com.cafe.app.order.repository;

import java.util.List;
import java.util.Map;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.vo.ItemSummaryVO;
import com.cafe.app.order.vo.PaymentResponse;
import com.cafe.app.order.vo.RequestOrderVO;
import com.cafe.app.seat.vo.RequestTempVO;

public interface OrderRepository {

	List<MenuVO> selectMenuList(String category);

	MenuVO selectMenuDetail(String menuId);

	int insertOrder(RequestOrderVO requestOrderVO);

	int insertOrderItem(Map<String, Object> map);

	int readItemCountById(RequestTempVO requestTempVO);

	List<ItemSummaryVO> readItemSummaryById(String orderId);

}
