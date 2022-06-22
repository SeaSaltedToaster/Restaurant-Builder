package com.seaSaltedToaster.simpleEngine.uis.text.rendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.seaSaltedToaster.simpleEngine.uis.text.FontType;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class FontRenderer {

	private static FontShader shader;
	 
    public FontRenderer() {
        shader = new FontShader();
    }
     
    public void render(Map<FontType, List<Text>> texts){
        prepare();
        for(FontType font : texts.keySet()){
        	GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
            for(Text text : texts.get(font)){
            	if(text.isActive())
            		renderText(text);
            }
        }
        endRendering();
    }
      
    private static void prepare(){
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        shader.useProgram();
    }
    
    public void renderTextSingle(Text text){
    	if(!text.isActive()) return;
    	prepare();
    	FontType font = text.getFont();
    	GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
    	text.getTextMeshVao().bind(0,1);
        shader.getColor().loadVec3(text.getColor());
        shader.getTranslation().loadVec2(text.getPosition());
        shader.getBounds().loadVec2(text.getScale());
        shader.getAlpha().loadFloat(text.getAlpha());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
	    text.getTextMeshVao().unbind(0,1);
	    endRendering();
    }
     
    public static void renderText(Text text){
    	text.getTextMeshVao().bind(0,1);
        shader.getColor().loadVec3(text.getColor());
        shader.getTranslation().loadVec2(text.getPosition());
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
	    text.getTextMeshVao().unbind(0,1);
    }
     
    private static void endRendering(){
        shader.stopProgram();
        GL11.glDisable(GL11.GL_BLEND);
    }
	
}
