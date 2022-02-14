package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

public class Danger extends Entity {

	public Danger(String entityID) {
		super(entityID);
		
		try {
			this.addComponent(new ImageRenderComponent(new Image("assets/Spikes.png")));
		} catch (SlickException e) {
			System.out.println("Gefahrenbild konnte nicht geladen werden");
		}
		this.setSize(new Vector2f(48,48));
	}

}
