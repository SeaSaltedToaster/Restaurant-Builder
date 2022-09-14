package com.seaSaltedToaster.restaurantGame.menus.employees;

import com.seaSaltedToaster.restaurantGame.objects.people.Employee;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class LevelBar extends UiComponent {

	//Components
	private UiComponent levelProgress;
	private Text progressText;
	
	//Numbers :/
	private float curProgress = -100.0f;
	
	public LevelBar(float progress) {
		super(5);
		createPanel();
		addProgress(progress);
	}

	private void addProgress(float progress) {
		this.levelProgress = new UiComponent(6);		
		this.levelProgress.setColor(0.33f);
		
		UiConstraints cons = this.levelProgress.getConstraints();
		cons.setHeight(new RelativeScale(0.85f));
		cons.setX(new AlignX(XAlign.LEFT, 0.025f));
		cons.setY(new AlignY(YAlign.MIDDLE));
		this.addComponent(levelProgress);
		
		this.progressText = new Text("Level " + 0 + " : " + Math.round(progress) + " / 100", 0.5f, 5);
		this.progressText.setColor(1.0f);
		UiConstraints cons2 = this.progressText.getConstraints();
		cons2.setX(new AlignX(XAlign.CENTER));
		cons2.setY(new AlignY(YAlign.TOP, 0.33f));
		this.addComponent(progressText);
	}

	public void setProgress(Employee employee) {
		float progress = employee.getProgress();
		int level = employee.getLevel();
		
		if(progress == curProgress) return;
		this.curProgress = progress;
		
		this.progressText.setTextString("Level " + level + " : " + (int) (curProgress) + " / 100");
		this.levelProgress.getScale().x = (progress / 100) * this.getScale().x;
	}

	private void createPanel() {
		this.setColor(0.2f);
		
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(0.95f));
		cons.setHeight(new AspectRatio(0.1f));
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.BOTTOM, 0.1f));
	}

}
