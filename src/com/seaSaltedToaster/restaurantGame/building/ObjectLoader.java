package com.seaSaltedToaster.restaurantGame.building;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.models.wavefront.ObjLoader;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ObjectLoader {

	private InputStreamReader isr;
	private BufferedReader bufferedReader;
	
	public Building loadObject(String file, Engine engine) {
		//Get file
		List<String> lines = this.openModelFile(file);
		
		//Basic information
		String name = getValue(lines, "name");
		BuildingType type = BuildingType.valueOf(getValue(lines, "type")); 
		
		int price = Integer.parseInt(getValue(lines, "price"));
		float iconZoom = Float.parseFloat(getValue(lines, "iconZoom"));
		
		String category = getValue(lines, "category");
		String modelLoc = getValue(lines, "model");
		
		Vector3f defaultPrimary = getDefaultColor(lines, "primary");
		Vector3f defaultSecondary = getDefaultColor(lines, "secondary");
		
		//Building object
		Vao model = engine.getObjLoader().loadObjModel("models/" + modelLoc);
		Entity entity = new Entity();
		entity.addComponent(new ModelComponent(model));
		Building building = new Building(entity);
		
		//Set the right settings
		building.name = name;
		building.type = type;
		building.setPrice(price);
		building.setIconZoom(iconZoom);
		building.setCategory(BuildingList.getCategory(category));
		if(type == BuildingType.Wall)
			building.setWall(true);
		if(type == BuildingType.Floor)
			building.setFloor(true);
		building.setDefPrimary(defaultPrimary);
		building.setDefSecondary(defaultSecondary);
		
		//Add components
		//TODO components
		
		//Return
		BuildingList.register(building, category);
		return building;
	}

	private Vector3f getDefaultColor(List<String> lines, String colorName) {
		String full = getValue(lines, colorName);
		String[] values = full.split(",");
		
		Vector3f color = new Vector3f();
		color.x = Float.parseFloat(values[0]);
		color.y = Float.parseFloat(values[1]);
		color.z = Float.parseFloat(values[2]);
		return color;
	}

	private String getValue(List<String> lines, String value) {
		for(String line : lines) {
			if(line.startsWith(value)) {
				String[] split = line.split("=");
				return split[1];
			}
		}
		return "null";
	}

	private List<String>  openModelFile(String file) {
		//Open file
		isr = new InputStreamReader(ObjLoader.class.getResourceAsStream("/buildings/" + file + ".rtbuilding"));
		bufferedReader = new BufferedReader(isr);
		
		//Create array of lines in file
		List<String> objFile = new ArrayList<String>();
		
		try {
			String line;
		    while ((line = bufferedReader.readLine()) != null) {
		    	objFile.add(line);
		    }
			bufferedReader.close();
		} catch (IOException e) { e.printStackTrace(); }
		
		//Return full file
		return objFile;
	}

	
}
