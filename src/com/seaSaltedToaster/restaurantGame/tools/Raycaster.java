package com.seaSaltedToaster.restaurantGame.tools;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.menus.PaintMenu;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.BuildingViewer;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosListener;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Raycaster implements MouseListener, MousePosListener {

	//Objects
	private Engine engine;
	public static RayMode mode;
	
	//Raycasting
	private MousePicker picker;
	public static Vector3f lastRay;
	
	//Building
	public BuildingManager builder;
	public PaintMenu paint;
	
	//Viewer
	private BuildingViewer viewer;
	
	//Action objects
	public Ground ground;
	
	public Raycaster(Engine engine) {
		this.engine = engine;
		this.engine.getMouse().getMouseButtonCallback().addListener(this);
		this.engine.getMouse().getMousePositionCallback().addListener(this);
		this.picker = new MousePicker(engine);
		
		this.viewer = new BuildingViewer(engine);
		engine.addUi(viewer);
		viewer.open(null);
		Raycaster.mode = RayMode.DEFAULT;
	}
	
	@Override
	public void notifyButton(MousePosData eventData) {
		if(MainApp.menuFocused) {
			ground.selectAt(null);
			return;
		}
		
		//Update picker
		float groundHeight = BuildLayer.HEIGHT_OFFSET * builder.getCurLayer();
		picker.update(groundHeight);
		
		//Get raycast
		Vector3f ray = picker.getCurrentTerrainPoint();
		ground.getRenderer().setHighlight(-1);
		if(ray == null) return;
		
		builder.movePreview(ray);
		
		Vector3f placePosition = new Vector3f(Math.round(ray.x), Math.round(ray.y), Math.round(ray.z));
		lastRay = placePosition;
		
		if(builder.getSelectedEntity() != null) {
			ground.selectAt(null);
		}
		ground.selectAt(placePosition);
	}

	private void pickColor(Entity selectedEntity) {
		if(selectedEntity == null) return;
		BuildingId id = (BuildingId) selectedEntity.getComponent("BuildingId");
		if(id == null) return;
		paint.setPrimary(id.getPrimary());
		paint.setSecondary(id.getSecondary());
	}

	@Override
	public void notifyButton(MouseEventData eventData) {
		if(MainApp.menuFocused) {
			ground.selectAt(null);
			return;
		}
				
		//Key states
		boolean isLeftDown = eventData.getKey() == GLFW.GLFW_MOUSE_BUTTON_LEFT;
		boolean isPlaceDown = eventData.getAction() == GLFW.GLFW_PRESS;
		
		switch(mode) {
			case DEFAULT:
				this.placeDefault(eventData);
				break;
			case DELETE:
				if(!isLeftDown || !isPlaceDown || builder.getSelectedEntity() == null) break;
				BuildingId id = (BuildingId) builder.getSelectedEntity().getComponent("BuildingId");
				if(id == null) return;
				
				id.getLayer().remove(builder.getSelectedEntity());
				break;
			case PAINT:
				if(!isLeftDown || !isPlaceDown || builder.getSelectedEntity() == null) break;
				BuildingId id2 = (BuildingId) builder.getSelectedEntity().getComponent("BuildingId");
				if(id2 == null) return;
				id2.setPrimary(paint.getPrimary());
				id2.setSecondary(paint.getSecondary());
				break;
			case PICKER:
				this.pickColor(builder.getSelectedEntity());
				break;
			default:
				break;
		}
	}
	
	private void placeDefault(MouseEventData eventData) {
		//Key states
		boolean isLeftDown = eventData.getKey() == GLFW.GLFW_MOUSE_BUTTON_LEFT;
		boolean isPlaceDown = eventData.getAction() == GLFW.GLFW_PRESS;
		
		//Check for object viewer
		if(builder.getSelectedEntity() != null && isLeftDown && isPlaceDown && !builder.isBuilding()) {
			Entity selected = builder.getSelectedEntity();
			viewer.open(selected);
			return;
		}

		//Get raycast
		Vector3f ray = picker.getCurrentTerrainPoint();
		if(ray == null) return;
		Vector3f placePosition = new Vector3f(Math.round(ray.x), Math.round(ray.y), Math.round(ray.z));
		
		//Place
		if(isPlaceDown && isLeftDown) {
			if(builder.isBuilding()) {
				boolean placed = builder.startPlacement(placePosition);
			}
		}
		
		if(eventData.getAction() != GLFW.GLFW_PRESS) return;
		
		//Check for delete
		if(builder.getSelectedEntity() != null && !builder.isBuilding() && eventData.getKey() == GLFW.GLFW_KEY_X) {
			Entity selected = builder.getSelectedEntity();
			builder.delete(selected);
			return;
		}
		
		if(eventData.getKey() != GLFW.GLFW_MOUSE_BUTTON_LEFT) return;
		//Update picker
		float groundHeight = BuildLayer.HEIGHT_OFFSET * builder.getCurLayer();
		picker.update(groundHeight);
	}
		
}