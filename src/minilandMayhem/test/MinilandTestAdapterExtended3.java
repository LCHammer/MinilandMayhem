package minilandMayhem.test;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.model.entities.Fire;
import minilandMayhem.model.entities.Mario;
import minilandMayhem.model.entities.Trampoline;

public class MinilandTestAdapterExtended3 extends MinilandTestAdapterExtended2 {	

	/**
	 * 
	 * @param mario, welcher  ueberprueft wird
	 * @return true, wenn der zu ueberpruefende Mario ein Power-Up hat, sonst false
	 */
	public boolean marioPowerUp(Entity mario) {
		if(mario instanceof Mario) {
			Mario m = (Mario) mario;
			return m.getHasPowerUp();
		}
		return false;
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
	 * 
	 * @return true, wenn noch mindestens ein Kugelwilli im Spiel existiert, sonst false
	 */
	public boolean existsBill() {
		return StateBasedEntityManager.getInstance().hasEntity(getGameStateID(),"Kugelwilli");
		
	}

	
}
