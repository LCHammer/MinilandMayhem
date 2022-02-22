package minilandMayhem.test.tutors.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.*;
import minilandMayhem.test.tutors.testcase.*;

public class MinilandTestsuiteExtended2Tutor {

public static Test suite() {
		
		TestSuite extended2 = new TestSuite("Tutor tests - Ausbaustufe 2");
		extended2.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(SocketTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(EnemiesTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(HighscoreTestStudent.class));
		extended2.addTest(new JUnit4TestAdapter(GamePlayTestTutor.class));
		extended2.addTest(new JUnit4TestAdapter(ParserTestTutor.class));
		extended2.addTest(new JUnit4TestAdapter(SocketTestTutor.class));
		extended2.addTest(new JUnit4TestAdapter(EnemiesTestTutor.class));
		extended2.addTest(new JUnit4TestAdapter(HighscoreTestTutor.class));
		return extended2;
	}
}
