package framesis.weka.SubScenario.Classifiers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.lazy.IBk;
import framesis.api.SubScenario;
import framesis.weka.WekaParams;
import framesis.weka.SubScenario.ClassifierExecution;

public class IBkSubScenario implements SubScenario{

	String name, phase, desc, results;
	private Map<String, String> config;
	String result;
	
	public IBkSubScenario()
	{
		name = "AN-WEKA-IBk";
		phase = PHASE_AN;
		desc = "Anwendung des Weka IBk Algorithmus (K-NN) auf eine Datenmenge." +
				"Falls \"Distribution = true\": Berechnung der Wahrscheinlichkeit der Klassenzuordnung der Testdaten";
		results = "";
		
		this.config = new HashMap<String, String>();
		config.put(SOURCE, "");
		config.put(WekaParams.CLASSATTRIBUTE, "");
		config.put(WekaParams.CLASSINDEX, "");
		config.put(WekaParams.TRAINDATA, "");
		config.put(WekaParams.CLASSIFIERMODUS, "");
		config.put("-I", "");
		config.put("-F", "");
		config.put("-K", "");
		config.put("-E", "");
		config.put("-W", "");
		config.put("-X", "");
		config.put("-A", "");
	}
	
	@Override
	public URI execute() {
		ClassifierExecution exec = new ClassifierExecution();
		exec.setClassifier(new IBk(), config);
		
		if(Boolean.parseBoolean(config.get(WekaParams.CLASSIFIERMODUS)))
			results = exec.distribution();
		else
			results = exec.execute();

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
		return this.config;
	}

	@Override
	public void setConfig(Map<String, String> params) {
		this.config = params;
	}

	@Override
	public String getResults() {
		return this.results;
	}
}
