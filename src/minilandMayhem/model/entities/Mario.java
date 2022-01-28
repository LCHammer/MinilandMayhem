package minilandMayhem.model.entities;

import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.*;
import eea.engine.component.Component;
import eea.engine.component.render.AnimationRenderComponent;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.NOTEvent;
import eea.engine.event.basicevents.*;
import minilandMayhem.model.action.Collide;
import minilandMayhem.model.action.MarioCollide;
import minilandMayhem.model.events.GroundCollision;
import minilandMayhem.model.events.RobotLooksLeftEvent;
import minilandMayhem.model.events.RobotLooksRightEvent;
import minilandMayhem.ui.GamePlayState;

public class Mario extends Robot{

	//public boolean collided;
	private boolean hasKey;
	private boolean destroyed;
	
	private Component imageR;
	private Component imageL;
	private Component animationLeft;
	private Component animationRight;
	private Image r1;
	
	/**
	 * Konstruktor
	 * @param entityID ID von diesem Mario
	 */
	public Mario(String entityID) {
		super(entityID);
		isActive = false;
		destroyed = false;
		
		hasKey = false;
		this.setPassable(false);
		
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
				Mario r = (Mario) event.getOwnerEntity();
			    r.activate();
			}
			
		});
		this.addComponent(activate);
	
		//Event collide = new KeyPressedEvent(Input.KEY_SPACE);
		CollisionEvent collide = new CollisionEvent();
		collide.addAction(new MarioCollide());
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
			ANDEvent right = new ANDEvent(new LoopEvent(), new RobotLooksRightEvent("MoveRight"));
			right.addAction(new MoveRightAction(speed));
			this.addComponent(right);
			
			ANDEvent left = new ANDEvent(new LoopEvent(), new RobotLooksLeftEvent("MoveLeft"));
			left.addAction(new MoveLeftAction(speed));
			this.addComponent(left);
			//TODO: change animation!
			//this.removeComponent(imageR);
			//this.addComponent(imageL);
		}
	}
	
	@Override
	/**
	 * aendert die Laufrichtung und das Bild des Marios.
	 */
	public void changeDirection() {
		if(this.isActive) {
			if(this.looksRight) {
				this.removeComponent(imageR);
				this.addComponent(imageL);
			}else {
				this.removeComponent(imageL);
				this.addComponent(imageR);
			}
		
		super.changeDirection();
		}
	}
	
	/**
	 * Zieht dem Spieler Punkte ab und entfernt den Mario. Wird aufgerufen, wenn der Mario mit einer Gefahr/Gegner kollidiert.
	 */
	public void destroy() {
		if(!this.destroyed) {
			this.destroyed = true;
			GamePlayState.score-= 500;
			this.removeEntity();
		}
	}
	
	/**
	 * Gibt dem Spieler Punkte und entfernt den Mario. Wird aufgerufen, wenn der Mario durch eine Tür laeuft
	 */
	public void score() {
		if(!this.destroyed) {
			GamePlayState.score += 500;
			GamePlayState.successfulMario +=1;
			this.removeEntity();
			this.destroyed = true;
			
		}
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
	
	
}
