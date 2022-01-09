package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.Event;
import eea.engine.event.basicevents.LoopEvent;

public class Beam extends Entity {
	
	private BeamSocket first;
	private BeamSocket second;

	public Beam(String entityID,BeamSocket first,BeamSocket second) {
		super(entityID);
		this.first=first;
		this.second=second;
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/wall.png")));
			}catch (SlickException e) {
				System.out.println("Wandbild konnte nicht geladen werden");
			}
			this.setSize(new Vector2f(50,50));
	}
	
	
	/**
	 * destroys this Beam
	 */
	public void destroy() {
		first.removeSingleBeam(this);
		second.removeSingleBeam(this);
		Event l = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.addComponent(l);
	}


}
