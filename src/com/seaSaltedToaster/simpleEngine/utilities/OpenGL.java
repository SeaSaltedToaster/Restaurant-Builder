package com.seaSaltedToaster.simpleEngine.utilities;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class OpenGL {
	
	private static boolean scissorTest = false;
	
	private static int[] scissorBounds = new int[4];
	
	public static void clearBuffers(float r, float g, float b, float a) {
		//Clears Display 
		setDepthTest(true);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(r, g, b, a);
	}
	
	public static void detachAndRemoveShaders(int vert, int frag, int programID) {
		//Remove shader programs
		GL20.glDetachShader(programID, vert);
		GL20.glDetachShader(programID, frag);
		GL20.glDeleteShader(vert);
		GL20.glDeleteShader(frag);
	}
	
	public static void enableScissorTest(int x, int y, int width, int height) {
	    GL11.glEnable(GL11.GL_SCISSOR_TEST);
	    GL11.glScissor(x, y, width, height);
	  }
	  
	  public static void disableScissorTest() {
	      GL11.glDisable(GL11.GL_SCISSOR_TEST);
	  }
	
	public static void enableCull() {
		GL11.glEnable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCull() {
		GL11.glDisable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void enableFrontCull() {
		GL11.glEnable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_FRONT);
	}
	
	public static boolean setPolygonFill() {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		return false;
	}
	
	public static boolean setPolygonWire() {
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		return true;
	}
	
	public static void disableFrontCull() {
		GL11.glDisable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_FRONT);
	}
	
	public static void setDepthTest(boolean value) {
		if(value)
			GL11.glEnable(GL11.GL_DEPTH_TEST); 
		if(!value)
			GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
	

	public static void clearColor() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public static void clearDepth() {
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void activateTexture(int texture) {
		GL13.glActiveTexture(GL13.GL_TEXTURE+texture); 
	}
	
	public static void clearColor(Vector3f color, float a) {
		GL11.glClearColor(color.x, color.y, color.z, a);
	}

}
