package com.seaSaltedToaster.restaurantGame.menus.buildingSelector.colorPicker;

import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ColorOption extends UiComponent {
	
	//Colors
	private ColorPicker colorPicker;
	private Vector3f color;
	
	//UI box
	private UiComponent box;
	
	//Sizes
	private float size, incSize;
	
	public ColorOption(float size, Vector3f color, ColorPicker colorPicker) {
		super(9);
		this.color = color;
		this.colorPicker = colorPicker;
		createBox(size, color);
	}
	
	@Override
	public void onClick() {
		colorPicker.selectColor(color);
		System.out.println(color);
	}
	
	@Override
	public void onHover() {
		if(this.isHovering())
			setSize(incSize);
	}
	
	@Override
	public void stopHover() {
		setSize(size);
	}
	
	public void setOption(Vector3f color) {
		this.color = color;
		
		this.box.setColor(color);
		this.setColor(ColorPalette.MAIN_SHADE);
	}
	
	private void createBox(float size, Vector3f color) {
		this.size = size;
		this.incSize = size * 1.33f;
		
		this.setColor(ColorPalette.MAIN_SHADE);
		UiConstraints backCons = new UiConstraints();
		backCons.setWidth(new RelativeScale(size));
		backCons.setHeight(new AspectRatio(1.0f));
		backCons.setY(new AlignY(YAlign.MIDDLE));
		this.setConstraints(backCons);
		
		this.box = new UiComponent(8);
		this.box.setColor(color);
		UiConstraints boxCons = new UiConstraints();
		boxCons.setWidth(new RelativeScale(0.66f));
		boxCons.setHeight(new AspectRatio(1.0f));
		boxCons.setX(new AlignX(XAlign.CENTER));
		boxCons.setY(new AlignY(YAlign.MIDDLE));
		this.box.setConstraints(boxCons);
		this.addComponent(box);		
	}
	
	private void setSize(float size) {
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(size));
	}

}
