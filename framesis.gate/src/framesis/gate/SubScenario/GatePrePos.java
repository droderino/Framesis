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
import gate.LanguageAnalyser;
import gate.persist.SerialDataStore;
import gate.util.GateException;

public class GatePrePos implements SubScenario{

	private String name, phase, desc, result;
	private Map<String, String> config;
	
	public GatePrePos()
	{
		config = new HashMap<String, String>();
		config.put(GateParams.GATEHOME, "C:\\Program Files\\GATE_Developer_7.1");
		config.put(GateParams.PLUGINSHOME, "C:\\Program Files\\GATE_Developer_7.1\\plugins");
		config.put(GateParams.SITECONFIG, "C:\\Program Files\\GATE_Developer_7.1\\gate.xml");
		config.put(SOURCE, "");
		
		name = "PRE-GATE-POS";
		phase = PHASE_PRE;
		desc = "Verarbeitet die eingelesenen Daten mithilfe des Gate Pos-Taggers";
		result = "";
	}
	
	@Override
	public URI execute() {
		try {
			GateHelper.initializeGate(config);
			
			GateHelper.initAnnie();
			
			LanguageAnalyser pos = (LanguageAnalyser)Factory.createResource("gate.creole.POSTagger");
			
			URI uri = URI.create(config.get(SOURCE));
			
			SerialDataStore ds = GateHelper.createDataStore(uri);
			ds.open();
			Document doc = GateHelper.lookForBackup(ds);
			pos.setCorpus(null);
			pos.setDocument(doc);
			pos.execute();
			
			result = doc.toXml();
			doc.sync();
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
