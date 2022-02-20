package minilandMayhem.model.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import minilandMayhem.model.action.SpawnBillAction;
import minilandMayhem.model.events.TimedEvent;

public class BillBlaster extends Entity {

	public TimedEvent time;
	public BillBlaster(String entityID) {
		super(entityID);
		try {
			this.addComponent(new ImageRenderComponent(new Image("assets/canon.png")));
		} catch (SlickException e) {
			System.out.println("Kanonenbild konnte nicht geladen werden!");
		}
		this.setSize(new Vector2f(48,48));
		
		//spawne alle 5 Sekunden einen BulletBill (abwechselnd links, dann rechts)
		time = new TimedEvent("time",5000);
		time.addAction(new SpawnBillAction());
		this.addComponent(time);
	}

}
