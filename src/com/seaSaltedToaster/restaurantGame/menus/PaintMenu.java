package com.seaSaltedToaster.restaurantGame.menus;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.restaurantGame.tools.RayMode;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class PaintMenu extends UiComponent {

	//Coloring / Basics
	private Vector3f primary, secondary;
	private Engine engine;
	private boolean isOpen = false;
	
	//Actual menu
	private UiComponent picker, pickerIcon;
	private UiComponent paint, paintIcon;
	
	//Colors
	private float COLOR = ColorPalette.BUTTON_BASE;
	private float LIGHT = ColorPalette.BUTTON_HIGHLIGHT;
	
	public PaintMenu() {
		super(8);
		this.primary = new Vector3f(0);
		this.secondary = new Vector3f(0);
		this.setActive(false);
		this.engine = MainApp.restaurant.engine;
		createUis();
	}
	
	public void updateSelf() {
		RayMode mode = Raycaster.mode;
		picker.setColor(COLOR);
		paint.setColor(COLOR);
		switch(mode) {
		case DEFAULT:
			break;
		case DELETE:
			break;
		case PAINT:
			this.paint.setColor(LIGHT);
			break;
		case PICKER:
			this.picker.setColor(LIGHT);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onClick() {
		if(paint.isHovering()) {
			Raycaster.mode = RayMode.PAINT;
		}
		if(picker.isHovering()) {
			Raycaster.mode = RayMode.PICKER;
		}
	}
	
	public void show() {
		if(isOpen) {
			Raycaster.mode = RayMode.DEFAULT;
		} else if(!isOpen) {
			
		}
		this.isOpen = !isOpen;
		this.setActive(isOpen);
	}
	
	private void createUis() {
		UiConstraints cons = this.getConstraints();
		cons.setX(new AlignX(XAlign.LEFT, 2.0f));
		cons.setY(new AlignY(YAlign.MIDDLE, 0.0f));
		cons.setWidth(new RelativeScale(1.5f));
		cons.setHeight(new AspectRatio(1.5f));
		addPicker();
		addPainter();
		this.setAlpha(0.0f);
		this.setInteractable(true, engine);
	}

	private void addPainter() {
		this.paint = new UiComponent(4);
		paint.setColor(ColorPalette.BUTTON_BASE);
		paint.setInteractable(true, engine);
		this.addComponent(paint);
		UiConstraints cons = new UiConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.BOTTOM, 0.25f));
		cons.setWidth(new RelativeScale(0.5f));
		cons.setHeight(new AspectRatio(1.0f));
		paint.setConstraints(cons);
		
		int icon = engine.getTextureLoader().loadTexture("/uis/brush");
		this.paintIcon = new UiComponent(4);
		paintIcon.setTexture(icon);
		UiConstraints iconCons = new UiConstraints();
		iconCons.setX(new AlignX(XAlign.CENTER));
		iconCons.setY(new AlignY(YAlign.MIDDLE));
		iconCons.setWidth(new RelativeScale(0.75f));
		iconCons.setHeight(new AspectRatio(1.0f));
		paintIcon.setConstraints(iconCons);
		paint.addComponent(paintIcon);
	}

	private void addPicker() {
		this.picker = new UiComponent(4);
		picker.setColor(ColorPalette.BUTTON_BASE);
		picker.setInteractable(true, engine);
		this.addComponent(picker);
		UiConstraints cons = new UiConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.TOP, 0.25f));
		cons.setWidth(new RelativeScale(0.5f));
		cons.setHeight(new AspectRatio(1.0f));
		picker.setConstraints(cons);
		
		int icon = engine.getTextureLoader().loadTexture("/uis/picker");
		this.pickerIcon = new UiComponent(4);
		pickerIcon.setTexture(icon);
		UiConstraints iconCons = new UiConstraints();
		iconCons.setX(new AlignX(XAlign.CENTER));
		iconCons.setY(new AlignY(YAlign.MIDDLE));
		iconCons.setWidth(new RelativeScale(0.75f));
		iconCons.setHeight(new AspectRatio(1.0f));
		pickerIcon.setConstraints(iconCons);
		picker.addComponent(pickerIcon);// TODO Auto-generated method stub
		
	}

	public Vector3f getPrimary() {
		return primary;
	}

	public void setPrimary(Vector3f primary) {
		this.primary = primary;
	}

	public Vector3f getSecondary() {
		return secondary;
	}

	public void setSecondary(Vector3f secondary) {
		this.secondary = secondary;
	}

	public boolean isOpen() {
		return isOpen;
	}

}
