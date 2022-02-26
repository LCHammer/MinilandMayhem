package minilandMayhem.test.students.testcase;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import minilandMayhem.test.MinilandTestAdapterExtended1;

public class CollectableTestStudent {

	MinilandTestAdapterExtended1 adapter;
	String coll = "level/Collectable.txt";
	

	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterExtended1();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public void testCollectables() {
		File f = new File(coll);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =adapter.getEntities();
		Entity mario = null;
		for(Entity e: entities) {
			if(adapter.isMario(e)) {
				mario = e;
			}
		}
		
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(600);
		adapter.updateGame(0);
		assertTrue("Mario went through closed door",adapter.getCurrentStateID() == adapter.getGameStateID());
		assertTrue("Mario did not turn around", adapter.marioLooksLeft(mario));
		assertTrue("Key was not created",adapter.existsKey());
		//assertTrue("Powerup was not created",StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getPowerUpPrefix()));
		assertTrue("Ressource was not created",adapter.existsRessource());
		
	
		adapter.updateGame(1200);
		adapter.updateGame(0);
		assertTrue("Mario did not collect key",adapter.marioHasKey(mario));
		assertTrue("Key has not been removed",!adapter.existsKey());
		
		//adapter.updateGame(1000);
		//adapter.updateGame(0);
		//assertTrue("Mario did not collect Powerup",adapter.marioPowerUp(mario));
		//assertTrue("Powerup has not been removed",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getPowerUpPrefix()));
		
		adapter.updateGame(1000);
		adapter.updateGame(0);
		assertTrue("Mario did not collect Powerup",adapter.getRessources() == 8);
		assertTrue("Ressource has not been removed",!adapter.existsRessource());
		adapter.updateGame(500);
		assertTrue("Mario did not change direction",!adapter.marioLooksLeft(mario));
		adapter.updateGame(3200);
		adapter.updateGame(0);
		adapter.updateGame(0);
		assertTrue("Mario did not unlock closed door",adapter.getCurrentStateID() == adapter.getEndStateID());
		
	}
}
