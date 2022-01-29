package minilandMayhem.model.entities;

public class PowerUp extends Collectable{

	public PowerUp(String entityID) {
		super(entityID);
		// TODO setze Bild
	}

	@Override
	public void performPickup(Mario m) {
		m.powerUp();
		
	}

}
