package framesis.api;

import java.util.LinkedList;
import java.util.List;

public class Scenario {

	private List<SubScenario> subScenarios;
	
	public Scenario()
	{
		this.subScenarios = new LinkedList<SubScenario>();
	}
	
	public void add(SubScenario element)
	{
		this.subScenarios.add(element);
	}
	
	public void remove(SubScenario element)
	{
		this.subScenarios.remove(element);
	}
	
	public List<SubScenario> getExecSequence()
	{
		return this.subScenarios;
	}
}
