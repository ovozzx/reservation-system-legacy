package com.cafe.app.menu.vo;

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
	
	public String getMenuId() {
		return this.menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return this.price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getIsAvailable() {
		return this.isAvailable;
	}
	public void setIsAvailable(String isAvailable) {
		this.isAvailable = isAvailable;
	}
	public String getCreatedAt() {
		return this.createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getIsUsed() {
		return this.isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public String getCategoryId() {
		return this.categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getImagePath() {
		return this.imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "MenuVO [menuId=" + this.menuId + ", name=" + this.name + ", price=" + this.price + ", isAvailable=" + this.isAvailable
				+ ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ", isUsed=" + this.isUsed + "]";
	}
	
	

}
