package minilandMayhem.test.tutors.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.*;
import minilandMayhem.test.tutors.testcase.*;

public class MinilandTestsuiteExtended3Tutor {

	
public static Test suite() {
		
		TestSuite extended3 = new TestSuite("Tutor tests - Ausbaustufe 3");
		extended3.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(SocketTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(BillTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(EnemiesTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(HighscoreTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(CollectableTestStudent.class));
		extended3.addTest(new JUnit4TestAdapter(GamePlayTestTutor.class));
		extended3.addTest(new JUnit4TestAdapter(ParserTestTutor.class));
		extended3.addTest(new JUnit4TestAdapter(SocketTestTutor.class));
		extended3.addTest(new JUnit4TestAdapter(EnemiesTestTutor.class));
		extended3.addTest(new JUnit4TestAdapter(HighscoreTestTutor.class));
		extended3.addTest(new JUnit4TestAdapter(CollectableTestTutor.class));
		extended3.addTest(new JUnit4TestAdapter(BillTestTutor.class));
		return extended3;
	}
}
