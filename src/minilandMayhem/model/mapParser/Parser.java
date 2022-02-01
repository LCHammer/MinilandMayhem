package minilandMayhem.model.mapParser;

import eea.engine.entity.Entity;
import minilandMayhem.model.entities.*;

public class Parser {
	
	public static String[] map=null;
	public static String levelname = "default";
	//sorgt für eine unique benennung der Marios und des Feuers. Die nummer selbst ist egal,
	//wichtig ist nur, dass unterschiedliche Roboter unterschiedliche Nummern besitzen.
	private static int uniqueNum=0;
	
	
	
	/**
	 * konvertiert das String Array in ein Entity[][] mit entsprechenden Einträgen.
	 * Ist ein Eintrag null, so repraesentiert dies ein leeres Feld.
	 * @return Entity[][], bei dem die Stelle (x,y) der x-ten Stelle des y-ten Strings entspricht
	 */
	public static Entity[][] parse(){
		int length = Parser.map[0].length();
		char[][] chars = new char[Parser.map.length][Parser.map[0].length()];
		for(int y = 0; y < Parser.map.length; y++) {
			if(Parser.map[y].length() != length) {
				System.out.println("Level ist nicht rechteckig");
				return null;
			}else {
				chars[y] = Parser.map[y].toCharArray();
			}
		}
		
		Entity[][] map = new Entity[chars.length][chars[0].length];
		for(int y = 0; y < chars.length; y++) {
			for (int x = 0; x< chars[0].length; x++) {
				map[y][x] = Parser.convert(chars[y][x]);
			}
		}
		return map;
	}
	
	/**
	 * setzt die zu uebersetzende map im Parser
	 * @param map, die uebersetzt werden soll
	 */
	public static void setMap(String[] map) {
		Parser.map = map;
	}
	
	
	/**
	 * 
	 * @param c Zeichen, welches in eine Entity uebersetzt werden soll (aus 'M' wird ein RobotMario)
	 * @return die uebersetzte Entity 
	 */
	public static Entity convert(char c) {
		switch (c) {
		case '_': return null;
		case 'M': uniqueNum +=1;
				  return new Mario("Mario"+uniqueNum);
		case 'F': uniqueNum +=1;
				  return new Fire("Feuer"+uniqueNum,true);
		case 'W': return new Wall("Wand");
		case 'D': return new Door("Tür",true);
		case 'd': return new Door("Tür",false);
		case 'S': return new BeamSocket("Sockel");
		case 'K': return new Key("Key");
		case 'X': return new Danger("Gefahr");
		case 'C': return new Coin("Coin");
		case 'T': return new Trampoline("Trampoline");
		case 'B': return new BillBlaster("Kanone");
		case 'P': return new PowerUp("PowerUp");
		case 'R': return new SteelPickup("Ressource");
		default: return null;
		
		
		}
	}
	
	
	/**
	 * 
	 * @return true, wenn das aktuelle Level rechteckig ist.
	 */
	public static boolean check(){
		int length = Parser.map[0].length();
		for(int y = 0; y < Parser.map.length; y++) {
			if(Parser.map[y].length() != length) {
				return false;
			}
		}
		return true;
	}

}
