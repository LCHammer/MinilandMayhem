package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

public class Wall extends Entity{

	public Wall(String entityID) {
		super(entityID);
		
		try {
		this.addComponent(new ImageRenderComponent(new Image("/assets/wall.png")));
		}catch (SlickException e) {
			System.out.println("Wandbild konnte nicht geladen werden");
		}
		this.setPassable(false);
	}

}
