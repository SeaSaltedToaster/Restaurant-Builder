package com.seaSaltedToaster.restaurantGame.menus;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.objects.Restaurant;
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
import com.seaSaltedToaster.simpleEngine.utilities.Timer;

public class MoneyCounter extends UiComponent {

	//Time text
	private Text display;
	private int lastDisplay = 0;
	private String prefix = "$ ";
	
	//Animation
	private Timer timer;
	private Text animText;
	
	public MoneyCounter() {
		super(4);
		createPanel();
		addText(MainApp.restaurant);
		prepareAnimation();
		this.timer = new Timer(3);
	}
	
	@Override
	public void updateSelf() {
		double delta = Window.DeltaTime;
		this.timer.update(delta);
		
		if(timer.isRunning())
			updateAnimation();
		
		int money = MainApp.restaurant.money;
		if(money != lastDisplay) {
			int difference = (money - lastDisplay);
			this.lastDisplay = money;
			this.display.setTextString(prefix+money);
			doAnimation(difference);
		}
	}
	
	private void doAnimation(int difference) {
		this.timer.start();
		if(difference > 0)
			this.animText.setTextString("+"+difference);
		else if(difference < 0)
			this.animText.setTextString(""+difference);
	}

	private void updateAnimation() {
		if(timer.isFinished()) {
			timer.stop();
			this.animText.setTextString(" ");
		}
	}

	private void prepareAnimation() {
		this.animText = new Text(" ", 0.75f, 6);
		this.animText.setColor(0.0f);
		UiConstraints cons = animText.getConstraints();
		cons.setX(new AlignX(XAlign.LEFT, 0.05f));
		cons.setY(new AlignY(YAlign.TOP, 2.0f));
		this.addComponent(animText);		
	}
	
	private void addText(Restaurant restaurant) {
		this.lastDisplay = restaurant.money;
		this.display = new Text(prefix+lastDisplay, 1.0f, 6);
		this.display.setColor(1.0f);
		UiConstraints cons = display.getConstraints();
		cons.setX(new AlignX(XAlign.LEFT, 0.05f));
		cons.setY(new AlignY(YAlign.TOP));
		this.addComponent(display);
	}
	
	private void createPanel() {
		UiConstraints cons = this.getConstraints();
		cons.setX(new AlignX(XAlign.LEFT));
		cons.setY(new AlignY(YAlign.TOP));
		cons.setWidth(new RelativeScale(0.1125f));
		cons.setHeight(new AspectRatio(0.2f));
		this.setColor(0.15f);
	}

}
