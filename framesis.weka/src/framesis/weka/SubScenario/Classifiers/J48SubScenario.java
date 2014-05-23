package framesis.weka.SubScenario.Classifiers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.trees.J48;
import framesis.api.SubScenario;
import framesis.weka.WekaParams;
import framesis.weka.SubScenario.ClassifierExecution;

public class J48SubScenario implements SubScenario{

	String name, phase, desc, results;
	private Map<String, String> config;
	String result;
	
	public J48SubScenario()
	{
		name = "AN-WEKA-C4.5";
		phase = PHASE_AN;
		desc = "Anwendung des C4.5 Tree Algorithmus auf eine Datenmenge." +
				"Falls \"Distribution = true\": Berechnung der Wahrscheinlichkeit der Klassenzuordnung der Testdaten";
		results = "";
		
		this.config = new HashMap<String, String>();
		config.put(SOURCE, "");
		config.put(WekaParams.CLASSATTRIBUTE, "");
		config.put(WekaParams.CLASSINDEX, "");
		config.put(WekaParams.TRAINDATA, "");
		config.put(WekaParams.CLASSIFIERMODUS, "");
		config.put("-U", "");
		config.put("-C", "");
		config.put("-M", "");
		config.put("-R", "");
		config.put("-N", "");
		config.put("-B", "");
		config.put("-S", "");
		config.put("-L", "");
		config.put("-A", "");
		config.put("-Q", "");
	}
	
	@Override
	public URI execute() {
		ClassifierExecution exec = new ClassifierExecution();
		exec.setClassifier(new J48(), config);
		
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
