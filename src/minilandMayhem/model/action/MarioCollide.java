package minilandMayhem.model.action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.Collectable;
import minilandMayhem.model.entities.Door;
import minilandMayhem.model.entities.Key;
import minilandMayhem.model.entities.Mario;
import minilandMayhem.ui.GamePlayState;

public class MarioCollide extends Collide {

	
	private Entity collider;
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
		CollisionEvent collide =(CollisionEvent) event;
		this.collider =collide.getCollidedEntity();
		
		Mario mario = (Mario)event.getOwnerEntity();
		//mit einer Tür kollidiert
		if(collider instanceof Door) { 
			Door d = (Door)collider;
			
			//Kollidierender Mario hat Schlüssel oder Tür bereits aufgeschlossen
			if(mario.getHasKey() || d.getUnlocked()) { 
				//TODO: gib Punkte
				mario.score();
				
				if(!d.getUnlocked()) {
					d.unlock(); //schließe Tür auf
				}
			}else {
				if(canCollide(mario,collider)){
					mario.changeDirection();
				}
			}
			
				
		//Mit einem anderen Mario kollidiert
		}else if(collider instanceof Mario) {
			if(canCollide(mario,collider)) {
				Mario other = (Mario) collider;
				
				if(mario.getFalling()) {
					mario.land();
					mario.changeDirection();
				}
				else if(other.getFalling()) {
					other.land();
					other.changeDirection();
				}else {
					mario.changeDirection();
					other.changeDirection();
				}
			}
			
		}//mit einem Collectable kollidiert
		else if(collider instanceof Collectable) {
			Collectable c = (Collectable)collider;
			c.collect(mario);
		}else {
				super.update(gc, game, delta, event);
		}
	}
}
