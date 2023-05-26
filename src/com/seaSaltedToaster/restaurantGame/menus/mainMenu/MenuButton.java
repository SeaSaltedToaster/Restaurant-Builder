package com.seaSaltedToaster.restaurantGame.menus.mainMenu;

import com.seaSaltedToaster.restaurantGame.audio.AudioTracks;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.audio.AudioSource;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class MenuButton extends UiComponent {
	
	//Animation
	private MainMenu menu;
	private SmoothFloat scale;
	private AudioSource source;
	
	//Button Info
	private Text text;
	private	int index;
	
	//Resizing
	private float nScale = 0.15f, hScale = nScale * 1.25f;
	private float titleMulti = 2.0f;
	
	public MenuButton(MainMenu mainMenu, int index, String word) {
		super(0);
		this.menu = mainMenu;
		this.index = index;
		this.source = new AudioSource();
		createButton(word, mainMenu.getEngine());
		
		if(index == -1) {
			this.scale = new SmoothFloat(nScale * titleMulti);
		}
		this.scale.setValue(-2.5f);
	}
	
	@Override
	public void updateSelf() {
		scale.update(Window.DeltaTime);
		float newScale = scale.getValue();
		this.setScale(newScale, newScale);
	}
	
	@Override
	public void onClick() {
		//If it is the play button
		if(index == 0) {
			menu.getAllX().setTarget(-2.0f);
			menu.getManager().getSavesMenu().slide(true);
		}
		
		//If it is the settings button
		if(index == 1 ) {
			menu.getAllX().setTarget(-2.0f);
			menu.getManager().getSettingsMenu().slide(true);
		}
		
		//if it is the quit button
		if(index == 2) {
			menu.setClosingFade(true);
			menu.getFadeIn().setTarget(2.5f);
		}
	}
	
	@Override
	public void onHover() {
		this.scale.setTarget(hScale);
		if(index == -1) {
			this.scale.setTarget(hScale * titleMulti); 
		}
		this.source.Play(AudioTracks.BUTTON_HOVER);
	}
	
	@Override
	public void stopHover() {
		this.scale.setTarget(nScale);
		if(index == -1) {
			this.scale.setTarget(nScale * titleMulti); 
		}
	}
	
	private void createButton(String word, Engine engine) {		
		this.scale = new SmoothFloat(nScale);
		UiConstraints bCons = this.getConstraints();
		bCons.setX(new AlignX(XAlign.CENTER));
		
		float textSize = 1.125f;
		this.text = new Text(word, textSize, 0);
		this.text.setColor(0.0f);
		UiConstraints cons = text.getConstraints();
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.TOP, 0.66f));
		this.addComponent(text);
		this.setInteractable(true, engine);
		
		LanguageManager.addText(word, text);
	}

	public Text getText() {
		return text;
	}

	public MainMenu getMenu() {
		return menu;
	}

	public void setMenu(MainMenu menu) {
		this.menu = menu;
	}

}
