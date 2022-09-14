package com.seaSaltedToaster.restaurantGame.menus.buildingSelector;

import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.menus.iconMaker.IconMaker;
import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.VerticalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class BuildingViewer extends UiComponent {
	
	//Current
	private Entity currentObject;
	
	//Info
	private Text title;
	private ColorPicker primary;
	
	//Display
	private IconMaker iconMaker;
	private UiComponent iconBacker, iconDisplay;
	private int icon;
	
	//Animation
	private SmoothFloat yValue; 
	private float closed = -1.5f, open = 0;

	public BuildingViewer(Engine engine) {
		super(6);
		createPanel(engine);
		this.yValue = new SmoothFloat(closed);
		this.setInteractable(true, engine);
	}
	
	@Override
	public void updateSelf() {
		//Animation
		this.open = -1.0f + (this.getScale().y);
		this.yValue.update(Window.DeltaTime);
		this.getPosition().y = yValue.getValue();
		
		this.iconDisplay.setTexture(1);
		if(currentObject != null && this.isActive() && this.getPosition().y > closed) {
			ModelComponent comp = (ModelComponent) currentObject.getComponent("Model");
			this.iconMaker.increaseYAngle((float) (25f * Window.DeltaTime));
			this.icon = iconMaker.createIcon(comp.getMesh(), 1);
			iconDisplay.setTexture(icon);
		}
	}
	
	@Override
	public void onClickOff() {
		if(this.getPosition().y == open)
			this.open(null);
	}

	public void open(Entity selected) {
		//If null, close
		if(selected == null) {
			this.yValue.setTarget(closed);
			currentObject = null;
			return;
		}
		//If current, close
		if(selected == currentObject) {
			this.yValue.setTarget(closed);
			currentObject = null;
			return;
		}
		//If new, change
		if(selected != currentObject) {
			this.yValue.setTarget(open);
			currentObject = selected;
			if(selected != null && currentObject != null)
				addDetails(selected);
			return;
		}
	}
	
	private void addDetails(Entity currentObject) {
		//Set icon
		ModelComponent comp = (ModelComponent) currentObject.getComponent("Model");
		this.iconMaker.setYAngle(0.0f);
		this.icon = iconMaker.createIcon(comp.getMesh(), 1);
		BuildingId id = (BuildingId) currentObject.getComponent("BuildingId");
		this.title.setTextString(id.getType().name);
	}

	private void createPanel(Engine engine) {
		//Color
		float panelColor = ColorPalette.MAIN_SHADE;
		this.setColor(panelColor);
		
		//Size (and the shape? foo fighters reference?!?!)
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(0.15f));
		cons.setHeight(new AspectRatio(1.5f));
		cons.setX(new AlignX(XAlign.RIGHT));
		cons.setLayout(new VerticalLayout(-0.0f, 0.033f));
		
		//Title
		this.title = new Text("item_name", 1.0f, 6);
		this.title.setColor(1.0f);
		UiConstraints textCons = title.getConstraints();
		textCons.setX(new AlignX(XAlign.CENTER));
		this.addComponent(title);
				
		//Colors
		this.primary = new ColorPicker("Primary Color");
		this.addComponent(primary);
		
		//Icon builder
		addIcon(engine);
	}

	private void addIcon(Engine engine) {
		this.iconMaker = new IconMaker(engine);
		this.iconMaker.setClear(new Vector3f(0.1f, 0.15f, 0.2f));
		
		this.iconBacker = new UiComponent(6);
		this.iconBacker.setColor(0.2f);
		UiConstraints cons = this.iconBacker.getConstraints();
		cons.setWidth(new RelativeScale(0.75f));
		cons.setHeight(new AspectRatio(1.0f));
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.BOTTOM, 0.075f));
		this.addComponent(iconBacker);
		
		this.iconDisplay = new UiComponent(6);
		UiConstraints cons2 = this.iconDisplay.getConstraints();
		cons2.setWidth(new RelativeScale(1.0f));
		cons2.setHeight(new RelativeScale(1.0f));
		cons2.setX(new AlignX(XAlign.CENTER));
		cons2.setY(new AlignY(YAlign.MIDDLE));
		this.iconBacker.addComponent(iconDisplay);
	}

}
