package com.seaSaltedToaster.restaurantGame.menus.buildingSelector.subMenus;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class ValueDisplay extends UiComponent {

	private Text name, value;
	
	public ValueDisplay() {
		super(8);
		createPanel();
		addText();
	}
	
	public void setText(String name, String value) {
		this.name.setTextString(name);
		this.value.setTextString(value);
	}
	
	private void addText() {
		//Name of the value
		this.name = new Text("value_name", 0.75f, 7);
		this.name.setColor(1.0f);
		UiConstraints cons = this.name.getConstraints();
		cons.setX(new AlignX(XAlign.LEFT, 0.05f));
		cons.setY(new AlignY(YAlign.TOP, 0.4f));
		this.addComponent(name);
		
		//Name of the value
		this.value = new Text("value_text", 0.75f, 7);
		this.value.setColor(1.0f);
		UiConstraints cons2 = this.value.getConstraints();
		cons2.setX(new AlignX(XAlign.RIGHT, 0.05f));
		cons2.setY(new AlignY(YAlign.TOP, 0.4f));
		this.addComponent(value);
	}

	private void createPanel() {
		this.setAlpha(0.0f);
		UiConstraints cons = new UiConstraints();
		cons.setWidth(new RelativeScale(1.0f));
		cons.setHeight(new AspectRatio(0.1f));
		cons.setX(new AlignX(XAlign.CENTER));
		this.setConstraints(cons);
	}

}
