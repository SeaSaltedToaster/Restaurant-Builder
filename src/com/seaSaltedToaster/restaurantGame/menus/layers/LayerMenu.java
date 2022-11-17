 package com.seaSaltedToaster.restaurantGame.menus.layers;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class LayerMenu extends UiComponent {
	
	//Objects
	private BuildingManager manager;
	
	//Uis
	private IncrementButton increase, decrease;
	private Text number, floor;

	public LayerMenu(BuildingManager manager, Engine engine) {
		super(3);
		this.manager = manager;
		createMenu();
		addButtons(engine);
		this.setInteractable(true, engine);
	}
	
	public void increment(int increment) {
		int currentLayer = manager.getCurLayer();
		int nextLayer = currentLayer + increment;
		
		if(nextLayer >= manager.getLayers().size())
			return;
		if(nextLayer < 0)
			return;
		manager.setLayer(nextLayer);
		number.setTextString((nextLayer+1) + "");
	}

	private void addButtons(Engine engine) {
		//Increase
		this.increase = new IncrementButton(1, this, engine);
		this.addComponent(increase);
		
		//Decrease
		this.decrease = new IncrementButton(-1, this, engine);
		this.addComponent(decrease);
	}
	
	@Override
	public void onHover() {
		MainApp.menuFocused = true;
	}
	
	@Override
	public void stopHover() {
		MainApp.menuFocused = false;
	}

	private void createMenu() {
		UiConstraints cons = new UiConstraints();
		cons.setX(new AlignX(XAlign.LEFT, 0.0125f));
		cons.setY(new AlignY(YAlign.BOTTOM, 0.05f));
		this.setConstraints(cons);
		this.setScale(0.075f, 0.09f);
		this.setColor(ColorPalette.MAIN_LIGHT);
		
		float textShift = 0.025f;
		this.number = new Text("1", 1.66f, 3);
		this.number.setColor(1.0f);
		UiConstraints numCons = this.number.getConstraints();
		numCons.setX(new AlignX(XAlign.CENTER, textShift));
		numCons.setY(new AlignY(YAlign.BOTTOM, 1.25f));
		this.addComponent(number);
		
		this.floor = new Text("Floor", 0.75f, 3);
		this.floor.setColor(1.0f);
		UiConstraints floorCons = this.floor.getConstraints();
		floorCons.setX(new AlignX(XAlign.CENTER, textShift));
		floorCons.setY(new AlignY(YAlign.TOP, 0.3f));
		this.addComponent(floor);
	}

}
