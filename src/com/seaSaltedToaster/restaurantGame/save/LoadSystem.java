package com.seaSaltedToaster.restaurantGame.save;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.WorldCamera;
import com.seaSaltedToaster.restaurantGame.ai.person.Action;
import com.seaSaltedToaster.restaurantGame.ai.person.ActionComponent;
import com.seaSaltedToaster.restaurantGame.ai.person.GoToAction;
import com.seaSaltedToaster.restaurantGame.ai.person.WaitAction;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.BuildingType;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.objects.FloorComponent;
import com.seaSaltedToaster.restaurantGame.objects.WallComponent;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class LoadSystem {
	
	//Location of saves
	private static String saveLocation = System.getProperty("user.home") + "/Desktop";
	private static File file = new File(saveLocation + "/RestaurantGame/saves");
	
	//Ais
	private List<String> actions;
	
	//Save current
	private String curSave;
	
	public LoadSystem(String curSave) {
		this.curSave = curSave;
		this.actions = new ArrayList<String>();
		if(!file.exists())
			LoadSystem.file.mkdirs();
	}
	
	public String getGroundType() {
		File ground = new File(file.getAbsolutePath() + "/" + curSave + "/ground.rf");
		String allData = readFile(ground);
		if(!file.exists() || file.getPath() == "" || allData == "NO_FILE")
			return "Grassy";
		
		return allData.trim();
	}
		
	public static int getSaveIcon(String save, Engine engine) {
		File iconFile = new File(file.getAbsolutePath() + "/" + save + "/icon.png");
		if(iconFile.exists()) {
			File iconFileGo = new File(file.getAbsolutePath() + "/" + save + "/icon");
			return engine.getTextureLoader().loadTexture(iconFileGo.getAbsolutePath(), false);
		}
		return -1;
	}
	
	public void loadActions() {
		//File
		File bldFile = new File(file.getAbsolutePath() + "/" + curSave + "/actions.rf");
		if(!bldFile.exists()) return;
		
		//Get data
		String allData = readFile(bldFile);
		String[] allLines = allData.split(System.getProperty("line.separator"));
		
		
		//Load
		for(String line : allLines)
			this.actions.add(line);
		System.out.println(actions.get(0));
	}
	
	public void loadBuildings(BuildingManager manager) {
		File bldFile = new File(file.getAbsolutePath() + "/" + curSave + "/buildings.rf");
		if(!bldFile.exists()) return;
		
		//Get data
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
			if(typeAll.length == 0)
				continue;
			String category = typeAll[1];
			
			String type = typeAll[2];
			
			int layer = Integer.parseInt(typeAll[3]);
			int id = -127;
			if(typeAll.length >= 5)	
				Integer.parseInt(typeAll[4].replace("ID:", "").trim());

			Building bld = BuildingList.getBuilding(name, BuildingList.getCategory(category));
			BuildingType typeEnum = BuildingType.valueOf(type);
			
			Entity entity = null;
			
			switch(typeEnum) {
			case Wall:
				/*
				 * LOAD WALL OBJECTS
				 */
				entity = loadWall(bld, manager, parts, layer, id);
				break;
			case Floor :
				/*
				 * FLOOR
				 */
				entity = loadFloor(bld, manager, parts, layer, id);
				break;
			default:
				/*
				 * NORMAL OBJECTS LOADING
				 */
				entity = loadObject(bld, manager, parts, layer, id);
				break;
			}
			
			//load actions
			findActions(id, entity);
			
			//Create entity
			if(bld == null)
				return;
		}
	}	
	
	private void findActions(int id, Entity entity) {
		if(!entity.hasComponent("Action")) return;
		
		ActionComponent comp = (ActionComponent) entity.getComponent("Action");
		for(String line : actions) {
			if(line.startsWith(""+id)) {
				comp.getActions().add(processAction(line, entity));
			}
		}
		comp.getActions().remove(null);
		
		if(comp.getActions().isEmpty()) {
			for(int i = 0; i < 25; i++) {
				comp.getActions().add(new GoToAction(new Vector3f((float) Math.random() * 20, 0, (float) Math.random() * 20), entity, true));
			}
		}
	}

	private Action processAction(String line, Entity entity) {
		String[] vals = line.split(",");
		String type = vals[2];
		
		Action action = null;
		
		switch(type.trim()) {
		case "GoTo" :
			action = new GoToAction(null, null, false);
			action.object = entity;
			action.loadAction(vals[3]);
			break;
		case "Wait" :
			action = new WaitAction(0);
			action.object = entity;
			action.loadAction(vals[3]);
			break;
		default :
			action = null;
		}
		
		return action;
	}

	private Entity loadFloor(Building bld, BuildingManager manager, String[] parts, int layer, int index) {			
		//Floor parts
		String section = parts[2];
		String[] floorParts = section.split(">");
		
		int count = Integer.parseInt(floorParts[0]);
		int ix = 1;
		
		List<Vector3f> points = new ArrayList<Vector3f>();
		for(int i = 0; i < count; i++) {
			String vector = floorParts[ix];
			String[] vectorParts = vector.split(",");
			float vx = Float.parseFloat(vectorParts[0]);
			float vy = Float.parseFloat(vectorParts[1]);
			float vz = Float.parseFloat(vectorParts[2]);
			points.add(new Vector3f(vx,vy,vz));
			ix++;
		}
		
		String type = floorParts[ix++];
		String generator = floorParts[ix++];
		
		FloorComponent comp = new FloorComponent(type, generator, points);
		Entity entity = manager.getFloorBuilder().getMeshBuilder().buildFloor(points, comp, true);
		
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
		String prim = parts[3];
		String[] primParts = prim.split(",");
		float pr = Float.parseFloat(primParts[0]);
		float pg = Float.parseFloat(primParts[1]);
		float pb = Float.parseFloat(primParts[2]);
		Vector3f primary = new Vector3f(pr, pg, pb);
		
		String second = parts[4];
		String[] secondParts = second.split(",");
		float sr = Float.parseFloat(secondParts[0]);
		float sg = Float.parseFloat(secondParts[1]);
		float sb = Float.parseFloat(secondParts[2]);
		Vector3f secondary = new Vector3f(sr, sg, sb);
		
		//Add		
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		id.setPrimary(primary);
		id.setSecondary(secondary);	
		return entity;
	}

	private Entity loadWall(Building bld, BuildingManager manager, String[] parts, int layer, int index) {
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
		String prim = parts[3];
		String[] primParts = prim.split(",");
		float pr = Float.parseFloat(primParts[0]);
		float pg = Float.parseFloat(primParts[1]);
		float pb = Float.parseFloat(primParts[2]);
		Vector3f primary = new Vector3f(pr, pg, pb);
		
		String second = parts[4];
		String[] secondParts = second.split(",");
		float sr = Float.parseFloat(secondParts[0]);
		float sg = Float.parseFloat(secondParts[1]);
		float sb = Float.parseFloat(secondParts[2]);
		Vector3f secondary = new Vector3f(sr, sg, sb);
		
		String types = parts[2];
		String[] typesParts = types.split(":");
		String type = typesParts[0];
		String generator = typesParts[1];
		
		WallComponent comp = new WallComponent(type, generator);
		comp.setStart(start);
		comp.setEnd(end);
		Entity entity = manager.getWallBuilder().getBuilder().createMesh(start, end, comp).get(0);

		//Add
		BuildingId id = (BuildingId) entity.getComponent("BuildingId");
		id.setPrimary(primary);
		id.setSecondary(secondary);
		return entity;
	}

	private Entity loadObject(Building bld, BuildingManager manager, String[] parts, int layer, int index) {
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
		return entity;
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
//			e.printStackTrace();
			return "NO_FILE";
		}
		return new String(encoded, StandardCharsets.US_ASCII);
	}

	public boolean hasData() {
		File bldFile = new File(file.getAbsolutePath() + "/" + curSave + "/buildings.rf");
		return !bldFile.exists();
	}

}
