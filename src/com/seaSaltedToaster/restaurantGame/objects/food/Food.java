package com.seaSaltedToaster.restaurantGame.objects.food;

import com.seaSaltedToaster.simpleEngine.models.Vao;

public class Food {
	
	public Vao vao;
	
	public int eatingTime = 10;
	public int price = 10;
	public int cookTime = 15;

	public Food(Vao vao) {
		this.vao = vao;
	}

	public Vao getVao() {
		return vao;
	}

	public void setVao(Vao vao) {
		this.vao = vao;
	}

}
