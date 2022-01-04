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
		System.out.println(this.getSize());
		
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/socket.png")));
		}
		catch(SlickException e) {
			System.out.println("Schluesselbild konnte nicht geladen werden");
		}
		
		
		
		Event falling = new NOTEvent(new GroundCollision());
		falling.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
				RobotMario mario = self.getOwner();
				mario.fall(gc.getFPS());
				
			}
			
		});
		this.addComponent(falling);
		
		Event grounded = new GroundCollision();
		grounded.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				PhysicsHitbox self = (PhysicsHitbox) event.getOwnerEntity();
				RobotMario mario = self.getOwner();
				
				if(mario.getFalling()) {
				System.out.println("land");
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
				ph.setPosition(new Vector2f(pos.x, pos.y+50));
				
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
