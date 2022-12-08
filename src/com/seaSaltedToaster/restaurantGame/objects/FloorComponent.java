package com.seaSaltedToaster.restaurantGame.objects;

import java.util.List;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class FloorComponent extends Component {
	
	private String type, generator;
	private List<Vector3f> points;
	
	public FloorComponent(String type, String generator, List<Vector3f> points) {
		this.type = type;
		this.generator = generator;
		this.points = points;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Vector3f> getPoints() {
		return points;
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
		return "Floor";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		FloorComponent comp = new FloorComponent(type, generator, points);
		return comp;
	}

}
