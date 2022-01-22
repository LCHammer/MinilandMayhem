package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;
import minilandMayhem.model.entities.Mario;

public class MarioLooksLeftEvent extends Event {

	public MarioLooksLeftEvent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		Mario r = (Mario) this.owner;
		if(r.getLooksRight()) {
		return false;
		}else {
			return true;
		}
	}

}
