package com.seaSaltedToaster.restaurantGame.building;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.ai.PathfindingWorld;
import com.seaSaltedToaster.restaurantGame.building.floors.FloorBuilder;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.building.layers.PlacementListener;
import com.seaSaltedToaster.restaurantGame.building.objects.BuildingRenderer;
import com.seaSaltedToaster.restaurantGame.building.objects.ObjectBuilder;
import com.seaSaltedToaster.restaurantGame.building.objects.SelectionRenderer;
import com.seaSaltedToaster.restaurantGame.building.walls.WallBuilder;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.tools.RayMode;
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
	public static List<PlacementListener> listeners = new ArrayList<PlacementListener>();
	public static boolean isBuilding = false;
	
	//Others
	private Entity selectedEntity;
	
	//Layers
	private List<BuildLayer> layers;
	public static int curLayer = 0, layerCount = 10;
	
	//Walls related
	private WallBuilder wallBuilder;
	
	//Floors related
	private FloorBuilder floorBuilder;
	private PathfindingWorld pathWorld;
	
	//Objects related
	private ObjectBuilder objectBuilder;
	private BuildingRenderer renderer;
	private SelectionRenderer selectRenderer;
	
	public BuildingManager(Engine engine, float worldSize, Raycaster raycaster, Building preview) {		
		this.createLayers();
		
		//Floors related
		this.floorBuilder = new FloorBuilder();
		this.pathWorld = new PathfindingWorld((int) worldSize * 2);
		
		//Walls related
		this.wallBuilder = new WallBuilder();
		
		//Objects related
		this.objectBuilder = new ObjectBuilder(this, preview);
		this.renderer = new BuildingRenderer(this, engine);
		this.selectRenderer = new SelectionRenderer(this, engine);
		
		//Engine
		engine.getKeyboard().addKeyListener(this);
	}
	
	public void createLayers() {
		this.layers = new ArrayList<BuildLayer>();
		for(int i = 0; i < layerCount; i++) {
			BuildLayer layer = new BuildLayer(this, i);
			this.layers.add(layer);
			MainApp.restaurant.layers.add(layer);
		}
		layers.get(curLayer).show();
	}

	public void place(Vector3f position) {
		BuildLayer layer = layers.get(curLayer);
		switch(Raycaster.mode) {
		case DEFAULT:
			this.objectBuilder.placeAt(layer);
			break;
		case WALL:
			this.wallBuilder.click(position);
			break;
		case FLOOR: 
			this.floorBuilder.click(position);
			break;
		default:
			this.objectBuilder.placeAt(layer);
			break;
		}
	}

	public void movePreview(Vector3f rawPosition) {
		switch(Raycaster.mode) {
		case DEFAULT:
			this.objectBuilder.movePreview(rawPosition);
			break;
		case WALL:
			this.wallBuilder.update(rawPosition);
			break;
		case FLOOR:
			this.floorBuilder.update(rawPosition);
			break;
		default:
			break;
		}

	}
	
	public void updateFrame() {
		for(BuildLayer layer : getLayers()) {
			layer.updateLayer();
		}
		this.renderer.prepare();
		this.renderer.render(null);
		this.renderer.endRender();
	}
	
	public void renderSelection() {
		this.selectRenderer.prepare();
		this.selectRenderer.render(null);
		this.selectRenderer.endRender();
	}
	
	@Override
	public void notifyButton(KeyEventData eventData) {
		if(eventData.getAction() != GLFW.GLFW_PRESS) return;
		int key = eventData.getKey();
		float rotate = 45.0f;
		
		if(key == GLFW.GLFW_KEY_R) {
			Transform trans = objectBuilder.getPreview().getTransform();
			trans.getRotation().y += rotate;
		}
		if(key == GLFW.GLFW_KEY_ESCAPE) {
			setBuilding(false);
			switch(Raycaster.mode) {
			case DEFAULT:
				break;
			case WALL:
				break;
			default:
				break;
			}
		}
		if(key == GLFW.GLFW_KEY_ENTER) {
			setBuilding(false);
			switch(Raycaster.mode) {
			case DEFAULT:
				break;
			case FLOOR:
				break;
			default:
				break;
			}
		}
	}
	
	public Entity getBuilding(int index) {
		int curCount = 0;
		for(BuildLayer layer : layers) {
			if(layer.isEmpty()) continue;
			int buildingsOnLayer = layer.getBuildings().size();
			if(index < (curCount + buildingsOnLayer)) {
				return layer.getBuildings().get(index - curCount);
			} else {
				curCount += layer.getBuildings().size();
				continue;
			}
		}
		return null;
	}
	
	public static Entity getBuildingWithID(int id) {
		for(BuildLayer layer : MainApp.restaurant.layers) {
			for(Entity entity : layer.getBuildings()) {
				if(!entity.hasComponent("BuildingId")) continue;
				BuildingId ide = (BuildingId) entity.getComponent("BuildingId");
				if(ide.getId() == id)
					return entity;
			}
		}
		return null;
	}
	
	public void setCurrentBuilding(Building building) {
		BuildingType type = building.type;
		switch(type) {
		case Floor:
			Raycaster.mode = RayMode.FLOOR;
			this.floorBuilder.setBuilding(building);
			break;
		case Wall:
			Raycaster.mode = RayMode.WALL;
			this.wallBuilder.set(building);
			break;
		default:
			Raycaster.mode = RayMode.DEFAULT;
			this.objectBuilder.setObject(building);
			this.objectBuilder.showPreview(true);
			movePreview(Raycaster.lastRay);
			break;
		
		}
		setBuilding(true);
	}
	
	public void delete(Entity entity) {
		for(BuildLayer layer : layers) {
			for(Component comp : entity.getComponents()) {
				comp.reset();
			}
			layer.getBuildings().remove(entity);
			pathWorld.removeBuilding(entity, layer);
		}
	}
	
	public int getCurLayer() {
		return curLayer;
	}
	
	public void setLayer(int newLayer) {
		if(newLayer < 0) return;
		BuildingManager.curLayer = newLayer;
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
		if(!isBuilding) {
			this.objectBuilder.showPreview(false);
			this.wallBuilder.resetEndpoints();
		}
	}

	public FloorBuilder getFloorBuilder() {
		return floorBuilder;
	}

	public void setFloorBuilder(FloorBuilder floorBuilder) {
		this.floorBuilder = floorBuilder;
	}

	public WallBuilder getWallBuilder() {
		return wallBuilder;
	}

	public void setWallBuilder(WallBuilder wallBuilder) {
		this.wallBuilder = wallBuilder;
	}

	public ObjectBuilder getObjectBuilder() {
		return objectBuilder;
	}

	public void setObjectBuilder(ObjectBuilder objectBuilder) {
		this.objectBuilder = objectBuilder;
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

	public BuildingRenderer getRenderer() {
		return renderer;
	}

	public SelectionRenderer getSelectRenderer() {
		return selectRenderer;
	}

	public ObjectBuilder getBuilder() {
		return objectBuilder;
	}

	public PathfindingWorld getPathWorld() {
		return pathWorld;
	}

	public Building getPreview() {
		return objectBuilder.getObject();
	}

}
