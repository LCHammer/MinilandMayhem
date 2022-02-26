package minilandMayhem.test.students.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.*;

public class MinilandTestsuiteExtended3 {

	
public static Test suite() {
		
		TestSuite extended3 = new TestSuite("Student tests - Ausbaustufe 3");
		extended3.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(SocketTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(EnemiesTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(HighscoreTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(BillTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(CollectableTestStudent.class));
		return extended3;
	}
}
