package minilandMayhem.model.entities;

import minilandMayhem.ui.GamePlayState;

public class SteelPickup extends Collectable {

	public SteelPickup(String entityID) {
		super(entityID);
		//TODO: Bild setzen
	}

	@Override
	public void performPickup(Mario m) {
		GamePlayState.ressources += 3;
		
	}

}
