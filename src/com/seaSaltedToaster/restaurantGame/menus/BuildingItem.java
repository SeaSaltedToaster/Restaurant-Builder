package com.seaSaltedToaster.restaurantGame.menus;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingCategory;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.HorizontalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.VerticalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;

public class BuildingItem extends UiComponent {

	//Details
	private int icon;
	private UiComponent iconComp;
	
	//Objects
	private Building building;
	private BuildingCategory category;
	
	public BuildingItem(Building building) {
		super(4);
		this.building = building;
		createPanel();
	}
	
	public BuildingItem(BuildingCategory category) {
		super(4);
		this.category = category;
		createPanel();
	}

	private void createPanel() {
		this.setAlpha(0.0f);
		UiConstraints cons = this.getConstraints();
		cons.setY(new AlignY(VerticalAlignment.MIDDLE));
		cons.setHeight(new RelativeScale(0.75f));
		cons.setWidth(new AspectRatio(0.33f));
		
		this.iconComp = new UiComponent(5);
		UiConstraints iconCons = iconComp.getConstraints();
		iconCons.setX(new AlignX(HorizontalAlignment.CENTER));
		iconCons.setY(new AlignY(VerticalAlignment.TOP));
		iconCons.setHeight(new AspectRatio(0.95f));
		iconCons.setWidth(new RelativeScale(1.0f));
		this.addComponent(iconComp);
		
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

}
