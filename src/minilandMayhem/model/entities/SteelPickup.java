package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.component.render.ImageRenderComponent;
import minilandMayhem.ui.GamePlayState;

public class SteelPickup extends Collectable {

	public SteelPickup(String entityID) {
		super(entityID);
		try {
			this.addComponent(new ImageRenderComponent(new Image("/assets/iron.png")));
		}catch (SlickException e) {
			System.out.println("Ressourcenbild konnte nicht geladen werden");
	}
	}

	@Override
	/**
	 * erhoeht die Anzahl an verfuegbaren Ressourcen um 3
	 */
	public void performPickup(Mario m) {
		GamePlayState.ressources += 3;
		
	}

}
