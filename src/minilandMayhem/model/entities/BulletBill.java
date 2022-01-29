package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.Event;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LoopEvent;
import minilandMayhem.model.action.BulletBillCollision;
import minilandMayhem.model.events.TimedEvent;


public class BulletBill extends Entity{
	
	private float speed;
	private boolean looksLeft;

	
		public BulletBill(String entityID, boolean looksLeft) {
			super(entityID);
			speed = 0.125f;
			this.looksLeft = looksLeft;
			this.setSize(new Vector2f(50,30));
				activate();
				
				try {
					if(looksLeft) {
						this.addComponent(new ImageRenderComponent(new Image("/assets/rocket.png")));
					}else {
						this.addComponent(new ImageRenderComponent(new Image("/assets/rocketRight.png")));
					}
				}
				catch(SlickException e) {
					System.out.println("Bullet Bill konnte nicht geladen werden");
				}
						
		
				CollisionEvent collide = new CollisionEvent();
				collide.addAction(new BulletBillCollision());
				this.addComponent(collide);
				
				
			}
		
		
	
	/**
	 * aktiviert diesen Bill.
	 * Dieser bewegt sich dann immer nach rechts, solange er nach rechts schaut und 
	 * immer nach links, wenn er nach links schaut.
	 * 
	 */

	private void activate() {
			
			LoopEvent flight = new LoopEvent();
			if(looksLeft) {
				flight.addAction(new MoveLeftAction(speed));
			}else {
				flight.addAction(new MoveRightAction(speed));
			}
			this.addComponent(flight);

		}


	/**
	 * Zerstört diesen Bill
	 */
	public void destroy() {
		Event l = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.addComponent(l);
		}
}

