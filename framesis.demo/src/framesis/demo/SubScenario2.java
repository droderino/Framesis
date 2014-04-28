package framesis.demo;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import framesis.api.SubScenario;

public class SubScenario2 implements SubScenario{
	
	private Map<String, String> config;
	private String result;
	private String description;
	private String phase;
	private String name;
	
	public SubScenario2()
	{
		config = new HashMap<String, String>();
		config.put(SubScenario.OUTPUT, "foo");
		config.put(SubScenario.SOURCE, "bar");
		
		name = "SubScenario2";
		phase = SubScenario.PHASE_PRE;
		description = "nothing to do";
	}
	@Override
	public URI execute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getPhase() {
		// TODO Auto-generated method stub
		return this.phase;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return this.description;
	}

	@Override
	public String getResults() {
		// TODO Auto-generated method stub
		return this.result;
	}

	@Override
	public void setConfig(Map<String, String> params) {
		// TODO Auto-generated method stub
		this.config = params;
	}

	@Override
	public Map<String, String> getConfig() {
		// TODO Auto-generated method stub
		return this.config;
	}

}
