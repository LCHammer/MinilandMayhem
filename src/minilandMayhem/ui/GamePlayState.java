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
    public static BeamSocket selectedSocket = null;
	
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
    	
    	RobotMario mario = new RobotMario("Mario1");
    	mario.setPosition(new Vector2f(100,100));
    	//mario.getHitbox().setPosition(new Vector2f(200,230));
    	entityManager.addEntity(stateID, mario);
    	entityManager.addEntity(stateID, mario.getHitbox());
    	
    	
    	Entity wall1 = new Wall("Boden");
    	wall1.setPosition(new Vector2f(100,400));
    	entityManager.addEntity(stateID, wall1);
    	
    	Entity wall2 = new Wall("Wand2");
    	wall2.setPosition(new Vector2f(200,400));
    	entityManager.addEntity(stateID, wall2);
    	
    	Entity wall3 = new Wall("Wand3");
    	wall3.setPosition(new Vector2f(150,350));
    	entityManager.addEntity(stateID, wall3);
    	
    	Entity wall4 = new Wall("Wand4");
    	wall4.setPosition(new Vector2f(250,400));
    	entityManager.addEntity(stateID, wall4);
    	
    	Entity wall5 = new Wall("Wand5");
    	wall5.setPosition(new Vector2f(300,350));
    	entityManager.addEntity(stateID, wall5);
    /*	
    	Entity wall6 = new Wall("Wand6");
    	wall6.setPosition(new Vector2f(100,400));
    	entityManager.addEntity(stateID, wall6);
    */	
    	Entity wall7 = new Wall("Wand7");
    	wall7.setPosition(new Vector2f(150,400));
    	entityManager.addEntity(stateID, wall7);
    /*	
    	Entity doorL = new Door("TürL",false);
    	doorL.setPosition(new Vector2f(00,100));
    	entityManager.addEntity(stateID, doorL);
    	
    	Entity key = new Key("Schluessel");
    	key.setPosition(new Vector2f(200,100));
    	key.addComponent(new ImageRenderComponent(new Image("/assets/drop.png")));
    	entityManager.addEntity(stateID, key);
    	
    	
    	
    	Entity socket2 = new BeamSocket("Socket2");
    	socket2.setPosition(new Vector2f(200,200));
    	entityManager.addEntity(stateID, socket2);
    	
    	
    	BeamSocket socketR2 = new BeamSocket("SocketR2");
    	socketR2.setPosition(new Vector2f(300,200));
    	entityManager.addEntity(stateID, socketR2);
    	
    	
    	
    	Entity sockel = new BeamSocket("SocketL2");
    	sockel.setPosition(new Vector2f(100,200));
    	entityManager.addEntity(stateID, sockel);
    	*/
    	
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

	public static void createBeam(BeamSocket firstSocket, BeamSocket secondSocket) {
		Vector2f first = firstSocket.getPosition();
		Vector2f second = secondSocket.getPosition();
		double xdist = (double)(first.x - second.x);
		double ydist = (double)(first.y - second.y);
		double length = Math.sqrt(xdist*xdist + ydist*ydist);
		System.out.println(length);
		//TODO: also compute angle
		//add Beam to the game, also remove it
		
	}

}
