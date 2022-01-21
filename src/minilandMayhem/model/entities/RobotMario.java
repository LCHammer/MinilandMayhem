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
import eea.engine.action.basicactions.MoveUpAction;
import eea.engine.action.basicactions.RemoveEventAction;
import eea.engine.component.Component;
import eea.engine.component.render.AnimationRenderComponent;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.NOTEvent;
import eea.engine.event.basicevents.*;
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
	private LoopEvent walkUp;
	private boolean isWalkingUp=false;
	private BeamSocket start;
	private BeamSocket end;
	private LoopEvent walkDown;
	private boolean isWalkingDown = false;
	private Component imageR;
	private Component imageL;
	private Component animationLeft;
	private Component animationRight;
	private Image r1;
	private boolean isDestroyed = false;
	
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
		walkUp = new LoopEvent();
		this.addComponent(walkUp);
		walkDown = new LoopEvent();
		this.addComponent(walkDown);
		this.setSize(new Vector2f(48,48));
		//this.setRotation(30f);
		//this.pHitbox.setRotation(30f);
		
		
		try {
			this.imageR = new ImageRenderComponent(new Image("/assets/mario4.png"));
			this.imageL = new ImageRenderComponent(new Image("assets/Mario1Left.png"));
			this.r1 = new Image("assets/mario1.png");
			Image r2 = new Image("assets/mario2.png");
			Image r3 = new Image("assets/mario3.png");
			Image[] arrRight = new Image[] {r1,r2,r3};
			//this.animationRight = new AnimationRenderComponent(arrRight, -100f, 50, 50, false);
			this.addComponent(this.imageR);
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
			//TODO: change animation!
			//this.removeComponent(imageR);
			//this.addComponent(imageL);
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
			if(this.looksRight) {
				this.removeComponent(imageR);
				this.addComponent(imageL);
			}else {
				this.removeComponent(imageL);
				this.addComponent(imageR);
			}
			looksRight = !looksRight;
			
		}
	}
	

	/**
	 * Zerstört diesen Mario
	 * Wird aufgerufen, wenn Mario durch eine Tür geht oder von einer Gefahr zerstört wird.
	 */
	public void destroy() {
		isDestroyed  = true;
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
		/*
		if(this.getIsWalkingUp()) {
			
			this.setRotation(0);
			this.pHitbox.setRotation(0);
			this.isWalkingUp = false;
			this.walkUp.removeAction(0);
		}
		
		if(this.getIsWalkingDown()) {
			System.out.println("falling");
			
			this.setRotation(0f);
			this.pHitbox.setRotation(0f);
			this.isWalkingDown = false;
			this.walkDown.removeAction(0);
			
		}
		*/
	
		if(isActive) {
		fallings +=1;
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
	}
	
	/**
	 * 
	 * @return true wenn der Mario gerade faellt, sonst false.
	 */
	public boolean getFalling() {
		return isFalling;
	}
	
	/**
	 * laesst den uebergebenen Mario den Stahltraeger hochlaufen
	 * @param b Stahltraeger, den der Mario hochlaeuft
	 */
	public void walkOnBeam(Beam b) {
		//System.out.println(isFalling);
		if(!getIsWalkingUp() && !getIsWalkingDown()) {
			BeamSocket first = b.getFirst();
			BeamSocket second = b.getSecond();
			if(this.looksRight && first.getPosition().x < second.getPosition().x
					|| !this.looksRight && first.getPosition().x > second.getPosition().x) {
				this.start=first;
				this.end=second;
			}else {
				this.start = second;
				this.end = first;
			}
			//System.out.println(b.getRotation());
			this.setRotation(b.getRotation());
			this.pHitbox.setRotation(b.getRotation());
			double angle =Math.toRadians(Math.abs((double)b.getRotation()));
			double up_distance = Math.tan(angle)*(double)speed;
			
			if(this.getLooksRight() == b.getUpRight()) {
				this.walkUp.addAction(new MoveUpAction((float)up_distance));
				this.isWalkingUp =true;
			}else {
				this.walkDown.addAction(new MoveDownAction((float)up_distance));
				this.isWalkingDown=true;
				//this.setPosition(new Vector2f(this.getPosition().x,this.getPosition().y+5));
			}
			
		}
	}
	
	/**
	 * beendet das Hochlaufen auf einem Stahlträger.
	 * @param Sockel, an dem der Mario sein Hochlaufen beendet
	 */
	public void endWalkingUp(BeamSocket socket) {
		
		Vector2f socketpos = socket.getPosition();
		Vector2f pos = this.getPosition();
	
		if(socket.equals(end) && pos.y < socketpos.y-50) {
			this.setRotation(0f);
			this.pHitbox.setRotation(0f);
			this.isWalkingUp=false;
			this.walkUp.removeAction(0);
			System.out.println("end");
			this.end=null;
			this.start=null;
		}
	}
	
	/**
	 * 
	 * @return true, wenn der Mario gerade einen Stahltraeger hochlaeuft, sonst false. 
	 */
	public boolean getIsWalkingUp() {
		return this.isWalkingUp;
	}
	
	/**
	 * 
	 * @return startsockel des Hochlaufens
	 */
	public BeamSocket getStart() {
		return this.start;
	}
	
	
	
	/**
	 * 
	 * @return Endsockel des Hochlaufens
	 */
	public BeamSocket getEnd() {
		return this.end;
	}
	
	
	public boolean getIsWalkingDown() {
		return this.isWalkingDown;
	}
	
	public void walkDownBeam(Beam b) {
		//System.out.println("down");
		walkOnBeam(b);
	}
	
	
	public void endWalkingDown(BeamSocket s) {
		//System.out.println(s.getPosition().y);
		//System.out.println(this.getPosition().y);
		
		if(s.equals(end) && s.getPosition().y -55 < this.getPosition().y) {
			this.setRotation(0f);
			this.pHitbox.setRotation(0f);
			this.isWalkingDown=false;
			this.walkDown.removeAction(0);
			this.smoothLanding();
			this.end=null;
			this.start=null;
			System.out.println("end");
		}
	}
	
	//TODO: nur ausführen, wenn Wall unter den füßen/Beam mit rotation 0/ sockel
	public void smoothLanding() {
		float height = this.getPosition().y;
		double smoothHeight = Math.round(height/50)*50;
		this.setPosition(new Vector2f(this.getPosition().x,(float)smoothHeight));
		
	}
	
	/**
	 * beendet das Hoch-/Runterlaufen auf einem Träger
	 */
	public void stopWalkOnBeam() {
		
		if(this.getIsWalkingUp()) {	
			this.setRotation(0);
			this.pHitbox.setRotation(0);
			this.isWalkingUp = false;
			this.walkUp.removeAction(0);
		}
		
		
		if(this.getIsWalkingDown()) {
			this.setRotation(0f);
			this.pHitbox.setRotation(0f);
			this.isWalkingDown = false;
			this.walkDown.removeAction(0);
		}
		this.isFalling=true;
		this.end=null;
		this.start=null;
	}
	
	
	public boolean getDestroyed() {
		return isDestroyed;
	}
}
