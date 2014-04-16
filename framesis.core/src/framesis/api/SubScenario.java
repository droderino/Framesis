package framesis.api;

import java.util.Map;


public interface SubScenario {

	public void execute();
	public String getName();
	public String getPhase();
	public String getDescription();
	public Map<String, String> getConfig();
	public void setConfig(Map<String, String> params);
	public String getResults();
}
