package com.seaSaltedToaster.restaurantGame.menus.mainMenu.loadingScreen;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class IntroFade {
	
	//Load state of new scene
	private boolean open = false;
	
	//Cover fade
	private UiComponent cover;
	private SmoothFloat coverAnim;
	
	public IntroFade(Engine engine) {
		super();
		createPanel();
		engine.addUi(cover);
	}

	public void update() {
		if(!this.open) return;
		
		this.coverAnim.update(Window.DeltaTime);
		this.cover.setAlpha(coverAnim.getValue());
		
		if(cover.getAlpha() <= 0.0f) {
			this.open = false;
		}
	}
	
	public void slide(boolean in) {
		if(in) {
			this.open = in;
			
			this.coverAnim.setValue(2.75f);
			this.coverAnim.setTarget(0.0f);
		}
	}
	
	private void createPanel() {
		this.cover = new UiComponent(9);
		this.cover.setAlpha(1.0f);
		this.cover.setScale(1.0f, 1.0f);
		this.cover.setColor(1.0f);
		
		this.coverAnim = new SmoothFloat(1.0f);
		this.coverAnim.setAmountPer(0.025f);
	}

}
