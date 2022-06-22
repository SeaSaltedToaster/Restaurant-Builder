package com.seaSaltedToaster;

import com.seaSaltedToaster.restaurantGame.WorldCamera;
import com.seaSaltedToaster.restaurantGame.ground.Ground;
import com.seaSaltedToaster.restaurantGame.tools.Raycaster;
import com.seaSaltedToaster.simpleEngine.Engine;
import com.seaSaltedToaster.simpleEngine.entity.Transform;
import com.seaSaltedToaster.simpleEngine.models.Vao;
import com.seaSaltedToaster.simpleEngine.utilities.Vector3f;

public class MainApp {
	
	public static Transform transform = new Transform(new Vector3f(0.0f,0.0f,0.0f), new Vector3f(0,0,0), new Vector3f(1.0f,1.0f,1.0f));
	public static Transform transform2 = new Transform(new Vector3f(0.0f,0.0f,0.0f), new Vector3f(0,0,0), new Vector3f(1.0f,1.0f,1.0f));

	public static void main(String[] args) {
		//Engine
		Engine engine = new Engine("Engine Test", ClientConfigs.WINDOW_X, ClientConfigs.WINDOW_Y);
		engine.setCamera(new WorldCamera(engine));		
		
		//Ground
		Ground ground = new Ground(10, 2, engine);
		ground.generateGround(engine);
		
		Raycaster ray = new Raycaster(engine);
		ray.ground = ground;
		
		Vao wall = engine.getObjLoader().loadObjModel("simpleWall");
				
		while(!engine.getWindow().shouldClose()) {	
			engine.prepareFrame();
			ground.update(engine);
			engine.render(wall, transform);
			engine.update();
		}
		
		engine.getWindow().closeWindow();
	}

}