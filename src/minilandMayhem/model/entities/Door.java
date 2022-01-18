package minilandMayhem.model.entities;

import org.newdawn.slick.Image;

import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

public class Door extends Entity{
	
	private boolean unlocked;
	private Component open;
	private Component locked;
	//if true: no key required, if false key required for Mario to enter

	public Door(String entityID,boolean unlocked) {
		super(entityID);
		this.unlocked=unlocked;
		
		try {
			open = new ImageRenderComponent(new Image("assets/door.png"));
			locked = new ImageRenderComponent(new Image("assets/doorClosed.png"));
			
			if(unlocked) { //offene Tür hat kein Schloss im Bild
				this.addComponent(open);
			}
			else {
				this.addComponent(locked);
			}
		} catch (Exception e) {
			System.out.println("Türbild konnte nicht geladen werden");
		}
	}
	
	public void unlock() { //unlocks the door
		this.unlocked=true;
		this.removeComponent(locked);
		//TODO: needs to change image :remove lock or so
		this.addComponent(open);
	}

	public boolean getUnlocked() {
		return this.unlocked;
	}
}
