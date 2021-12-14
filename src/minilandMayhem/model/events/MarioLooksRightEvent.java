package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;
import minilandMayhem.model.entities.RobotMario;

public class MarioLooksRightEvent extends Event {

	public MarioLooksRightEvent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		RobotMario r = (RobotMario) this.owner;
		if(r.getLooksRight()) {
			return true;
		}else {
			return false;
		}
	}

}
