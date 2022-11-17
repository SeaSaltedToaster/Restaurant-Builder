package com.seaSaltedToaster;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.renderer.lighting.Light;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.utilities.Vector2f;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public abstract class Scene {
	
	//Scene entity information
	protected List<Entity> entities;
	protected Light light;
	
	//Ui Info
	protected int levels = 10;
	protected List<UiComponent> components;
	
	public Scene() {
		clearScene();
	}
	
	protected void clearScene() {
		this.entities = new ArrayList<Entity>();
		this.light = new Light(new Vector3f(0), new Vector3f(0));
		
		this.components = new ArrayList<UiComponent>(levels);
		this.components.clear();
		for(int i = 0; i < levels; i++) {
			UiComponent baseComponent = new UiComponent(0, new Vector2f(0,0), new Vector2f(1,1), new Vector3f(0,0,0));
			baseComponent.setAlpha(0.0f);
			components.add(baseComponent);
		}
	}

	public abstract void loadScene(Engine engine);
	
	public abstract void renderScene(Engine engine);
	
	public abstract void updateScene(Engine engine);

	public abstract void unloadScene(Engine engine);
	
	public void addComponent(UiComponent component) {
		int level = component.getLevel();
		components.get(level).addComponent(component);
	}
	
	public List<Entity> getBatches() {
		return entities;
	}

	public void setBatches(List<Entity> entities) {
		this.entities = entities;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}

	public int getLevels() {
		return levels;
	}

	public void setLevels(int levels) {
		this.levels = levels;
	}

	public List<UiComponent> getComponents() {
		return components;
	}

	public void setComponents(List<UiComponent> components) {
		this.components = components;
	}


}
