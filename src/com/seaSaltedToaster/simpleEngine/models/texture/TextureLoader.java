package com.seaSaltedToaster.simpleEngine.models.texture;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public class TextureLoader {
	
	private List<Integer> textureCache;
	
	public TextureLoader() {
		textureCache = new ArrayList<Integer>();
	}
	
	public int loadTexture(String fileName) {
		Texture tex = getTextureData(fileName+".png", true);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0.0f);
		int textureID = tex.getID();
		textureCache.add(textureID);
		return textureID;
	}
	
	public int loadTexture(String fileName, boolean stream) {
		Texture tex = getTextureData(fileName+".png", stream);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0.0f);
		int textureID = tex.getID();
		textureCache.add(textureID);
		return textureID;
	}
	
	public int loadTexture(String fileName, float bias) {
		Texture tex = getTextureData(fileName+".png", true);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, bias);
		int textureID = tex.getID();
		textureCache.add(textureID);
		return textureID;
	}
	
	private Texture getTextureData(String fileName, boolean isStream) {
		Texture texture = new Texture(0);
		
		BufferedImage bi = null;
		BufferedInputStream stream = null;
		if(isStream)
			stream = new BufferedInputStream(TextureLoader.class.getResourceAsStream(fileName));
		else
			try {
				stream = new BufferedInputStream(new FileInputStream(fileName));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		try {
			bi = ImageIO.read(stream);
			
			texture.width = bi.getWidth();
			texture.height = bi.getHeight();
			
			int[] pixels_raw = new int[texture.width * texture.height];
			pixels_raw = bi.getRGB(0, 0, texture.width, texture.height, null, 0, texture.width);
			
			ByteBuffer pixels = BufferUtils.createByteBuffer(texture.width * texture.height * 4);
			
			for(int i = 0; i < texture.height; i++) {
				for (int j = 0; j < texture.width; j++) {
					int pixel = pixels_raw[i*texture.width+j];
					 
					pixels.put((byte) ((pixel >> 16) & 0xFF));//RED			16
					pixels.put((byte) ((pixel >> 8) & 0xFF));//GREEN		8
					pixels.put((byte) ((pixel >> 0) & 0xFF));//BLUE		0
					pixels.put((byte) ((pixel >> 24) & 0xFF));//ALPHA		24
				}
			}
			
			pixels.flip();
			
			texture.id = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.id);
			
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, texture.width, texture.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixels);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		bi.flush();
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return texture;
	}
	
	public void deleteTextures() {
		for(Integer i : textureCache) {
			GL11.glDeleteTextures(i);
		}
	}

	public List<Integer> getTextureCache() {
		return textureCache;
	}

}
