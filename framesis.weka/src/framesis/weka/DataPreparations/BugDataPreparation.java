package framesis.weka.DataPreparations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import framesis.api.SubScenario;


public class BugDataPreparation implements SubScenario{

	public static final String REFDATA = "CSV";
	private Map<String, String> params;
	private String name, desc, phase, results;
	
	public BugDataPreparation()
	{
		params = new HashMap<String, String>();
		params.put(SubScenario.SOURCE, "");
		params.put(REFDATA, "");
		
		name = "REF-DATA-BUGZILLA";
		desc = "Erzeugt Referenzdaten aus Bugzilla XML-Export und CSV mit ID - Class Kategorisierung";
		phase = SubScenario.PHASE_DE;
		results = "";
	}
	
	@Override
	public URI execute() {

		Map<String, String> csv = parseCsv(params.get(REFDATA));
		try {
			modifyXml(params.get(SubScenario.SOURCE), csv);
			System.out.println("xml modified");
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("resource")
	private Map<String, String> parseCsv(String filename)
	{
		Map<String, String> csv = new HashMap<String, String>();
		
		try {
			BufferedReader in = new BufferedReader( new FileReader( new File( URI.create(filename) )) );
			
			String line = null;
			while( (line = in.readLine()) != null)
			{
				String[] rows = line.split(";");
				
				csv.put(rows[0], rows[1]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return csv;
	}

	private void modifyXml(String filename, Map<String, String> csv) throws ParserConfigurationException, SAXException, IOException, TransformerException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document xml = dBuilder.parse(filename);
		
		xml.getDocumentElement().normalize();
		NodeList nList = xml.getElementsByTagName("bug");
		
		for( int i = 0; i < nList.getLength(); i++ )
		{
			Node node = nList.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				String bug_id = element.getElementsByTagName("bug_id").item(0).getTextContent();
				String classification = null;
				
				if( (classification = csv.get(bug_id)) != null)
				{
					Element clazz = xml.createElement("bug_classification");
					clazz.appendChild(xml.createTextNode(classification));
					clazz.setAttribute("class", classification);
					node.appendChild(clazz);
				}
			}
		}
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(xml);
		StringWriter sw = new StringWriter();
		StreamResult result = new StreamResult(sw);
		transformer.transform(source, result);
		results = sw.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return desc;
	}

	@Override
	public void setConfig(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public Map<String, String> getConfig() {
		return this.params;
	}

	@Override
	public String getPhase() {
		return phase;
	}

	@Override
	public String getResults() {
		// TODO Auto-generated method stub
		return results;
	}
}
