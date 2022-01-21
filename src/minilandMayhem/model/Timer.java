package minilandMayhem.model;

public class Timer {

	private long startTime;
	
	
	public Timer() {
		start();
	}
	
	
	public void start() {
			startTime = System.currentTimeMillis();
		
	}
	
	public boolean didEnoughTimePass(long enoughTime) {
		long currentTime = System.currentTimeMillis();
		if(currentTime - startTime> enoughTime) {
			start();
			return true;
		}else {
			return false;
		}
		
	}
	
}
