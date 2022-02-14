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
import eea.engine.event.Event;
import eea.engine.event.OREvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import minilandMayhem.model.entities.*;
import minilandMayhem.model.events.NoMarioLeftEvent;
import minilandMayhem.model.events.TimedEvent;
import minilandMayhem.model.mapParser.Parser;

public class GamePlayState extends BasicGameState {
	
	
	private int stateID;
    private StateBasedEntityManager entityManager;
    public static int ressources = 5;
    public static BeamSocket selectedSocket = null;
    public static LinkedList<Mario> marios = new LinkedList<Mario>();
    public static LinkedList<Robot> robots = new LinkedList<Robot>();
    public static int successfulMario;
	public static int score;
	public static int maxMarios;
	private String pausetext = "";
    
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
    	
    	// Bei Druecken der ESC-Taste oder Ende des Spiels in den Endscreen wechseln
    	Entity finished = new Entity("finished");
    	Event end_game = new OREvent(new KeyPressedEvent(Input.KEY_ESCAPE),new NoMarioLeftEvent("noMario"));
    	end_game.addAction(new ChangeStateAction(MinilandMayhem.ENDSCREENSTATE));    	
    	finished.addComponent(end_game);    	
    	entityManager.addEntity(stateID, finished);
    	
    	
    	Entity t = new Entity("Timer");
    	Event time = new TimedEvent("Timer",1000);
    	time.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				//Zieht jede sekunde einen Punkt ab.
				GamePlayState.score -= 1;
			}
    		
    	});
    	t.addComponent(time);
    	entityManager.addEntity(stateID, t);
    	
    	//pausiert das Spiel bei Tastendruck auf P
    	Entity p = new Entity("Pause");    	
    	Event pause = new KeyPressedEvent(Input.KEY_P);
    	pause.addAction(new Action(){
    		@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
    			
				if(gc.isPaused()){
					gc.resume();
					pausetext = "";
				} else {
					gc.pause();
					pausetext = "pausiert";
				}
				
			}    		
    	});
    	p.addComponent(pause);
    	entityManager.addEntity(stateID, p);
    	
    	successfulMario = 0;
    	score = 500;
    	ressources = 5;
    	
    	//default Level, wenn keines ausgewaehlt wurde; enthaellt jede Entity 
    	if(Parser.map==null) {    	
    	Mario mario = new Mario("Mario1");
    	mario.setPosition(new Vector2f(100,100));
    	entityManager.addEntity(stateID, mario);
    	entityManager.addEntity(stateID, mario.getHitbox());
    	
    	Entity wall1 = new Wall("Boden");
    	wall1.setPosition(new Vector2f(150,100));
    	entityManager.addEntity(stateID, wall1);
    	
    	Fire fire = new Fire("Feuer",false);
    	fire.setPosition(new Vector2f(200,100));
    	entityManager.addEntity(stateID, fire);
    	entityManager.addEntity(stateID, fire.getHitbox());
    	
    	Entity socket = new BeamSocket("Sockel");
    	socket.setPosition(new Vector2f(250,100));
    	entityManager.addEntity(stateID, socket);
    	
    	Entity beam = new Beam("Träger", null, null, false);
    	beam.setPosition(new Vector2f(300,100));
    	entityManager.addEntity(stateID, beam);
    	
    	Entity blaster = new BillBlaster("Kanone");
    	blaster.setPosition(new Vector2f(250,400));
    	entityManager.addEntity(stateID, blaster);
    	
    	Entity closed = new Door("geschlossen",false);
    	closed.setPosition(new Vector2f(350,100));
    	entityManager.addEntity(stateID, closed);    	
    	
    	Entity doorL = new Door("Tür",true);
    	doorL.setPosition(new Vector2f(400,100));
    	entityManager.addEntity(stateID, doorL);
    
    	Entity coin = new Coin("coin");
    	coin.setPosition(new Vector2f(450,100));
    	entityManager.addEntity(stateID, coin);
    	
    	Entity danger = new Danger("Gefahr");
    	danger.setPosition(new Vector2f(100,200));
    	entityManager.addEntity(stateID, danger);
    	
    	Entity key = new Key("key");
    	key.setPosition(new Vector2f(150,200));
    	entityManager.addEntity(stateID, key);
    	
    	Entity star = new PowerUp("PowerUp");
    	star.setPosition(new Vector2f(200,200));
    	entityManager.addEntity(stateID, star);
    	
    	Entity iron = new SteelPickup("Ressource");
    	iron.setPosition(new Vector2f(250,200));
    	entityManager.addEntity(stateID, iron);
    	
    	Entity trampoline = new Trampoline("Trampolin");
    	trampoline.setPosition(new Vector2f(300,200));
    	entityManager.addEntity(stateID, trampoline);
    	
    	Entity bill = new BulletBill("Bill",false);
    	bill.setPosition(new Vector2f(100,300));
    	entityManager.addEntity(stateID, bill);
    	
    	
    	}
    	else if(Parser.map != null) {
    		Entity[][] map = Parser.parse();
    		int y_dim = map.length;
    		int x_dim = map[0].length;
    		robots = new LinkedList<Robot>();
    		for (int y = 0; y < y_dim; y++) {
    			for( int x = 0; x < x_dim; x++) {
    				//wichtig: beachte reihenfolge der Dimensionen!
    				if(map[y][x] != null) {
    					
    					map[y][x].setPosition(new Vector2f(100+x*50,150+y*50));
    					entityManager.addEntity(stateID, map[y][x]);
    					if(map[y][x] instanceof Mario) {
    						Mario m = (Mario) map[y][x];
    						entityManager.addEntity(stateID, m.getHitbox());
    						robots.add(m);
    						marios.add(m);
    						maxMarios +=1;
    					}else if(map[y][x] instanceof Fire) {
    						Fire f = (Fire)map[y][x];
    						robots.add(f);
    						entityManager.addEntity(stateID, f.getHitbox());
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
		
		String ressourcen = "Stahträger: "+ ressources;
		String points = "Punktzahl: "+ score;
		
		//float werte sind x und y koordinate der Stringposition
		g.drawString(ressourcen, 110, 10);
		g.drawString(points, 330, 10);
		g.drawString(pausetext, 400, 10);
		
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
		
		//berechnet, ob der Traeger nach oben rechts / unten links zeigt
		boolean upRight = false;
		if(xdist*ydist < 0) {
			upRight = true;
		}
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
		//Fülle den Zwischenraum mit Stahltraegern
		for (int i=0; i<count;i++) {
			Beam b  = new Beam("Stahltraeger",firstSocket,secondSocket,upRight);
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
		//entferne Ressourcen und ziehe 10 Punkte ab
		GamePlayState.ressources-=count-1;
		GamePlayState.score -= 10;
		
		}
	}
	
	
	/**
	 *  Ist ein Roboter auf einem der entfernten Träger, so soll er das Hoch/Runterlaufen beenden
	 * @param socket Sockel, auf den 2 mal geklickt wurde. 
	 */
	public void dropRobotFromBeams(BeamSocket socket) {
		for(Robot r : robots){
			if(socket.equals(r.getStart())|| socket.equals(r.getEnd())) {
				r.stopWalkOnBeam();
				System.out.println(r.getID());
			}
		}	
	}
	
}
