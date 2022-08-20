package com.seaSaltedToaster.restaurantGame.menus.employees;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.menus.BuildingMenu;
import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.input.Keyboard;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.HorizontalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.VerticalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.HorizontalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class EmployeeButton extends UiComponent implements KeyListener {
	
	//Settings
	private float WIDTH = 0.825f;
	private Employee employee;
	
	//Icon
	private UiComponent nameBacker, icon, iconBacker;
	private Text name;

	public EmployeeButton(Employee employee) {
		super(4);
		createPanel(employee);
		addDetails(employee.name);
		this.employee = employee;
		
		Engine engine = MainApp.restaurant.engine;
		this.setInteractable(true, engine);
		engine.getKeyboard().addKeyListener(this);
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
		backerCons.setHeight(new AspectRatio(0.2f));
		backerCons.setY(new AlignY(VerticalAlignment.TOP, 0.1f));
		this.nameBacker = new UiComponent(5);
		this.nameBacker.setConstraints(backerCons);
		this.nameBacker.setColor(0.2f);
		this.addComponent(nameBacker);
		this.nameBacker.setInteractable(true, MainApp.restaurant.engine);
		
		UiConstraints textCons = new UiConstraints();
		textCons.setX(new AlignX(HorizontalAlignment.CENTER));
		textCons.setY(new AlignY(VerticalAlignment.TOP, 0.0f));
		this.name = new Text(nameString, 0.85f, 4);
		this.name.setColor(1.0f);
		this.name.setConstraints(textCons);
		this.nameBacker.addComponent(name);
	}

	private void createPanel(Employee employee) {
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(WIDTH));
		cons.setHeight(new AspectRatio(0.33f));
		cons.setLayout(new HorizontalLayout(-0.25f, -0.45f));
		this.setColor(0.25f);
		
		UiConstraints backerCons = new UiConstraints();
		backerCons.setWidth(new RelativeScale(0.15f));
		backerCons.setHeight(new AspectRatio(1.0f));
		backerCons.setY(new AlignY(VerticalAlignment.TOP, 0.1f));
		this.iconBacker = new UiComponent(5);
		this.iconBacker.setConstraints(backerCons);
		this.iconBacker.setColor(0.2f);
		this.addComponent(iconBacker);
		
		int iconTex = BuildingMenu.iconMaker.createIcon(employee.model, 0.33f);
		this.icon = new UiComponent(5);
		this.icon.setConstraints(UiConstraints.getFillCenter());
		this.icon.setTexture(iconTex);
		this.iconBacker.addComponent(icon);
	}

}
