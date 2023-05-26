package com.seaSaltedToaster.restaurantGame.menus.buildingSelector;

import com.seaSaltedToaster.MainApp;
import com.seaSaltedToaster.restaurantGame.building.Building;
import com.seaSaltedToaster.restaurantGame.building.BuildingManager;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingCategory;
import com.seaSaltedToaster.restaurantGame.building.categories.BuildingList;
import com.seaSaltedToaster.restaurantGame.menus.iconMaker.IconMaker;
import com.seaSaltedToaster.restaurantGame.tools.ColorPalette;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.renderer.Window;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.constraints.XAlign;
import com.seaSaltedToaster.simpleEngine.uis.constraints.UiConstraints;
import com.seaSaltedToaster.simpleEngine.uis.constraints.YAlign;
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
	
	//Tooltip
	private ItemTooltip tooltip;
	
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
		this.tooltip = new ItemTooltip(this, engine);
	}
	
	@Override
	public void updateSelf() {		
		yValue.update(Window.DeltaTime);
		float newY = yValue.getValue();
		AlignY align = (AlignY) this.getConstraints().getYConstraint();
		align.setGap(newY);
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
		MainApp.menuFocused = true;
	}
	
	@Override
	public void whileHover() {
		for(UiComponent button : buttons) {
			if(button == null) return;
			
			BuildingItem item = (BuildingItem) button;
			if(item.isHovering())
				item.hover();
			else
				item.unHover();
		}
		MainApp.menuFocused = true;
	}
	
	@Override
	public void stopHover() {
		MainApp.menuFocused = false;
		for(UiComponent button : buttons) {
			if(button == null) continue;
			BuildingItem item = (BuildingItem) button;
			item.unHover();
		}
	}
	
	public void show() {
		this.tooltip.close();
		
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
		backCons.setX(new AlignX(XAlign.CENTER));
		float height = 0.33f;
		backCons.setY(new AlignY(YAlign.TOP, -height * 2.0f));
		backCons.setHeight(new RelativeScale(0.33f));
		backCons.setWidth(new RelativeScale(1.0f));
		this.addComponent(categoryBacking);	
		
		this.categoryName = new Text(curCategory.getName(), 0.825f, 4);
		this.categoryName.setColor(1.0f);
		UiConstraints textCons = categoryName.getConstraints();
		textCons.setX(new AlignX(XAlign.LEFT, 0.025f));
		textCons.setY(new AlignY(YAlign.TOP, -0.2f));
		categoryBacking.addComponent(categoryName);
	}
		
	private void createButtons(Engine engine, BuildingCategory category) {
		this.curCategory = category;
		int count = category.getChildCategories().size() + category.getChildBuildings().size();
		this.buttons = new UiComponent[32];
		for(int i = 0; i < count; i++) {
			boolean isBuilding = category.isIndexBuilding(i);
			
			BuildingItem item = null;
			if(isBuilding) {
				Building building = category.getChildBuildings().get(i - category.getChildCategories().size());
				if(building.show)
					item = new BuildingItem(building, this);
				else
					continue;
			} else {
				BuildingCategory newCat = category.getChildCategories().get(i);
				item = new BuildingItem(newCat, this);
			}
			
			item.setInteractable(true, engine);
			buttons[i] = item;
			this.addComponent(item);
		}
		if(!category.isTop()) {
			//TODO add back button
		}
		addCategoryName(category, engine);
		
		this.removeComponent(tooltip);
		this.addComponent(tooltip);
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
		cons.setX(new AlignX(XAlign.CENTER));
		cons.setY(new AlignY(YAlign.BOTTOM, 0.0f));
		cons.setLayout(new HorizontalLayout(0.0f, 0.05f));
		this.setConstraints(cons);
		this.setScale(0.75f, 0.075f);
		this.setColor(ColorPalette.MAIN_LIGHT);
		this.setClippingBounds(0.125f, 0, 1.75f, 1.0f); //TODO cutoff items
		
		this.yValue = new SmoothFloat(-0.25f);
	}

	public Engine getEngine() {
		return engine;
	}

	public ItemTooltip getTooltip() {
		return tooltip;
	}

	public boolean isOpen() {
		return isOpen;
	}

	public IconMaker getIconMaker() {
		return iconMaker;
	}

}
