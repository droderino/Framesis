package framesis.api;

import java.util.ArrayList;
import java.util.List;

public class PreparationRegistry {

private static List<Class<DataPreparation>> preparations;
	
	static
	{
		preparations = new ArrayList<Class<DataPreparation>>();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static synchronized void register(Class clazz)
	{
		preparations.add(clazz);
	}
	
	@SuppressWarnings("rawtypes")
	public static synchronized void deregister(Class clazz)
	{
		preparations.remove(clazz);
	}
	
	public static synchronized List<DataPreparation> createInstances()
	{
		List<DataPreparation> instances = new ArrayList<DataPreparation>();
		for( Class<DataPreparation> clazz : preparations )
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
