package com.seaSaltedToaster.restaurantGame.objects.people;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.models.Vao;

public abstract class Employee extends Component {

	//Basic
	public String name;
	public Vao model;
	
	//Level
	private float progress;
	private int level;
	
	//Pay per day
	private int wage = 10;
	
	@Override
	public void init() {
		this.progress = 33.0f;
		this.level = 2;
	}

	@Override
	public void update() {
		increaseXP();
	}
	
	protected void increaseXP() {
		progress += Math.abs(Math.random()) / 100.0f;
		if(progress >= 100) {
			level++;
			progress = progress - 100.0f;
		}		
	}

	public void addExp(float exp) {
		this.progress += exp;
	}
	
	public abstract void fireEmployee();

	public String getName() {
		return name;
	}

	public Vao getModel() {
		return model;
	}

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	public float getProgress() {
		return progress;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "Employee";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return null;
	}

}
