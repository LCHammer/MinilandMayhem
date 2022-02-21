package minilandMayhem.test.students.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.EnemiesTestStudent;
import minilandMayhem.test.students.testcase.GamePlayTestStudent;
import minilandMayhem.test.students.testcase.HighscoreTestStudent;
import minilandMayhem.test.students.testcase.ParserTestStudent;
import minilandMayhem.test.students.testcase.SocketTestStudent;

public class MinilandTestsuiteExtended2 {

public static Test suite() {
		
		TestSuite extended2 = new TestSuite("Student tests - Ausbaustufe 2");
		extended2.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class)); 
		extended2.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(SocketTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(EnemiesTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(HighscoreTestStudent.class));
		return extended2;
	}
}
