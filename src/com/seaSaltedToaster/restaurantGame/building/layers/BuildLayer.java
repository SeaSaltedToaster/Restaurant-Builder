package com.seaSaltedToaster.restaurantGame.building.layers;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class BuildLayer {
	
	//Data
	private List<Entity> buildings;
	private boolean isOn;
	private int layerId;
	
	//Animation
	private SmoothFloat scaleAnim;
	
	//Height
	public static float HEIGHT_OFFSET = 1f;
	
	public BuildLayer(int layerId) {
		this.layerId = layerId;
		this.buildings = new ArrayList<Entity>();
		this.isOn = false;
		this.scaleAnim = new SmoothFloat(0.0f);
		this.scaleAnim.setAmountPer(0.33f);
	}
	
	public void updateLayer() {
		scaleAnim.update(Window.getDelta());
		float scale = scaleAnim.getValue();
		for(Entity entity : buildings) {
			entity.getTransform().setScale(scale);
		}
	}
	
	public void show() {
		if(isOn) return;
		this.setOn(true);
		this.scaleAnim.setValue(0.0f);
		this.scaleAnim.setTarget(1.0f);
	}
	
	public void hide() {
		if(!isOn) return;
		this.setOn(false);
		this.scaleAnim.setValue(1.0f);
		this.scaleAnim.setTarget(0.0f);
	}
	
	public boolean isEmpty() {
		return (buildings.size() <= 0);
	}

	public void addBuilding(Entity entity) {
		this.buildings.add(entity);
	}
	
	public int getLayerId() {
		return layerId;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public List<Entity> getBuildings() {
		return buildings;
	}

}
