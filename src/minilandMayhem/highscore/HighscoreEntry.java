package minilandMayhem.highscore;

public class HighscoreEntry implements Comparable<HighscoreEntry> {

	private int score;
	private int maxMario;
	private int numMario;
	//ggf noch datum
	
	public HighscoreEntry(int score, int num, int max) {
		this.score = score;
		this.numMario = num;
		this.maxMario = max;
	}
	
	public HighscoreEntry(String s) {
		String[] arr1 = s.split(",");
		String[] arr2 = arr1[1].split("/");
		this.score=Integer.parseInt(arr1[0]);
		this.numMario = Integer.parseInt(arr2[0]);
		this.maxMario = Integer.parseInt(arr2[1]);
		//System.out.println(this.score+" "+this.numMario+" "+ this.maxMario);
	}
	
	public String toString() {
		return score+","+numMario +"/"+ maxMario;
	}
	
	@Override
	/**
	 * Vergleicht diesen Highscore mit einem anderen
	 * @param other anderer Highscore Eintrag, mit dem verglichen wird
	 * @return 0 bei gleichstand, 1 wenn der andere besser ist, -1 wenn dieser besser ist
	 */
	public int compareTo(HighscoreEntry o) {
		HighscoreEntry other = (HighscoreEntry)o;
		if(other.score > this.score || (other.score == this.score && other.numMario > this.numMario)) {
			return 1;
		}else if( other.score == this.score && other.numMario == this.numMario){
			return 0;
		}else {
			return -1;
		}
	}

	
	
}
