package minilandMayhem.model.action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import minilandMayhem.model.entities.*;

public class BulletBillCollision implements Action{

	
	public Entity collider;
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
		

		CollisionEvent collide =(CollisionEvent) event;
		collider =collide.getCollidedEntity();
		BulletBill b = (BulletBill) collide.getOwnerEntity();
        
		if(collider instanceof Mario) {
        	
			Mario m = (Mario)collider;
			if(!m.getHasPowerUp()) {
				m.destroy();
			}
        }else if(collider instanceof Fire) {
        	Fire f = (Fire)collider;
        	f.destroy();
        }
		b.destroy();
       
	}
	
	

}
