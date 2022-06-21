package com.seaSaltedToaster.simpleEngine.models.wavefront;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.models.VaoLoader;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ObjLoader {

	private InputStreamReader isr;
	private BufferedReader bufferedReader;
	
	private VaoLoader loader;
	
	private ObjMaterialLoader materialLoader;
	
	public ObjLoader(Engine engine) {
		this.loader = engine.getLoader();
		
		this.materialLoader = new ObjMaterialLoader();
	}
	
	public Vao loadObjModel(String file) {
		//Opening and extract contents of file
		List<String>  objFile = openModelFile(file);
		
		//Extracting Material Data from Opened File
		List<ObjMaterial> objMaterials = getMaterials(objFile, file);
		
		//Extracting Data from Opened File
		List<Vector3f> positions = getVertexPositions(objFile);
		List<Vector3f> normals = getVertexNormals(objFile);
		
		//Sort faces and materials
		ObjData data = sortModelData(positions, normals, objMaterials, objFile);
		
		//Convert Obj Data to Vao Mesh
		Vao vao = loader.loadToVAO(data.getPositions(), data.getColors(), data.getNormals(), data.getIndices());
		return vao;
	}
	
	private ObjData sortModelData(List<Vector3f> positions, List<Vector3f> normals, List<ObjMaterial> objMaterials, List<String> objFile) {
		//List of Colors
		List<Integer> indices = new ArrayList<Integer>();
		List<Vector3f> colors = new ArrayList<Vector3f>();
		
		//Arrays to fill
		float[] normalsArray = new float[positions.size() * 3];
		float[] colorArray = new float[positions.size() * 3];
		
		//Current Face Material
		ObjMaterial currentMaterial = null;
		
		//Fill Colors Array
		for (int i = 0; i < positions.size() * 3; i++) {
			  colors.add(new Vector3f(0.5f, 0f, 0f));
		}
		
		//Looping through each line of the file 
		for(int i = 0; i < objFile.size(); i++) {
			String line = objFile.get(i);
			if(line.startsWith("usemtl ")) {
				String materialName = line.replaceFirst("usemtl ", "");
				currentMaterial = getMaterial(materialName, objMaterials);
			}
			if(line.startsWith("f ")) {
				//Get Face Vertices
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				//Set Material for all Vertices
				colors.set(Integer.parseInt(vertex1[1]), currentMaterial.getDiffuseColor());
				colors.set(Integer.parseInt(vertex2[1]), currentMaterial.getDiffuseColor());
				colors.set(Integer.parseInt(vertex3[1]), currentMaterial.getDiffuseColor());
				
				//Proc Vertices
				processVertex(vertex1,indices,colors,normals,colorArray,normalsArray);
				processVertex(vertex2,indices,colors,normals,colorArray,normalsArray);
				processVertex(vertex3,indices,colors,normals,colorArray,normalsArray);
			}
		}
		
		//Create arrays
		float[] verticesArray = new float[positions.size()*3];
		int[] indicesArray = new int[indices.size()];
		
		//Set / Organize vertex in array
		int vertexPointer = 0;
		for(Vector3f vertex : positions){
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		//Same for indices ^
		for(int i=0;i<indices.size();i++){
			indicesArray[i] = indices.get(i);
		}
		
		//Clear Arrays
		colors.clear();
		indices.clear();
		
		return new ObjData(verticesArray, normalsArray, colorArray, indicesArray);
	}
	
	private void processVertex(String[] vertexData, List<Integer> indices,
			List<Vector3f> colors, List<Vector3f> normals, float[] colorArray,
			float[] normalsArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		if(colors.size() > Integer.parseInt(vertexData[1])-1) {
			Vector3f currentTex = colors.get(Integer.parseInt(vertexData[1]));
			colorArray[currentVertexPointer*3] = currentTex.x;
			colorArray[currentVertexPointer*3+1] = currentTex.y;
			colorArray[currentVertexPointer*3+2] = currentTex.z;
		}
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2])-1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3+1] = currentNorm.y;
		normalsArray[currentVertexPointer*3+2] = currentNorm.z;	
	}

	private ObjMaterial getMaterial(String materialName, List<ObjMaterial> objMaterials) {
		for(ObjMaterial material : objMaterials) {
			if(material.getMaterialName().equalsIgnoreCase(materialName)) 
				return material;
		}
		return null;
	}

	private List<ObjMaterial> getMaterials(List<String> objFile, String file) {
		//Creating the Array of positions
		List<ObjMaterial> materials = null;
		
		//Looping through each line of the file 
		for(int i = 0; i < objFile.size(); i++) {
			String line = objFile.get(i);
			if(line.startsWith("mtllib ")) {
				materials = materialLoader.loadMaterial(file);
			}
		}
		
		return materials;
	}

	private List<Vector3f> getVertexPositions(List<String>  objFile) {
		//Creating the Array of positions
		List<Vector3f> vertexPositions = new ArrayList<Vector3f>();
		
		//Looping through each line of the file 
		for(int i = 0; i < objFile.size(); i++) {
			String line = objFile.get(i);
			if(line.startsWith("v ")) {
				String[] currentLine = line.split(" ");
				Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
						Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
				vertexPositions.add(vertex);
			}
		}
		
		return vertexPositions;
	}
	
	private List<Vector3f> getVertexNormals(List<String>  objFile) {
		//Creating the Array of positions
		List<Vector3f> normals = new ArrayList<Vector3f>();
		
		//Looping through each line of the file 
		for(int i = 0; i < objFile.size(); i++) {
			String line = objFile.get(i);
			if(line.startsWith("vn ")) {
				String[] currentLine = line.split(" ");
				Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
						Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
				normals.add(normal);
			}
		}
		
		return normals;
	}

	private List<String>  openModelFile(String file) {
		//Open file
		isr = new InputStreamReader(ObjLoader.class.getResourceAsStream("/" + file + ".obj"));
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
