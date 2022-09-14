package com.seaSaltedToaster.restaurantGame.menus.buildingSelector;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class ColorPicker extends UiComponent {

	//Info
	private Text name;
	
	public ColorPicker(String colorName) {
		super(7);
		createPanel(colorName);
	}

	private void createPanel(String colorName) {
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(0.75f));
		cons.setHeight(new AspectRatio(0.15f));
		cons.setX(new AlignX(XAlign.CENTER));
		this.setColor(0.2f);
		
		this.name = new Text(colorName, 0.5f, 8);
		UiConstraints cons2 = this.name.getConstraints();
		cons2.setX(new AlignX(XAlign.LEFT, 0.05f));
		cons2.setY(new AlignY(YAlign.MIDDLE));
		this.name.setColor(1.0f);
		this.addComponent(name);
	}

}
