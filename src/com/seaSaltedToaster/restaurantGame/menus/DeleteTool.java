package com.seaSaltedToaster.restaurantGame.menus;

import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.restaurantGame.tools.RayMode;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;

public class DeleteTool {
	
	//Object
	private UiComponent button;
	private boolean deleting;
	
	//Colors
	private float base = ColorPalette.BUTTON_BASE;
	private float highlight = ColorPalette.BUTTON_HIGHLIGHT * 1.5f;
	
	public DeleteTool() {
		this.deleting = false;
	}
	
	public void update() {
		if(button == null) return;
		
		if(deleting) {
			button.setColor(highlight);
		}
		if(!deleting) {
			button.setColor(base);
		}
	}

	public void show(UiComponent uiComponent) {
		this.button = uiComponent;
		this.deleting = !deleting;
		
		if(deleting) {
			Raycaster.mode = RayMode.DELETE;
			button.setColor(highlight);
		}
		if(!deleting) {
			Raycaster.mode = RayMode.DEFAULT;
			button.setColor(base);
		}
	}

	public boolean isDeleting() {
		return deleting;
	}

}
