package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.BeamSocket;
import minilandMayhem.model.entities.RobotMario;
import minilandMayhem.model.entities.Wall;

public class GroundCollision extends CollisionEvent{
	
	public GroundCollision() {
		
	}
	
	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		if(super.performAction(gc, game, delta)) {
			Entity e = super.getCollidedEntity();
			if(e instanceof Wall || e instanceof BeamSocket) {
				return true;
			}else if( e instanceof RobotMario) {
				System.out.println("too much!");
			}
			
		}
		return false;
		
	}

}
