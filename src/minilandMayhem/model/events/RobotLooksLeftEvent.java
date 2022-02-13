package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;
import minilandMayhem.model.entities.Robot;

public class RobotLooksLeftEvent extends Event {

	public RobotLooksLeftEvent(String id) {
		super("LooksLeftEvent");
	}

	@Override
	/**
	 * triggert das Event, wenn der Roboter (=Besitzer des Events) nach links schaut
	 */
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		Robot r = (Robot) this.owner;
		if(r.getLooksRight()) {
		return false;
		}else {
			return true;
		}
	}

}
