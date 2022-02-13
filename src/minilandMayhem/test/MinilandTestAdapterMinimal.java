package minilandMayhem.test;

import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.model.entities.Mario;
import minilandMayhem.model.mapParser.Parser;
import minilandMayhem.ui.MinilandMayhem;

public class MinilandTestAdapterMinimal {
	
	MinilandMayhem miniland;
	TestGameContainer app;
	
	public void initGame() {
		// Setze den library Pfad abhaengig vom Betriebssystem
    	if (System.getProperty("os.name").toLowerCase().contains("windows")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
	} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
    	} else {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
    	}
    	
    	miniland = new MinilandMayhem(true);
    	
    	
    	// Setze dieses StateBasedGame in einen App Container (oder Fenster)
        try {
        	
        	 
			 app = new TestGameContainer(new MinilandMayhem(false));
			 Display.create();
			 app.start(0);
			// miniland.init(app);
			 
			 miniland.initStatesList(app);
			
			 //miniland.getCurrentState().init(app, miniland);
			 for(int i =0; i < miniland.getStateCount(); i++) {
				 //System.out.println(miniland.getState(i));
				 miniland.getState(i).init(app, miniland);
			 }
			
		
			 
			 
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
	

	/**
	 * Stoppe das im Hintergrund laufende Spiel
	 */
	public void stopGame() {
		if (app != null) {
			app.exit();
			app.destroy();
		}
		StateBasedEntityManager.getInstance().clearAllStates();
		miniland = null;
	}
	
	/**
	 * simuliert das Vergehen von Zeit in Hoehe des angegebenen Parameters in ms
	 * @param ms vergangene Zeit in ms
	 */
	public void updateGame(int ms) {
		try {
			miniland.update(app, ms);
			miniland.update(app,0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	/**
	 * simuliert einen Mausklick an Position (x,y) und triggert MouseEntered Event an Position (x,y) 
	 * sowie ein MouseClickedEvent
	 * @param x x-Koordinated des Klicks
	 * @param y y-Koordinate des Klicks
	 */
	public void handleMouseClick(float x, float y) {
		app.getTestInput().setMouseX((int)x);
		app.getTestInput().setMouseY((int)y);
		app.getTestInput().setMouseButtonPressed(Input.MOUSE_LEFT_BUTTON);
		try {
			miniland.update(app, 0); //das erste update laesst alle Events triggern, die bei Mausklick ausgeführt werden
			app.getTestInput().clearMouseButtonPressed(); //loslassen der Maus
			miniland.update(app, 0); //das zweite update fuehrt potentielle ChangeState Aktionen aus.
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	/**
	 * simuliert ein Tastendruck und triggert entsprechende keyPressedEvents
	 * @param key Taste, welche gedrückt wird.
	 */
	public void handleKeyPressed(int key) {
		app.getTestInput().setKeyPressed(key);
		
		try {
			miniland.update(app, 0); //das erste update laesst alle Events triggern, die bei Tastendruck ausgeführt werden
			app.getTestInput().clearPressedKeys(); //loslassen der Taste
			miniland.update(app, 0); //das zweite update fuehrt potentielle ChangeState Aktionen aus.
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	

	/**
	 * 
	 * @param mario Entity, bei welcher ueberprueft wird, ob sie aktiv ist (oder nicht)
	 * @return true wenn der uebergebene Mario aktiv ist, sonst false. Auch false, wenn Parameter kein Mario ist
	 */
	public boolean marioIsActive(Entity mario) {
		if(mario instanceof Mario) {
			Mario m = (Mario)mario;
			return m.getIsActive();
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param mario Entity, bei welcher ueberprueft wird, ob sie nach links schaut (oder nicht)
	 * @return true wenn der uebergebene Mario nach links schaut, sonst false. Auch false, wenn Parameter kein Mario ist
	 */
	public boolean marioLooksLeft(Entity mario) {
		if(mario instanceof Mario) {
			Mario m = (Mario)mario;
			return !m.getLooksRight();
		}
		return false;
	}
	
	/**
	 * 
	 * @param e zu ueberpruefende Entity
	 * @return true, wenn der uebergebene Parameter ein Mario ist
	 */
	public boolean isMario(Entity e) {
		return e instanceof Mario;
	}
	
	/**
	 * liest die Datei und extrahiert die Map aus der Datei. Ueberprueft danach, ob die Map rechteckig ist
	 * @param file einzulesende Datei
	 * @return true, wenn die in der Datei enthaltene Map korrekt(=rechteckig) ist, sonst false
	 */
	public boolean checkMap(File file) {
		return Parser.check(file);
	}
	
	/**
	 * 
	 * @return die Position des Buttons, welcher das Spiel vom Mainmenu aus startet (und in den GameState wechselt)
	 */
	public Vector2f getStartGamePosition() {
		return new Vector2f(170,190);
	}
	
	/**
	 * 
	 * @return die Position des Buttons, welcher ein neues Spiel vom Endscreen aus startet (und in den GameState wechselt)
	 */
	public Vector2f getNewGamePosition() {
		return new Vector2f(170,190);
	}
	
	/**
	 * 
	 * @return die Position des Buttons, welcher vom Endscreen aus in das Mainmenu wechselt
	 */
	public Vector2f getMainMenuPosition() {
		return new Vector2f(170,290);
	}
	
	
	
	/**
	 * 
	 * @return StateID des GamePlayStates
	 */
	public int getGameStateID() {
		return MinilandMayhem.GAMEPLAYSTATE;
	}
	
	/**
	 * 
	 * @return StateID des MainMenuStates
	 */
	public int getMainStateID() {
		return MinilandMayhem.MAINMENUSTATE;
	}
	
	/**
	 * 
	 * @return StateID des EndScreenStates
	 */
	public int getEndStateID() {
		return MinilandMayhem.ENDSCREENSTATE;
	}
		

	/**
	 * 
	 * @return StateID des aktuellen GameStates
	 */
	public int getCurrentStateID() {
		return miniland.getCurrentStateID();
	}

	
	/**
	 * 
	 * @return prefix aller IDs von Türen
	 * enthalten alle Tueren in ihrer ID den predix "Tuer", so soll "Tuer" zurueckgegeben werden
	 */
	public String getDoorPrefix() {
		return "Tür";
	}
	
	/**
	 * 
	 * @return prefix aller IDs von Marios
	 * enthalten alle Marios in ihrer ID den predix "Mario", so soll "Mario" zurueckgegeben werden
	 */
	public String getMarioPrefix() {
		return "Mario";
	}
	
	/**
	 * 
	 * @return prefix aller IDs von Waenden
	 * enthalten alle Waende in ihrer ID den predix "Wand", so soll "Wand" zurueckgegeben werden
	 */
	public String getWallPrefix() {
		return "Wand";
	}
	
	/**
	 * 
	 * @return prefix aller IDs von Gefahren
	 * enthalten alle Gefahren in ihrer ID den predix "Gefahr", so soll "Gefahr" zurueckgegeben werden
	 */
	public String getDangerPrefix() {
		return "Gefahr";
	}

	
	/**
	 * 
	 * @return prefix aller IDs von Sockeln
	 * enthalten alle Sockeln in ihrer ID den predix "Sockel", so soll "Sockel" zurueckgegeben werden
	 */
	public String getSocketPrefix() {
		return "Sockel";
	}

	
	
	
}
	
