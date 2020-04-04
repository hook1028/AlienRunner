package com.lastviolet.alienrunner;

public class ProductDTO 
{
	private String name;
	private int img;
	private int price;
	
	public ProductDTO(String name, int img, int price)
	{
		this.name = name;
		this.img = img;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}	
}
