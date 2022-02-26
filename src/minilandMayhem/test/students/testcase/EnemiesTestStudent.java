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
import minilandMayhem.test.MinilandTestAdapterExtended3;

public class EnemiesTestStudent {


	MinilandTestAdapterExtended3 adapter;
	String enemies = "level/Enemies.txt";
	String trampoline = "level/Trampoline.txt";
	String powerup = "level/Powerup.txt";

	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterExtended3();
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
		List<Entity> entities =adapter.getEntities();
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
	public void testPowerUp() {
		File f = new File(powerup);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		Entity blaster = null;
		Entity mario = null;
		List<Entity> entities =adapter.getEntities();
		for(Entity e: entities) {
			 if(adapter.isMario(e)) {
				mario = e;
			}else if(adapter.isBlaster(e)) {
				blaster = e;
			}
		}
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(100);
		assertTrue("Mario did not collect PowerUp",adapter.marioPowerUp(mario));
		adapter.updateGame(500);
		assertTrue("Destruction of Fire did not give 100 points", adapter.getScore() == 600);
		adapter.timeBlaster(5000l, blaster);
		assertTrue("no Bill was spawned",adapter.existsBill());
		adapter.updateGame(500);
		adapter.updateGame(0);
		adapter.updateGame(0);
		assertTrue("Bill was not destroyed",!adapter.existsBill());
		assertTrue("Bill destroyed Mario even though he had a powerUp",adapter.getCurrentStateID() == adapter.getGameStateID());
		
	}
	
	@Test 
	public void testFire() {
		File f = new File(enemies);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =adapter.getEntities();
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
		entities =adapter.getEntities();
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
		List<Entity> entities =adapter.getEntities();
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
