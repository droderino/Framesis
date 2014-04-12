package framesis.demo;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import framesis.api.SubScenarioRegistry;

public class Activator implements BundleActivator{

	@Override
	public void start(BundleContext arg0) throws Exception {
		SubScenarioRegistry.register(SubScenario1.class);
		SubScenarioRegistry.register(SubScenario2.class);
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		SubScenarioRegistry.deregister(SubScenario1.class);
		SubScenarioRegistry.deregister(SubScenario2.class);
	}

}
