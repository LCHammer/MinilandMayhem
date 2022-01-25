package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import minilandMayhem.model.events.TimedEvent;

public class BillBlaster extends Entity {

	public BillBlaster(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
		try {
			this.addComponent(new ImageRenderComponent(new Image("assets/canon.png")));
		} catch (SlickException e) {
			System.out.println("Kanonenbild konnte nicht geladen werden!");
		}
		this.setSize(new Vector2f(48,48));
		
		TimedEvent time = new TimedEvent("time",5000);
		time.addAction(new SpawnBillAction());
		this.addComponent(time);
	}

}
