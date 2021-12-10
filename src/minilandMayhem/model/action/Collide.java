package minilandMayhem.model.action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.Danger;
import minilandMayhem.model.entities.Door;
import minilandMayhem.model.entities.Key;
import minilandMayhem.model.entities.RobotMario;
import minilandMayhem.model.entities.Wall;

public class Collide implements Action {

	private Entity collider;
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
		
		CollisionEvent collide =(CollisionEvent) event;
		collider =collide.getCollidedEntity();
		RobotMario mario = (RobotMario)event.getOwnerEntity();
		//ist der Mario inaktiv, passiert nichts
		if(mario.getIsActive()) {
		
			//mit einer Wand kollidiert:
			if (collider instanceof Wall) { 
				if(!mario.collided) {
					mario.changeDirection();
					mario.collided=true;
				}
			
				//mit einer Tür kollidiert
			}else if(collider instanceof Door) { 
				Door d = (Door)collider;
			
				//Kollidierender Mario hat Schlüssel oder Tür bereits aufgeschlossen
				if(mario.getHasKey() || d.getUnlocked()) { 
					//TODO: gib Punkte
					mario.destroy();
					if(!d.getUnlocked()) {
						d.unlock(); //schließe Tür auf
					}
				}else {
					if(!mario.collided) {
						mario.changeDirection();
						mario.collided=true;
					}
				}
				
			//mit einer Gefahr kollidiert
			}else if(collider instanceof Danger) {
				//ziehe Punkte ab
				mario.destroy();
				
			//mit einem Schlüssel kollidiert	
			}else if(collider instanceof Key) {
				//gib diesem Mario einen Schlüssel (auch graphisch anzeigen) und entferne das Pickup
				Key k = (Key)collider;
				k.destroy();
				mario.collectKey();
			}
		
		}
	}

}
