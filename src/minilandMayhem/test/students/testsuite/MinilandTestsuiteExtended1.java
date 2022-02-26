package minilandMayhem.test.students.testsuite;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import minilandMayhem.test.students.testcase.*;

public class MinilandTestsuiteExtended1 {

	public static Test suite() {
		
		TestSuite extended1 = new TestSuite("Student tests - Ausbaustufe 1");
		extended1.addTest(new JUnit4TestAdapter(GamePlayTestStudent.class)); 
		extended1.addTest(new JUnit4TestAdapter(ParserTestStudent.class));
		extended1.addTest(new JUnit4TestAdapter(SocketTestStudent.class));
		extended1.addTest(new JUnit4TestAdapter(CollectableTestStudent.class));
		extended1.addTest(new JUnit4TestAdapter(BillTestStudent.class));
		
		return extended1;
	}
}
