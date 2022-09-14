package com.seaSaltedToaster.restaurantGame.ai.person;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.person.chef.WaitForChefStation;
import com.seaSaltedToaster.restaurantGame.ai.person.customer.WaitForTable;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.WaitForOrder;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.WaitForWorkstation;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class ActionComponent extends Component {

	//List of actions and our current action
	private List<Action> actions;
	private Action curAction;
	
	//Type of NPC the action is done on
	private PersonType type;
		
	public ActionComponent(PersonType type) {
		this.actions = new ArrayList<Action>();
		this.type = type;
	}
	
	@Override
	public void init() {
		//Switch by type of NPC
		switch(type){
		case CHEF:
			//Add the starting action of the chef
			actions.add(new WaitForChefStation(entity));
			break;
		case CUSTOMER:
			//Starting wait on customer spawn
			actions.add(new WaitForTable(entity)); //TODO randomize to make it less robotic
			break;
		case WAITER:
			//Start action of the waiter NPC
			actions.add(new WaitForWorkstation(entity));
			actions.add(new WaitForOrder(entity));
			break;
		default:
			break;
		}
	}
	
	@Override
	public void update() {
		//If there is not current action, get one
		if(curAction == null) {
			getNewAction();
		} 
		//If we do have an action
		else {
			//If it is done, go to the next
			if(curAction.isDone()) {
				getNewAction();
			}
			//If it is being run, update it this frame
			if(curAction != null) {
				curAction.update();
			}
		}
	}
	
	private void getNewAction() {
		//Get rid of the current action
		actions.remove(curAction);
		curAction = null;
		
		//If there are more actions, do the next one
		if(actions.size() > 0) {
			curAction = actions.get(0);	
			curAction.start();
		}
	}

	public List<Action> getActions() {
		return actions;
	}

	/*
	 * BASIC COMPONENT METHODS
	 */
	
	@Override
	public void reset() {
		
	}

	@Override
	public String getComponentType() {
		return "Action";
	}

	@Override
	public boolean changesRenderer() {
		return false;
	}

	@Override
	public Component copyInstance() {
		return new ActionComponent(type);
	}

}
