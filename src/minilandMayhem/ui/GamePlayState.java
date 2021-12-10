package minilandMayhem.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import minilandMayhem.model.entities.*;

public class GamePlayState extends BasicGameState {
	
	
	private int stateID;
    private StateBasedEntityManager entityManager;
    public static int ressources = 5;
	
	public GamePlayState(int stateID) {
		this.stateID = stateID;
		this.entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// Hintergrund laden
    	Entity background = new Entity("background");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(400,300));	// Startposition des Hintergrunds
    	background.addComponent(new ImageRenderComponent(new Image("/assets/background.png"))); // Bildkomponente
    	
    	entityManager.addEntity(stateID, background);
    	
    	// Bei DrÃ¼cken der ESC-Taste ins Pause-Menü wechseln
    	Entity esc_Listener = new Entity("ESC_Listener");
    	KeyPressedEvent esc_pressed = new KeyPressedEvent(Input.KEY_ESCAPE);
    	esc_pressed.addAction(new ChangeStateAction(MinilandMayhem.PAUSEMENUSTATE));
    	esc_Listener.addComponent(esc_pressed);    	
    	entityManager.addEntity(stateID, esc_Listener);
    	
    	
    	if(MinilandMayhem.debug) {
    	//das hier ist nur ein test, um zu schauen, wie man mithilfe von Actions
    	/*Entity click = new Entity("Click");
    	MouseClickedEvent e = new MouseClickedEvent();
    	e.addAction( new Action() {
    	
    			public void update(GameContainer gc, StateBasedGame sb, int delta,
    					Component event) {
    				//bei maus-klick, wird die ressouce um 1 erhöht
    			GamePlayState.ressources +=1;
    		}
    	}
    	);
    	click.addComponent(e);
    	entityManager.addEntity(stateID, click);
    		
    	*/
    	
    	Entity mario = new RobotMario("Mario1");
    	mario.setPosition(new Vector2f(100,100));
    	entityManager.addEntity(stateID, mario);
    	
    	Entity wallR = new Wall("WandR");
    	wallR.setPosition(new Vector2f(200,100));
    	entityManager.addEntity(stateID, wallR);
    	
    	Entity doorL = new Door("TürL",false);
    	doorL.setPosition(new Vector2f(00,100));
    	entityManager.addEntity(stateID, doorL);
    	
    	Entity mario2 = new RobotMario("Mario2");
    	mario2.setPosition(new Vector2f(100,200));
    	entityManager.addEntity(stateID, mario2);
    	
    	Entity wallR2 = new Wall("WandR2");
    	wallR2.setPosition(new Vector2f(200,200));
    	entityManager.addEntity(stateID, wallR2);
    	
    	Entity wallL2 = new Wall("WandL2");
    	wallL2.setPosition(new Vector2f(00,200));
    	entityManager.addEntity(stateID, wallL2);
    	
    	}	
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		entityManager.renderEntities(container, game, g);
		
		String ressourcen = "Stahträger: "+ this.ressources;
		
		//float werte sind x und y koordinate der Stringposition
		g.drawString(ressourcen, 110, 10);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		entityManager.updateEntities(container, game, delta);
		
	}

	@Override
	public int getID() {
		return stateID;
	}

}
