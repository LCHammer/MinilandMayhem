package minilandMayhem.model.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import minilandMayhem.model.entities.Mario;
import minilandMayhem.ui.GamePlayState;

public class NoMarioLeftEvent extends Event {

	public NoMarioLeftEvent(String id) {
		super("NoMarioEvent");
	}

	@Override
	/**
	 * Dieses Event feuert, wenn kein Mario übrig ist. (Und das Spiel wird dadurch beendet.)
	 */
	protected boolean performAction(GameContainer gc, StateBasedGame game, int delta) {
		
		for(Mario m: GamePlayState.marios) {
			if(StateBasedEntityManager.getInstance().hasEntity(game.getCurrentStateID(), m.getID())){
				return false;
			}
		}
		//damit ein Level ohne Marios nicht sofort beendet wird
		if(GamePlayState.marios.size()!=0) {
			return true;
		}else {
			return false;
		}
		}
	}