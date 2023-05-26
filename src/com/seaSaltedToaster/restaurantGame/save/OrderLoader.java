package com.seaSaltedToaster.restaurantGame.save;

import java.io.File;
import java.io.IOException;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.food.Food;
import com.seaSaltedToaster.restaurantGame.objects.food.FoodRegistry;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.restaurantGame.objects.seating.SeatComponent;
import com.seaSaltedToaster.restaurantGame.objects.seating.TableComponent;
import com.seaSaltedToaster.simpleEngine.entity.Entity;

public class OrderLoader extends SaveWriter {
	
	//Location of saves
	private File file, orders, chefOrders;
	
	//Save current
	private String curSave;
	
	public OrderLoader(File file, String curSave) {
		this.file = file;
		this.curSave = curSave;
		
		this.orders = new File(file.getAbsolutePath() + "/" + curSave + "/orders.rf");
		this.chefOrders = new File(file.getAbsolutePath() + "/" + curSave + "/chefOrders.rf");
		try {
			this.orders.createNewFile();
			this.chefOrders.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void saveOrder(ItemOrder itemOrder, int num) {		
		//0
		int orderId = itemOrder.getId();
		
		//1 and 2
		if(itemOrder == null || itemOrder.getTable() == null || itemOrder.getSeat() == null) return;
		int tableId = ((BuildingId) itemOrder.getTable().getEntity().getComponent("BuildingId")).getId();
		int seatId = ((BuildingId) itemOrder.getSeat().getEntity().getComponent("BuildingId")).getId();
		
		//3, 4 and 5
		int foodItem = itemOrder.getFoodItem().id;
		boolean isTaken = itemOrder.isTaken();
		boolean isDelivered = itemOrder.isDelivered();

		//6 and 7?
		boolean isCooked = itemOrder.isCooked();
		
		String details = "ORDER_DETAILS[" + orderId + "=" + tableId + "=" + seatId + "=" + foodItem + "=" + isTaken + "=" + isDelivered + "=" + isCooked + "];";
		if(num == 0)
			SaveSystem.writeToFile(orders, details, false);
		if(num == 1)
			SaveSystem.writeToFile(chefOrders, details, false);
	}

	public void loadOrders(Restaurant restaurant, int num) {
		File orderFile = null;
		
		if(num == 0)
			orderFile = new File(file.getAbsolutePath() + "/" + curSave + "/orders.rf");
		if(num == 1)
			orderFile = new File(file.getAbsolutePath() + "/" + curSave + "/chefOrders.rf");
		
		if(!orderFile.exists()) return;
		
		String allData = readFile(orderFile);
		String[] allLines = allData.split(";");
		
		for(String line : allLines) {
			String split = line.replace("[", "").replace("]", "").replace("ORDER_DETAILS", "").replace(";", "");
			String[] lines = split.split("=");
			
			ItemOrder order = new ItemOrder(null, null, null);
			
			int index = 0;
			for(String part : lines) {
				if(part.isEmpty() || part == "") continue;
				switch(index) {
				case 0 :
					int id = Integer.parseInt(part);
					order.setId(id);
					break;
				case 1 :
					int tableId = Integer.parseInt(part);
					Entity table = BuildingManager.getBuildingWithID(tableId);
					order.setTable((TableComponent) table.getComponent("Table"));
					break;
				case 2 :
					int seatId = Integer.parseInt(part);
					Entity seat = BuildingManager.getBuildingWithID(seatId);
					order.setSeat((SeatComponent) seat.getComponent("Chair"));
					break;
				case 3 :
					int foodId = Integer.parseInt(part);
					Food food = FoodRegistry.getFood(foodId);
					order.setFoodItem(food);
					break;
				case 4 :
					boolean isTaken = Boolean.parseBoolean(part);
					order.setTaken(isTaken);
					break;
				case 5 :
					boolean isDelivered = Boolean.parseBoolean(part);
					order.setDelivered(isDelivered);
					break;
				case 6 :
					boolean isCooked = Boolean.parseBoolean(part);
					order.setCooked(isCooked);
					break;
				case 7 :
					break;	
				}
				index++;
			}
			
			if(num == 0)
				MainApp.restaurant.orders.add(order);
			if(num == 1)
				MainApp.restaurant.chefOrders.add(order);
		}
	}


}
