package com.seaSaltedToaster.restaurantGame.menus.employees;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
import com.seaSaltedToaster.restaurantGame.objects.people.ChefComponent;
import com.seaSaltedToaster.restaurantGame.objects.people.ServerComponent;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.input.listeners.ScrollListener;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.VerticalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class EmployeeMenu extends UiComponent implements ScrollListener {
	
	//Animation
	private boolean isOpen = false;
	private final float hideFactor = -2.0f;
	private SmoothFloat yValue;
	
	//Title
	private Text title;
	private UiComponent titleBacker;
		
	//Settings
	private SmoothFloat smoothScroll;
	private float scroll = -0.25f;
	
	public EmployeeMenu() {
		super(3);
		createPanel();
		this.yValue = new SmoothFloat(hideFactor);
		this.smoothScroll = new SmoothFloat(scroll);
		
		Engine engine = MainApp.restaurant.engine;
		this.setInteractable(true, engine);
		engine.getMouse().addScrollListener(this);
		
	}
	
	@Override
	public void updateSelf() {
		yValue.update(Window.DeltaTime);
		float newY = yValue.getValue();
		this.getPosition().setY(newY);
		
		if(isOpen)
			MainApp.menuFocused = true;
		
		smoothScroll.update(Window.DeltaTime);
		VerticalLayout layout = (VerticalLayout) this.getConstraints().getLayout();
		layout.setEdgeSpace(smoothScroll.getValue());
		this.getConstraints().setLayout(layout);
	}
	
	@Override
	public void notifyScrollChanged(float scrollValue) {
		this.smoothScroll.increaseTarget(scrollValue / 10.0f);
		if(smoothScroll.getTarget() >= 0) {
			smoothScroll.setTarget(0.0f);
		}
	}
	
	public void show() {
		if(isOpen) {
			this.yValue.setTarget(hideFactor);
			this.isOpen = false;
			this.getChildren().clear();
			smoothScroll.setTarget(0.0f);
			MainApp.menuFocused = false;
		} else if(!isOpen) {
			this.yValue.setTarget(0.0f);
			this.isOpen = true;
			smoothScroll.setTarget(0.0f);
			createPanel();
		}
	}
	
	private void addEmployees() {
		Restaurant restaurant = MainApp.restaurant;
		
		for(ServerComponent server : restaurant.servers) {
			EmployeeButton employee = new EmployeeButton(server, this);
			employee.setClippingBounds(0.0f, 0.25f, 1.0f, 0.5f);
			this.addComponent(employee);
		}
		for(ChefComponent chef : restaurant.chefs) {
			EmployeeButton employee = new EmployeeButton(chef, this);
			employee.setClippingBounds(0.0f, 0.25f, 1.0f, 0.5f);
			this.addComponent(employee);
		}
	}
	
	private void createPanel() {
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(0.2f));
		cons.setHeight(new RelativeScale(0.5f));
		cons.setLayout(new VerticalLayout(-0.33f, 0.033f));
		this.setColor(0.15f);
		addEmployees();
		
		UiConstraints titleBackCons = new UiConstraints();
		titleBackCons.setWidth(new RelativeScale(1.0f));
		titleBackCons.setHeight(new RelativeScale(0.125f));
		titleBackCons.setX(new AlignX(XAlign.CENTER));
		titleBackCons.setY(new AlignY(YAlign.TOP, 0.0f));
		this.titleBacker = new UiComponent(5);
		this.titleBacker.setConstraints(titleBackCons);
		this.titleBacker.setColor(0.25f);
		this.addComponent(titleBacker);
		
		UiConstraints textCons = new UiConstraints();
		textCons.setX(new AlignX(XAlign.CENTER));
		textCons.setY(new AlignY(YAlign.TOP, 0.33f));
		this.title = new Text("Employees", 1.25f, 4);
		this.title.setColor(1.0f);
		this.title.setConstraints(textCons);
		this.titleBacker.addComponent(title);
	}
	
	public boolean isOpen() {
		return isOpen;
	}

}
