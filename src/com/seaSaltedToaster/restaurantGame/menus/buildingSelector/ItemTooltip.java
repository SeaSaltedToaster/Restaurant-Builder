package com.seaSaltedToaster.restaurantGame.menus.buildingSelector;

import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
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
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class ItemTooltip extends UiComponent {

	//Info
	private Text name, price, type;
	private SmoothFloat mScale, mX;
	
	public ItemTooltip(BuildingMenu buildingMenu, Engine engine) {
		super(9);
		createPanel(engine);
		close();
		buildingMenu.addComponent(this);
	}
	
	@Override
	public void updateSelf() {
		this.mScale.update(Window.DeltaTime);
		this.setScaleMultiplier(mScale.getValue());
		
		this.mX.update(Window.DeltaTime);
		this.getPosition().setX(mX.getValue());
	}
	
	public void open(BuildingItem item) {
		//Add to item
		close();
		this.mScale.setTarget(1.0f);
		
		//Set info
		this.name.setTextString(item.getBuilding().name);
		
		this.price.setTextString("Price : " + item.getBuilding().getPrice());
		LanguageManager.addText("buy_price", price, new Object[] {new String(""+item.getBuilding().getPrice())});
		
		this.type.setTextString("Type : " + item.getBuilding().type.name());
		
		//Movement
		this.getPosition().setY(item.getPosition().y + 0.15f);
		this.mX.setTarget(item.getPosition().x);
	}
	
	public void close() {
		this.mScale.setTarget(0.0f);
	}

	private void createPanel(Engine engine) {
		this.setAlpha(1.0f);
		UiConstraints cons = this.getConstraints();
		cons.setHeight(new RelativeScale(1.25f));
		cons.setWidth(new AspectRatio(1.0f));
		cons.applyLayout = false;
		cons.setLayout(new VerticalLayout(0.0125f, 0.0125f));
		this.setColor(0.15f);
		
		int tex = engine.getTextureLoader().loadTexture("/uis/slotBack");
		this.setTexture(tex);
		
		float textSize = 0.4f;
		this.name = new Text("item_name", textSize, 6);
		this.name.setColor(1.0f);
		UiConstraints textCons = name.getConstraints();
		textCons.setX(new AlignX(XAlign.CENTER));
		this.addComponent(name);
		
		this.price = new Text("item_price", textSize, 6);
		this.price.setColor(1.0f);
		UiConstraints textCons2 = price.getConstraints();
		textCons2.setX(new AlignX(XAlign.LEFT, 0.2f));
		this.addComponent(price);
		
		this.type = new Text("item_type", textSize, 6);
		this.type.setColor(1.0f);
		UiConstraints textCons3 = type.getConstraints();
		textCons3.setX(new AlignX(XAlign.LEFT, 0.2f));
		this.addComponent(type);
		
		this.mScale = new SmoothFloat(0.0f);
		this.mScale.setAmountPer(0.15f);
		
		this.mX = new SmoothFloat(0.0f);
		this.mX.setAmountPer(0.25f);
	}

}
