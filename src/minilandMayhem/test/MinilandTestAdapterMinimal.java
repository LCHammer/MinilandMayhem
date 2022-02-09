package minilandMayhem.test;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Input;
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
	 * 
	 * @return StateID des aktuellen GameStates
	 */
	public int getCurrentStateID() {
		return miniland.getCurrentStateID();
	}
	
	
	
	
	
	public void handleMouseClick(int x, int y) {
		app.getTestInput().setMouseX(x);
		app.getTestInput().setMouseY(y);
		
		//miniland.enterState(MinilandMayhem.GAMEPLAYSTATE);
		app.getTestInput().setMouseButtonPressed(Input.MOUSE_LEFT_BUTTON);
		
		
		System.out.println(miniland.getCurrentStateID());
		try {
			miniland.update(app, 0);
			//app.updateGame(0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		app.getTestInput().setKeyPressed(Input.KEY_P);
		System.out.println(miniland.getCurrentStateID());
		try {
			miniland.update(app, 0);
			//app.updateGame(0);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		
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
		
}
	
