package com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.create;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class DropdownBox extends UiComponent {

	private float width, heightR1, heightR2, space;
	private UiComponent pane, subBox;
	private Text display;
	
	private List<DropdownOption> options;
	private DropdownOption selected = null;
	
	private SmoothFloat scale, optScale;
	private boolean isDropped = false;
	
	public DropdownBox(float width, float heightR1, float space) {
		super(1);
		this.width = width;
		this.heightR1 = heightR1;
		this.space = space;
		this.scale = new SmoothFloat(0.0f);
		this.optScale = new SmoothFloat(0.0f);
		this.options = new ArrayList<DropdownOption>();
		makePanel();
		this.setInteractable(true, MainApp.restaurant.engine);
	}
	
	@Override
	public void onClick() {
		show(!isDropped);
	}
	
	private void show(boolean b) {
		if(b) 
		{
			this.scale.setTarget(heightR2);
			this.optScale.setTarget(1.0f);
			this.isDropped = true;
		}
		else
		{
			this.scale.setTarget(0.0f);
			this.optScale.setTarget(0.0f);
			this.isDropped = false;
		}
	}

	@Override
	public void onClickOff() {
		if(!isHovering() && !pane.isHovering())
			this.show(false);
	}
	
	@Override
	public void updateSelf() {
		//Update the pane scale
		this.heightR2 = options.size() * 0.5f * getScale().y;
		this.scale.update(Window.DeltaTime);
		this.pane.getScale().setY(scale.getValue());
		
		//Update the pane position
		this.pane.getPosition().setY(getPosition().y - getScale().y - pane.getScale().y);
		
		//Text scale
		this.optScale.update(Window.DeltaTime);
		for(DropdownOption text : options)
		{
			text.setScaleMultiplier(optScale.getValue());
		}
	}
	
	public void addOption(String val) {
		float stage = (0.05f) + (options.size() * 0.5f);
		DropdownOption text = new DropdownOption(val, stage, this);
		
		this.options.add(text);
		this.pane.addComponent(text);
	}
	
	public void addOptions(String... optionList) {
		for(String str : optionList)
			this.addOption(str);
		this.setSelected(options.get(0));
	}
	
	private void makePanel() {
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(width));
		cons.setHeight(new AspectRatio(heightR1));
		cons.setX(new AlignX(XAlign.CENTER));
		this.setConstraints(cons);
		this.setAlpha(0.5f);
		
		this.display = new Text("Grassy", 1.0f, 3);
		this.display.setColor(0.0f);
		UiConstraints textCons = display.getConstraints();
		textCons.setX(new AlignX(XAlign.CENTER));
		textCons.setY(new AlignY(YAlign.TOP, 0.4f));
		this.addComponent(display);
		
		this.subBox = new UiComponent(3);
		this.subBox.setAlpha(0.33f);
		UiConstraints backerCons = subBox.getConstraints();
		backerCons.setWidth(new RelativeScale(0.966f));
		backerCons.setHeight(new RelativeScale(0.9f));
		backerCons.setX(new AlignX(XAlign.CENTER));
		backerCons.setY(new AlignY(YAlign.MIDDLE));
		this.addComponent(subBox);
		
		this.pane = new UiComponent(1);
		this.pane.setAlpha(0.33f);
		UiConstraints cons2 = this.pane.getConstraints();
		cons2.setWidth(new RelativeScale(0.95f));
		cons2.setX(new AlignX(XAlign.CENTER));
		this.pane.setConstraints(cons2);
		this.pane.setColor(0.0f);
		this.addComponent(pane);
		this.pane.setInteractable(true, MainApp.restaurant.engine);
	}

	public DropdownOption getSelected() {
		return selected;
	}

	public void setSelected(DropdownOption selected) {
		this.selected = selected;
		this.display.setTextString(selected.getDisplay().getTextString());
	}

}
