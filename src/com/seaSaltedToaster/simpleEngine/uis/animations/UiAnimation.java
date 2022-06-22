package com.seaSaltedToaster.simpleEngine.uis.animations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.animations.drivers.ValueDriver;

public class UiAnimation {
	
	private HashMap<ValueDriver, UiAspect> drivers;
	
	private double currentTime;
	private float delay;
	
	public boolean isDone = false; 
	
	public UiAnimation() {
		this.drivers = new HashMap<ValueDriver, UiAspect>();
	}
	
	public void update(UiComponent component) {
		Iterator<Entry<ValueDriver, UiAspect>> iterator = drivers.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<ValueDriver, UiAspect> entry = iterator.next();
			updateDriver(entry.getKey(),entry.getValue(), component, iterator);
		}
	}
	
	private void updateDriver(ValueDriver driver, UiAspect type, UiComponent component, Iterator<Entry<ValueDriver, UiAspect>> entry) {
		if(delay > 0) {
			delay -= Math.abs(Window.DeltaTime);
			return;
		}
		if(driver.hasCompletedOnePeriod()) {
			isDone = true;
			entry.remove();
		}
		switch(type) {
			case AXIS_X:
				updateXDriver(driver, component);
				break;
			case AXIS_Y:
				updateYDriver(driver, component);
				break;
			case ALPHA:
				updateAlphaDriver(driver, component);
				break;
			case SCALE_X:
				updateXScaleDriver(driver, component);
				break;
			case SCALE_Y:
				updateYScaleDriver(driver, component);
				break;
		}
	}
	
	private void updateXDriver(ValueDriver driver, UiComponent component) {
		float ammount = driver.update(getTime());
		component.setPosition(ammount, component.getPosition().y);	
	}
	
	private void updateYDriver(ValueDriver driver, UiComponent component) {
		float ammount = driver.update(getTime());
		component.setPosition(component.getPosition().x, ammount);	
	}
	
	private void updateXScaleDriver(ValueDriver driver, UiComponent component) {
		float ammount = driver.update(getTime());
		component.setScale(ammount, component.getScale().y);	
	}
	
	private void updateYScaleDriver(ValueDriver driver, UiComponent component) {
		float ammount = driver.update(getTime());
		component.setScale(component.getScale().x, ammount);	
	}
	
	private void updateAlphaDriver(ValueDriver driver, UiComponent component) {
		float ammount = driver.update(getTime());
		component.setAlpha(ammount);
	}
	
	private float getTime() {
		this.currentTime += Window.DeltaTime;
		return (float) (currentTime);
	}
	
	public UiAnimation xDriver(ValueDriver driver) {
		drivers.put(driver, UiAspect.AXIS_X);
		return this;
	}
	
	public UiAnimation yDriver(ValueDriver driver) {
		drivers.put(driver, UiAspect.AXIS_Y);
		return this;
	}
	
	public UiAnimation xScaleDriver(ValueDriver driver) {
		drivers.put(driver, UiAspect.SCALE_X);
		return this;
	}
	
	public UiAnimation yScaleDriver(ValueDriver driver) {
		drivers.put(driver, UiAspect.SCALE_Y);
		return this;
	}
	
	public UiAnimation alphaDriver(ValueDriver driver) {
		drivers.put(driver, UiAspect.ALPHA);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public UiAnimation copy() {
		UiAnimation animation = new UiAnimation();
		animation.setCurrentTime(currentTime);
		animation.setDelay(delay);
		animation.setDrivers((HashMap<ValueDriver, UiAspect>) drivers.clone());
		return animation;
	}

	public float getDelay() {
		return delay;
	}

	public void setDelay(float delay) {
		this.delay = delay;
	}

	public HashMap<ValueDriver, UiAspect> getDrivers() {
		return drivers;
	}

	public void setDrivers(HashMap<ValueDriver, UiAspect> drivers) {
		this.drivers = drivers;
	}

	public double getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(double currentTime) {
		this.currentTime = currentTime;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
}
