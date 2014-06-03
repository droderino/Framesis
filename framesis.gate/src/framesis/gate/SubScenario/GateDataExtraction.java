package framesis.gate.SubScenario;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import framesis.api.SubScenario;
import framesis.gate.GateHelper;
import framesis.gate.GateParams;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.persist.SerialDataStore;
import gate.util.GateException;

public class GateDataExtraction implements SubScenario{

	private String name, phase, desc, result;
	private Map<String,String> config;
	
	public GateDataExtraction()
	{
		config = new HashMap<String, String>();
		config.put(GateParams.GATEHOME, "C:\\Program Files\\GATE_Developer_7.1");
		config.put(GateParams.PLUGINSHOME, "C:\\Program Files\\GATE_Developer_7.1\\plugins");
		config.put(GateParams.SITECONFIG, "C:\\Program Files\\GATE_Developer_7.1\\gate.xml");
		config.put(SOURCE, "");
		
		name = "DE-GATE";
		phase = PHASE_DE;
		desc = "Liest Gate-kompatible Datei ein und legt diese im Gate-XML-Schema ab.";
		result = "";
	}
	
	@Override
	public URI execute() {
		try {
			GateHelper.initializeGate(config);
			
			URI uri = URI.create(config.get(SOURCE));
			
			FeatureMap fm = Factory.newFeatureMap();
			fm.put("sourceUrl", uri.toURL());
			
			Document doc = (Document)Factory.createResource("gate.corpora.DocumentImpl", fm);
			
			result = doc.toXml();
			
			SerialDataStore ds = GateHelper.createDataStore(uri);
			ds.open();
			
			Document persistDoc = (Document)ds.adopt(doc, null);
			ds.sync(persistDoc);
			persistDoc.setName("backup");
			persistDoc.sync();
			ds.close();
			
		} catch (GateException | MalformedURLException e) {
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
