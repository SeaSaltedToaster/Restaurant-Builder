package com.seaSaltedToaster.seaSaltedEngine.uis.text;

import com.seaSaltedToaster.seaSaltedEngine.Engine;
import com.seaSaltedToaster.seaSaltedEngine.model.Vao;
import com.seaSaltedToaster.seaSaltedEngine.model.VaoLoader;
import com.seaSaltedToaster.seaSaltedEngine.uis.UiComponent;
import com.seaSaltedToaster.seaSaltedEngine.uis.constraints.HorizontalAlignment;
import com.seaSaltedToaster.seaSaltedEngine.uis.constraints.VerticalAlignment;
import com.seaSaltedToaster.seaSaltedEngine.uis.text.rendering.TextMeshData;

public class Text extends UiComponent {

	//Text and font settings
	private String textString;
	private float fontSize;
	private FontType font;
	    
	private HorizontalAlignment horizontalAlignment;
	private VerticalAlignment verticalAlignment;
	
	private float lineMaxSize;
	private int numberOfLines;
	    
	//Text mesh settings
	private Vao textMeshVao;
	private int vertexCount;
	
	public Text(String text, int level) {
		super(level);
		this.setPosition(0.0f, 0.0f);
		this.textString = text;
		this.fontSize = 1;
        this.font = Fonts.ARIAL;
        this.lineMaxSize = 1;
        this.horizontalAlignment = HorizontalAlignment.CENTER;
        this.verticalAlignment = VerticalAlignment.MIDDLE;
        loadText();
	}
	
	public Text(String text, float fontSize, int level) {
		super(level);
		this.setPosition(0.0f, 0.0f);
		this.textString = text;
		this.fontSize = fontSize;
        this.font = Fonts.ARIAL;
        this.lineMaxSize = 1;
        this.horizontalAlignment = HorizontalAlignment.CENTER;
        this.verticalAlignment = VerticalAlignment.MIDDLE;
        loadText();
	}
	
	@Override
	public void updateComponent(Engine engine) {
		if(!isActive) return;
		this.updateSelf();
		this.constraints.updateConstraints(this);
		
		for(UiComponent childComponent : children) {
			childComponent.updateComponent(engine);
		}
	}
	
	@Override
	public void renderUI(Engine engine) {
		engine.getFontRenderer().renderTextSingle(this);
		for(UiComponent childComponent : children) {
			childComponent.renderUI(engine);
		}
	}
	
	public void loadText() {
		FontType font = getFont();
        TextMeshData data = font.loadText(this);
        Vao vao = VaoLoader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
        setTextMeshVao(vao);
        setVertexCount(data.getVertexCount());
	}
	
	public void reset() {
		TextMaster.loadText(this);
	}

	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

	public void setTextMeshVao(Vao textMeshVao) {
		this.textMeshVao = textMeshVao;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public String getTextString() {
		return textString;
	}

	public float getFontSize() {
		return fontSize;
	}

	public FontType getFont() {
		return font;
	}

	public HorizontalAlignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public VerticalAlignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public float getLineMaxSize() {
		return lineMaxSize;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public Vao getTextMeshVao() {
		return textMeshVao;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setTextString(String textString) {
		this.textString = textString;
		loadText();
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public void setFont(FontType font) {
		this.font = font;
	}

	public void setLineMaxSize(float lineMaxSize) {
		this.lineMaxSize = lineMaxSize;
		reset();
	}

}
