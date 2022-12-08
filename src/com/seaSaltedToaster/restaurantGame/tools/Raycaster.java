package com.seaSaltedToaster.restaurantGame.tools;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
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
	public static RayMode mode;
	public static Vector3f lastRay;

	//Raycasting
	private MousePicker picker;
	private Engine engine;
	
	//Building and ground
	public Ground ground;
	public BuildingManager builder;
	
	//Menus used with mouse
	private BuildingViewer viewer;
	public PaintMenu paint;
	
	private float MAX_DIST = 250f;

	public Raycaster(Engine engine) {
		register(engine);
		this.picker = new MousePicker(engine);
		this.viewer = new BuildingViewer(engine);
		engine.addUi(viewer);
		viewer.open(null);
		
		Raycaster.mode = RayMode.DEFAULT;
	}
	
	private void register(Engine engine) {
		this.engine = engine;
		this.engine.getMouse().getMouseButtonCallback().addListener(this);
		this.engine.getMouse().getMousePositionCallback().addListener(this);		
	}

	@Override
	public void notifyButton(MousePosData eventData) {
		//If we are in a menu, do nothing
		if(MainApp.menuFocused) {
			this.ground.selectAt(null);
			return;
		}
		
		//Get layer height
		float groundHeight = BuildLayer.HEIGHT_OFFSET * builder.getCurLayer();
		this.picker.update(groundHeight);
		
		//If we are not on the ground (0), do nothing
		if(groundHeight > 0) {
			this.ground.selectAt(null);
		}
		
		//Get raycast point
		Vector3f ray = picker.getCurrentTerrainPoint();
		Raycaster.lastRay = ray;
		if(ray == null) return;
		
		if(builder.getSelectedEntity() != null) {
			BuildingId id = (BuildingId) builder.getSelectedEntity().getComponent("BuildingId");
			if(id != null) {
				if(id.getType().type != BuildingType.WallObject) {
					
				}
				else {
					float rayLength = ray.length();
					float dist = builder.getSelectedEntity().getPosition().copy().subtract(engine.getCamera().getPosition().copy()).length();
					if(rayLength > dist) {
						float multi = dist / rayLength;
						ray = ray.scale(multi - 0.5f);
					}
				}
			}
		}

		/*
		 * BUILDING UPDATE
		 */
		this.builder.movePreview(ray);
		
		/*
		 * GROUND UPDATE
		 */
		if(builder.getSelectedEntity() != null || builder.isBuilding() || groundHeight != 0)
			this.ground.selectAt(null);
		else
			this.ground.selectAt(ray.round());
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
				this.click(eventData);
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
				this.click(eventData);
				break;
		}
	}
	
	private void pickColor(Entity selectedEntity) {
		if(selectedEntity == null || !selectedEntity.hasComponent("BuildingId")) return;
		
		BuildingId id = (BuildingId) selectedEntity.getComponent("BuildingId");
		paint.setPrimary(id.getPrimary());
		paint.setSecondary(id.getSecondary());
	}
	
	private void click(MouseEventData eventData) {
		//Key states
		boolean leftClicked = (eventData.getAction() == GLFW.GLFW_PRESS && eventData.getKey() == GLFW.GLFW_MOUSE_BUTTON_LEFT);
		
		//Check if we clicked on an object
		boolean selectedExistingObject = (leftClicked && !builder.isBuilding() && builder.getSelectedEntity() != null);
		if(selectedExistingObject) {
			viewer.open(builder.getSelectedEntity());
			return;
		}
		
		//Get raycast
		Vector3f ray = picker.getCurrentTerrainPoint();
		if(ray == null) return;
		
		//Place object on the ground, if you havent selected another
		boolean canPlace = (leftClicked && builder.isBuilding());
		if(canPlace) {
			this.builder.place(ray);
		}
	}
		
}