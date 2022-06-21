package com.seaSaltedToaster.restaurantGame.ground;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.seaSaltedToaster.restaurantGame.ground.renderer.GroundRenderer;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.Color;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class Ground {
	
	//Mesh data
	private GroundRenderer renderer;
	private Color[] colorList;
	private List<Vector3f> vertices;
	private List<Vector3f> colors;
	private List<Vector3f> normals;
	private List<Integer> triangles;
	private List<Float> tileIds;
	private Vao vao;
	
	//Objects
	private List<GroundTile> tiles;
	private float tileSize;
	private float worldSize;

	public Ground(float worldSize, float tileSize, Engine engine) {
		this.renderer = new GroundRenderer(engine);
		this.tiles = new ArrayList<GroundTile>();
		this.worldSize = worldSize;
		this.tileSize = tileSize;
	}
	
	public void selectAt(Vector3f currentRay) {
		for(GroundTile tile : tiles) {
			float tileX = tile.getGridX() * tileSize;
			float tileY = tile.getGridY() * tileSize;
			if(Math.abs(currentRay.x - tileX) < tileSize/2) {
				if(Math.abs(currentRay.z - tileY) < tileSize/2) {
					//renderer.setHighlight(tile.getId());
				}
			}
		}
	}
	
	public void update(Engine engine) {
		renderer.render(this, engine.getCamera());
	}
	
	public void generateGround(Engine engine) {
		//Create tile array
		createArrays();
		createTiles();
		
		//Get arrays
		float[] positions = getVectorList(this.vertices);
		float[] colors = getColorList();
		float[] normals = getVectorList(this.normals);
		int[] indices = getTriangles(this.triangles);
		float[] ids = getIds(this.tileIds);
		
		//Generate mesh
		this.vao = engine.getLoader().loadToVAO(positions, colors, normals, indices);
		vao.bind();
		//vao.createAttribute(3, ids, 1);
		vao.unbind();
	}

	private float[] getIds(List<Float> ids) {
		float[] array = new float[ids.size()*3];
		for(int i=0;i<ids.size();i++){
			array[i] = ids.get(i);
		}		
		return array;
	}
	
	private int[] getTriangles(List<Integer> triangles) {
		int[] indices = new int[triangles.size()];
		for(int i=0;i<triangles.size();i++){
			indices[i] = triangles.get(i);
		}		
		return indices;
	}
	
	private float[] getColorList() {
		float[] colors = new float[this.vertices.size()*3];
		int colorPointer = 0;
		for(Vector3f color : this.colors){
			colors[colorPointer++] = color.x;
			colors[colorPointer++] = color.y;
			colors[colorPointer++] = color.z;
		}		
		return colors;
	}

	private float[] getVectorList(List<Vector3f> vectors) {
		float[] positions = new float[vectors.size()*3];
		int vertexPointer = 0;
		for(Vector3f vertex : vectors){
			positions[vertexPointer++] = vertex.x;
			positions[vertexPointer++] = vertex.y;
			positions[vertexPointer++] = vertex.z;
		}		
		return positions;
	}

	private void createTiles() {
		Random random = new Random();
		int id = 0;
		for(int x = (int) -worldSize; x < worldSize; x++) {
			for(int z = (int) -worldSize; z < worldSize; z++) {
				//Tile object
				GroundTile tile = new GroundTile(x, z, random.nextInt(2)+1);
				tile.setId(id++);
				this.tiles.add(tile);
				
				//Create mesh data
				createVertices(tile);
			}
		}
	}
	
	private void createVertices(GroundTile tile) {
		//Data
		float halfStep = tileSize / 2.0f;
		
		//Vertex 1
		Vector3f v1 = new Vector3f(halfStep + (tile.getGridX() * tileSize), 0 , halfStep + tile.getGridY() * tileSize);
		vertices.add(v1);
		
		//Vertex 2
		Vector3f v2 = new Vector3f(-halfStep + (tile.getGridX() * tileSize), 0 , halfStep + (tile.getGridY() * tileSize));
		vertices.add(v2);

		//Vertex 3
		Vector3f v3 = new Vector3f(-halfStep + (tile.getGridX() * tileSize), 0 , -halfStep + (tile.getGridY() * tileSize));
		vertices.add(v3);

		//Vertex 4
		Vector3f v4 = new Vector3f(halfStep + (tile.getGridX() * tileSize), 0 , -halfStep + (tile.getGridY() * tileSize));
		vertices.add(v4);

		//Triangle
		triangles.add(vertices.indexOf(v1));
		triangles.add(vertices.indexOf(v3));
		triangles.add(vertices.indexOf(v2));

		//Triangle 2
		triangles.add(vertices.indexOf(v4));
		triangles.add(vertices.indexOf(v3));
		triangles.add(vertices.indexOf(v1));
		
		//Colors and Normals
		for(int i = 0; i < 4; i++) {
			this.colors.add(colorList[tile.getColorId()].toVector());
			this.normals.add(new Vector3f(0,1,0));
			this.tileIds.add((float) tile.getId());
		}
	}

	private void createArrays() {
		//Mesh arrays
		this.vertices = new ArrayList<Vector3f>();
		this.colors = new ArrayList<Vector3f>();
		this.normals = new ArrayList<Vector3f>();
		this.triangles = new ArrayList<Integer>();
		this.tileIds = new ArrayList<Float>();

		//Colors
		this.colorList = new Color[64];
		this.colorList[0] = new Color(1,1,1);
		this.colorList[1] = new Color(0.53f, 0.82f, 0.52f);
		this.colorList[2] = new Color(0.50f, 0.79f, 0.52f);
	}

	public Vao getMesh() {
		return vao;
	}

	public GroundRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(GroundRenderer renderer) {
		this.renderer = renderer;
	}

}
