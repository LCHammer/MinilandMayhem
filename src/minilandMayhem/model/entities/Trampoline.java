package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

public class Trampoline extends Entity {

	public Trampoline(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
		try {
			this.addComponent(new ImageRenderComponent(new Image("assets/fire.png")));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			System.out.println("Trampolinbild konnte nicht geladen werden");
		}
	}

}
