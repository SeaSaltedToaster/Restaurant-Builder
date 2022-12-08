package com.seaSaltedToaster.restaurantGame.menus.buildingSelector.colorPicker;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus.ColorDisplay;
import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.HorizontalLayout;
import com.seaSaltedToaster.simpleEngine.uis.layouts.VerticalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ColorPicker extends UiComponent {
		
	//Title bar
	private Text title;
	private UiComponent titleBar;
	
	//Color list
	private int colorCount = 10, shadeCount = 8, recentColorCount = 7;
	
	//Recent colors list
	private List<Vector3f> recentColors = new ArrayList<Vector3f>();
	private List<ColorOption> recentColorUis = new ArrayList<ColorOption>();
	private UiComponent recentColorHolder;
	
	//Color edit
	private ColorDisplay editing;
	
	public ColorPicker() {
		super(9);
		createPanel();
		addColors();
		fillRecentColors();
		
		Engine engine = MainApp.restaurant.engine;
		this.setInteractable(true, engine);
		this.setActive(false);
	}
	
	@Override
	public void onHover() {
		MainApp.menuFocused = true;
	}
	
	
	@Override
	public void stopHover() {
		MainApp.menuFocused = false;
	}
	
	public void selectColor(Vector3f color) {
		//Set color
		ColorDisplay display = editing;
		display.setColor(color);
		String colorType = display.getNameString();
		
		Entity obj = display.getViewer().getCurrentObject();
		BuildingId id = (BuildingId) obj.getComponent("BuildingId");
		if(colorType.contains("Primary"))
			id.setPrimary(color);
		else if(colorType.contains("Secondary"))
			id.setSecondary(color);
		
		//Add to the list
		if(recentColors.contains(color)) {
			recentColors.remove(color);
		}
		recentColors.add(color);
		
		//Cap the size
		while(recentColors.size() > recentColorCount) {
			recentColors.remove(0);
		}
		
		//Set new colors
		for(int i = recentColorCount-1; i > 0; i--) {
			ColorOption option = this.recentColorUis.get(i);
			option.setOption(this.recentColors.get(i));
		}
	}

	private void fillRecentColors() {
		//Create list
		while(recentColors.size() < recentColorCount)  {
			recentColors.add(new Vector3f(0.0f));
		}
		
		//Color backing
		this.recentColorHolder = new UiComponent(9);
		this.recentColorHolder.setColor(ColorPalette.MAIN_SHADE);
		UiConstraints cons = recentColorHolder.getConstraints();
		cons.setWidth(new RelativeScale(1.0f));
		cons.setHeight(new AspectRatio(0.15f));
		cons.setX(new AlignX(XAlign.CENTER));	
		cons.setY(new AlignY(YAlign.BOTTOM));	
		cons.setLayout(new HorizontalLayout(0.125f, shadeCount * 0.0f));
		this.addComponent(recentColorHolder);
		
		//All colors
		Engine engine = MainApp.restaurant.engine;
		for(int i = 0; i < 7; i++) {
			ColorOption option = new ColorOption(0.1f, recentColors.get(i), this);
			option.setInteractable(true, engine);
			this.recentColorHolder.addComponent(option);
			this.recentColorUis.add(option);
		}
	}

	private void addColors() {
		//Objects necessary
		Engine engine = MainApp.restaurant.engine;
		
		//Left side
		for(int y = 0; y < colorCount; y+=2) {
			UiComponent rowLine = getRowLine(y, XAlign.LEFT);
			this.addComponent(rowLine);
			
			Vector3f color1 = getRowColor(y);
			for(int x = 0; x < shadeCount; x++) {
				float shade = (1.0f / shadeCount) * (x + 1);
				ColorOption option = new ColorOption(0.125f, color1.copy().scale(shade), this);
				option.setInteractable(true, engine);
				rowLine.addComponent(option);
			}
			
			Vector3f color2 = getRowColor(y+1);
			for(int x = 0; x < shadeCount; x++) {
				float shade = (1.0f / shadeCount) * (x + 1);
				ColorOption option = new ColorOption(0.125f, color2.copy().scale(shade), this);
				option.setInteractable(true, engine);
				rowLine.addComponent(option);
			}
		}
	}

	private UiComponent getRowLine(int y, XAlign side) {
		UiComponent line = new UiComponent(9);
		UiConstraints cons = line.getConstraints();
		cons.setWidth(new RelativeScale(0.5f));
		cons.setHeight(new AspectRatio(0.30f));
		cons.setX(new AlignX(side));	
		cons.setLayout(new HorizontalLayout(-0.125f, 0.0f));
		line.setAlpha(0.0f);
		return line;
	}

	private Vector3f getRowColor(int y) {
		switch(y) {
		case 0 :
			return new Vector3f(1,0,0); //red
		case 1 :
			return new Vector3f(0,1,0); //green
		case 2 :
			return new Vector3f(0,0,1); //blue
		case 3 : 
			return new Vector3f(1,0,1);
		case 4 :
			return new Vector3f(1,1,0);
		case 5 :
			return new Vector3f(0,1,1);
		case 6 :
			return new Vector3f(0.78,0.72,0.43); //creme
		case 7 :
			return new Vector3f(1,1,1);
		case 8 :
			return new Vector3f(0.7, 0.3, 0.25);
		case 9 :
			return new Vector3f(1, 0.5, 0);
		}
		return new Vector3f(1);
	}

	private void createPanel() {
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new AspectRatio(1.0f));
		cons.setHeight(new RelativeScale(1.0f));
		cons.setX(new AlignX(XAlign.LEFT, -1.0f - 0.25f));	
		cons.setY(new AlignY(YAlign.MIDDLE, 0.0f));		
		cons.setLayout(new VerticalLayout(-0.005f, -0.033f));
		this.setColor(ColorPalette.BUTTON_BASE);
		
		this.titleBar = new UiComponent(7);
		this.titleBar.setColor(0.15f);
		UiConstraints cons2 = this.titleBar.getConstraints();
		cons2.setWidth(new RelativeScale(1.0f));
		cons2.setHeight(new AspectRatio(0.2f));
		cons2.setX(new AlignX(XAlign.CENTER));
		cons2.setY(new AlignY(YAlign.TOP));
		cons2.setLayout(new HorizontalLayout(1.125f, 0.066f));
		this.addComponent(titleBar);
		
		this.title = new Text("Colors", 1.0f, 7);
		this.title.setColor(1.0f);
		UiConstraints cons3 = this.title.getConstraints();
		cons3.setX(new AlignX(XAlign.LEFT, 0.125f));
		cons3.setY(new AlignY(YAlign.TOP, 0.25f));
		this.titleBar.addComponent(title);
	}

	public ColorDisplay getEditing() {
		return editing;
	}

	public void setPicking(ColorDisplay colorDisplay) {
		this.editing = colorDisplay;
	}

}
