package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.Event;
import eea.engine.event.basicevents.LoopEvent;

public class Wall extends Entity{

	/**
	 * Entitaet einer Wand bzw. des Bodens. Objekte können mit Wänden kollidieren.
	 * @param entityID Name der Wand
	 */
	public Wall(String entityID) {
		super(entityID);
		
		try {
		this.addComponent(new ImageRenderComponent(new Image("/assets/wall.png")));
		}catch (SlickException e) {
			System.out.println("Wandbild konnte nicht geladen werden");
		}
		this.setPassable(false);
		this.setSize(new Vector2f(45,45));
	}
	
	
	
}
