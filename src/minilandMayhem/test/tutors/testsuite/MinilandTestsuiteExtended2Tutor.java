package minilandMayhem.test.tutors.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.tutors.testcase.EnemiesTestTutor;
import minilandMayhem.test.tutors.testcase.GamePlayTestTutor;
import minilandMayhem.test.tutors.testcase.HighscoreTestTutor;
import minilandMayhem.test.tutors.testcase.ParserTestTutor;
import minilandMayhem.test.tutors.testcase.SocketTestTutor;

public class MinilandTestsuiteExtended2Tutor {

public static Test suite() {
		
		TestSuite extended2 = new TestSuite("Tutor tests - Ausbaustufe 2");
		extended2.addTest(new JUnit4TestAdapter(GamePlayTestTutor.class));
		extended2.addTest(new JUnit4TestAdapter(ParserTestTutor.class));
		extended2.addTest(new JUnit4TestAdapter(SocketTestTutor.class));
		extended2.addTest(new JUnit4TestAdapter(EnemiesTestTutor.class));
		extended2.addTest(new JUnit4TestAdapter(HighscoreTestTutor.class));
		return extended2;
	}
}
