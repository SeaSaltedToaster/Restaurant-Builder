package com.seaSaltedToaster.restaurantGame;

import org.lwjgl.glfw.GLFW;

import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Camera;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyEventData;
import com.seaSaltedToaster.simpleEngine.input.listeners.KeyListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosData;
import com.seaSaltedToaster.simpleEngine.input.listeners.MousePosListener;
import com.seaSaltedToaster.simpleEngine.input.listeners.ScrollListener;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothValue;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothVector;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class WorldCamera extends Camera implements ScrollListener, MousePosListener, KeyListener {

	//Settings
	private Engine engine;
	private int RIGHT_MOUSE = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
	private int FORWARD = GLFW.GLFW_KEY_W;
	private int BACKWARD = GLFW.GLFW_KEY_S;

	//Smoothing
	private SmoothValue smoothZoom;
	private SmoothValue smoothPitch, smoothYaw;
	private SmoothVector smoothMove, smoothFocus;
	
	//Movement
	private float speed = 100;
	public float camDist = 5;
	private double lastX, lastY;
	private float outerAngle = 45;
	
	//Points
	private Vector3f posTarget;
	private Transform focus;
	
	public WorldCamera(Engine engine) {
		this.engine = engine;
		this.focus = new Transform();
		this.position = new Vector3f(0.0f, 0.0f, 0.0f);
		this.posTarget = new Vector3f(0.0f, 0.0f, 0.0f);
		
		this.smoothZoom = new SmoothValue(5.0f);
		this.smoothPitch = new SmoothValue(45.0f);
		this.smoothYaw = new SmoothValue(outerAngle);
		this.smoothMove = new SmoothVector(new Vector3f(0.0f));
		this.smoothFocus = new SmoothVector(new Vector3f(0.0f));
		registerCamera(engine);
	}
	
	@Override
	public void update() {		
		//Place camera
		float horizontal = getHorizontalDistance();
		float vertical = getVerticalDistance();
		calculateCameraPosition(horizontal, vertical);
		
		//Yaw change
		float yawTarget = -(focus.getRotation().y + outerAngle);
		smoothYaw.force(yawTarget);
		
		//Smoother
		float delta = (float) engine.getWindow().getDelta();
		smoothZoom.update(delta);
		smoothZoom.clampTarget(3, 100);
		this.camDist = smoothZoom.get();
		smoothPitch.update(delta);
		this.pitch = smoothPitch.get();
		smoothYaw.update(delta);
		this.yaw = smoothYaw.get();
		smoothMove.update(delta);
		this.position = smoothMove.get();
		smoothFocus.update(delta);
		this.focus.setPosition(smoothFocus.get());
	}
	
	@Override
	public void notifyButton(KeyEventData eventData) {
//		if(eventData.getAction() != GLFW.GLFW_PRESS) return;
//        float distance = (float) (speed * Display.getDelta());
//        float dx = (float) (distance * Math.sin(Math.toRadians(-smoothYaw.get())));
//        float dz = (float) (distance * Math.cos(Math.toRadians(-smoothYaw.get())));
//        if(eventData.getKey() == FORWARD) {
//        	smoothFocus.increaseTarget(dx, 0, dz);
//        }
//        if(eventData.getKey() == BACKWARD) {
//        	smoothFocus.increaseTarget(-dx, 0, -dz);
//        }
	}
	
	@Override
	public void notifyButton(MousePosData eventData) {
		boolean isRightDown = GLFW.glfwGetMouseButton(engine.getWindow().windowID, RIGHT_MOUSE) == GLFW.GLFW_PRESS;
		
		//Get change
		float xChange = (float) (lastX - eventData.getMouseX());
		float yChange = (float) (lastY - eventData.getMouseY());
		
		//Pitch / Y
		float pitchChange = yChange * -0.3f;
		if(isRightDown) {
			smoothPitch.force(pitch += pitchChange);
		}
			
		//Outer angle
		float angleChange = xChange * -0.3f;
		if(isRightDown) {
			this.outerAngle -= angleChange;
		}
		
		//Reset
		lastX = eventData.getMouseX();
		lastY = eventData.getMouseY();
	}
	
	@Override
	public void notifyScrollChanged(float scrollValue) {
		this.smoothZoom.increaseTarget(scrollValue * -2f);
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = focus.getRotation().y + outerAngle; //angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		
		posTarget.x = smoothFocus.get().x - offsetX;
		posTarget.y = smoothFocus.get().y + verticDistance;
		posTarget.z = smoothFocus.get().z - offsetZ;
		smoothMove.force(posTarget);
	}
	
	private float getHorizontalDistance() {
		return (float) (camDist * Math.cos(Math.toRadians(smoothPitch.get())));
	}
	
	private float getVerticalDistance() {
		return (float) (camDist * Math.sin(Math.toRadians(-smoothPitch.get())));
	}
	
	private void registerCamera(Engine engine) {
		engine.getMouse().addScrollListener(this);
		engine.getMouse().getMousePositionCallback().addListener(this);
		engine.getKeyboard().addKeyListener(this);
		engine.setCamera(this);		
	}
		
	public void delete() {
		
	}
	
}
