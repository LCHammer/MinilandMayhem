package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import eea.engine.component.render.ImageRenderComponent;
import minilandMayhem.ui.GamePlayState;

public class Coin extends Collectable {

	public Coin(String entityID) {
		super(entityID);
		try {
			this.addComponent(new ImageRenderComponent(new Image("assets/Coin.png")));
		} catch (SlickException e) {
			System.out.println("Münzenbild konnte nicht geladen werden");
		}
	}

	@Override
	public void performPickup(Mario m) {
		GamePlayState.score += 500;
		
	}

}
