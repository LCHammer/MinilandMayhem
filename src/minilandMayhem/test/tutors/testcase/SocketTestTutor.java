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
import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.test.MinilandTestAdapterMinimal;

public class SocketTestTutor {

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
		
		Entity top_left = sockel.get(0);
		Entity top_right = sockel.get(1);
		pos = top_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = top_left.getPosition();
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
		Entity mario = marios.get(0);
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(1000);
		assertTrue("Mario fell through beam",mario.getPosition().y==pos.y);
		adapter.updateGame(500);
		
		pos = top_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.handleMouseClick(pos.x, pos.y);
		//2 mal klicken entfernt Traeger, mario soll nun von Traeger fallen
		entities = StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		beamCount = 0;
		for(Entity e: entities) {
			if(adapter.isBeam(e) ) {
				beamCount+=1;
			}
		}
		assertTrue("Beams have not been removed",beamCount ==0);
		assertTrue("Wrong amount of Ressources received from deconstructing Beam",adapter.getRessources()==5);
		assertTrue("Mario is not falling from deconstruced beam",adapter.marioIsFalling(mario));
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
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.handleMouseClick(pos.x, pos.y);
		entities = StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		beamCount = 0;
		for(Entity e: entities) {
			if(adapter.isBeam(e) ) {
				beamCount+=1;
			}
		}
		assertTrue("Beams have not been removed",beamCount ==0);
		assertTrue("Wrong amount of Ressources received from deconstructing Beam",adapter.getRessources()==5);
		adapter.updateGame(700);
		assertTrue("Mario did not collide with Wall",!adapter.marioLooksLeft(mario));
		adapter.updateGame(700);
		assertTrue("Mario did collide with removed Beam",!adapter.marioLooksLeft(mario));
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
		
		Entity top_left = sockel.get(0);
		Entity bottom_left = sockel.get(2);
		Entity bottom_right = sockel.get(3);
		pos= top_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_right.getPosition();
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

		
		Entity mario = marios.get(0);
		Entity mario2 = marios.get(2);
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		
		adapter.updateGame(1000);
		adapter.updateGame(100);
		adapter.updateGame(500);
		Vector2f pos2 = mario.getPosition();
		assertTrue("Mario has not the right angle",(mario.getRotation() <= 31f && mario.getRotation() >= 30f) || 
				   mario.getRotation() <=-30f && mario.getRotation() >=-31f);
		adapter.updateGame(100);
		pos = mario.getPosition();
		assertTrue("Mario did not walk on beam",pos.y > pos2.y);
		assertTrue("Mario did not walk on beam",pos.x > pos2.x);
		assertTrue("Mario fell through beam",!adapter.marioIsFalling(mario));
		
		
		adapter.setRessources(10);
		pos = bottom_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		//laeuft der Mario von der falschen Seite gegen den Traeger, soll er die Richtugn wechseln
		pos = mario2.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(1100);
		assertTrue("Mario did not change direction",adapter.marioLooksLeft(mario2));
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
		List<Entity> marios = new LinkedList<Entity>();
		for(Entity e: entities) {
			if(adapter.isSocket(e) ) {
				sockel.add(e);	
			}else if(adapter.isMario(e)) {
				marios.add(e);
			}
		}
		
		Entity top_left = sockel.get(0);
		Entity top_right = sockel.get(1);
		Entity bottom_left = sockel.get(2);
		Entity bottom_right = sockel.get(3);
		adapter.setRessources(20);
		
		pos = top_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Wrong amount of Ressources",adapter.getRessources()==18);
		
		pos = top_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Wrong amount of Ressources",adapter.getRessources()==16);
		
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Wrong amount of Ressources",adapter.getRessources()==12);
		
		pos = top_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Wrong amount of Ressources",adapter.getRessources()==7);
		
		pos = top_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = bottom_right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Wrong amount of Ressources",adapter.getRessources()==2);
		
		pos = bottom_left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Wrong amount of Ressources",adapter.getRessources()==15);
	}
}
