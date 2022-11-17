package com.seaSaltedToaster.restaurantGame.menus.layers;

import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class IncrementButton extends UiComponent {

	//Layers
	private LayerMenu layerMenu;
	private int increment;
	
	//Scaling
	private SmoothFloat scale;
	private float nScale = 1.0f, hScale = 1.25f;
	
	//Coloring
	private float color1 = ColorPalette.MAIN_SHADE, color2 = 0.0f;

	public IncrementButton(int increment, LayerMenu layerMenu, Engine engine) {
		super(3);
		this.layerMenu = layerMenu;
		this.increment = increment;
		createPanel(increment, engine);
		
		this.scale = new SmoothFloat(1.0f);
	}
	
	@Override
	public void onClick() {
		this.layerMenu.increment(increment);
	}
	
	@Override
	public void updateSelf() {
		scale.update(Window.DeltaTime);
		float newScale = scale.getValue();
		this.setScaleMultiplier(newScale);
	}
	
	@Override
	public void onHover() {
		this.scale.setTarget(hScale);
		this.setColor(color2);
	}
	
	@Override
	public void stopHover() {
		this.scale.setTarget(nScale);
		this.setColor(color1);
	}
	
	private void createPanel(int increment, Engine engine) {
		int icon = 0;
		if(increment >= 1)
			icon = engine.getTextureLoader().loadTexture("/uis/up");
		else
			icon = engine.getTextureLoader().loadTexture("/uis/down");
		this.setTexture(icon);
		
		this.setColor(color1);
		this.setInteractable(true, engine);
		UiConstraints cons = this.getConstraints();
		cons.setX(new AlignX(XAlign.LEFT, 0.1f));
		float gap = 0.175f;
		if(increment >= 1)
			cons.setY(new AlignY(YAlign.TOP, gap));
		else
			cons.setY(new AlignY(YAlign.BOTTOM, gap));
		cons.setHeight(new RelativeScale(0.36f));
		cons.setWidth(new AspectRatio(1.0f));
	}

}
