package com.seaSaltedToaster.restaurantGame.menus.buildingSelector;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.VerticalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class ItemTooltip extends UiComponent {

	//Info
	private Text name, price, type;
	
	public ItemTooltip() {
		super(7);
		createPanel();
	}
	
	public void open(BuildingItem item) {
		//Add to item
		close();
		item.addComponent(this);
		
		//Set info
		this.name.setTextString(item.getBuilding().name);
		this.price.setTextString("Price : " + item.getBuilding().getPrice());
		this.type.setTextString("Type : " + item.getBuilding().type.name());
	}
	
	public void close() {
		if(this.getParentComponent() != null)
			this.getParentComponent().removeComponent(this);
	}

	private void createPanel() {
		this.setAlpha(1.0f);
		UiConstraints cons = this.getConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.TOP, -5.0f));
		cons.setHeight(new RelativeScale(2.5f));
		cons.setWidth(new AspectRatio(0.25f));
		cons.setLayout(new VerticalLayout(-0.0f, 0.025f));
		this.setColor(0.15f);
		
		this.name = new Text("item_name", 0.66f, 6);
		this.name.setColor(1.0f);
		UiConstraints textCons = name.getConstraints();
		textCons.setX(new AlignX(XAlign.CENTER));
		this.addComponent(name);
		
		this.price = new Text("item_price", 0.66f, 6);
		this.price.setColor(1.0f);
		UiConstraints textCons2 = price.getConstraints();
		textCons2.setX(new AlignX(XAlign.LEFT, 0.2f));
		this.addComponent(price);
		
		this.type = new Text("item_type", 0.66f, 6);
		this.type.setColor(1.0f);
		UiConstraints textCons3 = type.getConstraints();
		textCons3.setX(new AlignX(XAlign.LEFT, 0.2f));
		this.addComponent(type);
	}


}
