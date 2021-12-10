package minilandMayhem.model.entities;

import org.newdawn.slick.Image;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

public class Door extends Entity{
	
	private boolean unlocked;
	//if true: no key required, if false key required for Mario to enter

	public Door(String entityID,boolean unlocked) {
		super(entityID);
		this.unlocked=unlocked;
		
		try {
			if(unlocked) { //offene Tür hat kein Schloss im Bild
			this.addComponent(new ImageRenderComponent(new Image("assets/door.png")));
			}
			else {
				//TODO: setze Tür mit Schloss im Bild
				this.addComponent(new ImageRenderComponent(new Image("assets/door.png")));
			}
		} catch (Exception e) {
			System.out.println("Türbild konnte nicht geladen werden");
		}
	}
	
	public void unlock() { //unlocks the door
		this.unlocked=true;
		//TODO: needs to change image :remove lock or so
	}

	public boolean getUnlocked() {
		return this.unlocked;
	}
}
