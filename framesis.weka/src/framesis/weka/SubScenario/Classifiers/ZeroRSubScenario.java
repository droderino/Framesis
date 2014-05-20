package framesis.weka.SubScenario.Classifiers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import weka.classifiers.rules.ZeroR;
import framesis.api.SubScenario;
import framesis.weka.WekaParams;

public class ZeroRSubScenario implements SubScenario{

	String name, phase, desc, results;
	private Map<String, String> config;
	String result;
	
	public ZeroRSubScenario()
	{
		name = "AN-WEKA-0R";
		phase = PHASE_AN;
		desc = "Anwendung des 0-R Algorithmus auf eine Datenmenge.";
		results = "";
		
		this.config = new HashMap<String, String>();
		config.put(SOURCE, "");
		config.put(WekaParams.CLASSATTRIBUTE, "");
		config.put(WekaParams.CLASSINDEX, "");
		config.put(WekaParams.TRAINDATA, "");
		config.put("-D", "");
	}
	
	@Override
	public URI execute() {
		ClassifierExecution exec = new ClassifierExecution();
		exec.setClassifier(new ZeroR(), config);
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
