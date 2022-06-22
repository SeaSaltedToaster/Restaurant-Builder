package com.seaSaltedToaster.simpleEngine.models.texture;

import org.lwjgl.opengl.GL11;

public class Texture {
	
	int id;
	
	int width;
	int height;
	
	public Texture(int tex) {
		this.id = tex;
	}

	public int getID() {
		return id;
	}
	
	public void bind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public static void unbind() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
