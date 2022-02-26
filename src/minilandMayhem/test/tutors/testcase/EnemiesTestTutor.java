package minilandMayhem.test.tutors.testcase;

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

public class EnemiesTestTutor {


	MinilandTestAdapterExtended3 adapter;
	String enemies = "level/Enemies.txt";
	String fire = "level/Fire.txt";

	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterExtended3();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public void testEnemies() {
		File f = new File(enemies);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =adapter.getEntities();
		List<Entity> blasters = new LinkedList<Entity>();
		List<Entity> marios = new LinkedList<Entity>();
		for(Entity e: entities) {
			if(adapter.isBlaster(e)) {
				blasters.add(e);
			}else if(adapter.isMario(e)) {
				marios.add(e);
			}
		}
		pos = marios.get(0).getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.timeBlaster(5000,blasters.get(1));
		adapter.timeBlaster(5000,blasters.get(1));
		
		entities =adapter.getEntities();
		int bill = 0;
		int fire = 0;
		for(Entity e: entities) {
			if(adapter.isBill(e)) {
				bill +=1;
			}else if(adapter.isFire(e)) {
				fire +=1;
			}
		}
		assertTrue("Blaster did not spawn Bills on both sides",bill == 2);
		adapter.updateGame(100);
		adapter.updateGame(500);
		
		entities = adapter.getEntities();
		bill = 0;
		fire = 0;
		int mario =0;
		for(Entity e: entities) {
			if(adapter.isBill(e)) {
				bill +=1;
			}else if(adapter.isFire(e)) {
				fire +=1;
			}else if(adapter.isMario(e)) {
				mario +=1;
			}
		}
		assertTrue("Bill did not get destroyed after collision",bill == 0);
		assertTrue("Fire did not get destroyed after collision with bill",fire == 1);
		assertTrue("Mario did not get destroyed after collision with bill",mario == 0);		
		assertTrue("Destruction of Fire did not add points",adapter.getScore()== -400);
	}
	
	@Test
	public void testFireMovement() {
		File f = new File(fire);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =adapter.getEntities();
		List<Entity> sockets = new LinkedList<Entity>();
		Entity fire =null;
		for(Entity e: entities) {
			if(adapter.isSocket(e)) {
				sockets.add(e);
			}else if(adapter.isFire(e)) {
				fire = e;
			}
		}
		
		adapter.setRessources(15);
		Entity left = sockets.get(0);
		Entity right= sockets.get(1);
		pos = left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(1000);
		pos = fire.getPosition();
		adapter.updateGame(500);
		assertTrue("fire did not walk up beam",fire.getPosition().x < pos.x);
		assertTrue("fire did not walk up beam",fire.getPosition().y < pos.y);
		assertTrue("fire has wrong angle",fire.getRotation() < 27f || fire.getRotation() > -27f);
		assertTrue("fire has wrong angle",fire.getRotation() > 26f || fire.getRotation() < -26f);
		pos = left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.handleMouseClick(pos.x, pos.y);
		pos = fire.getPosition();
		adapter.updateGame(250);
		adapter.updateGame(250);
		assertTrue("fire did not fall from beam",fire.getPosition().x < pos.x);
		assertTrue("fire did not fall from beam",fire.getPosition().y > pos.y);
		assertTrue("fire did not fall from beam",fire.getRotation() == 0);
		
	}
}
