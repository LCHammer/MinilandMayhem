package minilandMayhem.model.action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.component.Component;
import eea.engine.event.Event;
import eea.engine.event.basicevents.LoopEvent;
import minilandMayhem.model.entities.RobotMario;

public class TurnMarioAround implements Action {

	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
		RobotMario r = (RobotMario)event.getOwnerEntity();
		if(!r.collided) {
		r.removeComponent("LoopEvent");
		Event e = new LoopEvent();
			if(r.getLooksRight()) {
				e.addAction(new MoveLeftAction(r.speed));
				r.addComponent(e);
			}else {
				e.addAction(new MoveRightAction(r.speed));
				r.addComponent(e);
			}
			r.changeDirection();
			r.collided=true;
		}
			
	}

	
}
