package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.component.render.ImageRenderComponent;

public class Key extends Collectable {

	public Key(String entityID) {
		super(entityID);
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/key.png")));
		}
		catch(SlickException e) {
			System.out.println("Schluesselbild konnte nicht geladen werden");
		}
	}

	@Override
	/**
	 * laesst den Mario einen Schluessel besitzen, sodass er verschlossene Türen aufschließen kann.
	 */
	public void performPickup(Mario m) {
		m.collectKey();
		
	}
}
