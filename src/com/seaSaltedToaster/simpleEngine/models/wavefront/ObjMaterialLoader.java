package com.seaSaltedToaster.simpleEngine.models.wavefront;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ObjMaterialLoader {
	
	public List<ObjMaterial> loadMaterial(String file) {
		InputStreamReader isr = new InputStreamReader(ObjMaterialLoader.class.getResourceAsStream("/" + file + ".mtl"));
		BufferedReader br = new BufferedReader(isr);
		String line;
		
		List<ObjMaterial> materials = null;
		ObjMaterial currentMaterial = null;
		
		try {
			while (true) {
				line = br.readLine();
				if(line == null) {
					break;
				} else if (line.startsWith("# Blender MTL File: ")) {
					
				} else if (line.startsWith("# Material Count: ")) {
					String count = line.replace("# Material Count: ", "");
					materials = new ArrayList<ObjMaterial>(Integer.parseInt(count));
				} else if (line.startsWith("end")) {
					break;
				}
				
				if(line.startsWith("newmtl ")) {
					currentMaterial = new ObjMaterial(line.replace("newmtl ","")); 
				}
				if(line.startsWith("Ka")) {
					currentMaterial.setAmbientColor(loadAmbientColor(line));
				} 
				if(line.startsWith("Kd")) {
					currentMaterial.setDiffuseColor(loadDiffuseColor(line));
				} 
				if(line.startsWith("Ks")) {
					materials.add(currentMaterial);
				}
			}
			br.close();
			isr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		currentMaterial = null;
		
		return materials;
	}
	
	private Vector3f loadDiffuseColor(String line) {
		Vector3f vector = new Vector3f();
		String string = line.replaceFirst("Kd ", "");
		String[] lines = string.split(" ");
		
		vector.x = Float.parseFloat(lines[0]);
		vector.y = Float.parseFloat(lines[1]);
		vector.z = Float.parseFloat(lines[2]);
		return vector;
	}
	
	private Vector3f loadAmbientColor(String line) {
		Vector3f vector = new Vector3f();
		String[] lines = line.split(" ");
		
		vector.x = Float.parseFloat(lines[1]);
		vector.y = Float.parseFloat(lines[2]);
		vector.z = Float.parseFloat(lines[3]);
		return vector;
	}

}
