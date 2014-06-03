package framesis.standalone;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import framesis.api.Analysis;
import framesis.api.AnalysisInterface;
import framesis.api.Scenario;
import framesis.api.SubScenario;
import framesis.api.SubScenarioRegistry;

public class Activator implements BundleActivator{

	@Override
	public void start(BundleContext arg0) throws Exception {
		System.out.println("Standalone Client");
		int choice = -1;
		List<SubScenario> registry = SubScenarioRegistry.createInstances();
		Scenario scenario = new Scenario("default");
		
		while(choice != 0)
		{
			System.out.println("1. Print SubScenarios\n" +
					"2. Add SubScenario\n" +
					"3. Execute Scenario");
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			choice = Integer.parseInt(in.readLine());
			
			switch(choice)
			{
			case 1:
				registry = SubScenarioRegistry.createInstances();
				for(SubScenario s : registry)
					System.out.println(s.getName());
				break;
			case 2:
				System.out.println("Enter SubScenario Name");
				String name = in.readLine();
				for(SubScenario s: registry)
					if(s.getName().equalsIgnoreCase(name))
					{
						scenario.addScenario(s);
						System.out.println("added");
						break;
					}
				break;
			case 3:
				System.out.println("Enter Input File");
				String file = in.readLine();
				AnalysisInterface analyse = new Analysis();
				
				analyse.setDataSource(file);
				analyse.setScenario(scenario);
				System.out.println("Analysis configured");
				
				analyse.execute();
				System.out.println("Analysis executed");
				
				File results = analyse.getResults();
				System.out.println("Results: " + results.getAbsolutePath());
				break;
			}
		}
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
