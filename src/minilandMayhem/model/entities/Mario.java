package minilandMayhem.model.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.*;
import eea.engine.component.Component;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.*;
import minilandMayhem.model.action.MarioCollide;
import minilandMayhem.model.events.RobotLooksLeftEvent;
import minilandMayhem.model.events.RobotLooksRightEvent;
import minilandMayhem.render.MyAnimationRenderComponent;
import minilandMayhem.ui.GamePlayState;

public class Mario extends Robot{

	private boolean hasKey;
	private boolean destroyed;
	private boolean hasPowerUp;
	
	private Component imageR;
	private Component animationLeft;
	private Component animationRight;
	private Component powerAnimationL;
	private Component powerAnimationR;
	
	/**
	 * Konstruktor
	 * @param entityID ID von diesem Mario
	 */
	public Mario(String entityID) {
		super(entityID);
		isActive = false;
		destroyed = false;
		hasPowerUp = false;
		
		hasKey = false;
		this.setPassable(false);
		
		this.setSize(new Vector2f(48,48));
		
		
		try {
			this.imageR = new ImageRenderComponent(new Image("assets/mario1.png"));
			Image r1 = new Image("assets/mario1.png");
			Image r2 = new Image("assets/mario2.png");
			Image r3 = new Image("assets/mario3.png");
			Image[] arrRight = new Image[] {r1,r2,r1,r3};
			
			Image l1 = new Image("assets/mario1Left.png");
			Image l2 = new Image("assets/mario2Left.png");
			Image l3 = new Image("assets/mario3Left.png");
			Image[] arrLeft = new Image[] {l1,l2,l1,l3};
			
			Image pr1 = new Image("assets/powerUp1.png");
			Image pr2 = new Image("assets/powerUp2.png");
			Image pr3 = new Image("assets/powerup3.png");
			Image[] powerArrRight = new Image[] {pr1,pr2,pr1,pr3};
			
			Image pl1 = new Image("assets/powerup1Left.png");
			Image pl2 = new Image("assets/powerup2Left.png");
			Image pl3 = new Image("assets/powerup3Left.png");
			Image[] powerArrLeft = new Image[] {pl1,pl2,pl1,pl3};
			
			this.animationRight = new MyAnimationRenderComponent(arrRight, 0.002f, 48, 48, true);
			this.animationLeft = new MyAnimationRenderComponent(arrLeft, 0.002f, 48, 48, true);
			
			this.powerAnimationR = new MyAnimationRenderComponent(powerArrRight, 0.002f, 48, 48, true);
			this.powerAnimationL = new MyAnimationRenderComponent(powerArrLeft, 0.002f, 48, 48, true);
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
			this.removeComponent(imageR);
			this.addComponent(animationRight);
			
		}
	}
	
	@Override
	/**
	 * aendert die Laufrichtung und das Bild des Marios.
	 */
	public void changeDirection() {
		if(this.isActive) {
			if(this.looksRight) {
				this.removeComponent(animationRight);
				this.addComponent(animationLeft);
			}else {
				this.removeComponent(animationLeft);
				this.addComponent(animationRight);
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
	 * laesst den Mario (fast) unbesiegbar werden und aendert seine Farbe
	 */
	public void powerUp() {
		this.hasPowerUp = true;
		if(this.looksRight) {
			this.removeComponent(animationRight);
			this.animationRight=powerAnimationR;
			this.animationLeft = powerAnimationL;
			this.addComponent(animationRight);
		}else {
			this.removeComponent(animationLeft);
			this.animationRight=powerAnimationR;
			this.animationLeft = powerAnimationL;
			this.addComponent(animationLeft);
			
		}
	}
	
	/**
	 * 
	 * @return true wenn dieser Mario einen Schlüssel trägt (sonst false)
	 */
	public boolean getHasKey() {
		return this.hasKey;
	}
	
	
	/**
	 * @return true, wenn dieser Mario ein PowerUp(=Stern) eingesammelt hat.
	 */
	public boolean getHasPowerUp() {
		return this.hasPowerUp;
	}
	
	
}
