package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.*;


public class FlatGroundCollision extends CollisionEvent{
	
	
	@Override
	/**
	 * Das event wird getriggert, wenn eine Kollision mit Wand/Boden oder einem Sockel passiert, oder einem Stahlträger,
	 * welcher flach (rotation == 0) ist.
	 */
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		if(super.performAction(gc, game, delta)) {
			Entity e = super.getCollidedEntity();
			if(e instanceof Wall || e instanceof BeamSocket) {
				return true;
			} else if(e instanceof Beam) {
				Beam b = (Beam)e;
				if(b.getRotation()==0) {
					return true;
				}
			}
		}
		return false;
		
	}

}
