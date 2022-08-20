package com.seaSaltedToaster.restaurantGame.objects.food;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class FoodRegistry {
	
	//All Registered Models
	private static Map<Food, Integer> foods;
	private static int dishCount = 2;

	public static void create() {
		FoodRegistry.foods = new HashMap<Food, Integer>();
	}
		
	//Register new model
	public static void registerFood(Food food, Integer id) {
		System.out.println("Registered new food with the id : " + id);
		foods.put(food, id);
	}
		
	//Clear Registered Models
	public static void clear() {
		System.out.println("Cleared all registered foods");
		for(Food food : foods.keySet()) {
			food.vao.delete();
		}
	}
		
	//Get model with certain ID
	public static Food getFood(int id) {
		for(Entry<Food, Integer> entry : foods.entrySet()) {
			if(entry.getValue() == id) {
			   return entry.getKey();
			}
		}
		return null;
	}
	
	public static Food getRandom() {
		int rnd = new Random().nextInt(foods.size()-dishCount);
		return getFood(rnd);
	}

	public static Map<Food, Integer> getRegisteredModels() {
		return foods;
	}

}
