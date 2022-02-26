package minilandMayhem.test.students.testcase;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import minilandMayhem.test.MinilandTestAdapterExtended1;

public class BillTestStudent {

	MinilandTestAdapterExtended1 adapter;
	String bill = "level/Bill.txt";

	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterExtended1();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public void testBill() {
		File f = new File(bill);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =adapter.getEntities();
		List<Entity> blasters = new LinkedList<Entity>();
		for(Entity e: entities) {
			if(adapter.isBlaster(e)) {
				blasters.add(e);
			}
		}
		adapter.timeBlaster(5000,blasters.get(0));
		adapter.timeBlaster(5000,blasters.get(1));
		
		entities =adapter.getEntities();
		int bill = 0;
		for(Entity e: entities) {
			if(adapter.isBill(e)) {
				bill +=1;
			}
		}
		assertTrue("Blaster did not spawn Bills",bill == 2);
		adapter.updateGame(1600);
		
		entities =adapter.getEntities();
		bill = 0;
		for(Entity e: entities) {
			if(adapter.isBill(e)) {
				bill +=1;
			}
		}
		assertTrue("Bills did not get destroyed after colliding",bill == 0);
	}
}
