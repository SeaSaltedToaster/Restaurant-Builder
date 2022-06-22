package com.seaSaltedToaster.simpleEngine.uis.animations.drivers;

import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;

public class SlideDriver extends ValueDriver {

	private float startValue;
	private float endValue;
	private float max = 0.0F;
	private boolean reachedTarget = false;
	
	private float currentValue;
	  
	public SlideDriver(float start, float end, float length) {
		super(length);
		this.startValue = start;
		this.endValue = end;
	}

	protected float calculateValue(float time) {
		if (!this.reachedTarget && time >= this.max) {
			this.max = time;
			currentValue = MathUtils.cosInterpolate(this.startValue, this.endValue, time);
			if(currentValue < endValue);
				return currentValue;
		}
		this.reachedTarget = true;
			return endValue;
		}

}
