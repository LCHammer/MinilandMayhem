package minilandMayhem.test.students.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.*;

public class MinilandTestsuiteExtended2 {

public static Test suite() {
		
		TestSuite extended2 = new TestSuite("Student tests - Ausbaustufe 2");
		extended2.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class)); 
		extended2.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(SocketTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(CollectableTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(BillTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(HighscoreTestStudent.class));
		return extended2;
	}
}
