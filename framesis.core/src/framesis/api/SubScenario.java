package framesis.api;

import java.net.URI;
import java.util.Map;


public interface SubScenario {

	public static final String SOURCE = "sourceUri";
	public static final String OUTPUT = "outputUri";
	
	public static final String PHASE_DE = "Data Extraction";
	public static final String PHASE_PRE = "Preprocessing";
	public static final String PHASE_AN = "Analysis";
	public static final String PHASE_EVAL = "Evaluation";
	
	public URI execute();
	public String getName();
	public String getPhase();
	public String getDescription();
	public Map<String, String> getConfig();
	public void setConfig(Map<String, String> params);
	public String getResults();
}
