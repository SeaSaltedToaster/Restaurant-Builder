package com.seaSaltedToaster.restaurantGame.ai.person;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.person.chef.WaitForChefOrder;
import com.seaSaltedToaster.restaurantGame.ai.person.chef.WaitForChefStation;
import com.seaSaltedToaster.restaurantGame.ai.person.customer.WaitForTable;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.WaitForOrder;
import com.seaSaltedToaster.restaurantGame.ai.person.waiter.WaitForWorkstation;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class ActionComponent extends Component {

	//Actions
	private List<Action> actions;
	private Action curAction;
	
	//Type
	private PersonType type;
		
	public ActionComponent(PersonType type) {
		createAction();
		this.type = type;
	}
	
	private void createAction() {
		this.actions = new ArrayList<Action>();
	}
	
	@Override
	public void update() {
		if(curAction == null) {
			getNewAction();
		} else {
			if(curAction.isDone()) {
				getNewAction();
			}
			if(curAction != null)
				curAction.update();
		}
	}
	
	private void getNewAction() {
		actions.remove(curAction);
		curAction = null;
		if(actions.size() > 0) {
			curAction = actions.get(0);	
			curAction.start();
		}
	}

	@Override
	public void init() {
		switch(type){
		case CHEF:
			actions.add(new WaitForChefStation(entity));
			actions.add(new WaitForChefOrder(entity));
			break;
		case CUSTOMER:
			actions.add(new WaitForTable(entity)); //TODO randomize to make it less robotic
			break;
		case WAITER:
			actions.add(new WaitForWorkstation(entity));
			actions.add(new WaitForOrder(entity));
			break;
		default:
			break;
		}
	}

	public List<Action> getActions() {
		return actions;
	}

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
