package com.seaSaltedToaster.restaurantGame.ai.person;

import java.util.ArrayList;
import java.util.List;

import com.seaSaltedToaster.simpleEngine.entity.componentArchitecture.Component;

public class ActionComponent extends Component {

	//List of actions and our current action
	private List<Action> actions;
	private Action curAction;
	
	//Type of NPC the action is done on
	private String tree;
		
	public ActionComponent(String tree) {
		this.actions = new ArrayList<Action>();
		this.tree = tree;
	}
	
	@Override
	public void init() {
		startBehaviourTree();
	}
	
	public void startBehaviourTree() {
		this.actions.clear();
		this.curAction = null;
		
		//Switch by type of NPC
		switch(tree.trim()){
		case "ChefOld":
			//Add the starting action of the chef
			break;
		case "CustomerOld":
			//Starting wait on customer spawn
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

	public Action getCurAction() {
		return curAction;
	}

	public String getTree() {
		return tree;
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
