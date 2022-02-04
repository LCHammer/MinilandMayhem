package minilandMayhem.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import eea.engine.entity.StateBasedEntityManager;
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
    	
    	// Setze dieses StateBasedGame in einen App Container (oder Fenster)
        try {
        	
        	 
			 app = new TestGameContainer(new MinilandMayhem(false));
			 Display.create();
			 app.start(0);
			 
			 
			 
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        // Lege die Einstellungen des Fensters fest und starte das Fenster
        // (nicht aber im Vollbildmodus)
        //app.setDisplayMode(800, 600, false);
       
    
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

}
