package com.seaSaltedToaster.restaurantGame.objects;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.objects.food.Food;
import com.seaSaltedToaster.restaurantGame.objects.food.FoodRegistry;
import com.seaSaltedToaster.simpleEngine.entity.Entity;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.ModelComponent;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.MathUtils;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class TableComponent extends Component {

	//Table Info
	public int seatCount = 4;
	private Vector3f[] seats;
	private boolean[] seatTaken;
	private boolean taken = false;
	
	//Food spots
	private Entity[] foodSpots;
	
	@Override
	public void init() {
		this.seats = new Vector3f[seatCount];
		this.seatTaken = new boolean[seatCount];
		this.foodSpots = new Entity[seatCount];
		addSeats();
	}

	private void addSeats() {
		this.seats[0] = new Vector3f(0.15f, 0.125f, 0.3f);
		this.seats[1] = new Vector3f(-0.15f, 0.125f, 0.3f);
		this.seats[2] = new Vector3f(0.15f, 0.125f, -0.3f);
		this.seats[3] = new Vector3f(-0.15f, 0.125f, -0.3f);
		for(int i = 0; i < seatCount; i++) {
			seatTaken[i] = false;
		}
		float tableHeight = 0.4125f;
		this.foodSpots[0] = addSpot(new Vector3f(0.15f, tableHeight, 0.1f));
		this.foodSpots[1] = addSpot(new Vector3f(-0.15f, tableHeight, 0.1f));
		this.foodSpots[2] = addSpot(new Vector3f(0.15f, tableHeight, -0.1f));
		this.foodSpots[3] = addSpot(new Vector3f(-0.15f, tableHeight, -0.1f));
	}
	
	private Entity addSpot(Vector3f vector3f) {
		Entity entity = new Entity();
		entity.addComponent(new ModelComponent(null));
		Vector3f spotPos = MathUtils.rotatePointAtCenter(vector3f, this.entity.getTransform().getRotation().y);
		entity.getTransform().setPosition(spotPos.add(this.entity.getTransform().getPosition()));
		MainApp.restaurant.engine.addEntity(entity);
		return entity;
	}

	public Vector3f[] getSeats() {
		return seats;
	}

	public boolean[] getSeatTaken() {
		return seatTaken;
	}

	public Entity[] getFoodSpots() {
		return foodSpots;
	}

	public void setSeatTaken(int index, boolean taken) {
		seatTaken[index] = taken;
	}
	
	public Vector3f getSeat(int index) {
		return seats[index];
	}
	
	public Vector3f getSeatIfOpen(int index) {
		boolean taken = seatTaken[index];
		if(!taken)
			return seats[index];
		return null;
	}
	
	public boolean hasFood(int seat) {
		ModelComponent comp = (ModelComponent) foodSpots[seat].getComponent("Model");
		return (comp.getMesh() != null);
	}
	
	public void setFood(Vao vao, int seat) {
		ModelComponent comp = (ModelComponent) foodSpots[seat].getComponent("Model");
		comp.setMesh(vao);
	}
	
	public boolean hasOpenSeat() {
		for(boolean taken : seatTaken) {
			if(!taken)
				return true;
		}
		return false;
	}

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getComponentType() {
		return "Table";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new TableComponent();
	}

}
