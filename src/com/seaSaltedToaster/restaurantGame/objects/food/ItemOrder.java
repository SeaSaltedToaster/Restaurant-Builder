package com.seaSaltedToaster.restaurantGame.objects.food;

import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.restaurantGame.objects.seating.TableComponent;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class ItemOrder {
	
	//Identification of all
	public static int totalId = 0;
	
	//Order id
	private int id; //0
	
	//Table data
	private TableComponent table; //1
	private SeatComponent seat; //2
	
	//Food status
	private Food foodItem; //3
	private boolean isTaken = false, isDelivered = false; //4,5

	//Cooking
	private boolean isCooked = false; //6
	private Entity chefWhoCooked; //7
	
	public ItemOrder(TableComponent table, SeatComponent seat, Food foodItem) {
		this.table = table;
		this.seat = seat;
		this.foodItem = foodItem;
		
		ItemOrder.totalId++;
		this.id = totalId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Entity getChefWhoCooked() {
		return chefWhoCooked;
	}

	public void setChefWhoCooked(Entity entity) {
		this.chefWhoCooked = entity;
	}

	public boolean isCooked() {
		return isCooked;
	}

	public void setCooked(boolean isCooked) {
		this.isCooked = isCooked;
	}

	public float getCookingTime() {
		return foodItem.getCookTime();
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

	public TableComponent getTable() {
		return table;
	}

	public void setTable(TableComponent table) {
		this.table = table;
	}

	public SeatComponent getSeat() {
		return seat;
	}

	public void setSeat(SeatComponent seat) {
		this.seat = seat;
	}

	public Food getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(Food foodItem) {
		this.foodItem = foodItem;
	}
	
}
