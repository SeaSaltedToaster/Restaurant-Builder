package com.seaSaltedToaster.restaurantGame.objects.food;

import com.seaSaltedToaster.simpleEngine.models.Vao;

public class Food {
	
	//Food model displayed on table
	public Vao vao;
	public int id;
	
	//Times and prices of the food
	private int cookTime = 10;
	private int eatingTime = 15;
	private int price = 25;

	public Food(Vao vao, int id) {
		this.vao = vao;
		this.id = id;
	}

	public Vao getVao() {
		return vao;
	}

	public void setVao(Vao vao) {
		this.vao = vao;
	}

	public int getEatingTime() {
		return eatingTime;
	}

	public void setEatingTime(int eatingTime) {
		this.eatingTime = eatingTime;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

}
