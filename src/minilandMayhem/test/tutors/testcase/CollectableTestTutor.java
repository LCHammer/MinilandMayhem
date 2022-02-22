package minilandMayhem.test.tutors.testcase;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.test.MinilandTestAdapterExtended3;

public class CollectableTestTutor {

	MinilandTestAdapterExtended3 adapter;
	String coll = "src/level/Collectable2.txt";
	

	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterExtended3();
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
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		Entity mario = null;
		for(Entity e: entities) {
			if(adapter.isMario(e)) {
				mario = e;
			}
		}
		
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Powerup was not created",StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getPowerUpPrefix()));
		assertTrue("Coin was not created",StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getCoinPrefix()));
		
		adapter.updateGame(100);
		adapter.updateGame(0);
		assertTrue("Mario did not collect Powerup",adapter.marioPowerUp(mario));
		assertTrue("Powerup has not been removed",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getPowerUpPrefix()));
		
		adapter.updateGame(500);
		adapter.updateGame(0);
		assertTrue("Mario did not collect Coin",adapter.getScore() == 1000);
		assertTrue("Coin was not removed",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getCoinPrefix()));
		
		adapter.updateGame(500);
		assertTrue("Mario was destroyed even though he had a power up",StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getMarioPrefix()));
		assertTrue("Fire was destroyed even though Mario had a power up",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getFirePrefix()));
		assertTrue("Destruction of fire did not give points",adapter.getScore() == 1100);
		
		
		
	}
}
