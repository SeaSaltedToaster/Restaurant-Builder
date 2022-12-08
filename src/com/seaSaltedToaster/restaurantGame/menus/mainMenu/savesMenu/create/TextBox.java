package com.seaSaltedToaster.restaurantGame.menus.mainMenu.savesMenu.create;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.AspectRatio;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;

public class TextBox extends UiComponent implements KeyListener {

	//Box
	private UiComponent textBox;
	private Text chatText;
	
	//Text
	private String text = "";
	private StringBuilder builder;
	private boolean isTyping = false;
		
	public TextBox(Engine engine) {
		super(2);
		this.setAlpha(0);
		initTextBox();
		
		this.setInteractable(true, engine);
		engine.getKeyboard().addKeyListener(this);
	}
	
	@Override
	public void updateSelf() {
		
	} 
	
	public String getText() {
		return text;
	}

	@Override
	public void onClick() {
		isTyping = true;
	}
	
	@Override
	public void onClickOff() {
		isTyping = false;
	}
	
	private void initTextBox() {
		UiConstraints mainCons = this.getConstraints();
		mainCons.setWidth(new RelativeScale(0.5f));
		mainCons.setHeight(new AspectRatio(0.25f));	
		mainCons.setX(new AlignX(XAlign.CENTER));
		mainCons.setY(new AlignY(YAlign.TOP, 0.25f));
		this.setAlpha(0.5f);
		
		this.textBox = new UiComponent(3);
		this.textBox.setAlpha(0.33f);
		UiConstraints backerCons = textBox.getConstraints();
		backerCons.setWidth(new RelativeScale(0.966f));
		backerCons.setHeight(new RelativeScale(0.9f));
		backerCons.setX(new AlignX(XAlign.CENTER));
		backerCons.setY(new AlignY(YAlign.MIDDLE));
		this.addComponent(textBox);
		
		this.chatText = new Text("...", 1.0f, 3);
		this.chatText.setColor(0.0f);
		UiConstraints textCons = chatText.getConstraints();
		textCons.setX(new AlignX(XAlign.CENTER));
		textCons.setY(new AlignY(YAlign.TOP, 0.4f));
		this.textBox.addComponent(chatText);
		
		this.builder = new StringBuilder();
	}
	
	@Override
	public void notifyButton(KeyEventData data) {
		addText(data.getKey(), data.getAction(), data.getModifiers());
	}
	
	private void addText(int key, int state, int mods) {
		if(!isActive || !isTyping || state == GLFW.GLFW_RELEASE) return;
		String keyName = GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
		if(checkSpecialChar(key)) return;
		if(getMods(keyName, mods)) return;
		text = text + keyName; chatText.setTextString(text);
	}
	
	private boolean getMods(String keyName, int mods) {
		if(mods == GLFW.GLFW_MOD_SHIFT) {
			text = text + keyName.toUpperCase(); chatText.setTextString(text);
			return true;
		}
		return false;
	}

	private boolean checkSpecialChar(int keyID) {
		if(keyID == GLFW.GLFW_KEY_SPACE) {
			builder.append(" ");
			text += new String(" ");
			chatText.setTextString(text); chatText.reset();
			return true;
		}
		if(keyID == GLFW.GLFW_KEY_BACKSPACE) {
			if(text.length() != 0)
				text = text.substring(0, text.length()-1);
			chatText.setTextString(text); chatText.reset();
			return true;
		}
		if(keyID == GLFW.GLFW_KEY_LEFT_SHIFT) {
			return true;
		}
		chatText.setTextString(text); chatText.reset();
		return false;
	}


}
