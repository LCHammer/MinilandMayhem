package minilandMayhem.test.tutors.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.tutors.testcase.GamePlayTestTutor;
import minilandMayhem.test.tutors.testcase.ParserTestTutor;

public class MinilandTestsuiteMinimalTutor {

public static Test suite() {
		
		TestSuite minimal = new TestSuite("Tutor tests - Minimal");
		minimal.addTest(new JUnit4TestAdapter(GamePlayTestTutor.class)); // add class
		minimal.addTest(new JUnit4TestAdapter(ParserTestTutor.class));
		return minimal;
	}
}
