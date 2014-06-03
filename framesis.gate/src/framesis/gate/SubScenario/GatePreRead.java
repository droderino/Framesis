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
import gate.LanguageAnalyser;
import gate.creole.SerialAnalyserController;
import gate.persist.SerialDataStore;
import gate.util.GateException;

public class GatePreRead implements SubScenario{

	Map<String, String> config;
	String result, name, desc, phase;
	
	public GatePreRead()
	{
		config = new HashMap<String, String>();
		config.put(GateParams.GATEHOME, "C:\\Program Files\\GATE_Developer_7.1");
		config.put(GateParams.PLUGINSHOME, "C:\\Program Files\\GATE_Developer_7.1\\plugins");
		config.put(GateParams.SITECONFIG, "C:\\Program Files\\GATE_Developer_7.1\\gate.xml");
		config.put(SOURCE, "");
		
		name = "PRE-GATE-READ";
		phase = PHASE_PRE;
		desc = "Verarbeitet die eingelesenen Daten mithilfe des Gate Tokenizers und SentenceSplitters";
		result = "";
	}
	
	@Override
	public URI execute() {
		try {
			GateHelper.initializeGate(config);
			
			GateHelper.initAnnie();
			
			LanguageAnalyser tokenizer = (LanguageAnalyser)Factory.createResource("gate.creole.tokeniser.DefaultTokeniser");
			LanguageAnalyser splitter = (LanguageAnalyser)Factory.createResource("gate.creole.splitter.SentenceSplitter");
			
			SerialAnalyserController controller = (SerialAnalyserController)Factory.createResource("gate.creole.SerialAnalyserController");
			controller.add(tokenizer);
			controller.add(splitter);
			
			URI uri = URI.create(config.get(SOURCE));
			
			SerialDataStore ds = GateHelper.createDataStore(uri);
			ds.open();
			
			Document doc = GateHelper.lookForBackup(ds);
			
			Corpus corpus = Factory.newCorpus("default");
			corpus.add(doc);
			controller.setCorpus(corpus);
			controller.execute();
			
			doc.sync();
			ds.close();
			result = doc.toXml();
			
		} catch (GateException | MalformedURLException e) {
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
