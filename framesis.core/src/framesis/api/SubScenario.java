package framesis.api;

import java.util.Map;

public interface SubScenario {

	public void execute(Map<String, String> params);
	public String getName();
	public String getPhase();
	public String getDescription();
	public String getResults();
}
