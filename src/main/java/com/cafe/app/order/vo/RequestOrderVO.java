package com.cafe.app.order.vo;

import java.util.List;

import com.cafe.app.menu.vo.MenuVO;

public class RequestOrderVO {
 
    private String status;
    private String orderId;
    private int amount;
    private List<MenuVO> menuVOList; 

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    
	public List<MenuVO> getMenuVOList() {
		return menuVOList;
	}
	public void setMenuVOList(List<MenuVO> menuVOList) {
		this.menuVOList = menuVOList;
	}

    

}
