package minilandMayhem.model;

public class Timer {

	private long startTime;
	private long elapsedTime;
	private boolean isPaused;
	
	
	public Timer() {
		isPaused = false;
		start();
		
	}
	
	
	public void start() {
		startTime = System.currentTimeMillis();
		elapsedTime = 0;
		
	}
	
	
	public void pause() {
		if(!isPaused) {
			isPaused = true;
			elapsedTime += System.currentTimeMillis() - startTime;
		}
		startTime = System.currentTimeMillis();
	}
	
	
	public void unpause() {
			isPaused = false;
	}
	
	
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
