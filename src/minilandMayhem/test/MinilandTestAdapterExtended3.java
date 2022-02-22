package minilandMayhem.test;

import eea.engine.entity.Entity;
import minilandMayhem.model.entities.Mario;

public class MinilandTestAdapterExtended3 extends MinilandTestAdapterExtended2 {

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
	 * 
	 * @return prefix des PowerUps. Haben alle PowerUps z.B. die ID "Power", so soll "Power" zurueckgegeben werden.
	 */
	public String getPowerUpPrefix() {
		return "PowerUp";
	}
	
	/**
	 * 
	 * @return prefix des Schluessels. Haben alle Schluessel z.B. die ID "Key", so soll "Key" zurueckgegeben werden.
	 */
	public String getKeyPrefix() {
		return "Key";
	}

	
	/**
	 * 
	 * @return prefix der Muenze. Haben alle Muenzen z.B. die ID "Coin", so soll "Coin" zurueckgegeben werden.
	 */
	public String getCoinPrefix() {
		return "Coin";
	}
	
	/**
	 * 
	 * @return prefix des Ressourcen Collectables. 
	 * Haben alle Ressourcen z.B. die ID "Ressource", so soll "Ressource" zurueckgegeben werden.
	 */
	public String getRessourcePrefix() {
		return "Ressource";
	}
}
