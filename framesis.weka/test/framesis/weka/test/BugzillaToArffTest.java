package framesis.weka.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.junit.Test;

import framesis.api.SubScenario;
import framesis.weka.DataPreparations.BugzillaBugsToArff;

public class BugzillaToArffTest {

	@Test
	public void test() {
		SubScenario bugs = new BugzillaBugsToArff();
		assertNotNull(bugs);
		
		String file = "file:///D:/Master/Framesis/Daten/0Ref-Data-Prep-BZ.xml";
		URI uri = URI.create(file);
		bugs.getConfig().put(SubScenario.SOURCE, uri.toString());
		System.out.println(URI.create(file).toString());
		
		bugs.execute();
		
		try {
			File out = new File(URI.create(file + "converted.arff"));
			FileWriter fw = new FileWriter(out);
			fw.write(bugs.getResults());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
