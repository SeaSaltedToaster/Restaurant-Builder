package com.seaSaltedToaster.restaurantGame.menus;

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

public class GeneralButton extends UiComponent {

	private SmoothFloat scale;
	private float nScale = 1.0f, hScale = nScale * 1.25f;
	
	public GeneralButton(int icon, Engine engine) {
		super(3);
		create(icon, engine);
		this.scale = new SmoothFloat(1.0f);
		this.scale.setAmountPer(0.15f);
	}
	
	@Override
	public void updateSelf() {
		scale.update(Window.DeltaTime);
		this.setScaleMultiplier(scale.getValue());
	}
	
	@Override
	public void onHover() {
		this.scale.setTarget(hScale);
	}
	
	@Override
	public void onClick() {
		this.scale.setValue(1.5f);
	}
	
	@Override
	public void stopHover() {
		this.scale.setTarget(nScale);
		this.setColor(ColorPalette.BUTTON_BASE);
	}
	
	private void create(int iconTex, Engine engine) {
		//Button
		this.setColor(ColorPalette.BUTTON_BASE);
		this.setInteractable(true, engine);
		UiConstraints cons = new UiConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setWidth(new RelativeScale(0.66f * 3));
		cons.setHeight(new AspectRatio(1.0f));
		this.setConstraints(cons);
		
		//Icon
		UiComponent icon = new UiComponent(4);
		icon.setTexture(iconTex);
		UiConstraints iconCons = new UiConstraints();
		iconCons.setX(new AlignX(XAlign.CENTER));
		iconCons.setY(new AlignY(YAlign.MIDDLE));
		iconCons.setWidth(new RelativeScale(0.75f));
		iconCons.setHeight(new AspectRatio(1.0f));
		icon.setConstraints(iconCons);
		this.addComponent(icon);
	}

}
