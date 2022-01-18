package minilandMayhem.model.action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.*;

public class Collide implements Action {

	private Entity collider;
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
		
		CollisionEvent collide =(CollisionEvent) event;
		collider =collide.getCollidedEntity();
		RobotMario mario = (RobotMario)event.getOwnerEntity();
		//ist der Mario inaktiv, passiert nichts
		if(mario.getIsActive()) {
			
			//System.out.println("coll");
		
			//mit einer Wand kollidiert:
			if (collider instanceof Wall ) { 
		
				if(canCollide(mario,collider)){
					mario.changeDirection();
				}
			//mit einem Stahltraeger kollidiert
			}else if(collider instanceof BeamSocket) {
				//ist es der Beamsocket, an dem er ankommen soll?
				//TODO: nicht nur instanceof sondern auch equals!
				BeamSocket socket = (BeamSocket)collider;
				if(mario.getIsWalkingUp() ) {
					if( canCollide(mario,socket)) {
						mario.endWalkingUp(socket);
					}
					
				}else if(mario.getIsWalkingDown()){
					mario.endWalkingDown(socket);
					
					
				}//Der Mario ist beim normalen Laufen mit einem Sockel kollidiert
				else {
					if(canCollide(mario,collider)){
						mario.changeDirection();
					}
				}
				
			}
			//Kollision mit Stahltraeger
			else if(collider instanceof Beam) {
				Beam b = (Beam)collider;
				if(b.getRotation() > 44f || b.getRotation() < -44f 
						||(b.getUpRight() && mario.getPosition().x > b.getPosition().x)
						||(!b.getUpRight() && mario.getPosition().x < b.getPosition().x)) {
					//Stahltraeger zu steil zum hochlaufen
					if(canCollide(mario,collider)){
						mario.changeDirection();
						
						System.out.println(mario.getPosition().y);
					}
				}else {
					
					//Stahltraeger flach genug, um ihn hochzulaufen
					if(mario.getLooksRight() == b.getUpRight() && b.getRotation() != 0f) {
						mario.walkOnBeam(b);
						
					} 				
					
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
					if(canCollide(mario,collider)){
						mario.changeDirection();
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
	
	/**
	 * berechnet, ob der Mario aufgrund der Kollision mit der Entity seine Richtung aendern darf oder nicht.
	 * Er darf die Richtung aendern, wenn er in Richtung Entity schaut und diese vor ihm (und nicht hinter ihm) liegt.
	 * @param mario 
	 * @param collider Entity, mit der der Mario kollidiert ist.
	 * @return true, wenn der Mario aufgrund der Kollision mit der Entity seine Richtung aendern darf. Sonst false. 
	 */
	public boolean canCollide(RobotMario mario, Entity collider) {
		return ( mario.getLooksRight() && mario.getPosition().x < collider.getPosition().x) ||
				(!mario.getLooksRight() && mario.getPosition().x > collider.getPosition().x);
	}

}
