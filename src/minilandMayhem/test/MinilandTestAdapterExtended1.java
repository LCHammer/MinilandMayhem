package minilandMayhem.test;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.model.entities.*;

public class MinilandTestAdapterExtended1 extends MinilandTestAdapterMinimal{

		
	/**
	 * 
	 * @param time Menge an Zeit, die für den blaster vergehen soll
	 * @param entity laesst für BillBlaster blaster Zeit vergehen in Hoehe von time
	 */
	public void timeBlaster(long time, Entity blaster) {
		if(blaster instanceof BillBlaster) {
			BillBlaster b =(BillBlaster)blaster;
			b.time.timer.letTimePass(time);
			this.updateGame(0);
		}
		
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
	

	
	/**
	 * 
	 * @param mario, welcher  ueberprueft wird
	 * @return true, wenn der zu ueberpruefende Mario einen Schluessel besitzt, sonst false
	 */
	public boolean marioHasKey(Entity mario) {
		if(mario instanceof Mario) {
			Mario m = (Mario) mario;
			return m.getHasKey();
		}
		return false;
	}
	
	
	/**
	 * 
	 * @return true wenn noch mindestens ein Ressoucen Collectable im Spiel existiert
	 */
	public boolean existsRessource() {
		return StateBasedEntityManager.getInstance().hasEntity(getGameStateID(),"Ressource");
	}


	/**
	 * 
	 * @return true wenn noch mindestens ein Schluessel Collectable im Spiel existiert
	 */
	public boolean existsKey() {
		return StateBasedEntityManager.getInstance().hasEntity(getGameStateID(),"Key");
	}
	
}
