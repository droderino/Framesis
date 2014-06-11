package framesis.gate.SubScenario;

import java.io.IOException;
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

public class GateNEAnalyse implements SubScenario{

	private String name, phase, desc, result;
	private Map<String, String> config;
	
	public GateNEAnalyse()
	{
		name = "AN-GATE-NE-TRANS";
		phase = PHASE_AN;
		desc = "Führt eine Named Entity Analyse auf den Daten aus mithilfe des Gate NE Transducer. " +
				"Dabei wird der Text auf verschiedene Entitäten untersucht (Personen, Datum, Adressen, etc.).";
		result = "";
		config = new HashMap<String, String>();
		config.put(GateParams.GATEHOME, "C:\\Program Files\\GATE_Developer_7.1");
		config.put(GateParams.PLUGINSHOME, "C:\\Program Files\\GATE_Developer_7.1\\plugins");
		config.put(GateParams.SITECONFIG, "C:\\Program Files\\GATE_Developer_7.1\\gate.xml");
		config.put(SOURCE, "");
		config.put(GateParams.XMLOUT, "false");
	}
	
	@Override
	public URI execute() {
		try {
			GateHelper.initializeGate(config);
			GateHelper.initAnnie();
			
			LanguageAnalyser trans = (LanguageAnalyser)Factory.createResource("gate.creole.ANNIETransducer");
			
			URI uri = URI.create(config.get(SOURCE));
			
			SerialDataStore ds = GateHelper.createDataStore(uri);
			ds.open();
			
			Document doc = GateHelper.lookForBackup(ds);
			trans.setCorpus(null);
			trans.setDocument(doc);
			trans.execute();

			result = GateHelper.extractResults(doc, Boolean.parseBoolean(config.get(GateParams.XMLOUT)));
			ds.delete("gate.corpora.DocumentImpl", doc.getLRPersistenceId());
			
			ds.close();
			ds.delete();
		} catch (GateException | IOException e) {
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
