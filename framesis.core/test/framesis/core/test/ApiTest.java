package framesis.core.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import framesis.api.Analysis;
import framesis.api.AnalysisInterface;
import framesis.api.Scenario;
import framesis.api.SubScenario;

public class ApiTest {

	private File testDir;
	private File test;
	
	@Test
	public void test() {
		testDir = this.createTempDir("testAPI");
		test = new File(testDir.getAbsolutePath(), "test.txt");
		
		try {
			FileWriter fw = new FileWriter(test);
			fw.write("testcase");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(true, test.exists());
		
		SubScenario subDummy1 = initSubScenario("dummy1");
		SubScenario subDummy2 = initSubScenario("dummy2");
		
		Scenario demo = initScenario();
		demo.addScenario(subDummy1);
		demo.addScenario(subDummy2);
		
		AnalysisInterface analyse = initAnalysis(demo);
		analyse.setScenario(demo);

		analyse.execute();
		File zip = analyse.getResults();
		assertNotNull(zip);
	}

	private AnalysisInterface initAnalysis(Scenario demo) {
		AnalysisInterface analyse = new Analysis();
		analyse.setDataSource(test.toURI().toString());
		return analyse;
	}

	private Scenario initScenario() {
		Scenario demo = new Scenario("test", test.toURI());
		assertNotNull(demo);
		return demo;
	}

	private SubScenario initSubScenario(String name) {
		SubScenario subDummy = new DummyScenario(name);
		assertNotNull(subDummy);
		return subDummy;
	}

	private File createTempDir(String prefix)
	{
		File dir = new File( System.getProperty("java.io.tmpdir") );
		String suffix = String.valueOf(System.currentTimeMillis());
		
		File tmpDir = new File(dir, prefix + suffix);
		tmpDir.mkdir();
		
		return tmpDir;
	}
}
