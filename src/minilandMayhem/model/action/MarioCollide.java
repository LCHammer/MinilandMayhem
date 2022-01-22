package minilandMayhem.model.action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.Door;
import minilandMayhem.model.entities.Key;
import minilandMayhem.model.entities.Mario;

public class MarioCollide extends Collide {

	
	private Entity collider;
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
		CollisionEvent collide =(CollisionEvent) event;
		this.collider =collide.getCollidedEntity();
		
		Mario mario = (Mario)event.getOwnerEntity();
		
		//mit einer T�r kollidiert
		if(collider instanceof Door) { 
			Door d = (Door)collider;
			
			//Kollidierender Mario hat Schl�ssel oder T�r bereits aufgeschlossen
			if(mario.getHasKey() || d.getUnlocked()) { 
				//TODO: gib Punkte
				mario.destroy();
				
				if(!d.getUnlocked()) {
					d.unlock(); //schlie�e T�r auf
				}
			}else {
				if(canCollide(mario,collider)){
					mario.changeDirection();
				}
			}
			
			//mit einem Schl�ssel kollidiert	
			}else if(collider instanceof Key) {
				//gib diesem Mario einen Schl�ssel (auch graphisch anzeigen) und entferne das Pickup
				Key k = (Key)collider;
				k.destroy();
				mario.collectKey();
			}else {
				super.update(gc, game, delta, event);
			}
	}
}
