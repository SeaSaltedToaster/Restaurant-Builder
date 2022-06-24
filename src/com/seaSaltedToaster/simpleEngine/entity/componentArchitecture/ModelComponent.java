package com.seaSaltedToaster.simpleEngine.entity.componentArchitecture;

import com.seaSaltedToaster.simpleEngine.models.Vao;

public class ModelComponent extends Component {

	private Vao mesh;
	private int modelID;
	
	public ModelComponent(Vao mesh) {
		this.mesh = mesh;
		this.modelID = 0;
	}
	
	public ModelComponent(Vao mesh, int id) {
		this.mesh = mesh;
		this.modelID = id;
	}
	
	@Override
	public String getComponentType() {
		return "Model";
	}

	public Vao getMesh() {
		return mesh;
	}

	public void setMesh(Vao mesh) {
		this.mesh = mesh;
	}

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
	}

	@Override
	public void update() {
		//UNUSED
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public void init() {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public Component copyInstance() {
		return new ModelComponent(mesh, modelID);
	}

}
