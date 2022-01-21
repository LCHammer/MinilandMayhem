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
		// TODO Auto-generated constructor stub
		timer = new Timer();
	}
	
	@Override
	/**
	 * Dieses Event feuert, wenn genügend Zeit beim internen Timer vergangen ist.
	 */
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		return timer.didEnoughTimePass(timeUntilTrigger);
	}
}
