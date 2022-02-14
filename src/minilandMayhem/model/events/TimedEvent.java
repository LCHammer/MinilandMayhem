package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;
import minilandMayhem.model.Timer;

public class TimedEvent extends Event {

	private long timeUntilTrigger;
	private Timer timer;
	public TimedEvent(String id, long time) {
		super(id);
		timeUntilTrigger = time;
		timer = new Timer();
	}
	
	@Override
	/**
	 * Dieses Event feuert, wenn genügend Zeit beim internen Timer vergangen ist.
	 * Pausiert zusätzlich den Timer, wenn das Spiel pausiert wird.d
	 */
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		if(gc.isPaused()) {
			timer.pause();
		}else {
			timer.unpause();
		}
		return timer.didEnoughTimePass(timeUntilTrigger);
	}
}
