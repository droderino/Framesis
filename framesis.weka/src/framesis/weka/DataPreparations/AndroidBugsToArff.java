package framesis.weka.DataPreparations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import weka.core.Instances;

public class AndroidBugsToArff extends DefaultHandler {

	public static final String FILE = "sourceFile";
	public static final String PREPARATEDFILE = "preparatedFile";
	private Map<String, String> params;
	
	public URI prepare() {		
		try {
			SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
			AndroidBugsXmlHelper helper = new AndroidBugsXmlHelper();
			saxParser.parse(new File(params.get(FILE)), helper);
			
			Instances output = helper.getData();
			String result = output.toString();
			params.put(PREPARATEDFILE, params.get(FILE) + "_converted.arff");
			FileWriter fw = new FileWriter(params.get(PREPARATEDFILE));
			fw.write(result);
			fw.close();
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return URI.create(params.get(PREPARATEDFILE));
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "AndroidBugsToArff";
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return "convert dump of Android Bug Database to Weka ARFF";
	}

	public void setConfig(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getConfig() {
		// TODO Auto-generated method stub
		return this.params;
	}
	
	
}
