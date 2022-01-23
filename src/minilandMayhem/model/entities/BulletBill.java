package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.Event;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LoopEvent;
import minilandMayhem.model.action.BulletBillCollision;


public class BulletBill extends Entity{
	
	public float speed;

	
		public BulletBill(String entityID) {
			super(entityID);
			speed = 0.125f;
				activate();
				
				try {
					this.addComponent(new ImageRenderComponent(new Image("/assets/rocket.png")));
				}
				catch(SlickException e) {
					System.out.println("Bullet Bill konnte nicht geladen werden");
				}
						
		
				CollisionEvent collide = new CollisionEvent();
				collide.addAction(new BulletBillCollision());
				this.addComponent(collide);
				
				//this.addComponent(collide);
			}
		
		
	
	/**
	 * aktiviert diesen Bill.
	 * Dieser bewegt sich dann immer nach rechts, solange er nach rechts schaut und 
	 * immer nach links, wenn er nach links schaut.
	 * 
	 */

	private void activate() {
			
			LoopEvent right = new LoopEvent();
			
			right.addAction(new MoveRightAction(speed));
			this.addComponent(right);

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

