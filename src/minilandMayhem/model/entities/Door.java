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
	
	/**
	 * schließt die Tuer auf, damit auch Marios ohne Schluessel hindurch gehen können
	 * Aendert außerdem das Bild der Tuer (in eine normale Tuer)
	 */
	public void unlock() { 
		this.unlocked=true;
		this.removeComponent(locked);
		this.addComponent(open);
	}

	/**
	 * 
	 * @return true, wenn die Tuer aufgeschlossen ist, sonst false
	 */
	public boolean getUnlocked() {
		return this.unlocked;
	}
}
