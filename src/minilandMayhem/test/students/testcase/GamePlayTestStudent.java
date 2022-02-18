package minilandMayhem.test.students.testcase;

import static org.junit.Assert.assertEquals;
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

public class GamePlayTestStudent {

	MinilandTestAdapterMinimal adapter;
	String win= "src/level/Win.txt";
	String lose= "src/level/Lose.txt";
	String marioCollide = "src/level/marioCollide.txt";
	String fall = "src/level/Falling.txt";
	
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
			}
	
	@Test
	public void testLoseGame() {
		File f = new File(lose);
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
		adapter.updateGame(750);
		assertTrue("Mario does not look left after collision",adapter.marioLooksLeft(mario));
		adapter.updateGame(1750);
		adapter.updateGame(0);
		assertTrue("Mario has not been removed after colliding with danger",!StateBasedEntityManager.getInstance().hasEntity(adapter.getGameStateID(), adapter.getMarioPrefix()));
		adapter.updateGame(0);
		assertTrue("Game has not ended after Mario has been removed",adapter.getCurrentStateID()==adapter.getEndStateID());
		
	}
/*	
	@Test
	public void collideMario() {
		File f = new File(marioCollide);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		Entity mario = null;
		Entity mario2 = null;
		for(Entity e: entities) {
			if(mario == null && adapter.isMario(e) ) {
			mario =e;	
			}else if(mario2 == null && adapter.isMario(e)) {
				mario2 = e;
			}
		}
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Mario was not activated",adapter.marioIsActive(mario));
		System.out.println(pos.x);
		//System.out.println(pos.y);
		//pos = mario2.getPosition();
		//System.out.println(pos.x);
		//System.out.println(pos.y);
		for(int i = 0; i <25;i++) {
			adapter.updateGame(100);
			System.out.println(mario.getPosition().x);
		}
		
		adapter.updateGame(1600);
		adapter.updateGame(0);
		pos = mario2.getPosition();

		adapter.updateGame(500);
		pos = mario.getPosition();
		System.out.println(pos.x);
		assertTrue("Mario did not turn around after colliding with other Mario",adapter.marioLooksLeft(mario));
		
		pos = mario.getPosition();
		System.out.println(pos.x);
		Vector2f pos2 = mario.getPosition();
		System.out.println(pos2.x);
		assertTrue("Mario did not walk in other direction after colliding",pos.x > pos2.x);
		//Tests collision with other mario
	}
	*/
	
	@Test
	public void marioFalling() {
		File f = new File(fall);
		assertTrue("correct map was considered incorrect",adapter.checkMap(f));
		adapter.initGame();
		Vector2f pos = adapter.getStartGamePosition();
		adapter.handleMouseClick(pos.x, pos.y);
		List<Entity> entities =StateBasedEntityManager.getInstance().getEntitiesByState(adapter.getGameStateID());
		Entity mario = null;
		for(Entity e: entities) {
			if(mario == null && adapter.isMario(e) ) {
			mario =e;	
			}
		}
		pos = mario.getPosition();
		adapter.handleMouseClick(pos.x, pos.y);
		assertTrue("Mario was not activated",adapter.marioIsActive(mario));
		int index = -1;
		for(int i = 0; i<2000; i++) {
			adapter.updateGame(1); //update fuer eine ms
			
			
			if(mario.getPosition().y!=pos.y && index == -1) {
				index=i; //wann faellt der Mario
			}
			
			if(index != -1 &&(i-index)%100==0) {
				int t = i-index;
				float t_quadrat = t*t/1000f;
				float height = (float)(0.5*0.981*t_quadrat); //s = 0.5*a*t*t
				float eps = 1+(t)/1000f; //bei t = 0.1s , erlaube Abweichung von 1.1; bei t=0.2s erlaube 1.2 usw.
				
				if(adapter.marioIsFalling(mario)) {
					assertTrue("Mario falling behaviour is not realistic",height-eps <= mario.getPosition().y-pos.y && height+eps >= mario.getPosition().y-pos.y );
				}
			}
			
		}
		
	}
	
	
}
