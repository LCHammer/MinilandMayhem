package minilandMayhem.test;

import java.io.File;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.highscore.Highscore;
import minilandMayhem.ui.GamePlayState;

public class MinilandTestAdapterExtended2 extends MinilandTestAdapterExtended1 {

	
	/**
	 * 
	 * @return aktuelle Punktzahl des Spielers
	 */
	public int getScore() {
		return GamePlayState.score;
	}
	
	/**
	 * 
	 * @return die Position des "Highscore Speichern" Buttons im Endescreen
	 */
	public Vector2f getSaveScorePosition() {
		return new Vector2f(170,390);
	}

	
	/**
	 * liest angegebene Datei und gibt ihren Inhalt als einen String zurueck
	 * @param f zu lesende Datei
	 * @return den Inhalt der Datei als String
	 */
	public String readScore(File f) {
		Highscore.readFile(f);
		return Highscore.score();
	}

	/**
	 * laesst Menge time an Zeit verstreichen
	 * @param time Menge an zu verstreichender Zeit
	 */
	public void waitFor(long time) {
		GamePlayState.time.timer.letTimePass(time);
		
	}
	
	
	/**
	 * 
	 * @return true, wenn mindestens eine Muenze im Spiel existert, sonst false
	 */
	public boolean existsCoin() {
		return StateBasedEntityManager.getInstance().hasEntity(getGameStateID(),"Coin");
		
	}
}
