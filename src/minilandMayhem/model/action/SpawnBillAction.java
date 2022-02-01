package minilandMayhem.model.action;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.model.entities.BulletBill;

public class SpawnBillAction implements Action {
	
	private boolean nextIsLeft = true;
	
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
		Entity owner = event.getOwnerEntity();
		Vector2f pos = owner.getPosition();
		BulletBill spawn = new BulletBill("Kugelwilli",nextIsLeft);
		
		StateBasedEntityManager.getInstance().addEntity(game.getCurrentStateID(), spawn);
		if(nextIsLeft) {
			spawn.setPosition(new Vector2f(pos.x-50, pos.y));
		}else {
			spawn.setPosition(new Vector2f(pos.x+50, pos.y));
		}
		nextIsLeft = !nextIsLeft;

	}

}
