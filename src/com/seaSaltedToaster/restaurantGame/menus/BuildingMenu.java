package com.seaSaltedToaster.restaurantGame.menus;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingCategory;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.menus.iconMaker.IconMaker;
import com.seaSaltedToaster.restaurantGame.menus.languages.LanguageManager;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.HorizontalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.VerticalAlignment;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignX;
import com.seaSaltedToaster.simpleEngine.uis.constraints.position.AlignY;
import com.seaSaltedToaster.simpleEngine.uis.constraints.scale.RelativeScale;
import com.seaSaltedToaster.simpleEngine.uis.layouts.HorizontalLayout;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.SmoothFloat;

public class BuildingMenu extends UiComponent {

	//Building
	private BuildingManager manager;
	private Engine engine;
	
	//Animation
	private SmoothFloat yValue;
	private boolean isOpen = false;
	
	//Objects
	public static IconMaker iconMaker;
	private UiComponent[] buttons;
	
	//Backing
	private UiComponent categoryBacking;
	private Text categoryName;
	
	//Categories
	private BuildingCategory curCategory;
	
	public BuildingMenu(BuildingManager manager, Engine engine) {
		super(2);
		this.manager = manager;
		this.engine = engine;
		BuildingMenu.iconMaker = new IconMaker(engine);
		createPanel();
		createButtons(engine, BuildingList.getRoot());
		addCategoryName(curCategory, engine);
		this.setInteractable(true, engine);
	}
	
	@Override
	public void updateSelf() {
		categoryName.setPosition(categoryBacking.getPosition());
		
		yValue.update(Window.DeltaTime);
		float newY = yValue.getValue();
		AlignY align = (AlignY) this.getConstraints().getYConstraint();
		align.setGap(newY);
		
		if(isOpen)
			MainApp.menuFocused = isHovering;	
	}
	
	@Override
	public void onClick() {
		int index = 0;
		for(UiComponent button : buttons) {
			if(button == null) continue;
			if(button.isHovering()) {
				boolean isBuilding = curCategory.isIndexBuilding(index);
				if(isBuilding) {
					Building build = curCategory.getChildBuildings().get(index - curCategory.getChildCategories().size());
					manager.setBuilding(true);
					manager.setCurrentBuilding(build);
				} else {
					BuildingCategory newCategory = curCategory.getChildCategories().get(index);
					clearButtons();
					createButtons(engine, newCategory);
				}
				return;
			}
			index++;
		}
	}
		
	@Override
	public void onHover() {
		manager.setBuilding(false);
	}
	
	@Override
	public void whileHover() {
		for(UiComponent button : buttons) {
			if(button == null) return;
			if(button.isHovering())
				button.setColor(0.5f);
			else
				button.setColor(0.0f);
		}
	}
	
	@Override
	public void stopHover() {
		for(UiComponent button : buttons) {
			if(button == null) return;
			button.setColor(0.0f);
		}
	}
	
	public void show() {
		if(curCategory != BuildingList.getRoot() && isHovering()) {
			clearButtons();
			BuildingCategory cat = curCategory.getParent();
			createButtons(engine, cat);
			addCategoryName(cat, engine);
			return;
		}
		
		if(isOpen) {
			this.yValue.setTarget(-0.25f);
			this.isOpen = false;
		} else if(!isOpen) {
			this.yValue.setTarget(0.05f);
			this.isOpen = true;
		}
	}
	
	private void addCategoryName(BuildingCategory curCategory, Engine engine) {
		this.removeComponent(categoryBacking);
		this.removeComponent(categoryName);
		
		this.categoryBacking = new UiComponent(4);
		this.categoryBacking.setColor(0.15f);
		UiConstraints backCons = categoryBacking.getConstraints();
		backCons.setX(new AlignX(HorizontalAlignment.CENTER));
		float height = 0.5f;
		backCons.setY(new AlignY(VerticalAlignment.TOP, -height * 2.0f));
		backCons.setHeight(new RelativeScale(height));
		backCons.setWidth(new RelativeScale(0.2f));
		this.addComponent(categoryBacking);	
		
		this.categoryName = new Text(curCategory.getName(), 1.0f, 4);
		this.categoryName.setColor(1.0f);
		UiConstraints textCons = categoryName.getConstraints();
		textCons.setY(new AlignY(VerticalAlignment.MIDDLE, 0.0f));
		categoryBacking.addComponent(categoryName);
		
		LanguageManager.addText("buildMenu_" + curCategory.getName().toLowerCase(), categoryName);
	}
		
	private void createButtons(Engine engine, BuildingCategory category) {
		this.curCategory = category;
		int count = category.getChildCategories().size() + category.getChildBuildings().size();
		this.buttons = new UiComponent[16];
		for(int i = 0; i < count; i++) {
			boolean isBuilding = category.isIndexBuilding(i);
			
			BuildingItem item = null;
			if(isBuilding) {
				Building building = category.getChildBuildings().get(i - category.getChildCategories().size());
				item = new BuildingItem(building);
			} else {
				BuildingCategory newCat = category.getChildCategories().get(i);
				item = new BuildingItem(newCat);
			}
			
			item.setInteractable(true, engine);
			buttons[i] = item;
			this.addComponent(item);
		}
		if(!category.isTop()) {
			//TODO add back button
		}
		addCategoryName(category, engine);
	}
	
	private void clearButtons() {
		for(UiComponent comp : buttons) {
			if(comp == null) continue;
			this.removeComponent(comp);
			comp.setActive(false);
			comp = null;
		}
		buttons = null;
	}
	
	private void createPanel() {
		UiConstraints cons = new UiConstraints();
		cons.setX(new AlignX(HorizontalAlignment.CENTER));
		cons.setY(new AlignY(VerticalAlignment.BOTTOM, 0.0f));
		cons.setLayout(new HorizontalLayout(-0.05f, 0.05f));
		this.setConstraints(cons);
		this.setScale(0.75f, 0.075f);
		this.setColor(0.15f);
		
		this.yValue = new SmoothFloat(-0.25f);
	}

	public boolean isOpen() {
		return isOpen;
	}

	public IconMaker getIconMaker() {
		return iconMaker;
	}

}