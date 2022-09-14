package com.seaSaltedToaster.restaurantGame.menus.buildingSelector;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingCategory;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;

public class BuildingItem extends UiComponent {

	//Details
	private int icon;
	private UiComponent iconComp;
	private BuildingMenu menu;
	
	//Objects
	private Building building;
	private BuildingCategory category;
	
	//Spin icon
	private int spinningIcon;
	
	public BuildingItem(Building building, BuildingMenu menu) {
		super(4);
		this.building = building;
		this.menu = menu;
		createPanel();
	}
	
	public BuildingItem(BuildingCategory category, BuildingMenu menu) {
		super(4);
		this.category = category;
		this.menu = menu;
		createPanel();
	}
	
	@Override
	public void onHover() {
		if(building != null) {
			menu.getTooltip().open(this);
		}
	}
	
	@Override
	public void whileHover() {
		if(building != null) {
			ModelComponent comp = (ModelComponent) building.getEntity().getComponent("Model");
			menu.getIconMaker().increaseYAngle(1.0f);
			this.spinningIcon = menu.getIconMaker().createIcon(comp.getMesh(), building.getIconZoom());
			if((Integer) spinningIcon != null)
				this.iconComp.setTexture(spinningIcon);
		}
	}
	
	@Override
	public void stopHover() {
		if(building != null) {
			ModelComponent compModel = (ModelComponent) building.getEntity().getComponent("Model");
			menu.getIconMaker().setYAngle(0.0f);
			icon = BuildingMenu.iconMaker.createIcon(compModel.getMesh(), building.getIconZoom());
		}
		this.iconComp.setTexture(icon);
		menu.getTooltip().close();
	}

	private void createPanel() {
		this.setAlpha(0.0f);
		UiConstraints cons = this.getConstraints();
		cons.setY(new AlignY(YAlign.MIDDLE));
		cons.setHeight(new RelativeScale(0.75f));
		cons.setWidth(new AspectRatio(0.33f));
		
		this.iconComp = new UiComponent(5);
		UiConstraints iconCons = iconComp.getConstraints();
		iconCons.setX(new AlignX(XAlign.CENTER));
		iconCons.setY(new AlignY(YAlign.TOP));
		iconCons.setHeight(new AspectRatio(0.95f));
		iconCons.setWidth(new RelativeScale(1.0f));
		this.addComponent(iconComp);
		
		menu.getIconMaker().setYAngle(0.0f);
		if(building != null) {
			ModelComponent compModel = (ModelComponent) building.getEntity().getComponent("Model");
			icon = BuildingMenu.iconMaker.createIcon(compModel.getMesh(), building.getIconZoom());
		}
		if(category != null) {
			icon = category.getIcon();
			this.setAlpha(1.0f);
		}
		this.iconComp.setTexture(icon);
		
		
	}
	
	@Override
	public void setColor(float color) {
		this.iconComp.setColor(color);
		this.color.set(color, color, color);;
	}

	public Building getBuilding() {
		return building;
	}

	public BuildingCategory getCategory() {
		return category;
	}

}
