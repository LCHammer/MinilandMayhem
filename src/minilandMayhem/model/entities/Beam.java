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
	private boolean upRight;

	public Beam(String entityID,BeamSocket first,BeamSocket second, boolean isUpRight) {
		super(entityID);
		this.first=first;
		this.second=second;
		this.upRight=isUpRight;
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/Beam.png")));
			}catch (SlickException e) {
				System.out.println("Wandbild konnte nicht geladen werden");
			}
			this.setSize(new Vector2f(48,48));
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
	
	
	/**
	 * 
	 * @return erster Sockel
	 */
	public BeamSocket getFirst() {
		return this.first;
	}
	
	
	
	/**
	 * 
	 * @return zweiter Sockel
	 */
	public BeamSocket getSecond() {
		return this.second;
	}
	
	
	/**
	 * 
	 * @return true, wenn der Traeger nach oben rechts /oder unten links zeigt, false wenn er nach oben links/unten rechts zeigt
	 */
	public boolean getUpRight() {
		return this.upRight;
	}


}
