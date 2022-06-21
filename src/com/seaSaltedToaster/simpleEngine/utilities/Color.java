package com.seaSaltedToaster.simpleEngine.utilities;

public class Color {
	
	public float r,g,b;
	protected Vector3f colorVector3f;

	public Color(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.colorVector3f = new Vector3f(r, g, b);
	}
	
	public Color(Vector3f color) {
		this.r = color.x;
		this.g = color.y;
		this.b = color.z;
		this.colorVector3f = new Vector3f(r, g, b);
	}


	public Color brighten(float value) {
		r *= value;
		g *= value;
		b *= value;
		return new Color(r,g,b);
	}
	
	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public void setR(float r) {
		this.r = r;
	}

	public void setG(float g) {
		this.g = g;
	}

	public void setB(float b) {
		this.b = b;
	}
	
	public Color add(Vector3f other) {
        float x = this.r + other.x;
        float y = this.g + other.y;
        float z = this.b + other.z;
        return new Color(x, y, z);
    }
	
	public Vector3f toVector() {
		colorVector3f.set(r, g, b);
		return colorVector3f;
	}

	public Color addVariation() {
		return this;
	}

	public Color round() {
		this.r = (int) Math.round(r);
		this.g = (int) Math.round(g);
		this.b = (int) Math.round(b);
		return this;
	}

}
