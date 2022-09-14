package com.seaSaltedToaster.restaurantGame.menus;

import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.skybox.TimeHandler;

public class TimeDisplay extends UiComponent {

	//Time text
	private Text display;
	
	//Memory saver
	private int lastMinute = 0;
	
	public TimeDisplay() {
		super(5);
		createPanel();
		addText();
	}
	
	@Override
	public void updateSelf() {
		int minute = (int) TimeHandler.minute;
		if(minute != lastMinute) {
			this.display.setTextString(getTimeString());
		}
	}
	
	private String getTimeString() {
		String hour = ((int) TimeHandler.hour) + "";
		int minute = (int) Math.floor(TimeHandler.minute);
		String minuteString = (minute + "");
		if(minute < 10) {
			minuteString = ("0" + minute);
		}
		String dayString = "Day " + TimeHandler.dayNumber;
		this.lastMinute = minute;
		return dayString + " - " + hour + ":" + minuteString;
	}

	private void addText() {
		this.display = new Text(getTimeString(), 1.0f, 6);
		this.display.setColor(1.0f);
		UiConstraints cons = display.getConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.TOP));
		this.addComponent(display);
	}

	private void createPanel() {
		UiConstraints cons = this.getConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.TOP));
		cons.setWidth(new RelativeScale(0.1125f));
		cons.setHeight(new AspectRatio(0.2f));
		this.setColor(0.15f);
	}

}
