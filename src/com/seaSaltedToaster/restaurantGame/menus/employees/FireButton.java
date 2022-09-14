package com.seaSaltedToaster.restaurantGame.menus.employees;

import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;

public class FireButton extends UiComponent {

	//Status
	private Employee employee;
	private EmployeeButton button;
	private EmployeeMenu menu;
	
	//Icon
	private UiComponent iconComp;
	private int icon;
	
	public FireButton(Employee employee, EmployeeButton employeeButton, EmployeeMenu employeeMenu, Engine engine) {
		super(6);
		this.employee = employee;
		this.button = employeeButton;
		this.menu = employeeMenu;
		this.icon = engine.getTextureLoader().loadTexture("/uis/fire");
		createPanel();
	}
	
	@Override 
	public void onClick() {
		employee.fireEmployee();
		
		button.setActive(false);
		this.menu.removeComponent(button);
	}

	private void createPanel() {
		//This panel
		this.setColor(0.2f);
		UiConstraints cons = this.getConstraints();
		cons.setX(new AlignX(XAlign.RIGHT, 0.05f));
		cons.setY(new AlignY(YAlign.TOP, 0.1f));
		cons.setWidth(new RelativeScale(0.1f));
		cons.setHeight(new AspectRatio(1.0f));
		
		//Icon panel
		this.iconComp = new UiComponent(6);
		this.iconComp.setTexture(icon);
		UiConstraints cons2 = this.iconComp.getConstraints();
		cons2.setX(new AlignX(XAlign.CENTER));
		cons2.setY(new AlignY(YAlign.MIDDLE));
		cons2.setWidth(new RelativeScale(0.75f));
		cons2.setHeight(new RelativeScale(0.75f));
		this.addComponent(iconComp);
	}

}
