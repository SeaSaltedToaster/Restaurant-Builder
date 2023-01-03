package com.seaSaltedToaster.restaurantGame.ground;

public class Spawnable {

	private String name;
	private float chance;
	
	public Spawnable(String name, float chance) {
		this.name = name;
		this.chance = chance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getChance() {
		return chance;
	}

	public void setChance(float chance) {
		this.chance = chance;
	}
	
}
