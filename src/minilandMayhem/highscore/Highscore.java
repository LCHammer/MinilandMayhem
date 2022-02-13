package minilandMayhem.highscore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class Highscore {

	public static LinkedList<HighscoreEntry> entries;
	
	/**
	 * @return den ganzen Highscore als ein String, welcher dann in eine Datei geschrieben wird.
	 */
	public static String score() {
		String ret ="";
		if(entries != null) {
			for(HighscoreEntry e: entries) {
				if(ret == "") {
					ret = e.toString();
				}else {
					ret += System.lineSeparator() + e.toString();
				}
			}
		}
		return ret;
	}
	
	/**
	 * fuegt den entry in die Liste ein, sortiert die Liste und behaellt nur die besten 5 Eintraege
	 * @param entry einzufuegender Eintrag
	 */
	public static void insert(HighscoreEntry entry) {
		entries.add(entry);
		Collections.sort(entries);
		if(entries.size() == 6) {
			entries.remove(5);
		}
	}
	
	/**
	 * 
	 * @param highscore liest den highscore in der angegebenen Datei ein und speichert diesen in Highscore.entries, sofern es keine Fehler gibt
	 * @return true, wenn die Datei problemlos eingelesen werden kann, sonst false
	 */
	public static boolean readFile(File highscore) {
		
	
		try {
			FileReader f= new FileReader(highscore);
			BufferedReader b = new BufferedReader(f);
			
			Highscore.entries = new LinkedList<HighscoreEntry>();
			for (String line=b.readLine(); line !=null; line = b.readLine()) {
				if(Pattern.matches("-?[0-9]+,[0-9]+/[0-9]+", line) ) {
					HighscoreEntry h = new HighscoreEntry(line);
					Highscore.entries.add(h);
				}else {
					//loesche alle bereits vorhandenen Werte.
					Highscore.entries = new LinkedList<HighscoreEntry>();
					b.close();
					return false;
				}
			}
			b.close();
		
			
		}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		return true;
	}
}
