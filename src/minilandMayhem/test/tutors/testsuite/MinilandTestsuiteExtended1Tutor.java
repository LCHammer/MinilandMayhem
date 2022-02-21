package minilandMayhem.test.tutors.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.tutors.testcase.EnemiesTestTutor;
import minilandMayhem.test.tutors.testcase.GamePlayTestTutor;
import minilandMayhem.test.tutors.testcase.ParserTestTutor;
import minilandMayhem.test.tutors.testcase.SocketTestTutor;

public class MinilandTestsuiteExtended1Tutor {

public static Test suite() {
		
		TestSuite extended1 = new TestSuite("Tutor tests - Ausbaustufe 1");
		extended1.addTest(new JUnit4TestAdapter(GamePlayTestTutor.class));
		extended1.addTest(new JUnit4TestAdapter(ParserTestTutor.class));
		extended1.addTest(new JUnit4TestAdapter(SocketTestTutor.class));
		extended1.addTest(new JUnit4TestAdapter(EnemiesTestTutor.class));
		return extended1;
	}
}
