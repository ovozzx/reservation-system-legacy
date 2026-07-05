package com.cafe.app.menu.vo;

import lombok.Data;

@Data
public class MenuVO {
	
	private String menuId;
	private String name;
	private int price;
	private String isAvailable;
	private String createdAt;
	private String updatedAt;
	private String isUsed;
	private String categoryId;
	private String imagePath;
	private String description;

}
