package com.seaSaltedToaster.simpleEngine.models;

import com.seaSaltedToaster.simpleEngine.models.memoryManager.VaoMemoryManager;

public class VaoLoader {
	
	private VaoMemoryManager memoryManager;
	
	public VaoLoader() {
		this.memoryManager = new VaoMemoryManager();
	}
	
	public Vao loadToVAO(float[] positions, float[] colors, float[] normals, int[] indices) {
		//Add attrib 0, using positions, 3D size
		Vao vao = new Vao();
		memoryManager.addVao(vao);
		loadToVAO(vao, positions, colors, normals, indices);
		return vao;
	}
	
	public void loadToVAO(Vao vao, float[] positions, float[] colors, float[] normals, int[] indices) {
		//Add attrib 0, using positions, 3D size
		checkVao(vao);
		vao.bind();
		vao.createFloatAttribute(0, positions, 3);
		vao.createFloatAttribute(1, colors, 3);
		vao.createFloatAttribute(2, normals, 3);
		vao.createIndexBuffer(indices);
		vao.unbind();
	}
	
	public Vao loadToVAO(float[] positions, float[] colors, int[] indices) {
		//Create new vao and store data
		Vao vao = new Vao();
		memoryManager.addVao(vao);
		loadToVAO(vao, positions, colors, indices);
		return vao;
	}
	
	public void loadToVAO(Vao vao, float[] positions, float[] colors, int[] indices) {
		//Add attrib 0, using positions, 3D size
		checkVao(vao);
		vao.bind();
		vao.createFloatAttribute(0, positions, 3);
		vao.createFloatAttribute(1, colors, 3);
		vao.createIndexBuffer(indices);
		vao.unbind();
	}
	
	public Vao loadToVAO(float[] positions, int[] indices) {
		//Create new vao and store data
		Vao vao = new Vao();
		memoryManager.addVao(vao);
		loadToVAO(vao, positions, indices);
		return vao;
	}
	
	public Vao loadToVAO(float[] positions, int dimensions){
		Vao vao = new Vao();
		vao.bind(0);
		vao.createFloatAttribute(0, positions, dimensions);
		vao.unbind(0);
		vao.setIndexCount(positions.length / dimensions);
		return vao;
	}
	
	public static Vao loadToVAO(float[] positions, float[] textureCoords){
		Vao vao = new Vao();
		vao.bind(0);
		vao.createFloatAttribute(0, positions, 2);
		vao.createFloatAttribute(1, textureCoords, 2);
		vao.unbind(0);
		vao.setIndexCount(positions.length / 2);
		return vao;
	}
	
	public void loadToVAO(Vao vao, float[] positions, int[] indices) {
		//Add attrib 0, using positions, 3D size
		checkVao(vao);
		vao.bind();
		vao.createFloatAttribute(0, positions, 3);
		vao.createIndexBuffer(indices);
		vao.unbind();
	}
	
	public Vao loadToVAO(float[] positions) {
		//Create new vao and store data
		Vao vao = new Vao();
		memoryManager.addVao(vao);
		loadToVAO(vao, positions);
		return vao;
	}
	
	public void loadToVAO(Vao vao, float[] positions) {
		//Add attrib 0, using positions, 3D size
		checkVao(vao);
		vao.bind();
		vao.createFloatAttribute(0, positions, 3);
		vao.setIndexCount(positions.length / 3);
		vao.unbind();
	}

	private void checkVao(Vao vao) {
		//Make sure vao is in check
		if(!memoryManager.contains(vao)) {
			memoryManager.addVao(vao);
		}
	}
	
	public void destroy() {
		//Delete all memory
		memoryManager.destroyAll();
	}

}
