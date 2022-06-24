package com.seaSaltedToaster.restaurantGame.building;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.restaurantGame.building.renderer.BuildingRenderer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class BuildingManager implements KeyListener {
	
	//Building entity
	private Building preview;
	private boolean isBuilding;
	
	//Ground
	private Ground ground;
	
	//Rendering
	private List<Entity> placedBuildings;
	private BuildingRenderer renderer;
	private Engine engine;
	
	public BuildingManager(Engine engine, Ground ground, Raycaster raycaster, Building preview) {
		this.placedBuildings = new ArrayList<Entity>();
		this.engine = engine;
		this.renderer = new BuildingRenderer(this, engine);
		this.ground = ground;
		this.isBuilding = false;
		engine.getKeyboard().addKeyListener(this);
		if(preview != null) {
			this.preview = preview;
			this.preview.getEntity().getTransform().setScale(0.0f);
		}
	}
	
	public void updateFrame() {
		this.renderer.render();
	}
	
	public void placeBuilding(Vector3f placePosition) {
		Entity copy = preview.getEntity().copyEntity();
		placedBuildings.add(copy);
		//engine.addEntity(copy);
	}

	public void movePreview(Vector3f placePosition) {
		if(preview == null) return;
		Vector3f newPos = calculatePlacePosition(placePosition.copy());
		this.preview.getEntity().getTransform().setPosition(newPos);
	}

	@Override
	public void notifyButton(KeyEventData eventData) {
		if(eventData.getAction() != GLFW.GLFW_PRESS) return;
		int key = eventData.getKey();
		if(key == GLFW.GLFW_KEY_R) {
			Transform previewTransform = preview.getEntity().getTransform();
			previewTransform.getRotation().y += 90;
			if(previewTransform.getRotation().y >= 360)
				previewTransform.getRotation().y = 0;
			movePreview(previewTransform.getPosition());
		}
		if(key == GLFW.GLFW_KEY_ESCAPE) {
			setBuilding(false);
		}
	}
	
	private Vector3f calculatePlacePosition(Vector3f placement) {
		Vector3f snapPosition = null;
		if(preview.isWall()) {
			float yRot = preview.getEntity().getTransform().getRotation().y;
			float offset = ground.getTileSize() / 2;
			if(yRot == 0 || yRot == 180) {
				snapPosition = new Vector3f(Math.round(placement.x - offset) + offset, Math.round(placement.y), Math.round(placement.z));
			} else if(yRot == 90 || yRot == 270) {
				snapPosition = new Vector3f(Math.round(placement.x), Math.round(placement.y), Math.round(placement.z - offset) + offset);
			}
		} else {
			snapPosition = new Vector3f(Math.round(placement.x), Math.round(placement.y), Math.round(placement.z));
		}
		return snapPosition;
	}
	
	public void setPlacement(Building building) {
		this.preview = building;
		setBuilding(true);
	}

	public boolean isBuilding() {
		return isBuilding;
	}

	public void setBuilding(boolean isBuilding) {
		this.isBuilding = isBuilding;
		if(preview != null) {
			Transform trans = preview.getEntity().getTransform();
			trans.setScale(isBuilding ? 1 : 0);	
		}
	}

	public Building getPreview() {
		return preview;
	}

	public List<Entity> getPlacedBuildings() {
		return placedBuildings;
	}

	public Engine getEngine() {
		return engine;
	}

}
