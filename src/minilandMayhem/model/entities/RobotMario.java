package minilandMayhem.model.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.action.basicactions.MoveForwardAction;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.action.basicactions.RemoveEventAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.NOTEvent;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import eea.engine.event.basicevents.LoopEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import eea.engine.event.basicevents.MovementDoesNotCollideEvent;
import minilandMayhem.model.action.Collide;
import minilandMayhem.model.events.GroundCollision;
import minilandMayhem.model.events.MarioLooksLeftEvent;
import minilandMayhem.model.events.MarioLooksRightEvent;

public class RobotMario extends Entity{

	private boolean isActive;
	private boolean looksRight;
	public float speed;
	//public boolean collided;
	private boolean hasKey;
	private PhysicsHitbox pHitbox;
	private LoopEvent fall;
	private int fallings = 0;
	private boolean isFalling = false;
	
	/**
	 * Konstruktor
	 * @param entityID ID von diesem Mario
	 */
	public RobotMario(String entityID) {
		super(entityID);
		isActive = false;
		looksRight = true;
		speed = 0.125f;
		hasKey = false;
		pHitbox = new PhysicsHitbox("Phyisc"+entityID, this.getPosition(), this);
		this.setPassable(true);
		fall = new LoopEvent();
		this.addComponent(fall);
		this.setSize(new Vector2f(48,48));
		
		
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/mario1.png")));
		}
		catch(SlickException e) {
			System.out.println("Mariobild konnte nicht geladen werden");
		}
		
		//Standardverhalten des Mario
    	ANDEvent activate = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		activate.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				// TODO Auto-generated method stub
				RobotMario r = (RobotMario) event.getOwnerEntity();
			    r.activate();
			}
			
		});
		this.addComponent(activate);
	
		//Event collide = new KeyPressedEvent(Input.KEY_SPACE);
		CollisionEvent collide = new CollisionEvent();
		collide.addAction(new Collide());
		this.addComponent(collide);
				
		
	}
	
	/**
	 * aktiviert diesen Mario.
	 * Dieser bewegt sich dann immer nach rechts, solange er nach rechts schaut und 
	 * immer nach links, wenn er nach links schaut.
	 * 
	 */
	private void activate() {
		if(!this.isActive) {
			this.isActive=true;
			ANDEvent right = new ANDEvent(new LoopEvent(), new MarioLooksRightEvent("MoveRight"));
			right.addAction(new MoveRightAction(speed));
			this.addComponent(right);
			
			
			ANDEvent left = new ANDEvent(new LoopEvent(), new MarioLooksLeftEvent("MoveLeft"));
			left.addAction(new MoveLeftAction(speed));
			this.addComponent(left);
		}
	}
	
	/**
	 * 
	 * @return true wenn dieser Mario nach rechts schaut und somit auch nach rechts läuft.
	 *		   Schaut er nach links, wird false zurückgegeben.
	 */
	public boolean getLooksRight() {
		return looksRight;
	}
	
	/**
	 * Ändert die Blickrichtung dieses Marios und somit auch die Richtung, in die er sich bewegt.
	 */
	public void changeDirection() { 
		if(!isFalling) {
		looksRight = !looksRight;
		}
	}
	

	/**
	 * Zerstört diesen Mario
	 * Wird aufgerufen, wenn Mario durch eine Tür geht oder von einer Gefahr zerstört wird.
	 */
	public void destroy() {
		Event l = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.pHitbox.destroy();
		this.addComponent(l);
	}
	
	/**
	 * setzt das Feld hasKey auf true, somit kann dieser Mario nun verschlossene Türen aufschließen.
	 */
	public void collectKey() {
		this.hasKey=true;
	}
	
	/**
	 * 
	 * @return true wenn dieser Mario einen Schlüssel trägt (sonst false)
	 */
	public boolean getHasKey() {
		return this.hasKey;
	}

	/**
	 * 
	 * @return true wenn dieser Mario bereits aktiviert wurde, sonst false
	 */
	public boolean getIsActive() {
		return this.isActive;
	}
	
	/**
	 * 
	 * @return die PhysicsHitbox von diesem Mario
	 */
	public PhysicsHitbox getHitbox() {
		return this.pHitbox;
	}
	
	
	/**
	 * laesst den Mario fallen. Dabei wird er enstsprechend 1/10 der Erdgravitation beschleunigt.
	 * Um Echtzeitverhalten zu simulieren, werden die aktuellen FPS miteinbezogen
	 */
	public void fall(int fps) {
		isFalling = true;
		if(isActive) {
		fallings +=1;
		//collided = true;
		isFalling = true;
		fall.addAction(new MoveDownAction(0.981f/fps));
		}
	}
	
	
	/**
	 * beendet die Fallbewegung und laesst den Mario landen
	 */
	public void land() {
		for(int i =0; i<fallings; i++) {
		fall.removeAction(0);
		}
		fallings = 0;
		isFalling = false;
		//collided = true;
	}
	
	/**
	 * 
	 * @return true wenn der Mario gerade faellt, sonst false.
	 */
	public boolean getFalling() {
		return isFalling;
	}
}
