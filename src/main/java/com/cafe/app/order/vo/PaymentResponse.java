package com.cafe.app.order.vo;

import java.util.List;

import com.cafe.app.menu.vo.MenuVO;
import lombok.Data;

@Data
public class PaymentResponse {

    private String status;
    private String orderId;
    private int amount;
    private List<MenuVO> menuVOList;
}
