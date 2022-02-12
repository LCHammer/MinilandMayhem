package minilandMayhem.test.students.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.GamePlayTestStudent;
import minilandMayhem.test.students.testcase.ParserTestStudent;

public class MinilandTestsuiteMinimal {

	
	public static Test suite() {
		
		TestSuite minimal = new TestSuite("Student tests - Minimal");
		minimal.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class)); // add class
		minimal.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		return minimal;
	}
	
}
