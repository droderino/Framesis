package framesis.weka.SubScenario.Classifiers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.bayes.NaiveBayes;

import framesis.api.SubScenario;
import framesis.weka.WekaParams;
import framesis.weka.SubScenario.ClassifierExecution;

public class NaiveBayesScenario implements SubScenario{

	String name, phase, desc, results;
	private Map<String, String> config;
	String result;
	
	public NaiveBayesScenario()
	{
		name = "AN-WEKA-NAIVE-BAYES";
		phase = PHASE_AN;
		desc = "Anwendung des Naive Bayes Algorithmus auf eine Datenmenge. " +
				"Falls \"Distribution = true\": Berechnung der Wahrscheinlichkeit der Klassenzuordnung der Testdaten";
		results = "";
		
		this.config = new HashMap<String, String>();
		config.put(SOURCE, "");
		config.put(WekaParams.CLASSATTRIBUTE, "");
		config.put(WekaParams.CLASSINDEX, "");
		config.put(WekaParams.TRAINDATA, "");
		config.put(WekaParams.CLASSIFIERMODUS, "");
		config.put("-D", "");
		config.put("-K", "");
		config.put("-O", "");
	}
	
	@Override
	public URI execute() {
		ClassifierExecution exec = new ClassifierExecution();
		exec.setClassifier(new NaiveBayes(), config);
		
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
