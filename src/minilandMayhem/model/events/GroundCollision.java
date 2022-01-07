package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.*;


public class GroundCollision extends CollisionEvent{
	
	public GroundCollision() {
		
	}
	
	@Override
	/**
	 * Das event wird getriggert, wenn eine Kollision mit Wand/Boden oder einem Sockel passiert.
	 */
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		if(super.performAction(gc, game, delta)) {
			Entity e = super.getCollidedEntity();
			if(e instanceof Wall || e instanceof BeamSocket || e instanceof Beam) {
				return true;
			}
		}
		return false;
		
	}

}
