package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;
import minilandMayhem.model.entities.Robot;

public class RobotLooksRightEvent extends Event {

	public RobotLooksRightEvent(String id) {
		super(id);
		
	}

	@Override
	/**
	 * triggert das Event, wenn der Roboter (=Besitzer des Events) nach rechts schaut
	 */
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		Robot r = (Robot) this.owner;
		if(r.getLooksRight()) {
			return true;
		}else {
			return false;
		}
	}

}
