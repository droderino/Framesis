package framesis.gate;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import framesis.api.SubScenarioRegistry;
import framesis.gate.SubScenario.GateDemo;

public class GateActivator implements BundleActivator{

	public void start(BundleContext arg0) throws Exception {
		SubScenarioRegistry.register(GateDemo.class);
	}

	public void stop(BundleContext arg0) throws Exception {
		SubScenarioRegistry.deregister(GateDemo.class);
	}

}
