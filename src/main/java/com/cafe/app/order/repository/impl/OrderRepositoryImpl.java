package com.cafe.app.order.repository.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe.app.menu.vo.MenuVO;
import com.cafe.app.order.repository.OrderRepository;
import com.cafe.app.order.vo.ItemSummaryVO;
import com.cafe.app.order.vo.OrderItemVO;
import com.cafe.app.order.vo.PaymentResponse;
import com.cafe.app.order.vo.RequestOrderVO;
import com.cafe.app.seat.vo.RequestTempVO;

@Repository
public class OrderRepositoryImpl extends SqlSessionDaoSupport implements OrderRepository{

   private final String NAME_SPACE = "com.cafe.app.order.repository.impl.OrderRepositoryImpl.";
   @Autowired // 안 붙이면 에러 발생
   @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
	
	@Override
	public List<MenuVO> selectMenuList(String category) {
		return super.getSqlSession().selectList(this.NAME_SPACE + "selectMenuList", category);
	}

	@Override
	public MenuVO selectMenuDetail(String menuId) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "selectMenuDetail", menuId);
	}

	@Override
	public int insertOrder(RequestOrderVO requestOrderVO) {
		return super.getSqlSession().insert(this.NAME_SPACE + "insertOrder", requestOrderVO);
	}


	@Override
	public int insertOrderItem(Map<String, Object> map) {
		return super.getSqlSession().insert(this.NAME_SPACE + "insertOrderItem", map);
	}

	@Override
	public int readItemCountById(RequestTempVO requestTempVO) {
		return super.getSqlSession().selectOne(this.NAME_SPACE + "readItemCountById", requestTempVO);
	}

	@Override
	public List<ItemSummaryVO> readItemSummaryById(String orderId) {
		return super.getSqlSession().selectList(this.NAME_SPACE + "readItemSummaryById", orderId);
	}



}
