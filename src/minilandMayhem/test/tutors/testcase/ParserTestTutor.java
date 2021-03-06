package minilandMayhem.test.tutors.testcase;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import minilandMayhem.test.MinilandTestAdapterMinimal;

public class ParserTestTutor {

	MinilandTestAdapterMinimal adapter;
	String win= "level/Win.txt";
	String lose= "level/Lose.txt";
	String sockets= "level/Sockets.txt";
	String incorrect = "level/incorrect.txt";
	String otherChars = "level/nonSpecifiedCharacters.txt";
	
	
	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterMinimal();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public void testParseCorrectMap1() {
		
		File f = new File(win);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		int doors = 0;
		int marios = 0;
		int walls = 0;
		List<Entity> entities = adapter.getEntities();
		for(Entity e: entities) {
			if(adapter.isDoor(e)) {
				doors+=1;
			}else if(adapter.isMario(e)) {
				marios +=1;
			}else if(adapter.isWall(e)) {
				walls+=1;
			}
		}
		assertTrue("not all doors have been recognized",doors == 3);
		assertTrue("Mario has not been recognized",marios == 1);
		assertTrue("The bounding box of Walls is missing",walls == 24);
	}
	

	@Test
	public void testParseCorrectMap2() {
		
		File f = new File(lose);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		int doors = 0;
		int marios = 0;
		int walls = 0;
		int dangers = 0;
		List<Entity> entities = adapter.getEntities();
		for(Entity e: entities) {
			if(adapter.isDoor(e)) {
				doors+=1;
			}else if(adapter.isMario(e)) {
				marios +=1;
			}else if(adapter.isWall(e)) {
				walls+=1;
			}else if(adapter.isDanger(e)) {
				dangers+=1;
			}
		}
		assertTrue("not all doors have been recognized",doors == 1);
		assertTrue("Mario has not been recognized",marios == 1);
		assertTrue("not all walls (including bounding box) have been recognized",walls == 28);
		assertTrue("not all dangers have been recognized",dangers == 4);
	}
	
	
	@Test
	public void testIncorrectMap() {
		File f = new File(incorrect);
		assertTrue("incorrect file has been detected as correct",!adapter.checkMap(f));
	}
	
	
	@Test
	public void testParseCorrectMap3() {
		
		File f = new File(sockets);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		int doors = 0;
		int marios = 0;
		int walls = 0;
		int dangers = 0;
		int sockets = 0;
		List<Entity> entities = adapter.getEntities();
		for(Entity e: entities) {
			if(adapter.isDoor(e)) {
				doors+=1;
			}else if(adapter.isMario(e)) {
				marios +=1;
			}else if(adapter.isWall(e)) {
				walls+=1;
			}else if(adapter.isDanger(e)) {
				dangers+=1;
			}else if(adapter.isSocket(e)) {
				sockets+=1;
			}
		}
		assertTrue("not all doors have been recognized",doors == 1);
		assertTrue("Mario has not been recognized",marios == 3);
		assertTrue("not all walls (including bounding box) have been recognized",walls == 55);
		assertTrue("not all dangers have been recognized",dangers == 4);
		assertTrue("not all sockets have been recognized",sockets == 4);
	}
	
	
	@Test
	public void testParseOtherCharacters() {
		
		File f = new File(otherChars);	
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		int doors = 0;
		int marios = 0;
		int walls = 0;
		List<Entity> entities = adapter.getEntities();
		for(Entity e: entities) {
			if(adapter.isDoor(e)) {
				doors+=1;
			}else if(adapter.isMario(e)) {
				marios +=1;
			}else if(adapter.isWall(e)) {
				walls+=1;
			}
		}
		assertTrue("not all doors have been recognized",doors == 1);
		assertTrue("Mario has not been recognized",marios == 1);
		assertTrue("not all walls (including bounding box) have been recognized",walls == 31);
	}
}


