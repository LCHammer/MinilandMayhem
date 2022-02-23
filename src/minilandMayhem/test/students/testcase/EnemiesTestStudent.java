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
import minilandMayhem.test.MinilandTestAdapterExtended1;

public class EnemiesTestStudent {


	MinilandTestAdapterExtended1 adapter;
	String enemies = "level/Enemies.txt";
	String trampoline = "level/Trampoline.txt";

	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterExtended1();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public void parseTest() {
		File f = new File(enemies);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		int trampoline = 0;
		int fire = 0;
		int blaster = 0;
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		for(Entity e: entities) {
			if(adapter.isTrampoline(e)) {
				trampoline+=1;
			}else if(adapter.isFire(e)) {
				fire +=1;
			}else if(adapter.isBlaster(e)) {
				blaster+=1;
			}
		}
		assertTrue("not all trampolines have been recognized",trampoline == 3);
		assertTrue("not all fires have been recognized",fire == 2);
		assertTrue("not all blasters have been recognized",blaster == 2);
	}
	
	@Test
	public void testBlaster() {
		File f = new File(enemies);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		List<Entity> blasters = new LinkedList<Entity>();
		for(Entity e: entities) {
			if(adapter.isBlaster(e)) {
				blasters.add(e);
			}
		}
		adapter.timeBlaster(5000,blasters.get(0));
		adapter.timeBlaster(5000,blasters.get(1));
		
		 entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		int bill = 0;
		for(Entity e: entities) {
			if(adapter.isBill(e)) {
				bill +=1;
			}
		}
		assertTrue("not enough Bills have spawned",bill == 2);
	}
	
	@Test 
	public void testFire() {
		File f = new File(enemies);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		int marios =0;
		List<Entity> fire = new LinkedList<Entity>();
		for(Entity e: entities) {
			if(adapter.isFire(e)) {
				fire.add(e);
			}else if(adapter.isMario(e)) {
				marios +=1;
			}
		}
		assertTrue("not all Marios have been detected",marios == 2);
		pos= fire.get(0).getPosition();
		adapter.updateGame(100);
		assertTrue("fire did not move to the left",fire.get(0).getPosition().x < pos.x);
		assertTrue("fire did not stay on the ground", fire.get(0).getPosition().y == pos.y);
		adapter.updateGame(0);
		entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		marios = 0;
		for(Entity e: entities) {
			 if(adapter.isMario(e)) {
				marios +=1;
			}
		}
		assertTrue("Mario did not get destroyed after colliding with fire",marios == 1);
		pos = fire.get(1).getPosition();
		adapter.updateGame(1000);
		assertTrue("only one fire moved",fire.get(1).getPosition().x < pos.x);
		assertTrue("fire did not stay on the ground", fire.get(1).getPosition().y == pos.y);
		
	}
	
	@Test
	public void testTrampoline() {
		File f = new File(trampoline);	
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
		adapter.updateGame(750);
		adapter.updateGame(1000);
		assertTrue("Mario did not jump",mario.getPosition().y < pos.y);
		assertTrue("Mario did not continue moving to the left", mario.getPosition().x > pos.x);
	}
}
