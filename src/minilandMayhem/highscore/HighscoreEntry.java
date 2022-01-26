package minilandMayhem.highscore;

public class HighscoreEntry {

	private int score;
	private int maxMario;
	private int numMario;
	//ggf noch datum
	
	public HighscoreEntry(int score, int num, int max) {
		this.score = score;
		this.numMario = num;
		this.maxMario = max;
	}
	
	public String toString() {
		return score+","+numMario +"/"+ maxMario;
	}
	
}
