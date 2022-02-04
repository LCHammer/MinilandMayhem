package minilandMayhem.test.students.testcase;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import minilandMayhem.test.MinilandTestAdapterMinimal;

public class HasEntitiesTest {

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
	public void test1() {
		adapter.initGame();
		assertTrue(true);
		System.out.println("here");
		adapter.stopGame();
	}
	
}
