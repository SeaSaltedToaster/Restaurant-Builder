package com.seaSaltedToaster.simpleEngine.uis.text.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Renderer;
import com.seaSaltedToaster.simpleEngine.uis.text.FontType;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.OpenGL;
import com.seaSaltedToaster.simpleEngine.utilities.Vector2f;

public class FontRenderer extends Renderer {
	 	
	public FontRenderer(Engine engine) {
		super(new FontShader(), engine);
    }
	
	public void renderText(Text text) {
    	if(!text.isActive()) return;
    	prepare();
    	render(text);
    	endRender();
	}
    
    @Override
	public void prepare() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        super.prepareFrame(false);
    }
    
    @Override
    public void render(Object obj){
    	//Load Text attribs
    	Text text = (Text) obj;
    	FontType font = text.getFont();
    	GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
        
        shader.loadUniform(text.getColor(), "color");
        shader.loadUniform(text.getAlpha(), "alpha");
        shader.loadUniform(text.getPosition(), "translation");
        shader.loadUniform(text.getScaleMultiplier(), "scale");
        setScissorTest(text.getClippingBounds());
        
        //Render
    	text.getTextMeshVao().bind(0,1,2,3);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
	    text.getTextMeshVao().unbind(0,1,2,3);
    }
    
    private void setScissorTest(int[] bounds) {
	    if (bounds == null) {
	      OpenGL.disableScissorTest();
	    } else {
	    	OpenGL.enableScissorTest(bounds[0], bounds[1], bounds[2], bounds[3]);
	    } 
	}
    
    @Override
	public void endRender() {
    	super.endRendering();
        GL11.glDisable(GL11.GL_BLEND);
    }
	
}
