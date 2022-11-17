package com.seaSaltedToaster.simpleEngine.uis;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.input.Mouse;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosListener;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.animations.UiAnimator;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.utilities.Vector2f;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class UiComponent implements MouseListener, MousePosListener {
	
	private Vector2f position, scale;
	protected UiConstraints constraints;
	private int[] clippingBounds;
	
	protected Vector3f color;
	private int texture;
	
	protected boolean isActive = true;
	protected boolean interactable = false;
	protected boolean isHovering = false;
	
	protected int level;
	private float alpha = 1.0f;
	private float scaleMultiplier = 1.0f;
	
	private UiAnimator animator;
	
	protected List<UiComponent> children;
	private UiComponent parentComponent;
	
	public UiComponent(int level) {
		this.level = level;
		this.position = new Vector2f(0);
		this.scale = new Vector2f(0.1f);
		this.color = new Vector3f(0.0f);
		this.children = new ArrayList<UiComponent>();
		this.constraints = new UiConstraints();
		this.animator = new UiAnimator(this);
	}

	public UiComponent(int level, Vector2f position, Vector2f scale, Vector3f color) {
		this.level = level;
		this.position = position;
		this.scale = scale;
		this.color = color;
		this.children = new ArrayList<UiComponent>();
		this.constraints = new UiConstraints();
		this.animator = new UiAnimator(this);
	}
	
	public void updateComponent(Engine engine) {
		constraints.updateConstraints(this);
		if(isActive) {
			updateSelf();
			animator.update(this);
			if(isHovering())
				this.whileHover();
		}
		for(UiComponent component : children) {
			component.updateComponent(engine);
		}
	}
	
	public void renderUI(Engine engine) {
		if(!isActive) return;
		engine.getUiRenderer().renderGui(this);
		for(UiComponent component : children) {
			component.renderUI(engine);
		}
	}
	
	protected void updateSelf() {
		
	}
	
	@Override
	public void notifyButton(MouseEventData eventData) {
		if(!isActive) return;
		int key = eventData.getKey();
		int state = eventData.getAction();
		updateClick(Mouse.getMouseX(), Mouse.getMouseY());
		
		if(isHovering && isActive) {
			if(key == Mouse.LEFT_BUTTON && state == Mouse.PRESSED) {
				onClick();
			}
			if(key == Mouse.RIGHT_BUTTON && state == Mouse.PRESSED) {
				onRightClick();
			}
		} else if(!isHovering && isActive && key == Mouse.LEFT_BUTTON
				&& state == Mouse.PRESSED) {
			onClickOff();
		}
	}
	
	@Override
	public void notifyButton(MousePosData eventData) {
		if(!isActive) return; //|| !MainApp.restaurant.engine.getCurrentScene().getComponents().contains(this)) return;
		updateClick(eventData.getMouseX(), eventData.getMouseY());
	}
	
	public void updateClick(double x, double y) {
        double mouseCoordinatesX = Mouse.normalizeMouseCoordX(x);
        double mouseCoordinatesY = Mouse.normalizeMouseCoordY(y);
        
        if (position.y + (scale.y*scaleMultiplier) > mouseCoordinatesY && 
        		position.y - (scale.y*scaleMultiplier) < mouseCoordinatesY &&
        		position.x + (scale.x*scaleMultiplier) > mouseCoordinatesX &&
        		position.x - (scale.x*scaleMultiplier) < mouseCoordinatesX  && isActive) {
        	whileHover(); 
        	if(!isHovering && isActive) {
            	isHovering = true;
        		onHover(); 
        	}
        	isHovering = true;
        } else if(isHovering) {
        	stopHover();
        	isHovering = false;
        }
	}
	
	public boolean childHovering() {	
		if(isHovering())
			return true;
		
		for(UiComponent child : children) {
			if(child.isHovering())
				return true;
			if(child.childHovering())
				return true;
		}
		
		return false;
	}
	
	protected void onClickOff() {
		
	}

	protected void onRightClick() {
		
	}

	protected void stopHover() {
		
	}
	
	protected void onHover() {
		
	}
	
	protected void whileHover() {
		
	}
	
	protected void onClick() {
		
	}
	
	public void setClippingBounds() {
		float x = position.x - (scale.x / 2);
		float y = position.y - (scale.y / 2);
		
		int xPixels = (int) Math.round(x * Window.getWidth());
	    int yPixels = (int) (Window.getHeight() - Math.round((y + scale.y) * Window.getHeight()));
	    int widthPixels = (int) Math.round(scale.x * Window.getWidth());
	    int heightPixels = (int) Math.round(scale.y * Window.getHeight());
	    if (this.clippingBounds == null) {
	    	int[] bounds = { xPixels, yPixels, widthPixels, heightPixels };
	    	setChildrenClippingBounds(bounds);
	    } else {
	    	this.clippingBounds[0] = xPixels;
	    	this.clippingBounds[1] = yPixels;
	    	this.clippingBounds[2] = widthPixels;
	    	this.clippingBounds[3] = heightPixels;
	    } 
	}
	

	public void setClippingBounds(float x, float y, float width, float height) {
	    int xPixels = (int) Math.round(x * Window.getWidth());
	    int yPixels = (int) (Window.getHeight() - Math.round((y + height) * Window.getHeight()));
	    int widthPixels = (int) Math.round(width * Window.getWidth());
	    int heightPixels = (int) Math.round(height * Window.getHeight());
	    if (this.clippingBounds == null) {
	      int[] bounds = { xPixels, yPixels, widthPixels, heightPixels };
	      setChildrenClippingBounds(bounds);
	    } else {
	      this.clippingBounds[0] = xPixels;
	      this.clippingBounds[1] = yPixels;
	      this.clippingBounds[2] = widthPixels;
	      this.clippingBounds[3] = heightPixels;
	    } 
	  }
	
	private void setChildrenClippingBounds(int[] bounds) {
	    this.clippingBounds = bounds;
	    for (UiComponent child : children)
	      child.setChildrenClippingBounds(this.clippingBounds); 
	  }
	
	public int[] getClippingBounds() {
		return clippingBounds;
	}

	public void setClippingBounds(int[] clippingBounds) {
		this.clippingBounds = clippingBounds;
	}
	
	public void setInteractable(boolean interactable, Engine engine) {
		this.interactable = interactable;
		if(interactable) {
			engine.getMouse().getMouseButtonCallback().addListener(this);
			engine.getMouse().getMousePositionCallback().addListener(this);
		} else {
			engine.getMouse().getMouseButtonCallback().removeListener(this);
			engine.getMouse().getMousePositionCallback().removeListener(this);
		}
	}
	
	public void addComponent(UiComponent component) {
		if(component == null) return;
		this.children.add(component);
		component.setParentComponent(this);
	}
	
	public void removeComponent(UiComponent component) {
		if(component == null) return;
		this.children.remove(component);
		component.setParentComponent(null);
	}

	public float getScaleMultiplier() {
		return scaleMultiplier;
	}

	public void setScaleMultiplier(float scaleMultiplier) {
		this.scaleMultiplier = scaleMultiplier;
		for(UiComponent child : this.getChildren()) {
			child.setScaleMultiplier(scaleMultiplier);
		}
	}

	public UiAnimator getAnimator() {
		return animator;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(float x, float y) {
		this.position.setX(x);
		this.position.setY(y);
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(float x, float y) {
		this.scale.setX(x);
		this.scale.setY(y);
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(float x, float y, float z) {
		this.color.set(x, y, z);
	}
	
	public void setColor(Vector3f color) {
		this.color.set(color);
	}
	
	public void setColor(float x) {
		this.color.set(x, x, x);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		for(UiComponent component : children)
			component.setActive(isActive);
	}
	
	public void setSingleActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UiConstraints getConstraints() {
		return constraints;
	}

	public void setConstraints(UiConstraints constraints) {
		this.constraints = constraints;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public int getTexture() {
		return texture;
	}

	public void setTexture(int texture) {
		this.texture = texture;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public UiComponent getParentComponent() {
		return parentComponent;
	}

	public void setParentComponent(UiComponent parentComponent) {
		this.parentComponent = parentComponent;
	}

	public List<UiComponent> getChildren() {
		return children;
	}

	public boolean isInteractable() {
		return interactable;
	}

	public boolean isHovering() {
		return isHovering;
	}

	/**
	 * @param isHovering the isHovering to set
	 */
	public void setHovering(boolean isHovering) {
		this.isHovering = isHovering;
	}
	
}
