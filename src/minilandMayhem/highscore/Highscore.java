package minilandMayhem.highscore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class Highscore {

	public static LinkedList<HighscoreEntry> entries;
	
	public static String score() {
		String ret ="";
		for(HighscoreEntry e: entries) {
			if(ret == "") {
				ret = e.toString();
			}else {
			ret += System.lineSeparator() + e.toString();
			}
		}
		return ret;
	}
	
	public static void insert(HighscoreEntry entry) {
		entries.add(entry);
		Collections.sort(entries);
		if(entries.size() == 6) {
			entries.remove(5);
		}
	}
	
	public static void readFile(File highscore) {
		
		try {
			FileReader f= new FileReader(highscore);
		
			BufferedReader b = new BufferedReader(f);
			Highscore.entries = new LinkedList<HighscoreEntry>();
			for (String line=b.readLine(); line !=null; line = b.readLine()) {
				HighscoreEntry h = new HighscoreEntry(line);
				Highscore.entries.add(h);
			}
		
			FileWriter fw = new FileWriter(highscore);
			fw.write(Highscore.score());
			fw.close();
			b.close();
		}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
