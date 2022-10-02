package com.seaSaltedToaster.restaurantGame.menus.employees;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.menus.buildingSelector.BuildingMenu;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.input.Keyboard;
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
import com.seaSaltedToaster.simpleEngine.uis.layouts.HorizontalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class EmployeeButton extends UiComponent implements KeyListener {
	
	//Settings
	private float WIDTH = 0.9f;
	private Employee employee;
	
	//Icon
	private UiComponent nameBacker, icon, iconBacker;
	private Text name;
	
	//Level
	private LevelBar level;
	
	//Status
	private UiComponent statusBar;
	private Text wage;
	
	//Fire
	private FireButton fire;

	public EmployeeButton(Employee employee, EmployeeMenu employeeMenu) {
		super(4);
		createPanel(employee);
		addDetails(employee.name);
		this.employee = employee;
		
		Engine engine = MainApp.restaurant.engine;
		this.setInteractable(true, engine);
		engine.getKeyboard().addKeyListener(this);
		
		this.fire = new FireButton(employee, this, employeeMenu, engine);
		this.fire.setInteractable(true, engine);
		this.addComponent(fire);
	}
	
	@Override
	public void updateSelf() {
		level.setProgress(employee);
	}
	
	@Override
	public void notifyButton(KeyEventData eventData) { 
		if(eventData.getAction() == GLFW.GLFW_RELEASE) return;
		int key = eventData.getKey();
		
		if(nameBacker.isHovering()) {
			String letter = Keyboard.getKeyName(key);
			checkModifier(key, eventData.getModifiers());
			
			int nameLength = employee.name.length();
			if(nameLength >= 16) return;
			if(Keyboard.LSHIFT) letter = letter.toUpperCase();
			
			String newName = name.getTextString() + letter;
			name.setTextString(newName);
			employee.name = newName;
		}
	}

	private void checkModifier(int key, int modifiers) {
		String currentText = name.getTextString();
		if(key == GLFW.GLFW_KEY_BACKSPACE && currentText.length() > 0) {
			String newName = currentText.substring(0, currentText.length()-1);
			name.setTextString(newName);
			employee.name = newName;
		}
		
		int nameLength = employee.name.length();
		if(nameLength >= 16) return;
		if(key == GLFW.GLFW_KEY_SPACE) {
			String newName = name.getTextString() + " ";
			name.setTextString(newName);
			employee.name = newName;
		}
	}

	private void addDetails(String nameString) {
		UiConstraints backerCons = new UiConstraints();
		backerCons.setWidth(new RelativeScale(0.66f));
		backerCons.setHeight(new AspectRatio(0.15f));
		backerCons.setY(new AlignY(YAlign.TOP, 0.1f));
		this.nameBacker = new UiComponent(5);
		this.nameBacker.setConstraints(backerCons);
		this.nameBacker.setColor(0.2f);
		this.addComponent(nameBacker);
		this.nameBacker.setInteractable(true, MainApp.restaurant.engine);
		
		UiConstraints textCons = new UiConstraints();
		textCons.setX(new AlignX(XAlign.CENTER));
		textCons.setY(new AlignY(YAlign.TOP, 0.0f));
		this.name = new Text(nameString, 0.85f, 4);
		this.name.setColor(1.0f);
		this.name.setConstraints(textCons);
		this.nameBacker.addComponent(name);
		
		this.level = new LevelBar(25f);
		this.addComponent(level);
	}

	private void createPanel(Employee employee) {
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(WIDTH));
		cons.setHeight(new AspectRatio(0.33f));
		cons.setLayout(new HorizontalLayout(-0.14f, -0.45f));
		this.setColor(ColorPalette.MAIN_SHADE);
		
		UiConstraints backerCons = new UiConstraints();
		backerCons.setWidth(new RelativeScale(0.15f));
		backerCons.setHeight(new AspectRatio(1.0f));
		backerCons.setY(new AlignY(YAlign.TOP, 0.1f));
		this.iconBacker = new UiComponent(5);
		this.iconBacker.setConstraints(backerCons);
		this.iconBacker.setColor(0.2f);
		this.addComponent(iconBacker);
		
		int iconTex = BuildingMenu.iconMaker.createIcon(employee.model, 0.33f);
		this.icon = new UiComponent(5);
		this.icon.setConstraints(UiConstraints.getFillCenter());
		this.icon.setTexture(iconTex);
		this.iconBacker.addComponent(icon);
		
		this.statusBar = new UiComponent(6);
		UiConstraints cons2 = this.statusBar.getConstraints();
		cons2.setWidth(new RelativeScale(0.75f));
		cons2.setHeight(new AspectRatio(0.1f));
		cons2.setX(new AlignX(XAlign.RIGHT, 0.075f));
		cons2.setY(new AlignY(YAlign.MIDDLE, 0.0f));
		this.statusBar.setColor(0.2f);
		this.addComponent(statusBar);
		
		this.wage = new Text("Wage : $"+employee.getWage() + " / day", 0.5f, 6);
		UiConstraints textCons = new UiConstraints();
		textCons.setX(new AlignX(XAlign.LEFT, 0.15f));
		textCons.setY(new AlignY(YAlign.TOP, 0.0f));
		this.wage.setColor(1.0f);
		this.wage.setConstraints(textCons);
		this.statusBar.addComponent(wage);
	}

}
