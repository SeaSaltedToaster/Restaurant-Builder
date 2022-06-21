package com.seaSaltedToaster.simpleEngine.models.wavefront;

public class ObjData {
	
	private float[] positions;
	private float[] normals;
	private float[] colors;
	
	private int[] indices;

	public ObjData(float[] positions, float[] normals, float[] colors, int[] indices) {
		this.positions = positions;
		this.normals = normals;
		this.colors = colors;
		this.indices = indices;
	}
	
	public void delete() {
		this.positions = null;
		this.normals = null;
		this.colors = null;
		this.indices = null;
	}

	public float[] getPositions() {
		return positions;
	}

	public float[] getNormals() {
		return normals;
	}

	public float[] getColors() {
		return colors;
	}

	public int[] getIndices() {
		return indices;
	}

	public void setPositions(float[] positions) {
		this.positions = positions;
	}

	public void setNormals(float[] normals) {
		this.normals = normals;
	}

	public void setColors(float[] colors) {
		this.colors = colors;
	}

	public void setIndices(int[] indices) {
		this.indices = indices;
	}

}
