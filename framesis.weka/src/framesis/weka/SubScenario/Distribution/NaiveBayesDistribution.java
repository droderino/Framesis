package framesis.weka.SubScenario.Distribution;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.bayes.NaiveBayes;
import framesis.api.SubScenario;
import framesis.weka.WekaParams;
import framesis.weka.SubScenario.ClassifierExecution;

public class NaiveBayesDistribution implements SubScenario{

	String name, phase, desc, results;
	private Map<String, String> config;
	String result;
	
	public NaiveBayesDistribution()
	{
		name = "AN-WEKA-DIST-NAIVE-BAYES";
		phase = PHASE_AN;
		desc = "Berechnung der Wahrscheinlichkeit der Klassenzuordnung der Testdaten anhand Naive Bayes";
		results = "";
		
		this.config = new HashMap<String, String>();
		config.put(SOURCE, "");
		config.put(WekaParams.CLASSATTRIBUTE, "");
		config.put(WekaParams.CLASSINDEX, "");
		config.put(WekaParams.TRAINDATA, "");
		config.put("-D", "");
		config.put("-K", "");
		config.put("-O", "");
	}
	
	@Override
	public URI execute() {
		ClassifierExecution exec = new ClassifierExecution();
		exec.setClassifier(new NaiveBayes(), config);
		results = exec.distribution();

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
