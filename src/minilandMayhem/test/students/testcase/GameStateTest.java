package minilandMayhem.test.students.testcase;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import minilandMayhem.test.MinilandTestAdapterMinimal;

public class GameStateTest {

	MinilandTestAdapterMinimal adapter;
	
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
		System.out.println(adapter.getCurrentStateID());
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
	public void testParseMap() {
		//TODO Parse Map test
	}
	
	
	@Test
	public void testEndGame() {
		//TODO test for ending game after no Mario is left (with transition to "new game) and resetting 
	}
	
	
}
