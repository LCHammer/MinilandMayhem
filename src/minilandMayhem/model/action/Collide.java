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
	/**
	 * Standardverhalten f�r jeden Roboter, wenn er mit anderen Entities kollidiert.
	 * Genauere Beschreibungen sind als Kommentare jeweils vorhanden
	 */
	public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
		
		CollisionEvent collide =(CollisionEvent) event;
		collider =collide.getCollidedEntity();
		Robot robot = (Robot)event.getOwnerEntity();
			
		
			//mit einer Wand (oder einer Kanone) kollidiert:
			if (collider instanceof Wall || collider instanceof BillBlaster ) { 
				robot.endJump();
		
				if(canCollide(robot,collider)){
					robot.changeDirection();
				}
			//mit einem Stahltraeger kollidiert
			}else if(collider instanceof BeamSocket) {
				robot.endJump();
				//ist es der Beamsocket, an dem er ankommen soll?
				//TODO: nicht nur instanceof sondern auch equals!
				BeamSocket socket = (BeamSocket)collider;
				if(robot.getIsWalkingUp() ) {
					if( canCollide(robot,socket)) {
						robot.endWalkingUp(socket);
					}
					
				}else if(robot.getIsWalkingDown()){
					robot.endWalkingDown(socket);
					
					
				}//Der Mario ist beim normalen Laufen mit einem Sockel kollidiert
				else {
					if(canCollide(robot,collider)){
						robot.changeDirection();
					}
				}
				
			}
			//Kollision mit Stahltraeger
			else if(collider instanceof Beam) {
				robot.endJump();
				Beam b = (Beam)collider;
				//soll nur kollidieren, wenn er aktuell nicht hoch- oder runterlaeuft.
				if(!robot.getWalkingUpDown()) {
					if(b.getRotation() > 44f || b.getRotation() < -44f 
					||(b.getUpRight() && robot.getPosition().x > b.getPosition().x)
					||(!b.getUpRight() && robot.getPosition().x < b.getPosition().x)) {
					
					//Stahltraeger zu steil zum hochlaufen oder falsche Seite
						if(canCollide(robot,collider)){
							robot.changeDirection();
						}
					
					}else {	
						//Stahltraeger flach genug, um ihn hochzulaufen
						if(robot.getLooksRight() == b.getUpRight() && b.getRotation() != 0f) {
							robot.walkOnBeam(b);
						} 					
					}
				}else if( (b.getRotation() > 44f || b.getRotation() < -44f) && canCollide(robot,collider) )		{
					robot.changeDirection();
				}
				//mit einer Gefahr kollidiert
			}else if(collider instanceof Danger) {
				//ziehe Punkte ab
				robot.destroy();
			
			//mit einem Trampolin kollidiert
			} else if(collider instanceof Trampoline) {
				robot.jump();
				
				
			//Mit einem Mario kollidiert
			//Beachte: Dies ist das Standardverhalten f�r das Feuer, welches die Marios zerst�rt.
			//Diese besitzen jedoch ein eigenes Verhalten bei Kollision mit einem anderen Mario,
			//welches dieses hier �berschreibt
			}else if(collider instanceof Mario) {
				Mario m = (Mario) collider;
				if(m.getHasPowerUp()) {
					robot.destroy();
				}else {
				m.destroy();
				}
				//Dieses Feuer ist mit einem anderen Feuer kollidiert
			}else if(collider instanceof Fire && robot instanceof Fire) {
				if(canCollide(robot,collider)) {
					Fire other = (Fire) collider;
					
					if(robot.getFalling()) {
						robot.land();
						robot.changeDirection();
					}
					else if(other.getFalling()) {
						other.land();
						other.changeDirection();
					}else {
						robot.changeDirection();
						other.changeDirection();
					}
				}
			}
			
			
		
		}
	
	/**
	 * berechnet, ob der R aufgrund der Kollision mit der Entity seine Richtung aendern darf oder nicht.
	 * Er darf die Richtung aendern, wenn er in Richtung Entity schaut und diese vor ihm (und nicht hinter ihm) liegt.
	 * @param robot Roboter, welcher kollidiert ist
	 * @param collider Entity, mit der der Roboter kollidiert ist.
	 * @return true, wenn der Roboter aufgrund der Kollision mit der Entity seine Richtung aendern darf. Sonst false. 
	 */
	public boolean canCollide(Robot robot, Entity collider) {
		return ( robot.getLooksRight() && robot.getPosition().x < collider.getPosition().x) ||
				(!robot.getLooksRight() && robot.getPosition().x > collider.getPosition().x);
	}

}
