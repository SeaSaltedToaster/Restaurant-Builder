package com.seaSaltedToaster.restaurantGame.menus;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.BuildingMenu;
import com.seaSaltedToaster.restaurantGame.menus.employees.EmployeeMenu;
import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.VerticalLayout;

public class GeneralMenu extends UiComponent implements KeyListener {

	//Other menus
	private BuildingMenu building;
	private EmployeeMenu employee;
	private PaintMenu paint;
	private DeleteTool delete;
	
	//Objects
	private int buttonCount = 6;
	private int[] buttonIcons;
	private UiComponent[] buttons;
	
	public GeneralMenu(Engine engine) {
		super(1);
		createBacking();
		loadTextures(engine);
		addButtons(engine);
		this.setInteractable(true, engine);
		engine.getKeyboard().addKeyListener(this);
	}
	
	@Override
	public void updateSelf() {
		delete.update();
	}
	
	@Override
	public void notifyButton(KeyEventData eventData) {
		if(eventData.getAction() != GLFW.GLFW_PRESS) return;
		if(eventData.getKey() == GLFW.GLFW_KEY_ESCAPE) {
			closeOthers();
		}
	}
		
	@Override
	public void onClick() {
		//General
		if(buttons[0].isHovering()) {
			closeOthers();
		}
		//Building
		if(buttons[1].isHovering()) {
			closeOthers();
			building.show();
		}
		//Employees
		if(buttons[2].isHovering()) {
			closeOthers();
			employee.show();
		}
		//Paint
		if(buttons[3].isHovering()) {
			closeOthers();
			paint.show();
			
		}
		//Delete
		if(buttons[4].isHovering()) {
			closeOthers();
			delete.show(buttons[4]);
		}
		//Settings
		if(buttons[5].isHovering()) {
			closeOthers();
		}
	}

	@Override
	public void whileHover() {
		for(UiComponent button : buttons) {
			if(button == null) return;
			if(button.isHovering())
				button.setColor(ColorPalette.BUTTON_HIGHLIGHT);
			else
				button.setColor(ColorPalette.BUTTON_BASE);
		}
	}
	
	@Override
	public void stopHover() {
		for(UiComponent button : buttons) {
			if(button == null) return;
			button.setColor(ColorPalette.BUTTON_BASE);
		}
	}
	
	private void closeOthers() {
		if(building.isOpen()) {
			building.show();
		}
		if(employee.isOpen() ) {
			employee.show();
		}
		if(paint.isOpen()) {
			paint.show();
		}
		if(delete.isDeleting()) {
			delete.show(buttons[4]);
		}
	}

	private void addButtons(Engine engine) {
		this.buttons = new UiComponent[buttonCount];
		for(int i = 0; i < buttonCount; i++) {
			//Button
			UiComponent button = new UiComponent(4);
			button.setColor(ColorPalette.BUTTON_BASE);
			button.setInteractable(true, engine);
			this.addComponent(button);
			buttons[i] = button;
			UiConstraints cons = new UiConstraints();
			cons.setX(new AlignX(XAlign.CENTER));
			cons.setWidth(new RelativeScale(0.66f * 3));
			cons.setHeight(new AspectRatio(1.0f));
			button.setConstraints(cons);
			
			//Icon
			UiComponent icon = new UiComponent(4);
			icon.setTexture(buttonIcons[i]);
			UiConstraints iconCons = new UiConstraints();
			iconCons.setX(new AlignX(XAlign.CENTER));
			iconCons.setY(new AlignY(YAlign.MIDDLE));
			iconCons.setWidth(new RelativeScale(0.75f));
			iconCons.setHeight(new AspectRatio(1.0f));
			icon.setConstraints(iconCons);
			button.addComponent(icon);
		}
	}
	
	private void loadTextures(Engine engine) {
		this.buttonIcons = new int[buttonCount];
		this.buttonIcons[0] = engine.getTextureLoader().loadTexture("/uis/general");
		this.buttonIcons[1] = engine.getTextureLoader().loadTexture("/uis/building");
		this.buttonIcons[2] = engine.getTextureLoader().loadTexture("/uis/employees");
		this.buttonIcons[3] = engine.getTextureLoader().loadTexture("/uis/paint");
		this.buttonIcons[4] = engine.getTextureLoader().loadTexture("/uis/delete");
		this.buttonIcons[5] = engine.getTextureLoader().loadTexture("/uis/settings");
	}

	private void createBacking() {
		UiConstraints cons = new UiConstraints();
		cons.setX(new AlignX(XAlign.LEFT, 0.025f));
		cons.setY(new AlignY(YAlign.TOP, 0.5f));
		cons.setWidth(new AspectRatio(0.05f));
		cons.setLayout(new VerticalLayout(-0.066f, 0.025f));
		this.setConstraints(cons);
		this.setScale(0.066f, 0.4f);
		this.setColor(0.15f);
		this.setAlpha(0.0f);
	}

	public UiComponent[] getButtons() {
		return buttons;
	}

	public BuildingMenu getBuilding() {
		return building;
	}

	public void setBuilding(BuildingMenu building) {
		this.building = building;
	}

	public EmployeeMenu getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeMenu employee) {
		this.employee = employee;
	}

	public PaintMenu getPaint() {
		return paint;
	}

	public void setPaint(PaintMenu paint) {
		this.paint = paint;
	}

	public DeleteTool getDelete() {
		return delete;
	}

	public void setDelete(DeleteTool delete) {
		this.delete = delete;
	}

}
