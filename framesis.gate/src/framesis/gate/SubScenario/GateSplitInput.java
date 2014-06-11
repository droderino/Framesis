package framesis.gate.SubScenario;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import framesis.api.SubScenario;
import framesis.gate.GateHelper;
import framesis.gate.GateParams;
import gate.Corpus;
import gate.DataStore;
import gate.Factory;
import gate.FeatureMap;
import gate.util.GateException;

public class GateSplitInput implements SubScenario{
	
	private String name, phase, desc, result;
	private Map<String,String> config;
	
	public GateSplitInput()
	{
		config = new HashMap<String, String>();
		config.put(GateParams.GATEHOME, "C:\\Program Files\\GATE_Developer_7.1");
		config.put(GateParams.PLUGINSHOME, "C:\\Program Files\\GATE_Developer_7.1\\plugins");
		config.put(GateParams.SITECONFIG, "C:\\Program Files\\GATE_Developer_7.1\\gate.xml");
		config.put(SOURCE, "");
		config.put(GateParams.XMLOUT, "false");
		
		name = "DE-FILE";
		phase = PHASE_DE;
		desc = "";
		result = "";
	}
	
	@Override
	public URI execute() {
		try {
			File source = new File(URI.create(config.get(SOURCE)));
			List<URI> files = new ArrayList<URI>();

			InputStream inputStream = new FileInputStream(source);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document input = dBuilder.parse(is);
			
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			
			input.getDocumentElement().normalize();
			
			NodeList nList = input.getElementsByTagName("bug");
			
			for(int i=0; i<nList.getLength(); i++)
			{
				Node node = nList.item(i);
				Document doc = dBuilder.newDocument();
				
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element)node;
					Node newNode = doc.importNode(element, true);
					doc.appendChild(newNode);
				}
				
				DOMSource dSource = new DOMSource(doc);
				URI name = URI.create(source.getParentFile().toURI().toString() + "/bug" + i + ".xml");
				
				File target = new File(name);
				target.createNewFile();
				Writer fw = new FileWriter(target);
				StreamResult res = new StreamResult(fw);
				transformer.transform(dSource, res);
				fw.close();
				
				files.add(target.toURI());
			}
			
			GateHelper.initializeGate(config);
			Corpus corpus = Factory.newCorpus("Bugs");
			
			for(URI u : files)
			{
				FeatureMap fm = Factory.newFeatureMap();
				fm.put("sourceUrl", u.toURL().toString());
				
				gate.Document gDoc = (gate.Document)Factory.createResource("gate.corpora.DocumentImpl", fm);
				corpus.add(gDoc);
			}
			
			DataStore ds = GateHelper.createDataStore(source.toURI());
			ds.open();
			Corpus pCorpus = (Corpus)ds.adopt(corpus, null);
			ds.sync(pCorpus);
			ds.close();
			
			Iterator<URI> it = files.iterator();
			while(it.hasNext())
			{
				URI u = it.next();
				File f = new File(u);
				f.delete();
				files.remove(u);
				it = files.iterator();
			}
			
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException | GateException e) {
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
		config = params;
	}

	@Override
	public String getResults() {
		return result;
	}

}
