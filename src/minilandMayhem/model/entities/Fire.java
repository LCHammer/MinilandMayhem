package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LoopEvent;
import minilandMayhem.model.action.Collide;
import minilandMayhem.model.events.RobotLooksLeftEvent;
import minilandMayhem.model.events.RobotLooksRightEvent;
import minilandMayhem.ui.GamePlayState;

public class Fire extends Robot {

	private boolean destroyed;
	private Component imageL;
	private Component imageR;
	
	public Fire(String entityID, boolean canMove) {
		super(entityID);
		destroyed = false;
		
		try {
			imageL = new ImageRenderComponent(new Image("assets/fire.png"));
			imageR = new ImageRenderComponent(new Image("assets/fireRight.png"));
			this.addComponent(imageL);
		} catch (SlickException e) {
			System.out.println("Feuerbild konnte nicht geladen werden");
		}
		
		this.setSize(new Vector2f(48,48));
		CollisionEvent collide = new CollisionEvent();
		collide.addAction(new Collide());
		this.addComponent(collide);
		if(canMove) {
			activate();
		}
		
	}
	
	public void activate() {
		this.isActive=true;
		this.looksRight = false;
		ANDEvent right = new ANDEvent(new LoopEvent(), new RobotLooksRightEvent("MoveRight"));
		right.addAction(new MoveRightAction(speed));
		this.addComponent(right);
		
		ANDEvent left = new ANDEvent(new LoopEvent(), new RobotLooksLeftEvent("MoveLeft"));
		left.addAction(new MoveLeftAction(speed));
		this.addComponent(left);
	}
	
	/**
	 * Zerstroert dieses Feuer
	 */
	public void destroy() {
		if(!this.destroyed) {
			this.destroyed = true;
			GamePlayState.score += 100;
			this.removeEntity();
		}
	
	}
	
	
	@Override
	/**
	 * aendert die Blick- und Laufrichtung des Feuers
	 */
	public void changeDirection() {
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
