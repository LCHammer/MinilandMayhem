package minilandMayhem.test.tutors.testcase;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import minilandMayhem.test.MinilandTestAdapterExtended1;

public class CollectableTestTutor {

	MinilandTestAdapterExtended1 adapter;
	String coll = "level/Collectable2.txt";
	

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
		adapter.updateGame(100);
		adapter.updateGame(0);
		assertTrue("Mario did not collect Ressource",adapter.getRessources()==8);
		adapter.updateGame(500);
		assertTrue("Mario did not collect Ressource",adapter.getRessources()==11);
		adapter.updateGame(500);
		assertTrue("Mario did not collect Ressource",adapter.getRessources()==14);
		adapter.updateGame(0);
		assertTrue("Ressource has not been removed",!adapter.existsRessource());
		
		//adapter.updateGame(500);
		//adapter.updateGame(0);
		//assertTrue("Mario did not collect Coin",adapter.getScore() == 1000);
		//assertTrue("Coin was not removed",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getCoinPrefix()));
		
		//adapter.updateGame(500);
		//assertTrue("Mario was destroyed even though he had a power up",StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getMarioPrefix()));
		//assertTrue("Fire was destroyed even though Mario had a power up",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(),adapter.getFirePrefix()));
		//assertTrue("Destruction of fire did not give points",adapter.getScore() == 1100);
		
		
		
	}
}
