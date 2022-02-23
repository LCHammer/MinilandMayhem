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
import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.test.MinilandTestAdapterExtended2;

public class HighscoreTestStudent {

	
	MinilandTestAdapterExtended2 adapter;
	String score = "level/Score.txt";
	
	
	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterExtended2();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public void testScore() {
		File f = new File(score);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game started with wrong amount of points",adapter.getScore() == 500);
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		List<Entity> sockel = new LinkedList<Entity>(); 
		List<Entity> marios = new LinkedList<Entity>();
		for(Entity e: entities) {
			if(adapter.isSocket(e) ) {
				sockel.add(e);	
			}else if(adapter.isMario(e)) {
				marios.add(e);
			}
		}
		Entity left = sockel.get(0);
		Entity right = sockel.get(1);
		Entity mario1 = marios.get(0);
		Entity mario2 = marios.get(1);
		adapter.updateGame(100);
		assertTrue("Destruction of fire does not give correct amount of points",adapter.getScore() == 600);
		pos = left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Creation of beams does not subtract correct amount of points",adapter.getScore() == 590);
		pos = mario1.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(1100);
		assertTrue("Mario going through door does not give correct amount of points",adapter.getScore() == 1090);
		pos = mario2.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(600);
		assertTrue("Destruction of Mario does not subtract correct amount of points",adapter.getScore() == 590);
	}
}

