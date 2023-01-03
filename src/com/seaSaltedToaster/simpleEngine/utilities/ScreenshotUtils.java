package com.seaSaltedToaster.simpleEngine.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.renderer.Window;

public class ScreenshotUtils implements KeyListener {

	public void screenshot() {
        GL11.glReadBuffer(GL11.GL_FRONT);
        int width = (int) Window.getWidth();
        int height= (int) Window.getHeight();
        int bpp = 4;
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
        GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
        
        File file = new File("C:/Users/natha/Desktop/OpenGL/New Game Engine/Restaurant-Builder/screenshots/imgs/" + LocalDate.now()+"-"+LocalTime.now().toString().replace(".", "").replace(":", "")+".png");
        try {
			file.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        String format = "png";
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
           
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
           
        try {
            ImageIO.write(image, format, file);
        } catch (IOException e) { e.printStackTrace(); }
	}
	
	public static void screenshot(File file, int width, int height) {
        GL11.glReadBuffer(GL11.GL_FRONT);
        int bpp = 4;
        ByteBuffer buffer = BufferUtils.createByteBuffer((int) (Window.getWidth() * Window.getHeight()) * bpp);
        int halfX = (int) (Window.getWidth()/2);
        int halfY = (int) (Window.getHeight()/2);
        GL11.glReadPixels(halfX - (width/2), halfY - (height/2), width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer );
        
        String format = "png";
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
           
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                int i = (x + (width * y)) * bpp;
                int r = buffer.get(i) & 0xFF;
                int g = buffer.get(i + 1) & 0xFF;
                int b = buffer.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
           
        try {
            ImageIO.write(image, format, file);
        } catch (IOException e) { e.printStackTrace(); }
	}
	
	@Override
	public void notifyButton(KeyEventData eventData) {
		if(eventData.getKey() == GLFW.GLFW_KEY_F2 && eventData.getAction() == GLFW.GLFW_PRESS) {
			screenshot();
			System.out.println("Saved screenshot");
		}
	}
	
}
