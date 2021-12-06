package minilandMayhem.model.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.MoveForwardAction;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.action.basicactions.RemoveEventAction;
import eea.engine.component.Component;
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
import minilandMayhem.model.action.TurnMarioAround;
import minilandMayhem.model.events.MoveMarioLeft;
import minilandMayhem.model.events.MoveMarioRight;

public class RobotMario extends Entity{

	private boolean isActive;
	private boolean looksRight;
	public float speed;
	public boolean collided;
	
	public RobotMario(String entityID) {
		super(entityID);
		isActive = false;
		looksRight = true;
		speed = 0.125f;
		collided = false;
		
		//default behaviour:
		
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
		Entity wall = collide.getCollidedEntity();
		collide.addAction(new TurnMarioAround());
		this.addComponent(collide);
		
		//resets the collision field if no collision occurs, otherwise a collision still occurs even if the 
		//movement was changed. This new collision would make the mario turn back to the wall again
		NOTEvent reset = new NOTEvent(new CollisionEvent());
		reset.addAction(new Action() {

			@Override
			public void update(GameContainer gc, StateBasedGame game, int delta, Component event) {
				RobotMario m = (RobotMario)event.getOwnerEntity();
				m.collided=false;
				
			}
			
		});
		this.addComponent(reset);
		
		
	}
	
	private void activate() {
		if(!this.isActive) {
		this.isActive=true;
		ANDEvent right = new ANDEvent(new LoopEvent(), new MoveMarioRight("MoveRight"));
		right.addAction(new MoveRightAction(speed));
		this.addComponent(right);
		ANDEvent left = new ANDEvent(new LoopEvent(), new MoveMarioLeft("MoveLeft"));
		left.addAction(new MoveLeftAction(speed));
		this.addComponent(left);
		}
	}
	
	public boolean getLooksRight() {
		return looksRight;
	}
	
	public void changeDirection() {
		looksRight = !looksRight;
	}

	
}
