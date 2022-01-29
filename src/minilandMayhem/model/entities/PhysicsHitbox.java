package minilandMayhem.model.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.Event;
import eea.engine.event.NOTEvent;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LoopEvent;
import minilandMayhem.model.events.GroundCollision;
import minilandMayhem.model.events.IsOnBeamEvent;

public class PhysicsHitbox extends Entity{
	
	private Robot owner;

	public PhysicsHitbox(String entityID, Vector2f position, Robot robot) {
		super(entityID);
		owner = robot;
		this.setVisible(true); 
		//Wichtig: setzt die Groesse der Hitbox. Diese darf nicht mit dem entsprechenden Roboter kollidieren,
		//da sonst alle anderen Kollisionen überdeckt werden.
		this.setSize(new Vector2f(48,2));		
		//this.setPassable(true);
		try {
			this.addComponent(new ImageRenderComponent(new Image("assets/stahltrager.png")));
		} catch (SlickException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//ueberprueft, ob der Roboter aktuell KEINEN Boden unter sich hat.
		Event falling = new NOTEvent(new GroundCollision());
		falling.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				if(!gc.isPaused()) {
					PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
					Robot r = self.getOwner();
					//Hat der Roboter keinen Boden unter sich, soll er fallen und dabei schneller werden.
					r.fall(gc.getFPS());
				}
			}
			
		});
		this.addComponent(falling);
		
		
		//ueberprueft, ob der Roboter gerade auf festem Boden steht.
		Event grounded = new GroundCollision();
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
	 * zerstört diese Hitbox. Wird ausgeführt, wenn der Besitzer dieser Hitbox auch zerstört wird.
	 */
	public void destroy() {
		Event l = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.addComponent(l);
	}
}
