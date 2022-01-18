package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.event.basicevents.LoopEvent;

public class Key extends Collectable {

	public Key(String entityID) {
		super(entityID);
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/fire.png")));
		}
		catch(SlickException e) {
			System.out.println("Schluesselbild konnte nicht geladen werden");
		}
	}
	
	public void destroy() {
		LoopEvent l  = new LoopEvent();
		l.addAction(new DestroyEntityAction());
		this.addComponent(l);
	}

}
