package com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.BuildingId;
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

public class DeleteButton extends UiComponent {

	//Info
	private UiComponent icon;
	private int iconTex;
	
	//Deletable
	private BuildingViewer viewer;
	
	public DeleteButton(BuildingViewer viewer) {
		super(7);
		createPanel();
		this.viewer = viewer;
	}
	
	@Override
	public void onHover() {
		this.setColor(ColorPalette.BUTTON_HIGHLIGHT);
	}
	
	@Override
	public void stopHover() {
		this.setColor(ColorPalette.BUTTON_BASE);
	}
	
	@Override
	public void onClick() {
		BuildingId id = (BuildingId) viewer.getCurrentObject().getComponent("BuildingId");
		id.getLayer().remove(viewer.getCurrentObject());
		viewer.open(null);
	}

	private void createPanel() {
		UiConstraints cons = this.getConstraints();
		cons.setHeight(new AspectRatio(1.0f));
		cons.setWidth(new RelativeScale(0.1f));
		cons.setY(new AlignY(YAlign.MIDDLE));
		this.setColor(0.2f);
		
		Engine engine = MainApp.restaurant.engine;
		this.iconTex = engine.getTextureLoader().loadTexture("/uis/delete");
		
		this.icon = new UiComponent(8);;
		UiConstraints cons2 = this.icon.getConstraints();
		cons2.setWidth(new RelativeScale(0.9f));
		cons2.setHeight(new AspectRatio(1.0f));
		cons2.setX(new AlignX(XAlign.CENTER));
		cons2.setY(new AlignY(YAlign.MIDDLE));
		this.icon.setTexture(iconTex);
		this.addComponent(icon);
	}

}
