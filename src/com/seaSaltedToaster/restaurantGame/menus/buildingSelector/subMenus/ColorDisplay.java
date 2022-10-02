package com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.BuildingViewer;
import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class ColorDisplay extends UiComponent {

	//Name of color
	private Text colorName;
	private String nameString;
	
	//Color box
	private UiComponent back, box;
	private BuildingViewer viewer;
	
	public ColorDisplay(String nameString, BuildingViewer viewer) {
		super(8);
		createPanel();
		addInfo(nameString);
		this.nameString = nameString;
		this.viewer = viewer;
		
		Engine engine = MainApp.restaurant.engine;
		this.setInteractable(true, engine);
		box.setInteractable(true, engine);
	}
	
	@Override
	public void onClick() {
		if(box.isHovering() || box.childHovering()) {
			this.viewer.getPicker().setPicking(this);
			this.viewer.getPicker().setActive(true);
		}
	}
	
	public void setColor(Vector3f color) {
		this.box.setColor(color);
	}
	
	private void addInfo(String nameString) {
		//Name of the color
		this.colorName = new Text(nameString, 0.75f, 7);
		this.colorName.setColor(1.0f);
		UiConstraints cons = this.colorName.getConstraints();
		cons.setX(new AlignX(XAlign.LEFT, 0.05f));
		cons.setY(new AlignY(YAlign.TOP, 0.4f));
		this.addComponent(colorName);
		
		//Color box
		this.back = new UiComponent(8);
		this.back.setColor(ColorPalette.MAIN_SHADE);
		UiConstraints backCons = new UiConstraints();
		backCons.setWidth(new RelativeScale(0.075f));
		backCons.setHeight(new AspectRatio(1.0f));
		backCons.setX(new AlignX(XAlign.RIGHT, 0.05f));
		backCons.setY(new AlignY(YAlign.BOTTOM));
		this.back.setConstraints(backCons);
		this.addComponent(back);
		
		this.box = new UiComponent(8);
		UiConstraints boxCons = new UiConstraints();
		boxCons.setWidth(new RelativeScale(0.75f));
		boxCons.setHeight(new AspectRatio(1.0f));
		boxCons.setX(new AlignX(XAlign.CENTER));
		boxCons.setY(new AlignY(YAlign.MIDDLE));
		this.box.setConstraints(boxCons);
		this.back.addComponent(box);
	}

	private void createPanel() {
		this.setAlpha(0.0f);
		UiConstraints cons = new UiConstraints();
		cons.setWidth(new RelativeScale(1.0f));
		cons.setHeight(new AspectRatio(0.1f));
		cons.setX(new AlignX(XAlign.CENTER));
		this.setConstraints(cons);
	}

	public String getNameString() {
		return nameString;
	}

	public Text getColorName() {
		return colorName;
	}

	public BuildingViewer getViewer() {
		return viewer;
	}

}
