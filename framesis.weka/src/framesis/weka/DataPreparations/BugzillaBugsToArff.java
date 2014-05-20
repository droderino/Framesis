package framesis.weka.DataPreparations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import weka.core.Instances;

import framesis.api.SubScenario;

public class BugzillaBugsToArff extends DefaultHandler implements SubScenario{

	String name, desc, phase, results;
	Map<String, String> config;
	
	public BugzillaBugsToArff()
	{
		name = "WEKA-DE-I-FILE";
		desc = "Konvertiert Bugzilla XML in ARFF";
		phase = SubScenario.PHASE_DE;
		results = "";
		
		config = new HashMap<String, String>();
		config.put(SubScenario.SOURCE, "");
	}
	
	@Override
	public URI execute() {
		try {
			File file = new File(URI.create(config.get(SOURCE)));
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			BugzillaBugsXmlHelper helper = new BugzillaBugsXmlHelper();
			saxParser.parse(is, helper);
			
			Instances output = helper.getData();
			results = output.toString();
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPhase() {
		return phase;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public Map<String, String> getConfig() {
		return config;
	}

	@Override
	public void setConfig(Map<String, String> params) {
		this.config = params;
	}

	@Override
	public String getResults() {
		return results;
	}

}
