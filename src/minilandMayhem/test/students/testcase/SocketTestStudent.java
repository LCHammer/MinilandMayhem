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
import minilandMayhem.test.MinilandTestAdapterMinimal;

public class SocketTestStudent {

	MinilandTestAdapterMinimal adapter;
	String sockets = "src/level/Sockets.txt";
	
	
	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterMinimal();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	
	@Test
	public void testHorizontal() {
		File f = new File(sockets);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game started with wrong amount of ressources",adapter.getRessources()==5);
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
		
		Entity bottom_left = sockel.get(2);
		Entity bottom_right = sockel.get(3);
		pos = bottom_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		entities = StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		int beamCount = 0;
		for(Entity e: entities) {
			if(adapter.isBeam(e) ) {
				beamCount+=1;
				assertTrue("Beam is not horizontal",e.getRotation()==0);
			}
		}
		assertTrue("Beam has not been created",beamCount>=1);
		assertTrue("Wrong amount of Ressources used",adapter.getRessources()==1);
		Entity mario = marios.get(2);
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(1000);
		assertTrue("Mario fell through beam",mario.getPosition().y==pos.y);
		adapter.updateGame(1000);
		assertTrue("Mario fell through beam",mario.getPosition().y==pos.y);
		adapter.updateGame(1000);
		adapter.updateGame(500);
		assertTrue("Mario could not walk over created horzontal beam",mario.getPosition().x > bottom_right.getPosition().x);
		assertTrue("GameState has changed!",adapter.getCurrentStateID()==adapter.getGameStateID());
		
		
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.handleMouseClick(pos.x, pos.y);
		//2 mal klicken entfernt Traeger
		entities = StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		beamCount = 0;
		for(Entity e: entities) {
			if(adapter.isBeam(e) ) {
				beamCount+=1;
			}
		}
		assertTrue("Beams have not been removed",beamCount ==0);
		assertTrue("Wrong amount of Ressources received from deconstructing Beam",adapter.getRessources()==5);
		
	}
		
	
	
	@Test
	public void testVertical() {
		File f = new File(sockets);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game started with wrong amount of ressources",adapter.getRessources()==5);
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
		
		Entity top_left = sockel.get(0);
		Entity bottom_left = sockel.get(2);
		pos = top_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		int beamCount = 0;
		for(Entity e: entities) {
			if(adapter.isBeam(e) ) {
				beamCount+=1;
				assertTrue("Beam is not horizontal",e.getRotation() == 90f || e.getRotation() == -90f);
			}
		}
		assertTrue("Beam has not been created",beamCount>=1);
		assertTrue("Wrong amount of Ressources used",adapter.getRessources()==3);
		Entity mario = marios.get(2);
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(100);		
		assertTrue("Mario did not collide with Beam",adapter.marioLooksLeft(mario));
	}
	
	@Test
	public void testDiagonal() {
		File f = new File(sockets);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game started with wrong amount of ressources",adapter.getRessources()==5);
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
		Entity top_right = sockel.get(1);
		Entity bottom_left = sockel.get(2);
		pos= bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = top_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		int beamCount = 0;
		for(Entity e: entities) {
			if(adapter.isBeam(e) ) {
				beamCount+=1;
				assertTrue("Beam has not the right angle",(e.getRotation() <= 31f && e.getRotation() >= 30f) || 
														   e.getRotation() <=-30f && e.getRotation() >=-31f);
			}
		}
		assertTrue("Beam has not been created",beamCount>=1);
		assertTrue("Wrong amount of Ressources used",adapter.getRessources()==0);
		Entity mario = marios.get(2);
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(600);
		assertTrue("Mario changed direction after collision with beam",!adapter.marioLooksLeft(mario));
		adapter.updateGame(400);
		Vector2f pos2 = mario.getPosition();
		assertTrue("Mario did not walk on beam",pos.y > pos2.y);
		assertTrue("Mario did not walk on beam",pos.x < pos2.x);
		assertTrue("Mario has not the right angle",(mario.getRotation() <= 31f && mario.getRotation() >= 30f) || 
													mario.getRotation() <=-30f && mario.getRotation() >=-31f);
		
	}
	
	@Test
	public void testRessources() {
		File f = new File(sockets);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game started with wrong amount of ressources",adapter.getRessources()==5);
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		List<Entity> sockel = new LinkedList<Entity>(); 
		for(Entity e: entities) {
			if(adapter.isSocket(e) ) {
				sockel.add(e);	
			}
		}
		
		Entity top_left = sockel.get(0);
		Entity top_right = sockel.get(1);
		Entity bottom_left = sockel.get(2);
		Entity bottom_right = sockel.get(3);
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		int size = entities.size();
		pos = top_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = top_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		assertTrue("Beam was constructed, but Ressources were not suffcient",size==entities.size());
		
		
	}
}
