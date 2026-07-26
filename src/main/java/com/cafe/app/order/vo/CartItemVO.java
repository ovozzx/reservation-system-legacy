package com.cafe.app.order.vo;

import lombok.Data;

@Data
public class CartItemVO {
    private String menuId;
    private String name;
    private int quantity;
    private int price;
}
