package minilandMayhem.test;

import eea.engine.entity.Entity;
import minilandMayhem.model.entities.*;

public class MinilandTestAdapterExtended1 extends MinilandTestAdapterMinimal{

		
	/**
	 * 
	 * @param time Menge an Zeit, die für den blaster vergehen soll
	 * @param entity laesst für BillBlaster blaster Zeit vergehen in Hoehe von time
	 */
	public void timeBlaster(int time, Entity blaster) {
		if(blaster instanceof BillBlaster) {
			BillBlaster b =(BillBlaster)blaster;
			b.time.timer.letTimePass((long)time);
			this.updateGame(0);
		}
		
	}

	/**
	 * @param e zu ueberpruefende Entity
	 * @return true wenn e ein Feuer ist
	 **/
	public boolean isFire(Entity e) {
		return e instanceof Fire;
	}

	/**
	 * @param e zu ueberpruefende Entity
	 * @return true wenn e ein Trampolin ist
	 **/
	public boolean isTrampoline(Entity e) {
		return e instanceof Trampoline;
	}
	
	
	/**
	 * @param e zu ueberpruefende Entity
	 * @return true wenn e eine Kanone ist
	 **/
	public boolean isBlaster(Entity e) {
		return e instanceof BillBlaster;
	}
	
	/**
	 * @param e zu ueberpruefende Entity
	 * @return true wenn e ein Kugelwilli ist
	 **/
	public boolean isBill(Entity e) {
		return e instanceof BulletBill;
	}
}
