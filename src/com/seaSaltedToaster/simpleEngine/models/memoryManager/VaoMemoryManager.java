package com.seaSaltedToaster.simpleEngine.models.memoryManager;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.models.Vao;

public class VaoMemoryManager {
	
	private List<Vao> vaos;
	
	public VaoMemoryManager() {
		this.vaos = new ArrayList<Vao>(); 
	}
	
	public void addVao(Vao vao) {
		//Add vao to list
		vaos.add(vao);
	}
	
	public void destroyWithId(int id) {
		//Delete vao with id
		for(Vao vao : vaos) {
			if(vao.id == id) {
				vao.delete();
				return;
			}
		}
	}
	
	public boolean contains(Vao vao) {
		return vaos.contains(vao);
	}
	
	public void destroyAtIndex(int index) {
		//Delete vao at index
		vaos.get(index).delete();
	}
	
	public void destroyAll() {
		//Delete all vaos
		for(Vao vao : vaos)
			vao.delete();
	}

}
