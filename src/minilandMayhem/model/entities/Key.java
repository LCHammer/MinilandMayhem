package minilandMayhem.model.entities;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.event.basicevents.LoopEvent;

public class Key extends Collectable {

	public Key(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
	}
	
	public void destroy() {
		LoopEvent l  = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.addComponent(l);
	}

}
