package com.seaSaltedToaster.restaurantGame.save;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.seaSaltedToaster.restaurantGame.WorldCamera;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class LoadSystem {
	
	//Location of saves
	private static String saveLocation = System.getProperty("user.home") + "/Desktop";
	private static File file = new File(saveLocation + "/RestaurantGame/saves");
	
	//Save current
	private String curSave;
	
	public LoadSystem(String curSave) {
		this.curSave = curSave;
		if(!file.exists())
			LoadSystem.file.mkdirs();
	}
	
	public static int getSaveIcon(String save, Engine engine) {
		File iconFile = new File(file.getAbsolutePath() + "/" + save + "/icon.png");
		if(iconFile.exists()) {
			File iconFileGo = new File(file.getAbsolutePath() + "/" + save + "/icon");
			return engine.getTextureLoader().loadTexture(iconFileGo.getAbsolutePath(), false);
		}
		return -1;
	}
	
	public void loadBuildings(BuildingManager manager) {
		File bldFile = new File(file.getAbsolutePath() + "/" + curSave + "/buildings.rf");
		if(!bldFile.exists()) return;
		
		//Get data
		int index = 0;
		String allData = readFile(bldFile);
		String[] allLines = allData.split(System.getProperty("line.separator"));
		
		//Go through all lines\
		for(String line : allLines)	{	
			if(line.startsWith("entities") || line.startsWith("}")) continue;
			String[] parts = line.split(";");
			
			//Get type and name
			String typeData = parts[0];
			String[] typeAll = typeData.split(",");
			
			String name = typeAll[0];
			String category = typeAll[1];
			
			String type = typeAll[2];
			
			int layer = Integer.parseInt(typeAll[3]);
			Building bld = BuildingList.getBuilding(name, BuildingList.getCategory(category));
			
			BuildingType typeEnum = BuildingType.valueOf(type);
			switch(typeEnum) {
			case Wall:
				/*
				 * LOAD WALL OBJECTS
				 */
				loadWall(bld, manager, parts, layer, index);
				break;
			default:
				/*
				 * NORMAL OBJECTS LOADING
				 */
				loadObject(bld, manager, parts, layer, index);
				break;
			}
			
			//Create entity
			if(bld == null)
				return;
			index++;
		}
	}	
	
	private void loadWall(Building bld, BuildingManager manager, String[] parts, int layer, int index) {		
		//Get position
		String transData = parts[1];
		String[] posParts = transData.split(",");
		float x = Float.parseFloat(posParts[0]);
		float y = Float.parseFloat(posParts[1]);
		float z = Float.parseFloat(posParts[2]);
		Vector3f start = new Vector3f(x, y, z);
		
		float dx = Float.parseFloat(posParts[3]);
		float dy = Float.parseFloat(posParts[4]);
		float dz = Float.parseFloat(posParts[5]);
		Vector3f end = new Vector3f(dx, dy, dz);
		
		//Get colors
		String prim = parts[2];
		String[] primParts = prim.split(",");
		float pr = Float.parseFloat(primParts[0]);
		float pg = Float.parseFloat(primParts[1]);
		float pb = Float.parseFloat(primParts[2]);
		Vector3f primary = new Vector3f(pr, pg, pb);
		
		String second = parts[3];
		String[] secondParts = second.split(",");
		float sr = Float.parseFloat(secondParts[0]);
		float sg = Float.parseFloat(secondParts[1]);
		float sb = Float.parseFloat(secondParts[2]);
		Vector3f secondary = new Vector3f(sr, sg, sb);
		
		Entity entity = manager.getWallBuilder().getBuilder().createMesh(start, end).get(0);

		//Add
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		id.setPrimary(primary);
		id.setSecondary(secondary);
	}

	private void loadObject(Building bld, BuildingManager manager, String[] parts, int layer, int index) {
		Entity entity = bld.getEntity().copyEntity();
		
		//Get position
		String transData = parts[1];
		String[] posParts = transData.split(",");
		float x = Float.parseFloat(posParts[0]);
		float y = Float.parseFloat(posParts[1]);
		float z = Float.parseFloat(posParts[2]);
		entity.getTransform().getPosition().set(x, y, z);
		
		float dx = Float.parseFloat(posParts[3]);
		entity.getTransform().getRotation().x = dx;
		float dy = Float.parseFloat(posParts[4]);
		entity.getTransform().getRotation().y = dy;
		float dz = Float.parseFloat(posParts[5]);
		entity.getTransform().getRotation().z = dz;
		
		float sx = Float.parseFloat(posParts[6]);
		float sy = Float.parseFloat(posParts[7]);
		float sz = Float.parseFloat(posParts[8]);
		entity.getTransform().setScale(new Vector3f(sx, sy, sz));
		
		//Get colors
		String prim = parts[2];
		String[] primParts = prim.split(",");
		float pr = Float.parseFloat(primParts[0]);
		float pg = Float.parseFloat(primParts[1]);
		float pb = Float.parseFloat(primParts[2]);
		Vector3f primary = new Vector3f(pr, pg, pb);
		
		String second = parts[3];
		String[] secondParts = second.split(",");
		float sr = Float.parseFloat(secondParts[0]);
		float sg = Float.parseFloat(secondParts[1]);
		float sb = Float.parseFloat(secondParts[2]);
		Vector3f secondary = new Vector3f(sr, sg, sb);
		
		//Add
		manager.getLayers().get(layer).addBuilding(entity, bld, index);
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		id.setPrimary(primary);
		id.setSecondary(secondary);		
	}

	public void loadCamera(WorldCamera camera) {
		//Camera file
		File camFile = new File(file.getAbsolutePath() + "/" + curSave + "/camera.rf");
		if(!camFile.exists()) return;
		
		//Get data
		String cameraData = readFile(camFile);
		String[] pieces = cameraData.split(";");
		
		//Get position
		String[] pos = pieces[0].split(",");
		float x = Float.parseFloat(pos[0]);
		float y = Float.parseFloat(pos[1]);
		float z = Float.parseFloat(pos[2]);
		camera.getPosition().set(x, y, z);
		
		//Get rotation
		String[] rotation = pieces[1].split(",");
		float yaw = Float.parseFloat(rotation[0]);
		camera.setYaw(yaw);
		float pitch = Float.parseFloat(rotation[1]);
		camera.setPitch(pitch);
		float roll = Float.parseFloat(rotation[2]);
		camera.setRoll(roll);
		
		//Get other
		String[] other = pieces[2].split(",");
		float camDist = Float.parseFloat(other[0]);
		camera.setCamDist(camDist);
		float outerAngle = Float.parseFloat(other[1]);
		camera.setOuterAngle(outerAngle);
	}
	
	public String readFile(File path)
	{
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(path.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, StandardCharsets.US_ASCII);
	}

}
