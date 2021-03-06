package minilandMayhem.test.tutors.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.*;
import minilandMayhem.test.tutors.testcase.*;

public class MinilandTestsuiteMinimalTutor {

public static Test suite() {
		
		TestSuite minimal = new TestSuite("Tutor tests - Minimal");
		minimal.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class));
		minimal.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		minimal.addTest(new JUnit4TestAdapter(SocketTestStudent.class));
		minimal.addTest(new JUnit4TestAdapter(GamePlayTestTutor.class));
		minimal.addTest(new JUnit4TestAdapter(ParserTestTutor.class));
		minimal.addTest(new JUnit4TestAdapter(SocketTestTutor.class));
		return minimal;
	}
}
