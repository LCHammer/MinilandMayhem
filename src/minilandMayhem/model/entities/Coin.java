package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.component.render.ImageRenderComponent;

public class Coin extends Collectable {

	public Coin(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
		try {
			this.addComponent(new ImageRenderComponent(new Image("assets/Coin.png")));
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			System.out.println("Münzenbild konnte nicht geladen werden");
		}
	}

}
