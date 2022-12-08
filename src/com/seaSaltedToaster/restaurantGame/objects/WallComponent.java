package com.seaSaltedToaster.restaurantGame.objects;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class WallComponent extends Component {
	
	private String wallType, generator;
	
	private Vector3f start, end;
	private float projAngle;
	
	public WallComponent(String wallType, String generator) {
		this.wallType = wallType;
		this.generator = generator;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getWallType() {
		return wallType;
	}

	public void setWallType(String wallType) {
		this.wallType = wallType;
	}

	public Vector3f getStart() {
		return start;
	}

	public void setStart(Vector3f start) {
		this.start = start;
	}

	public Vector3f getEnd() {
		return end;
	}

	public void setEnd(Vector3f end) {
		this.end = end;
	}

	public float getProjAngle() {
		return projAngle;
	}

	public void setProjAngle(float projAngle) {
		this.projAngle = projAngle;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "Wall";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new WallComponent(wallType, generator);
	}

}
