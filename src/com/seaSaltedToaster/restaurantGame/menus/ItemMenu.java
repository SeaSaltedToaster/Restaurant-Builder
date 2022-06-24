package com.seaSaltedToaster.restaurantGame.menus;

import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingList;
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
import com.seaSaltedToaster.simpleEngine.uis.layouts.HorizontalLayout;

public class ItemMenu extends UiComponent {

	//Building
	private BuildingManager manager;
	
	//Objects
	private UiComponent[] buttons;
	
	public ItemMenu(BuildingManager manager, Engine engine) {
		super(3);
		this.manager = manager;
		createPanel();
		createButtons(engine);
		this.setInteractable(true, engine);
	}
	
	@Override
	public void onClick() {
		int index = 0;
		for(UiComponent button : buttons) {
			if(button == null) continue;
			if(button.isHovering()) {
				Building build = BuildingList.getBuilding(index);
				manager.setBuilding(true);
				manager.setPlacement(build);
				return;
			}
			index++;
		}
	}

	private void createButtons(Engine engine) {
		int count = BuildingList.getBuildings().size();
		this.buttons = new UiComponent[16];
		for(int i = 0; i < count; i++) {
			UiComponent comp = new UiComponent(4);
			comp.setInteractable(true, engine);
			UiConstraints cons = comp.getConstraints();
			cons.setY(new AlignY(VerticalAlignment.MIDDLE));
			cons.setHeight(new RelativeScale(0.75f));
			cons.setWidth(new AspectRatio(0.33f));
			buttons[i] = comp;
			this.addComponent(comp);
		}
	}

	private void createPanel() {
		UiConstraints cons = new UiConstraints();
		cons.setX(new AlignX(HorizontalAlignment.CENTER));
		cons.setY(new AlignY(VerticalAlignment.BOTTOM, 0.05f));
		cons.setLayout(new HorizontalLayout(0.05f, 0.05f));
		this.setConstraints(cons);
		this.setScale(0.6f, 0.066f);
		this.setColor(0.5f);
	}

}
