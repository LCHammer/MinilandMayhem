package minilandMayhem.model.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.NOTEvent;
import eea.engine.event.basicevents.LoopEvent;
import minilandMayhem.model.events.FlatGroundCollision;
import minilandMayhem.model.events.GroundCollision;
import minilandMayhem.model.events.IsOnBeamEvent;

public class PhysicsHitbox extends Entity{
	
	private Robot owner;

	//Ist eine Hitbox, welche immer unter dem jeweiligen Roboter ist, um auf Kollisionen mit dem Boden zu reagieren
	public PhysicsHitbox(String entityID, Vector2f position, Robot robot) {
		super(entityID);
		owner = robot;
		this.setVisible(false); 
		//Wichtig: setzt die Groesse der Hitbox. Diese darf nicht mit dem entsprechenden Roboter kollidieren,
		//da sonst alle anderen Kollisionen �berdeckt werden.
		this.setSize(new Vector2f(48,2));		
		
		
		//ueberprueft, ob der Roboter aktuell KEINEN Boden unter sich hat.
		Event falling = new NOTEvent(new GroundCollision());
		falling.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				if(!gc.isPaused()) {
					PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
					Robot r = self.getOwner();
					//Hat der Roboter keinen Boden unter sich, soll er fallen und dabei schneller werden.
					r.fall(delta/1000f);
				}
			}
			
		});
		this.addComponent(falling);
		
		
		//ueberprueft, ob der Roboter gerade auf festem Boden steht.
		Event grounded = new ANDEvent(new NOTEvent(new FlatGroundCollision()), new GroundCollision());
		grounded.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
				Robot r = self.getOwner();
				//ist der Roboter gerade am fallen und hat nun festen Boden unter sich, soll er landen (und nicht mehr fallen)
				if(r.getFalling()) {
					r.land();
				}
				
			}
			
		});
		this.addComponent(grounded);
		
		Event flat = new FlatGroundCollision();
		flat.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
				Robot r = self.getOwner();
				//ist der Roboter gerade am fallen und hat nun festen Boden unter sich, soll er landen (und nicht mehr fallen)
				//zus�tzlich ist dieser Boden gerade, daher soll der Roboter auch direkt auf ihm stehen
				if(r.getFalling()) {
					r.land();
					r.smoothLanding();
				}
				
			}
			
		});
		this.addComponent(flat);

		
		LoopEvent e = new LoopEvent();
		e.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				PhysicsHitbox ph = (PhysicsHitbox)event.getOwnerEntity();
				Vector2f pos = ph.getOwner().getPosition();
				//sorgt dafuer, dass diese Hitbox sich immer unter ihrem jeweiligen Roboter befindet.
				ph.setPosition(new Vector2f(pos.x, pos.y+32));
				
			}
			
		});
		
		this.addComponent(e);
		
		//detektiert Kollisionen mit einem Stahltraeger.
		IsOnBeamEvent isOnBeam = new IsOnBeamEvent();
		isOnBeam.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
				IsOnBeamEvent e = (IsOnBeamEvent) event;
				Beam b = (Beam) e.getCollidedEntity();
				if(b.getRotation() <= 44f && b.getRotation() >= -44f && !(b.getRotation() == 0f)) {
					if(self.getOwner().getLooksRight() == b.getUpRight()) {
					}else {
						self.getOwner().walkOnBeam(b);
					}
					
				}
				
				
			}
			
		});
		this.addComponent(isOnBeam);
		
		
		
	}
	
	/**
	 * 
	 * @return der Roboter dieser Hitbox
	 */
	public Robot getOwner() {
		return this.owner;
	}

	
	/**
	 * zerst�rt diese Hitbox. Wird ausgef�hrt, wenn der Besitzer dieser Hitbox auch zerst�rt wird.
	 */
	public void destroy() {
		Event l = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.addComponent(l);
	}
}
