package com.seaSaltedToaster;

import com.seaSaltedToaster.restaurantGame.WorldCamera;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.uis.UiComponent;
import com.seaSaltedToaster.simpleEngine.uis.text.Fonts;
import com.seaSaltedToaster.simpleEngine.uis.text.Text;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class MainApp {
	
	public static Transform transform = new Transform(new Vector3f(0.0f,0.0f,0.0f), new Vector3f(0,0,0), new Vector3f(1.0f,1.0f,1.0f));
	public static Transform transform2 = new Transform(new Vector3f(0.0f,0.0f,0.0f), new Vector3f(0,0,0), new Vector3f(1.0f,1.0f,1.0f));

	public static void main(String[] args) {
		//Engine
		Engine engine = new Engine("Engine Test", ClientConfigs.WINDOW_X, ClientConfigs.WINDOW_Y);
		engine.setCamera(new WorldCamera(engine));
		
		//Assets
		Fonts.loadFonts(engine);
		
		//Ground
		Ground ground = new Ground(10, 1, engine);
		ground.generateGround(engine);
		
		//Ray
		Raycaster ray = new Raycaster(engine);
		ray.ground = ground;
		
		//Uis
		UiComponent ui = new UiComponent(0);
		Text text = new Text("Bob", 1, 0);
		text.setColor(1.0f);
		ui.addComponent(text);
		engine.addUi(ui);
		
		//Models
		Vao wall = engine.getObjLoader().loadObjModel("simpleWall");
		Vao floor = engine.getObjLoader().loadObjModel("simpleFloor");

		while(!engine.getWindow().shouldClose()) {	
			engine.prepareFrame();
			ground.update(engine);
			engine.render(wall, transform);
			engine.renderUis();
			engine.update();
		}
		
		engine.getWindow().closeWindow();
	}

}