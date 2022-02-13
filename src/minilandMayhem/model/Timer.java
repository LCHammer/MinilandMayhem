package minilandMayhem.model;

public class Timer {

	private long startTime;
	private long elapsedTime;
	private boolean isPaused;
	
	
	public Timer() {
		isPaused = false;
		start();
		
	}
	
	/**
	 * startet den Timer
	 */
	public void start() {
		startTime = System.currentTimeMillis();
		elapsedTime = 0;
		
	}
	
	
	/**
	 * pausiert den Timer
	 */
	public void pause() {
		if(!isPaused) {
			isPaused = true;
			elapsedTime += System.currentTimeMillis() - startTime;
		}
		startTime = System.currentTimeMillis();
	}
	
	
	/**
	 * laesst den Timer weiterlaufen
	 */
	public void unpause() {
			isPaused = false;
	}
	
	
	/**
	 * ueberprueft, ob genug Zeit vergangen ist und wenn ja, wird der Timer resettet und true zurueckgegeben
	 * @param enoughTime Menge an Zeit, die vergehen soll
	 * @return true, wenn genug Zeit vergangen ist, sonst false
	 */
	public boolean didEnoughTimePass(long enoughTime) {
		long currentTime = System.currentTimeMillis();
		if(currentTime - (startTime + elapsedTime) >= enoughTime) {
			start();
			return true;
		}else {
			return false;
		}
		
	}
	
}
