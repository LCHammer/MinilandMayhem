package minilandMayhem.model.entities;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.LoopEvent;

public abstract class Collectable extends Entity {

	protected boolean collected;
	
	public Collectable(String entityID) {
		super(entityID);
		collected = false;
		this.setSize(new Vector2f(50,50));
	}
	
	/**
	 * dieses collectable wurde eingesammelt und entsprechende Methoden der erbenden Klassen sollen aufgerufen werden
	 * @param m Mario, welcher dieses Collectable aufgesammelt hat
	 */
	public void collect(Mario m) {
		if(!collected) {
			performPickup(m);
			this.collected = true;
			this.destroy();
		}
	}

	/**
	 * führt die bei Collision mit diesem Collectable die entsprechende Methode der erbenden Klasse aus
	 * @param m Mario, welcher dieses Collectable aufgesammelt hat
	 */
	public abstract void performPickup(Mario m);
	
	
	/**
	 * zerstoert dieses Collectable
	 */
	public void destroy() {
		LoopEvent l  = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.addComponent(l);
	}
}
