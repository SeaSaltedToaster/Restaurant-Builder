package com.seaSaltedToaster.restaurantGame.ground;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.utilities.Color;

public class GroundSettings {
	
	public String name;
	public Color[] colors;
	
	private List<Spawnable> objs;
	
	public GroundSettings(String name, Color... colors) {
		this.name = name;
		this.colors = colors;
		this.objs = new ArrayList<Spawnable>();
	}
	
	public void addSpawnable(String name, float chance) {
		this.objs.add(new Spawnable(name, chance));
	}

	public List<Spawnable> getObjs() {
		return objs;
	}

	public void setObjs(List<Spawnable> objs) {
		this.objs = objs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color[] getColors() {
		return colors;
	}

	public void setColors(Color[] colors) {
		this.colors = colors;
	}
	
}
