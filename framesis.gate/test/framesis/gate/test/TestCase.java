package framesis.gate.test;

import static org.junit.Assert.*;

import org.junit.Test;

import framesis.api.SubScenario;
import framesis.gate.SubScenario.GatePreRead;

public class TestCase {

	@Test
	public void test() {
		SubScenario sub = new GatePreRead();
		sub.getConfig().put(SubScenario.SOURCE, "file:///D:/Master/Framesis/Daten/tomcat-500-bugs.xml");
		
		sub.execute();
	}

}
