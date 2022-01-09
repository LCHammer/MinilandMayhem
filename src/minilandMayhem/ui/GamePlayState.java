package minilandMayhem.ui;

import java.util.LinkedList;

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
import minilandMayhem.model.mapParser.Parser;

public class GamePlayState extends BasicGameState {
	
	
	private int stateID;
    private StateBasedEntityManager entityManager;
    public static int ressources = 50;
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
    	
    	
    	if(MinilandMayhem.debug || Parser.map==null) {
    	
    	
    	RobotMario mario = new RobotMario("Mario1");
    	mario.setPosition(new Vector2f(100,100));
    	//mario.getHitbox().setPosition(new Vector2f(200,230));
    	entityManager.addEntity(stateID, mario);
    	entityManager.addEntity(stateID, mario.getHitbox());
    	
    	
    	Entity wall1 = new Wall("Boden");
    	wall1.setPosition(new Vector2f(150,400));
    	entityManager.addEntity(stateID, wall1);
    	
    	Entity wall2 = new Wall("Wand2");
    	wall2.setPosition(new Vector2f(200,400));
    	entityManager.addEntity(stateID, wall2);
    	
    	Entity wall3 = new Wall("Wand3");
    	wall3.setPosition(new Vector2f(100,350));
    	entityManager.addEntity(stateID, wall3);
    	
    	Entity wall4 = new Wall("Wand4");
    	wall4.setPosition(new Vector2f(250,400));
    	entityManager.addEntity(stateID, wall4);
    	
    	Entity wall5 = new Wall("Wand5");
    	wall5.setPosition(new Vector2f(300,400));
    	entityManager.addEntity(stateID, wall5);
    /*	
    	Entity wall6 = new Wall("Wand6");
    	wall6.setPosition(new Vector2f(100,400));
    	entityManager.addEntity(stateID, wall6);
    */	
    	Entity wall7 = new Wall("Wand7");
    	wall7.setPosition(new Vector2f(150,400));
    	entityManager.addEntity(stateID, wall7);
    	
    	Entity doorL = new Door("TürL",true);
    	doorL.setPosition(new Vector2f(300,350));
    	entityManager.addEntity(stateID, doorL);
    /*	
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
    	else if(Parser.map != null) {
    		Entity[][] map = Parser.parse();
    		int y_dim = map.length;
    		int x_dim = map[0].length;
    		for (int y = 0; y < y_dim; y++) {
    			for( int x = 0; x < x_dim; x++) {
    				//wichtig: beachte reihenfolge der Dimensionen!
    				if(map[y][x] != null) {
    					
    					map[y][x].setPosition(new Vector2f(100+x*50,150+y*50));
    					entityManager.addEntity(stateID, map[y][x]);
    					if(map[y][x] instanceof RobotMario) {
    						RobotMario m = (RobotMario) map[y][x];
    						entityManager.addEntity(stateID, m.getHitbox());
    					}
    				}
    			}
    		}
    		
    		//erstellt Levelraender
    		//Decke
    		for (int i=0; i < x_dim+2;i++) {
    			Wall w = new Wall("Wand");
    			w.setPosition(new Vector2f(50+i*50,100));
    			entityManager.addEntity(stateID, w);
    		}
    		//Boden
    		for (int i=0; i < x_dim+2;i++) {
    			Wall w = new Wall("Wand");
    			w.setPosition(new Vector2f(50+i*50,y_dim*50+150));
    			entityManager.addEntity(stateID, w);
    		}
    		
    		//Linke Wand
    		for (int i = 0; i<y_dim;i++) {
    			Wall w = new Wall("Wand");
    			w.setPosition(new Vector2f(50,150+50*i));
    			entityManager.addEntity(stateID, w);
    		}
    		
    		//Rechte Wand
    		for (int i = 0; i<y_dim;i++) {
    			Wall w = new Wall("Wand");
    			w.setPosition(new Vector2f(50*x_dim+100,150+50*i));
    			entityManager.addEntity(stateID, w);
    		}
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

	/**
	 * Baut einen Stahlträger zwischen dem ersten und zweiten Sockel
	 * @param firstSocket erster Sockel
	 * @param secondSocket zweiter Sockel
	 */
	public void createBeam(BeamSocket firstSocket, BeamSocket secondSocket) {
		Vector2f first = firstSocket.getPosition();
		Vector2f second = secondSocket.getPosition();
		//Abstaende zwischen erstem und zweiten Sockel
		double xdist = (double)(first.x - second.x);
		double ydist = (double)(first.y - second.y);
		double length = Math.sqrt(xdist*xdist + ydist*ydist);
		
		//Winkel, den der Stahltraeger haben muss
		double angle =Math.toDegrees(Math.asin(ydist/length));
		
		//Anzahl an Stahltraeger-teilen, die gebaut werden müssen. Jedes kostet genau eine Ressource.
		double round_up = Math.ceil(length/50)*50;
		int count = (int)round_up/50;
		//GamePlayState.ressources=20;
		if(GamePlayState.ressources>=count-1) {
		LinkedList<Beam> lst = new LinkedList<Beam>();
		if(first.x < second.x ) {
			angle*=-1;
			
		}
		System.out.println(angle);
		//Fülle den Zwischenraum mit Stahltraegern
		for (int i=1; i<count;i++) {
			Beam b  = new Beam("Stahltraeger",firstSocket,secondSocket);
			int x = (int)(second.x+(xdist/count)*i);
			int y= (int)(second.y+(ydist/count)*i);
			b.setPosition(new Vector2f(x,y));
			lst.add(b);
			//Rotiere sie entsprechend des Winkels
			
			
			
			b.setRotation((float) angle);
			
			
			entityManager.addEntity(stateID, b);
		}
		firstSocket.addBeams(lst);
		secondSocket.addBeams(lst);
		//entferne Ressourcen
		GamePlayState.ressources-=count-1;
		
		//TODO: also remove it
		}
	}

}
