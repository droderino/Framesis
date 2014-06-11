package framesis.gate.SubScenario;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import framesis.api.SubScenario;
import framesis.gate.GateHelper;
import framesis.gate.GateParams;
import gate.Corpus;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.LanguageAnalyser;
import gate.creole.SerialAnalyserController;
import gate.persist.SerialDataStore;
import gate.util.GateException;

public class GateLearning implements SubScenario{

	private String name, phase, desc, result;
	private Map<String,String> config;
	
	public GateLearning()
	{
		config = new HashMap<String, String>();
		config.put(GateParams.GATEHOME, "C:\\Program Files\\GATE_Developer_7.1");
		config.put(GateParams.PLUGINSHOME, "C:\\Program Files\\GATE_Developer_7.1\\plugins");
		config.put(GateParams.SITECONFIG, "C:\\Program Files\\GATE_Developer_7.1\\gate.xml");
		config.put(SOURCE, "");
		config.put(GateParams.XMLOUT, "false");
		config.put(GateParams.CONFIGLEARN, "");
		config.put(GateParams.LEARNINGMODE, "TRAINING");
		
		name = "AN-GATE-LEARNING";
		phase = PHASE_AN;
		desc = "Learning mit Gate";
		result = "";
	}
	
	@Override
	public URI execute() {
		try {
			GateHelper.initializeGate(config);
			GateHelper.initPlugin("Learning");
			
			FeatureMap fm = Factory.newFeatureMap();
			fm.put("configFileURL", config.get(GateParams.CONFIGLEARN));
			fm.put("learningMode", config.get(GateParams.LEARNINGMODE));
			
			LanguageAnalyser ml = (LanguageAnalyser)Factory.createResource("gate.learning.LearningAPIMain", fm);
						
			URI uri = URI.create(config.get(SOURCE));
			SerialDataStore ds = GateHelper.createDataStore(uri);
			ds.open();
			
			Corpus corpus = (Corpus)GateHelper.lookForResource(ds, "gate.corpora.SerialCorpusImpl", "Bugs");
			
			SerialAnalyserController controller = (SerialAnalyserController)Factory.createResource("gate.creole.SerialAnalyserController");
			controller.add(ml);
			controller.setCorpus(corpus);
			controller.execute();
			
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
