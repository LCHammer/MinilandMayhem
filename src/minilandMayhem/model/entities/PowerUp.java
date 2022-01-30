package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.component.render.ImageRenderComponent;

public class PowerUp extends Collectable{

	public PowerUp(String entityID) {
		super(entityID);
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/key.png")));
		}
		catch(SlickException e) {
			System.out.println("Pilzbild konnte nicht geladen werden");
		}
	}

	@Override
	public void performPickup(Mario m) {
		m.powerUp();
		
	}

}
