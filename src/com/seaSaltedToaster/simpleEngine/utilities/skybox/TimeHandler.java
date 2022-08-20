package com.seaSaltedToaster.simpleEngine.utilities.skybox;

import com.seaSaltedToaster.simpleEngine.renderer.Window;

public class TimeHandler {
	
	//Timing
	public static float DAY_VALUE = 1;
	public static float hour, minute;
	public static int dayNumber = 0;
	private float daySpeed = 100f;
	
	//Times for color
	private float midnightNew = 0; //new day
	private float midday = 12;
	private float evening = 17;
	private float midnightLate = 24; //old day

	public TimeHandler() {
		TimeHandler.hour = 8f;
		TimeHandler.minute = 30;
	}
	
	public void updateTime() {
		//Add to time
		float delta = (float) Window.DeltaTime;
		minute += (daySpeed * delta);
		if(minute >= 60) {
			hour++;
			minute = 0;
		}
		if(hour >= 24) {
			dayNumber++;
			hour = 0;
		}
		
		//Set day value
		float newValue = 1;
		float realHour = hour + (minute / 60);

		//Day start to midday
		if(hour >= midnightNew && hour < midday) {
			float value = realHour / midday;
			newValue = value;
		}
		//Midday to midnight
		if(hour >= evening && hour < midnightLate) {
			float value = (realHour - evening) / (midnightLate - evening);
			newValue = (1 - value);
		}
		//Set
		TimeHandler.DAY_VALUE = (1.0f - newValue) - 0.125f;
	}
	
}
