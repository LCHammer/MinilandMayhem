package minilandMayhem.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;



import org.newdawn.slick.AppGameContainer;
import eea.engine.entity.StateBasedEntityManager;



public class MinilandMayhem extends StateBasedGame {

	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	public static final int OPTIONSTATE = 2;
	public static final int HIGHSCORESTATE = 3;
	public static final int ENDSCREENSTATE = 4;
	
	public static boolean debug = false;
	
	public MinilandMayhem(boolean debugging) {
		super("MinilandMayhem");
		setDebug(debugging);
	}
	
	public static void setDebug(boolean debugging){
    	debug = debugging;
    }
	
	 public static void main(String[] args) throws SlickException
	    {
			// Setze den library Pfad abhaengig vom Betriebssystem
	    	if (System.getProperty("os.name").toLowerCase().contains("windows")) {
	    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
		} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
	    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
	    	} else {
	    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
	    	}
	    	
	    	// Setze dieses StateBasedGame in einen App Container (oder Fenster)
	        AppGameContainer app = new AppGameContainer(new MinilandMayhem(false));
	 
	        // Lege die Einstellungen des Fensters fest und starte das Fenster
	        // (nicht aber im Vollbildmodus)
	        app.setDisplayMode(800, 600, false);
	        app.start();
	    }

	@Override
	public void initStatesList(GameContainer container) throws SlickException {

		//der zuerst hinzugefügte state ist der, mit dem das Spiel startet
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GamePlayState(GAMEPLAYSTATE));
		//this.addState(new OptionState(OPTIONSTATE));
		//this.addState(new HighScoreState(HIGHSCORESTATE));
		this.addState(new EndScreen(ENDSCREENSTATE));
		
		StateBasedEntityManager.getInstance().addState(MAINMENUSTATE);
		StateBasedEntityManager.getInstance().addState(GAMEPLAYSTATE);
		//StateBasedEntityManager.getInstance().addState(OPTIONSTATE);
		//StateBasedEntityManager.getInstance().addState(HIGHSCORESTATE);
		StateBasedEntityManager.getInstance().addState(ENDSCREENSTATE);
		
	}

}
