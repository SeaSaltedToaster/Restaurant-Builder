package com.seaSaltedToaster.restaurantGame.building;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.building.renderer.BuildingRenderer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class BuildingManager implements KeyListener {
	
	//Building entity
	public static boolean isBuilding = false;
	
	//Others
	private Entity selectedEntity;
	
	//Layers
	private List<BuildLayer> layers;
	private int curLayer = 0, layerCount = 10;
	
	//Building
	private BuildingRenderer renderer;
	private AdvancedBuilder builder;
	
	public BuildingManager(Engine engine, Ground ground, Raycaster raycaster, Building preview) {
		this.renderer = new BuildingRenderer(this, engine);
		this.builder = new AdvancedBuilder(preview);
		createLayers();
		engine.getKeyboard().addKeyListener(this);
	}
	
	private void createLayers() {
		this.layers = new ArrayList<BuildLayer>();
		for(int i = 0; i < layerCount; i++) {
			BuildLayer layer = new BuildLayer(i);
			this.layers.add(layer);
			MainApp.restaurant.layers.add(layer);
		}
		layers.get(curLayer).show();
	}

	public void updateFrame() {
		this.renderer.render();
	}
	
	public boolean startPlacement(Vector3f placePosition) {
		if(MainApp.menuFocused) return false;
		this.builder.startPlacement();
		endPlacement();
		return true;
	}

	public void movePreview(Vector3f rawPosition) {
		Vector3f newPos = calculatePlacePosition(rawPosition.copy());
		builder.increasePlacement(newPos);
	}
	
	public boolean endPlacement() {
		if(MainApp.menuFocused) return false;
		BuildLayer layer = layers.get(curLayer);
		this.builder.endPlacement(layer);
		return true;
	}

	@Override
	public void notifyButton(KeyEventData eventData) {
		if(eventData.getAction() != GLFW.GLFW_PRESS) return;
		int key = eventData.getKey();
		if(key == GLFW.GLFW_KEY_R) {
			Transform previewTransform = builder.getStart().getTransform();
			previewTransform.getRotation().y += 90;
			if(previewTransform.getRotation().y >= 360)
				previewTransform.getRotation().y = 0;
			movePreview(previewTransform.getPosition());
			
			Transform previewTransform2 = builder.getEnd().getTransform();
			previewTransform2.getRotation().y += 90;
			if(previewTransform2.getRotation().y >= 360)
				previewTransform2.getRotation().y = 0;
			movePreview(previewTransform2.getPosition());
		}
		if(key == GLFW.GLFW_KEY_ESCAPE) {
			setBuilding(false);
		}
	}
	
	private Vector3f calculatePlacePosition(Vector3f placement) {
		Vector3f snapPosition = null;
		if(builder.getObject().isWall()) {
			float yRot = builder.getStart().getTransform().getRotation().y;
			float offset = Ground.getTileSize() / 2;
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
	
	public Entity getBuilding(int index) {
		int curCount = 0;
		for(BuildLayer layer : layers) {
			if(layer.isEmpty()) continue;
			int buildingsOnLayer = layer.getBuildings().size();
			if(index < (curCount + buildingsOnLayer)) {
				return layer.getBuildings().get(index - curCount);
			} else {
				curCount += layer.getBuildings().size()-1;
				continue;
			}
		}
		return null;
	}
	
	public void setCurrentBuilding(Building building) {
		this.builder.setObject(building);
		movePreview(Raycaster.lastRay);
		setBuilding(true);
	}
	
	public void delete(Entity entity) {
		for(BuildLayer layer : layers) {
			for(Component comp : entity.getComponents()) {
				comp.reset();
			}
			layer.getBuildings().remove(entity);
		}
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
		BuildingManager.isBuilding = isBuilding;
		builder.showPreview(isBuilding);
//		if(preview != null) {
//			Transform trans = preview.getEntity().getTransform();
//			trans.setScale(isBuilding ? 1 : 0);	
//		}
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


	public AdvancedBuilder getBuilder() {
		return builder;
	}

	public Building getPreview() {
		return builder.getObject();
	}

}
