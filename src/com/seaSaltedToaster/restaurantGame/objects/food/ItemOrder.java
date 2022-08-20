package com.seaSaltedToaster.restaurantGame.objects.food;

import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ItemOrder {
	
	//ID
	public static int totalId = 0;
	private int id;
	
	//Data
	private Entity table;
	private int seat;
	private Food foodItem; //TODO multiple food items
	private boolean isTaken = false, isDelivered = false;

	//Cooking
	private boolean isCooked = false;
	private Vector3f cookingLocation;
	
	public ItemOrder(Entity table, int seat, Food foodItem) {
		this.table = table;
		this.seat = seat;
		this.foodItem = foodItem;
		
		totalId++;
		this.id = totalId;
	}

	public int getId() {
		return id;
	}

	public Vector3f getCookingLocation() {
		return cookingLocation;
	}

	public void setCookingLocation(Vector3f cookingLocation) {
		this.cookingLocation = cookingLocation;
	}

	public boolean isCooked() {
		return isCooked;
	}

	public void setCooked(boolean isCooked) {
		this.isCooked = isCooked;
	}

	public float getCookingTime() {
		return foodItem.cookTime;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public boolean isTaken() {
		return isTaken;
	}

	public void setTaken(boolean isTaken) {
		this.isTaken = isTaken;
	}

	public Entity getTable() {
		return table;
	}

	public void setTable(Entity table) {
		this.table = table;
	}

	public int getTableSpot() {
		return seat;
	}

	public void setTableSpot(int seat) {
		this.seat = seat;
	}

	public Food getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(Food foodItem) {
		this.foodItem = foodItem;
	}
	
}