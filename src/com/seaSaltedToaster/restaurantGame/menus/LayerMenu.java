package com.seaSaltedToaster.restaurantGame.menus;

import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.HorizontalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.VerticalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;

public class LayerMenu extends UiComponent {
	
	//Objects
	private BuildingManager manager;
	private UiComponent increase, decrease;

	public LayerMenu(BuildingManager manager, Engine engine) {
		super(3);
		this.manager = manager;
		createMenu();
		addButtons(engine);
		this.setInteractable(true, engine);
	}
	
	@Override
	public void onClick() {
		int currentLayer = manager.getCurLayer();
		if(increase.isHovering()) {
			manager.setLayer(currentLayer+1);
		}
		if(decrease.isHovering()) {
			manager.setLayer(currentLayer-1);
		}
	}

	private void addButtons(Engine engine) {
		//Increase
		this.increase = new UiComponent(4);
		increase.setInteractable(true, engine);
		UiConstraints cons = increase.getConstraints();
		cons.setX(new AlignX(HorizontalAlignment.CENTER));
		cons.setY(new AlignY(VerticalAlignment.TOP, 0.15f));
		cons.setHeight(new AspectRatio(1.0f));
		cons.setWidth(new RelativeScale(0.66f));
		this.addComponent(increase);
		
		//Decrease
		this.decrease = new UiComponent(4);
		decrease.setInteractable(true, engine);
		UiConstraints cons2 = decrease.getConstraints();
		cons2.setX(new AlignX(HorizontalAlignment.CENTER));
		cons2.setY(new AlignY(VerticalAlignment.BOTTOM, 0.15f));
		cons2.setHeight(new AspectRatio(1.0f));
		cons2.setWidth(new RelativeScale(0.66f));
		this.addComponent(decrease);
	}

	private void createMenu() {
		UiConstraints cons = new UiConstraints();
		cons.setX(new AlignX(HorizontalAlignment.LEFT, 0.05f));
		cons.setY(new AlignY(VerticalAlignment.BOTTOM, 0.05f));
		this.setConstraints(cons);
		this.setScale(0.05f, 0.175f);
		this.setColor(0.5f);
	}

}
