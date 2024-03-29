package com.seaSaltedToaster.restaurantGame.save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.seaSaltedToaster.restaurantGame.WorldCamera;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.layers.BuildLayer;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.menus.TimeDisplay;
import com.seaSaltedToaster.restaurantGame.objects.FloorComponent;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.restaurantGame.objects.food.ItemOrder;
import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.ScreenshotUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class SaveSystem {
	
	//Location of saves
	private static String saveLocation = System.getProperty("user.home") + "/Desktop";
	private static File file = new File(saveLocation + "/RestaurantGame/saves");;
	
	//Save current
	private static FileWriter writer;
	private String curSave;
	
	//Loaders
	private OrderLoader orderLoader;
	
	public SaveSystem(String curSave) {
		this.curSave = curSave;
		
		this.orderLoader = new OrderLoader(file, curSave);
	}
	
	public static void createFolder() {
		SaveSystem.file.mkdirs();
	}
	
	public void saveOrder(ItemOrder order) {
		this.orderLoader.saveOrder(order, 0);
	}
	
	public void loadOrders(Restaurant restaurant) {
		this.orderLoader.loadOrders(restaurant, 0);
	}
	
	public void saveChefOrder(ItemOrder order) {
		this.orderLoader.saveOrder(order, 1);
	}
	
	public void loadChefOrders(Restaurant restaurant) {
		this.orderLoader.loadOrders(restaurant, 1);
	}
	
	public static void setGroundType(String save, String type) {
		File ground = new File(file.getAbsolutePath() + "/" + save + "/ground.rf");
		try {
			ground.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writeToFile(ground, type, true);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openTo(String file) {
		File dest = new File(SaveSystem.file.getAbsolutePath() + "/" + curSave + "/" + file + ".rf");
		SaveSystem.setWriterFile(dest);
	}
	
	public void saveAction(String data) {
		File actions = new File(file.getAbsolutePath() + "/" + curSave + "/actions.rf");
		try {
			actions.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		writeToFile(actions, data + System.getProperty("line.separator"), false);
	}
	
	public void closeWriter() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAction(int id, int index, String type, String data) {
		this.saveAction(id + "," + index + "," + type + "," + data + ";");
	}
	
	public static File createSave(String name) {
		SaveSystem.file.mkdirs();
		File cur = new File(file.getAbsolutePath() + "/" + name);
		cur.mkdirs();
		return cur;
	}
	
	public void save(Camera camera, TimeDisplay timeDisplay, Ground ground, BuildingManager manager) {
		//File for the save
		File cur = new File(file.getAbsolutePath() + "/" + curSave);
		cur.mkdirs();
		
		//Save camera position
		File camFile = new File(cur.getAbsolutePath() + "/camera.rf");
		saveCamera(camera, camFile);
		
		//Save buildings
		File bldFile = new File(cur.getAbsolutePath() + "/buildings.rf");
		saveBuildings(manager, bldFile);
		
		//Take icon shot
		File scrn = new File(cur.getAbsolutePath() + "/icon.png");
		ScreenshotUtils.screenshot(scrn, 750, 750);
		
		//Save ground
		SaveSystem.setGroundType(curSave, ground.groundType);
	}
	
	public void saveBuildings(BuildingManager manager, File buildingsFile) {
		//Create file
		try {
			buildingsFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Start
		writeToFile(buildingsFile, "entities {", true);
		writeToFile(buildingsFile, System.getProperty("line.separator"), false);
		
		//Go to each TODO other layers?
		for(BuildLayer layer : manager.getLayers())
			for(Entity bld : layer.getBuildings()) {
				saveEntity(bld, buildingsFile);
				writeToFile(buildingsFile, System.getProperty("line.separator"), false);
			}
		
		//End 
		writeToFile(buildingsFile, "}", false);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveEntity(Entity bld, File buildingsFile) {
		//Components necessary
		if(bld == null) return;
		if(!bld.hasComponent("BuildingId")) return;
		BuildingId id = (BuildingId) bld.getComponent("BuildingId");
		
		//Basics
		Building type = id.getType();
		writeToFile(buildingsFile, type.getName() + "," + type.getCategory().getName() + "," + type.getType().name() + "," + id.getLayer().getLayerId() + "," + "ID:" + id.getId() + ";", false);
		switch(type.type) {
		case Wall:
			//Wall
			WallComponent wallComp = (WallComponent) bld.getComponent("Wall");
			writeToFile(buildingsFile, wallComp.getStart().toString() + "," + wallComp.getEnd().toString() + ";" + wallComp.getWallType() + ":" + wallComp.getGenerator() + ";", false);
			break;
		case Floor:
			FloorComponent floorComp = (FloorComponent) bld.getComponent("Floor");
			writeToFile(buildingsFile, bld.getTransform().toString().replace(" ", ""), false);
			writeToFile(buildingsFile, floorComp.getPoints().size() + "", false);
			for(Vector3f point : floorComp.getPoints()) {
				writeToFile(buildingsFile, ">" + point, false);
			}
			writeToFile(buildingsFile, ">" + floorComp.getType() + ">" + floorComp.getGenerator() + ";", false);
			break;
		default:
			//Position
			writeToFile(buildingsFile, bld.getTransform().toString().replace(" ", ""), false);
			break;
		
		}		

		//Colors
		writeToFile(buildingsFile, id.getPrimary().toString().replace(" ", "") + ";" + id.getSecondary().toString().replace(" ", "") + ";", false);
	}

	public void saveTime(TimeDisplay timeDisplay, File timeFile) {
		
	}

	private void saveCamera(Camera camera, File camFile) {
		//Create file
		try {
			camFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//What to write to file
		WorldCamera cam = (WorldCamera) camera;
		String position = cam.getPosition().toString().replace(" ", "");
		position += ";" + cam.getYaw() + "," + cam.getPitch() + "," + cam.getRoll() + ";";
		position += cam.getCamDist() + "," + cam.getOuterAngle() + ";";
		
		//Write to the file
		writeToFile(camFile, position, true);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void setWriterFile(File file) {
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFile(File file, String string, boolean newFileWriter) {
		if(newFileWriter)
			setWriterFile(file);
		try {
			writer.write(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
