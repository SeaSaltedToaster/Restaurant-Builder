package com.seaSaltedToaster.restaurantGame.building.layers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdMaker {
	
	public static List<Integer> usedIds = new ArrayList<Integer>();
	public static Random random = new Random((int) Math.random() * 1000);
	
	public static int getNew() {
		int index = 0;
		while(true) {
			index = random.nextInt(Integer.MAX_VALUE - 1);
			if(!isUsed(index))
				break;
		}
		return index;
	}
	
	public static void addId(int id) {
		usedIds.add(id);
	}
	
	public static boolean isUsed(int id) {
		return usedIds.contains(id);
	}

}
