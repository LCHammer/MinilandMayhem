package minilandMayhem.model.entities;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.event.Event;
import eea.engine.event.basicevents.LoopEvent;
import minilandMayhem.ui.GamePlayState;

public class Fire extends Robot {

	private boolean destroyed;
	
	public Fire(String entityID) {
		super(entityID);
		destroyed = false;
	}
	
	/**
	 * Zerstroert dieses Feuer
	 */
	public void destroy() {
		if(!this.destroyed) {
			this.destroyed = true;
			GamePlayState.score += 100;
			this.removeEntity();
		}
	
	}

}
