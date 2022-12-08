package com.seaSaltedToaster.restaurantGame.menus.buildingSelector;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.colorPicker.ColorPicker;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus.ColorButton;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus.ColorDisplay;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus.DeleteButton;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus.InfoButton;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus.ValueDisplay;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
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
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class BuildingViewer extends UiComponent {
	
	//Current
	private Entity currentObject;
	
	//Title bar
	private Text title;
	private UiComponent titleBar;
	
	//Bar buttons
	private InfoButton infoButton;
	private ColorButton colorButton;
	private DeleteButton deleteButton;
	
	//Info panel
	private UiComponent infoPanel;
	private ValueDisplay name, cost;
	
	//Color change panel
	private UiComponent colorPanel;
	private ColorDisplay primary, secondary;
	private ColorPicker picker;
	
	//Animation for pop up
	private SmoothFloat yValue; 
	private float closed = -1.5f, open = 0;

	public BuildingViewer(Engine engine) {
		super(9);
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
	}
	
	@Override
	public void onHover() {
		MainApp.menuFocused = true;
	}
	
	
	@Override
	public void stopHover() {
		MainApp.menuFocused = false;
	}
	
	@Override
	public void onClickOff() {
		if(isHovering || this.childHovering()) return;
		
		if(this.getPosition().y >= -1.0f) {
			this.open(null);
		}
	}

	public void open(Entity selected) {
		//If null, close
		if(selected == null) {
			this.yValue.setTarget(closed);
			currentObject = null;
			this.picker.setActive(false);
			return;
		}
		//If current, close
		if(selected == currentObject) {
			this.yValue.setTarget(closed);
			currentObject = null;
			this.picker.setActive(false);
			return;
		}
		//If new, change
		if(selected != currentObject) {
			this.open(this.currentObject);
			
			this.yValue.setTarget(open);
			currentObject = selected;
			if(selected != null && currentObject != null)
				addDetails(selected);
			return;
		}
	}
	
	private void addDetails(Entity currentObject) {
		BuildingId id = (BuildingId) currentObject.getComponent("BuildingId");
		this.title.setTextString(id.getType().name);
		
		this.name.setText("Name", id.getType().name);
		this.cost.setText("Cost", "$" + id.getType().getPrice());
		
		this.primary.setColor(id.getPrimary());
		this.secondary.setColor(id.getSecondary());
	}
	
	public void openInfoPanel() {
		this.colorPanel.setActive(false);
		this.infoPanel.setActive(true);
		this.picker.setActive(false);
	}
	
	public void openColorPanel() {
		this.infoPanel.setActive(false);
		this.colorPanel.setActive(true);
	}

	private void createPanel(Engine engine) {
		//Color
		this.setColor(0.2f);
		
		//Size (and the shape? foo fighters reference?!?!)
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(0.2f));
		cons.setHeight(new AspectRatio(0.6f));
		cons.setX(new AlignX(XAlign.RIGHT));
		cons.setLayout(new VerticalLayout(-0.0f, 0.033f));
		
		//Title bar
		addTitleBar();
		
		//Info buttons
		this.infoButton = new InfoButton(this);
		this.infoButton.setInteractable(true, engine);
		this.titleBar.addComponent(infoButton);
		
		this.colorButton = new ColorButton(this);
		this.colorButton.setInteractable(true, engine);
		this.titleBar.addComponent(colorButton);
		
		this.deleteButton = new DeleteButton(this);
		this.deleteButton.setInteractable(true, engine);
		this.titleBar.addComponent(deleteButton);
		
		//Info panel
		this.infoPanel = new UiComponent(7);
		this.infoPanel.setAlpha(0.0f);
		this.infoPanel.setConstraints(getPanelConstraints());
		this.addComponent(infoPanel);
		
		this.name = new ValueDisplay();
		this.infoPanel.addComponent(name);
		
		this.cost = new ValueDisplay();
		this.infoPanel.addComponent(cost);
		
		//Color panel
		this.colorPanel = new UiComponent(7);
		this.colorPanel.setAlpha(0.0f);
		this.colorPanel.setActive(false);
		this.colorPanel.setConstraints(getPanelConstraints());
		this.addComponent(colorPanel);
		
		this.primary = new ColorDisplay("Primary", this);
		this.colorPanel.addComponent(primary);
		
		this.secondary = new ColorDisplay("Secondary", this);
		this.colorPanel.addComponent(secondary);
		
		this.picker = new ColorPicker();
		this.picker.setInteractable(true, engine);
		this.addComponent(picker);
	}

	private UiConstraints getPanelConstraints() {
		UiConstraints cons = new UiConstraints();
		cons.setWidth(new RelativeScale(1.0f));
		cons.setHeight(new RelativeScale(0.75f));
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.BOTTOM));
		cons.setLayout(new VerticalLayout(-0.085f, 0.0f));
		this.addComponent(titleBar);		
		return cons;
	}

	private void addTitleBar() {
		this.titleBar = new UiComponent(7);
		this.titleBar.setColor(0.15f);
		UiConstraints cons = this.titleBar.getConstraints();
		cons.setWidth(new RelativeScale(1.0f));
		cons.setHeight(new AspectRatio(0.15f));
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.TOP));
		cons.setLayout(new HorizontalLayout(1.125f, 0.066f));
		this.addComponent(titleBar);
		
		this.title = new Text("object_name", 1.0f, 7);
		this.title.setColor(1.0f);
		UiConstraints cons2 = this.title.getConstraints();
		cons2.setX(new AlignX(XAlign.LEFT, 0.125f));
		cons2.setY(new AlignY(YAlign.TOP, 0.4f));
		this.titleBar.addComponent(title);
	}

	public ColorPicker getPicker() {
		return picker;
	}

	public Entity getCurrentObject() {
		return currentObject;
	}

}
