package minilandMayhem.test.students.testcase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void testMainMenu() {
		adapter.initGame();
		assertTrue("Game does not start in Main menu",adapter.getCurrentStateID()==adapter.getMainStateID());
		adapter.handleMouseClick(170, 190);
		System.out.println(adapter.getCurrentStateID());
		assertTrue("Game is not in GameState after click on start",adapter.getCurrentStateID()==adapter.getGameStateID());
		adapter.stopGame();
	}
	
}
