package com.seaSaltedToaster.restaurantGame.ai.person;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.restaurantGame.ai.person.customer.CreateParty;
import com.seaSaltedToaster.restaurantGame.ai.person.customer.FindTable;
import com.seaSaltedToaster.restaurantGame.ai.person.customer.IdleStay;
import com.seaSaltedToaster.restaurantGame.save.SaveSystem;
import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class ActionComponent extends Component {

	//List of actions and our current action
	private List<Action> actions;
	private Action curAction;
	
	//Type of NPC the action is done on
	private String tree;
	public boolean doTree = true;
		
	public ActionComponent(String tree) {
		this.actions = new ArrayList<Action>();
		this.tree = tree;
	}
	
	@Override
	public void init() {
		startBehaviourTree();
	}
	
	public void startBehaviourTree() {
		actions.remove(null);
		if(!actions.isEmpty() || !doTree) return;
		
		for(int i = 0; i < actions.size(); i++) {
			Action action = actions.get(i);
			action.actionIndex = i;
			action.object = getEntity();
		}
		
		//Switch by type of NPC
		switch(tree.trim()){
		case "ChefOld":
			//Add the starting action of the chef
			break;
		case "Customer":
			//Starting wait on customer spawn (party size based on capacity)
			this.actions.add(new CreateParty());
			
			//Once we have our group, find a table to sit at
			this.actions.add(new FindTable());
			this.actions.add(new IdleStay());
			
			//The FindTable class will continue the rest of the tree
			break;
		case "WaiterOld":
			//Start action of the waiter NPC
			break;
		
		case "Waiter1":
			//New waiter behaviour tree
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
		
		//Set action entityes and indexes
		for(int i = 0; i < actions.size(); i++) {
			if(actions.get(i) == null)
				continue;
			Action action = actions.get(i);
			action.actionIndex = i;
			action.object = getEntity();
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
	
	public void save(SaveSystem system) {
		for(Action action : actions)
			action.saveAction(system);
	}

	public Action getCurAction() {
		return curAction;
	}

	public String getTree() {
		return tree;
	}
	
	public void setTree(String string) {
		this.tree = string;
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
		return new ActionComponent(tree);
	}


}
