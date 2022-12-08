package com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.menus.mainMenu.MenuSettings;
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
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class SaveButton extends UiComponent {

	//Objects
	private SavesMenu savesMenu;
	private UiComponent icon;
	private Text name, lastPlayed, size;
	
	//Animation
	private SmoothFloat hovScale;
	
	public SaveButton(File child, int icon, SavesMenu savesMenu) {
		super(1);
		this.savesMenu = savesMenu;
		
		FileTime time = null;
		try {
			time = Files.getLastModifiedTime(child.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm");
		
		createPanel();
		addDetails(child.getName(), df.format(time.toMillis()), child.length() + " bytes");
		addIcon(savesMenu, icon);
	}
	
	@Override
	public void updateSelf() {		
		this.hovScale.update(Window.DeltaTime);
		this.setScaleMultiplier(hovScale.getValue());
		
		this.setClippingBounds(0.0f, 0.2f, 1.0f, 0.6f);
	}
	
	@Override
	public void onClick() {	
		if(this.getPosition().y < -0.55f || this.getPosition().y > 0.55f) return;
		
		MainApp.curSave = name.getTextString();
		
		this.savesMenu.slide(false);
		this.savesMenu.getManager().getLoadingScreen().slide(true);
	}
	
	@Override
	public void onHover() {
		this.hovScale.setTarget(1.075f);
	}
	
	@Override
	public void stopHover() {
		this.hovScale.setTarget(1.0f);
	}
	
	public void pop(boolean in) {
		if(in) {
			this.hovScale.setValue(-0.5f);
			this.hovScale.setTarget(1.0f);
		} 
		else {
			this.hovScale.setTarget(0.0f);
		}
	}
	
	private void addIcon(SavesMenu savesMenu, int iconTx) {
		this.icon = new UiComponent(1);
		this.addComponent(icon);
		
		int defaultIcon = savesMenu.getEngine().getTextureLoader().loadTexture("/uis/defaultSave");
		this.icon.setTexture(defaultIcon);
		if(iconTx != -1)
			this.icon.setTexture(iconTx);
		
		UiConstraints cons = this.icon.getConstraints();
		cons.setHeight(new RelativeScale(0.875f));
		cons.setWidth(new AspectRatio(1.0f));
		cons.setX(new AlignX(XAlign.LEFT, 0.05f));
		cons.setY(new AlignY(YAlign.MIDDLE));
	}
	
	private void addDetails(String nameTxt, String lpTxt, String sizeTxt) {
		float rightAlign = 0.05f;
		
		this.name = new Text(nameTxt, 1.0f, 1);
		UiConstraints titleCons = name.getConstraints();
		titleCons.setX(new AlignX(XAlign.RIGHT, rightAlign));
		titleCons.setY(new AlignY(YAlign.TOP, 0.1f));
		this.addComponent(name);
		
		this.lastPlayed = new Text(lpTxt, 0.75f, 1);
		UiConstraints lpCons = lastPlayed.getConstraints();
		lpCons.setX(new AlignX(XAlign.RIGHT, rightAlign));
		lpCons.setY(new AlignY(YAlign.TOP, 0.66f));
		this.addComponent(lastPlayed);
		
		this.size = new Text(sizeTxt, 0.75f, 1);
		UiConstraints sizeCons = this.size.getConstraints();
		sizeCons.setX(new AlignX(XAlign.RIGHT, rightAlign));
		sizeCons.setY(new AlignY(YAlign.TOP, 1.15f));
		this.addComponent(this.size);
	}

	private void createPanel() {
		this.setAlpha(MenuSettings.S_TRANSP);
		this.setColor(MenuSettings.S_COLOR);
		
		UiConstraints cons = this.getConstraints();
		cons.setWidth(new RelativeScale(0.8f));
		cons.setHeight(new AspectRatio(0.25f));
		cons.setX(new AlignX(XAlign.CENTER));
		
		this.hovScale = new SmoothFloat(1.0f);
		this.hovScale.setValue(0.0f);
		this.hovScale.setTarget(1.0f);
	}
}
