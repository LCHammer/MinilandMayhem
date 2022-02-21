package minilandMayhem.test.students.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.*;

public class MinilandTestsuiteMinimal {

	
	public static Test suite() {
		
		TestSuite minimal = new TestSuite("Student tests - Minimal");
		minimal.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class)); 
		minimal.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		minimal.addTest(new JUnit4TestAdapter(SocketTestStudent.class));
		return minimal;
	}
	
}
