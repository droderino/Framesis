package framesis.api;

import java.util.ArrayList;
import java.util.List;

public class SubScenarioRegistry {

private static List<Class<SubScenario>> subScenarios;
	
	static
	{
		subScenarios = new ArrayList<Class<SubScenario>>();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static synchronized void register(Class clazz)
	{
		subScenarios.add(clazz);
	}
	
	@SuppressWarnings("rawtypes")
	public static synchronized void deregister(Class clazz)
	{
		subScenarios.remove(clazz);
	}
	
	public static synchronized List<SubScenario> createInstances()
	{
		List<SubScenario> instances = new ArrayList<SubScenario>();
		for( Class<SubScenario> clazz : subScenarios )
		{
			try {
				instances.add(clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return instances;
	}
}
