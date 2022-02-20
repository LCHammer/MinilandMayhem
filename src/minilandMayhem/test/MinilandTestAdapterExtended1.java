package minilandMayhem.test;

import eea.engine.entity.Entity;
import minilandMayhem.model.entities.BillBlaster;

public class MinilandTestAdapterExtended1 extends MinilandTestAdapterMinimal{

	
	/**
	 * 
	 *@return prefix aller IDs von Feuer Entities
	 * enthalten alle Feuer in ihrer ID den prefix "Feuer", so soll "Feuer" zurueckgegeben werden
	 
	 */
	public String getFirePrefix() {
		return "Feuer";
	}
	
	/**
	 * 
	 * @return prefix aller IDs von Trampolin Entities
	 * enthalten alle Trampolne in ihrer ID den prefix "Trampolin", so soll "Trampolin" zurueckgegeben werden
	 */
	public String getTrampolinePrefix() {
		return "Trampolin";
	}
	
	/**
	 * 
	 * @return prefix aller IDs von BillBlaster Entities
	 * enthalten alle BillBlaster(=Kanonen) in ihrer ID den prefix "Kanone", so soll "Kanone" zurueckgegeben werden
	 */
	public String getBlasterPrefix() {
		return "Kanone";
	}

	/**
	 * 
	 * @return prefix aller IDs von Kugelwilli Entities
	 * enthalten alle Kugelwillis in ihrer ID den prefix "Bill", so soll "Bill" zurueckgegeben werden
	 */
	public String getBillPrefix() {
		return "Kugelwilli";
	}
	
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
}
