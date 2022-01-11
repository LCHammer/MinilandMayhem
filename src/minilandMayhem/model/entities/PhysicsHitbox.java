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

public class PhysicsHitbox extends Entity{
	
	private RobotMario owner;

	public PhysicsHitbox(String entityID, Vector2f position, RobotMario mario) {
		super(entityID);
		owner = mario;
		this.setVisible(false); 
		//Wichtig: setzt die Groesse der Hitbox. Diese darf nicht mit dem entsprechenden Mario kollidieren,
		//da sonst alle anderen Kollisionen überdeckt werden.
		this.setSize(new Vector2f(48,2));		
		
		
		//ueberprueft, ob der Mario aktuell KEINEN Boden unter sich hat.
		Event falling = new NOTEvent(new GroundCollision());
		falling.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
				RobotMario mario = self.getOwner();
				//Hat der Mario keinen Boden unter sich, soll er fallen und dabei schneller werden.
				mario.fall(gc.getFPS());
				
			}
			
		});
		this.addComponent(falling);
		
		
		//ueberprueft, ob der Mario gerade auf festem Boden steht.
		Event grounded = new GroundCollision();
		grounded.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
				RobotMario mario = self.getOwner();
				//ist der Mario gerade am fallen und hat nun festen Boden unter sich, soll er landen (und nicht mehr fallen)
				if(mario.getFalling()) {
					mario.land();
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
				//sorgt dafuer, dass diese Hitbox sich immer unter ihrem jeweiligen Mario befindet.
				ph.setPosition(new Vector2f(pos.x, pos.y+32));
				
			}
			
		});
		
		this.addComponent(e);
		
		
		
	}
	
	/**
	 * 
	 * @return der Mario dieser Hitbox
	 */
	public RobotMario getOwner() {
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
