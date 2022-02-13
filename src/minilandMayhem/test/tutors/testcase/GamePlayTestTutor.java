package minilandMayhem.test.tutors.testcase;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import minilandMayhem.test.MinilandTestAdapterMinimal;

public class GamePlayTestTutor {

	MinilandTestAdapterMinimal adapter;
	String win= "src/level/Win.txt";
	String lose= "src/level/Lose.txt";
	String winAndLose= "src/level/WinAndLose.txt";
	
	@Before
	public void setUp() {
		adapter = new MinilandTestAdapterMinimal();
	}
	
	@After
	public void finish() {
		adapter.stopGame();
	}
	
	@Test
	public void testStateTransitions() {
		adapter.initGame();
		assertTrue("Game does not start in Main menu",adapter.getCurrentStateID()==adapter.getMainStateID());
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game is not in GameState after click on start",adapter.getCurrentStateID()==adapter.getGameStateID());
		adapter.handleKeyPressed(Input.KEY_ESCAPE);
		assertTrue("Game is not in EndScreen after press on ESC from GameState",adapter.getCurrentStateID() == adapter.getEndStateID());
		pos = adapter.getNewGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game is not in GameState after click on new Game",adapter.getCurrentStateID()==adapter.getGameStateID());
		adapter.handleKeyPressed(Input.KEY_ESCAPE);
		assertTrue("Game is not in EndScreen after press on ESC from GameState",adapter.getCurrentStateID() == adapter.getEndStateID());
		pos = adapter.getMainMenuPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Game does is not in Main menu after click on mainmenu Button",adapter.getCurrentStateID()==adapter.getMainStateID());
		adapter.stopGame();
	}
	
	
	@Test
	public void testWinGame() {
		File f = new File(win);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		Entity mario = null;
		for(Entity e: entities) {
			if(adapter.isMario(e)) {
			mario =e;	
			}
		}
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Mario was not activated",adapter.marioIsActive(mario));
		adapter.updateGame(2000);
		adapter.updateGame(0);
		assertTrue("Mario has not been removed after colliding with door",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(), adapter.getMarioPrefix()));
		adapter.updateGame(0);
		assertTrue("Game has not ended after Mario has been removed",adapter.getCurrentStateID()==adapter.getEndStateID());
		//click new game and test again
		Vector2f newGamePos = adapter.getNewGamePosition();
		adapter.handleMouseClick(newGamePos.x, newGamePos.y);
		assertTrue("Game is not in GameState after click on new Game",adapter.getCurrentStateID()==adapter.getGameStateID());
		entities = StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		mario = null;
		for(Entity e: entities) {
			if(adapter.isMario(e)) {
			mario =e;	
			}
		}
		assertTrue("Mario is not at the same position after new Game",pos.x==mario.getPosition().x);
		assertTrue("Mario is not at the same position after new Game",pos.y==mario.getPosition().y);
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Mario can not be activated after new Game",adapter.marioIsActive(mario));
	
	}
	
	
	@Test
	public void testLoseGame() {
		File f = new File(lose);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities = StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		Entity mario = null;
		for(Entity e: entities) {
			if(adapter.isMario(e)) {
			mario =e;	
			}
		}
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		System.out.println(pos.x);
		assertTrue("Mario was not activated",adapter.marioIsActive(mario));
		adapter.updateGame(750);
		assertTrue("Mario does not look left after collision",adapter.marioLooksLeft(mario));
		adapter.updateGame(1750);
		adapter.updateGame(0);
		assertTrue("Mario has not been removed after colliding with danger",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(), adapter.getMarioPrefix()));
		adapter.updateGame(0);
		assertTrue("Game has not ended after Mario has been removed",adapter.getCurrentStateID()==adapter.getEndStateID());
		//click new game and test again
		Vector2f newGamePos = adapter.getNewGamePosition();
		adapter.handleMouseClick(newGamePos.x, newGamePos.y);
		assertTrue("Game is not in GameState after click on new Game",adapter.getCurrentStateID()==adapter.getGameStateID());
		entities = StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		mario = null;
		for(Entity e: entities) {
			if(adapter.isMario(e)) {
			mario =e;	
			}
		}
		assertTrue("Mario is not at the same position after new Game",pos.x==mario.getPosition().x);
		assertTrue("Mario is not at the same position after new Game",pos.y==mario.getPosition().y);
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Mario can not be activated after new Game",adapter.marioIsActive(mario));	
	}
}

