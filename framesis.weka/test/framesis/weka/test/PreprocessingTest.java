package framesis.weka.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.junit.Test;

import framesis.api.SubScenario;
import framesis.weka.WekaParams;
import framesis.weka.SubScenario.PreprocessingSubScenario;

public class PreprocessingTest {

	@Test
	public void test() {
		SubScenario sub = new PreprocessingSubScenario();
		
		String file = "file:///D:/Master/Framesis/Daten/0Ref-Data-Prep-BZxmlconverted.arff";
		URI uri = URI.create(file);
		
		sub.getConfig().put(SubScenario.SOURCE, uri.toString());
		sub.getConfig().put(WekaParams.CLASSINDEX, "8");
		
		sub.execute();
		
		try {
			File out = new File(URI.create(uri.toString() + "preprocessed.arff"));
			FileWriter fw = new FileWriter(out);
			fw.write(sub.getResults());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
