package com.seaSaltedToaster.restaurantGame;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MouseListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.ScrollListener;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothVector;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothVector3;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class WorldCamera extends Camera implements ScrollListener, MousePosListener, MouseListener {

	//Settings
	private Engine engine;
	private int RIGHT_MOUSE = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
	
	//Camera focus movement
	private float lastClick = 0;
	private float clickOffset = 0.0625f / 1.0f;

	//Smoothing
	private SmoothFloat smoothZoom, smoothPitch;
	private SmoothFloat smoothYaw;
	
	//Movement
	public float camDist = 25, maxCamDist = 150;
	private double lastX, lastY;
	private float outerAngle = 360;
	
	//Points
	private Vector3f posTarget;
	private Transform focus;
	
	public WorldCamera(Engine engine) {
		this.engine = engine;
		this.focus = new Transform();
		this.position = new Vector3f(0.0f, 0.0f, 0.0f);
		this.posTarget = new Vector3f(0.0f, 0.0f, 0.0f);
		
		this.smoothZoom = new SmoothFloat(camDist);
		this.smoothPitch = new SmoothFloat(45.0f);
		this.smoothYaw = new SmoothFloat(outerAngle);
		registerCamera(engine);
	}
	
	@Override
	public void update() {		
		//Place camera
		float horizontal = getHorizontalDistance();
		float vertical = getVerticalDistance();
		calculateCameraPosition(horizontal, vertical);
		
		//Delta
		double delta = Window.DeltaTime;
		
		//Yaw change
		smoothYaw.update(delta);
		this.outerAngle = smoothYaw.getValue();
		float yawTarget = -(focus.getRotation().y + outerAngle);
		this.yaw = yawTarget;

		//Smoother
		smoothZoom.update(delta);
		this.camDist = smoothZoom.getValue();
		smoothPitch.update(delta);
		this.pitch = smoothPitch.getValue();
		lastClick += delta;
		
	}
	
	@Override
	public void notifyButton(MouseEventData eventData) {
		if(MainApp.menuFocused)
			return;
		
		boolean isRightDown = (eventData.getKey() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && eventData.getAction() == GLFW.GLFW_RELEASE);
		
		//Movement
		if(isRightDown) {
			if(lastClick < clickOffset) {
				this.focus.setPosition(Raycaster.lastRay);
			} 
			lastClick = 0;
		}
	}
		
	@Override
	public void notifyButton(MousePosData eventData) {
		if(MainApp.menuFocused)
			return;
		
		boolean isRightDown = GLFW.glfwGetMouseButton(Window.windowID, RIGHT_MOUSE) == GLFW.GLFW_PRESS;
		
		//Get change
		float xChange = (float) (lastX - eventData.getMouseX());
		float yChange = (float) (lastY - eventData.getMouseY());
		
		//Pitch / Y
		if(isRightDown) {
			float pitchChange = yChange * -0.3f;
			smoothPitch.increaseTarget(pitchChange);
		}
			
		//Outer angle
		if(isRightDown) {
			float angleChange = xChange * 0.3f;
			smoothYaw.increaseTarget(angleChange);
		}
		
		//Move with middle mouse
		boolean isMiddleDown = GLFW.glfwGetMouseButton(Window.windowID, GLFW.GLFW_MOUSE_BUTTON_MIDDLE) == GLFW.GLFW_PRESS;
		if(isMiddleDown) {
			float cap = 250.0f;
			Vector3f belowCam = new Vector3f(position.x, 0, position.z);
			Vector3f direction = focus.getPosition().copy().subtract(belowCam);
			
			Vector3f rotDir = MathUtils.rotatePointAtCenter(direction.copy(), focus.getRotation().y + 90);
	        focus.getPosition().increase((xChange * rotDir.x) / cap, 0, (xChange * rotDir.z) / cap);
	        focus.getPosition().increase((yChange * -direction.x) / cap, 0, (yChange * -direction.z) / cap);
		}
		
		//Reset
		lastX = eventData.getMouseX();
		lastY = eventData.getMouseY();
	}
	
	@Override
	public void notifyScrollChanged(float scrollValue) {
		if(MainApp.menuFocused)
			return;
		
		float increment = this.camDist / 10.0f; 
		float scrollAmount = (scrollValue * increment);
		this.smoothZoom.increaseTarget(-scrollAmount);
		
		if(smoothZoom.getTarget() > maxCamDist)
			this.smoothZoom.setTarget(maxCamDist);
		if(smoothZoom.getTarget() < 1)
			this.smoothZoom.setTarget(1);
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = focus.getRotation().y + outerAngle; //angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		
		posTarget.x = focus.getPosition().x - offsetX;
		posTarget.y = focus.getPosition().y + verticDistance;
		posTarget.z = focus.getPosition().z - offsetZ;
		this.setPosition(posTarget);
	}
	
	private float getHorizontalDistance() {
		return (float) (-camDist * Math.cos(Math.toRadians(smoothPitch.getValue())));
	}
	
	private float getVerticalDistance() {
		return (float) (-camDist * Math.sin(Math.toRadians(-smoothPitch.getValue())));
	}
	
	private void registerCamera(Engine engine) {
		engine.getMouse().addScrollListener(this);
		engine.getMouse().getMousePositionCallback().addListener(this);
		engine.getMouse().getMouseButtonCallback().addListener(this);
		engine.setCamera(this);		
	}
	
	@Override
	public void setYaw(float yaw) {
		this.yaw = yaw;
		this.smoothYaw.setTarget(yaw);
	}
	
	@Override
	public void setPitch(float pitch) {
		this.pitch = pitch;
		this.smoothPitch.setTarget(pitch);
	}

	public float getCamDist() {
		return camDist;
	}

	public void setCamDist(float camDist) {
		this.camDist = camDist;
		this.smoothZoom.setTarget(camDist);
	}

	public float getOuterAngle() {
		return outerAngle;
	}

	public void setOuterAngle(float outerAngle) {
		this.outerAngle = outerAngle;
	}

	
}
