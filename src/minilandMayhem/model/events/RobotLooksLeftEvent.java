package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;
import minilandMayhem.model.entities.Mario;
import minilandMayhem.model.entities.Robot;

public class RobotLooksLeftEvent extends Event {

	public RobotLooksLeftEvent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		Robot r = (Robot) this.owner;
		if(r.getLooksRight()) {
		return false;
		}else {
			return true;
		}
	}

}