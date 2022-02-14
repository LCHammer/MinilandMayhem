package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.Beam;

public class IsOnBeamEvent extends CollisionEvent {

	@Override
	/**
	 * wird getriggert, wenn eine Kollision mit einem Stahltraeger stattfindet
	 */
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		
	if(super.performAction(gc, game, delta)) {
		Entity collider = super.getCollidedEntity();
		if(collider instanceof Beam) {
			return true;
		}
	}
	return false;
	
	}
}
