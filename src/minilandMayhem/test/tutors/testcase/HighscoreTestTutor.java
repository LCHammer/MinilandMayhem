package minilandMayhem.test.tutors.testcase;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import minilandMayhem.test.MinilandTestAdapterExtended2;

public class HighscoreTestTutor {

	MinilandTestAdapterExtended2 adapter;
	String score = "level/Score.txt";
	String win = "level/Win.txt";
	String highscore = "highscores/Highscore_Score.txt";
	String winningscore = "highscores/Highscore_Win.txt";
	
	
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
		List<Entity> entities =adapter.getEntities();
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
		pos = left.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		pos = right.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Creation of beams does not subtract correct amount of points",adapter.getScore() == 490);
		pos = mario1.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(1100);
		assertTrue("Mario going through door does not give correct amount of points",adapter.getScore() == 990);
		adapter.waitFor(1000l);
		adapter.updateGame(0);
		assertTrue("If a second passes, no points are removed",adapter.getScore() == 989);
		pos = mario2.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(100);
		assertTrue("Mario did not collect Coin",adapter.getScore() == 1489);
		assertTrue("Coin has not been removed",!adapter.existsCoin());
		adapter.updateGame(500);
		assertTrue("Destruction of Mario does not subtract correct amount of points",adapter.getScore() == 989);
		adapter.updateGame(0);
		adapter.updateGame(0);
		assertTrue("Game did not end",adapter.getCurrentStateID() == adapter.getEndStateID());
		pos = adapter.getSaveScorePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		File f2 = new File(highscore);
		String s = adapter.readScore(f2);
		assertTrue("wrong content was written in file",s.equals("989,1/2"));
		FileWriter fw;
		try {
			fw = new FileWriter(f2);
			fw.write("");
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
	}
	
	@Test
	public void testSorted() {
		File f = new File(win);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game started with wrong amount of points",adapter.getScore() == 500);
		List<Entity> entities =adapter.getEntities();
		Entity mario =null;
		for(Entity e: entities) {
			 if(adapter.isMario(e)) {
				mario = e;
			}
		}
		
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		adapter.updateGame(2000);
		assertTrue("Mario going through door does not give correct amount of points",adapter.getScore() == 1000);
		adapter.updateGame(0);
		adapter.updateGame(0);
		assertTrue("Game did not end",adapter.getCurrentStateID() == adapter.getEndStateID());
		pos = adapter.getSaveScorePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		File f2 = new File(winningscore);
		String s = adapter.readScore(f2);
		String compare = "1500,1/1"+System.lineSeparator()+"1400,1/1"+System.lineSeparator()+"1000,1/1"+System.lineSeparator()+
				"0,0/1"+System.lineSeparator()+"-500,0/1";
		assertTrue("wrong content was written in file",s.equals(compare));
		FileWriter fw;
		try {
			fw = new FileWriter(f2);
			String s2 = "1500,1/1"+System.lineSeparator()+"1400,1/1"+System.lineSeparator()+
					"0,0/1"+System.lineSeparator()+"-500,0/1"+System.lineSeparator()+"-500,0/1";
			fw.write(s2);
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
