package com.cafe.app.order.vo;

import java.util.List;

import com.cafe.app.menu.vo.MenuVO;
import lombok.Data;

@Data
public class RequestOrderVO {

	private Long orderId;
    private String status;
    private int amount;
    private List<MenuVO> menuVOList; 


}
