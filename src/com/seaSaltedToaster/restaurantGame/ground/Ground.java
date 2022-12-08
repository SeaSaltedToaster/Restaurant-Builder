package com.seaSaltedToaster.restaurantGame.ground;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.seaSaltedToaster.restaurantGame.ground.renderer.GroundRenderer;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.renderer.shadows.ShadowRenderer;
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
	private List<Integer> tileIds;
	private Vao vao;
	
	//Objects
	private List<GroundTile> tiles;
	public List<Entity> meshes;
	public static float worldSize, tileSize;

	public Ground(float worldSize, float tileSize, Engine engine) {
		this.renderer = new GroundRenderer(this, engine);
		this.tiles = new ArrayList<GroundTile>();
		Ground.worldSize = worldSize;
		Ground.tileSize = tileSize;
		this.meshes = new ArrayList<Entity>();
	}
	
	public void selectAt(Vector3f currentRay) {
		if(currentRay == null) {
			renderer.setHighlight(-256); 
			return;
		}
		for(GroundTile tile : tiles) {
			float tileX = tile.getGridX();
			float tileY = tile.getGridY();
			if(Math.abs(currentRay.x - tileX) < 1) {
				if(Math.abs(currentRay.z - tileY) < 1) {
					renderer.setHighlight(tile.getId());
				}
			}
		}
	}
	
	public void update(ShadowRenderer shadowRenderer, Engine engine) {
		renderer.prepare();
		renderer.render(shadowRenderer);
		renderer.endRender();
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
		int[] ids = getIds(this.tileIds);
		
		//Generate mesh
		this.vao = engine.getLoader().loadToVAO(positions, colors, normals, indices);
		this.vao.bind(0,1,2,3);
		this.vao.createIntAttribute(3, ids, 1);
		this.vao.unbind(0,1,2,3);
		
		Entity entity = new Entity();
		entity.addComponent(new ModelComponent(vao));
		this.meshes.add(entity);
	}

	private int[] getIds(List<Integer> ids) {
		int[] array = new int[ids.size()*3];
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
		float halfStep = 1.0f / 2.0f;
		float tileY = 0.0000005f * (float) (tile.getId());
		
		//Vertex 1
		Vector3f v1 = new Vector3f(halfStep + (tile.getGridX()), tileY, halfStep + tile.getGridY());
		vertices.add(v1);
		
		//Vertex 2
		Vector3f v2 = new Vector3f(-halfStep + (tile.getGridX()), tileY, halfStep + (tile.getGridY()));
		vertices.add(v2);

		//Vertex 3
		Vector3f v3 = new Vector3f(-halfStep + (tile.getGridX()), tileY, -halfStep + (tile.getGridY()));
		vertices.add(v3);

		//Vertex 4
		Vector3f v4 = new Vector3f(halfStep + (tile.getGridX()), tileY, -halfStep + (tile.getGridY()));
		vertices.add(v4);

		//Triangle
		triangles.add(vertices.indexOf(v2));
		triangles.add(vertices.indexOf(v3));
		triangles.add(vertices.indexOf(v1));

		//Triangle 2
		triangles.add(vertices.indexOf(v1));
		triangles.add(vertices.indexOf(v3));
		triangles.add(vertices.indexOf(v4));
		
		//Colors and Normals
		for(int i = 0; i < 4; i++) {
			this.colors.add(colorList[tile.getColorId()].toVector());
			this.normals.add(new Vector3f(0,1,0));
			this.tileIds.add(tile.getId());
		}
	}

	private void createArrays() {
		//Mesh arrays
		this.vertices = new ArrayList<Vector3f>();
		this.colors = new ArrayList<Vector3f>();
		this.normals = new ArrayList<Vector3f>();
		this.triangles = new ArrayList<Integer>();
		this.tileIds = new ArrayList<Integer>();

		//Colors
		this.colorList = new Color[64];
		this.colorList[0] = new Color(1,1,1);
		this.colorList[1] = new Color(0.50f, 0.775f, 0.52f);
		this.colorList[2] = new Color(0.50f, 0.75f, 0.52f);
	}

	public static float getTileSize() {
		return tileSize;
	}

	public float getWorldSize() {
		return worldSize;
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
