package com.seaSaltedToaster.restaurantGame.building;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
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
	private boolean isBuilding = false;
	
	//Others
	private Ground ground;
	private Entity selectedEntity;
	
	//Layers
	private List<BuildLayer> layers;
	private int curLayer = 0;
	private int buildingIndex = 0;
	
	//Rendering
	private BuildingRenderer renderer;
	private Engine engine;
	
	public BuildingManager(Engine engine, Ground ground, Raycaster raycaster, Building preview) {
		this.renderer = new BuildingRenderer(this, engine);
		this.engine = engine;
		this.ground = ground;
		if(preview != null) {
			this.preview = preview;
			this.preview.getEntity().getTransform().setScale(0.0f);
		}
		createLayers();
		engine.getKeyboard().addKeyListener(this);
	}
	
	private void createLayers() {
		this.layers = new ArrayList<BuildLayer>();
		for(int i = 0; i < 10; i++) {
			BuildLayer layer = new BuildLayer(i);
			this.layers.add(layer);
		}
		layers.get(curLayer).show();
	}

	public void updateFrame() {
		this.renderer.render();
	}
	
	public void placeBuilding(Vector3f placePosition) {
		Entity copy = preview.getEntity().copyEntity();
		copy.addComponent(new BuildingId(buildingIndex));
		BuildLayer layer = layers.get(curLayer);
		layer.addBuilding(copy);
		buildingIndex++;
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
		snapPosition.y = curLayer * BuildLayer.HEIGHT_OFFSET;
		return snapPosition;
	}
	
	public void delete(Entity entity) {
		for(BuildLayer layer : layers) {
			layer.getBuildings().remove(entity);
		}
	}
	
	public Entity getBuilding(int index) {
		int curCount = 0;
		for(BuildLayer layer : layers) {
			if(layer.isEmpty()) continue;
			if(index <= curCount + layer.getBuildings().size()) {
				return layer.getBuildings().get(index - curCount);
			} else {
				curCount += layer.getBuildings().size();
				continue;
			}
		}
		return null;
	}
	
	public void setPlacement(Building building) {
		this.preview = building;
		setBuilding(true);
	}
	
	public int getCurLayer() {
		return curLayer;
	}
	
	public void setLayer(int newLayer) {
		if(newLayer < 0) return;
		this.curLayer = newLayer;
		for(BuildLayer layer : layers) {
			if(layer.getLayerId() > newLayer)
				layer.hide();
			else
				layer.show();
		}
		layers.get(newLayer).show();
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

	public Entity getSelectedEntity() {
		return selectedEntity;
	}

	public void setSelectedEntity(Entity selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	public List<BuildLayer> getLayers() {
		return layers;
	}


	public Building getPreview() {
		return preview;
	}

	public Engine getEngine() {
		return engine;
	}

}
