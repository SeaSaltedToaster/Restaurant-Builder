package com.seaSaltedToaster.simpleEngine.entity;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;

public class Entity {
	
	//Transform
	private Transform transform;
	
	//Data
	private List<Component> components;

	//Create entity
	public Entity(Transform transform) {
		this.transform = transform;
		this.components = new ArrayList<Component>();
	}
	
	public Entity() {
		this.transform = new Transform();
		this.components = new ArrayList<Component>();
	}
	
	public void updateComponents() {
		for(Component component : this.getComponents()) {
			component.update();
		}
	}
	
	public Vao getModel() {
		ModelComponent comp = (ModelComponent) this.getComponent("Model");
		return comp.getMesh();
	}
	
	public Entity copyEntity() {
		Entity copyEntity = new Entity();
		copyEntity.setTransform(transform.copyTransform());
		for(Component component : components ) {
			copyEntity.addComponent(component);
			component.setEntity(copyEntity);
			component.init();
		}
		return copyEntity;
	}
	
	public Entity copyEntity(Transform transform) {
		Entity copyEntity = new Entity();
		copyEntity.setTransform(transform);
		for(Component component : components ) {
			copyEntity.addComponent(component);
			component.setEntity(copyEntity);
		}
		return copyEntity;
	}

	public void addComponent(Component component) {
		if(hasComponent(component.getComponentType())) return;
		components.add(component);
		component.setEntity(this);
		component.init();
	}
	
	public void removeComponent(Component component) {
		components.remove(component);
		component.setEntity(null);
	}
	
	public boolean hasComponent(String comp) {
		for(Component component : components) {
			if(component.getComponentType() == comp)  {
				return true;
			}
		}
		return false;
	}
	
	public Component getComponent(String comp) {
		for(Component component : components) {
			if(component.getComponentType() == comp)  {
				return component;
			}
		}
		System.out.println("Entity does not have '" + comp +"' component");
		return null;
	}
	
	public Transform getTransform() {
		return transform;
	}

	public void setTransform(Transform transform) {
		this.transform = transform;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

}
