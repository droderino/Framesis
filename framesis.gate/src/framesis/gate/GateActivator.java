package framesis.gate;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import framesis.api.SubScenarioRegistry;
import framesis.gate.SubScenario.GateLearning;
import framesis.gate.SubScenario.GateNEAnalyse;
import framesis.gate.SubScenario.GateDataExtraction;
import framesis.gate.SubScenario.GatePrePos;
import framesis.gate.SubScenario.GatePreRead;
import framesis.gate.SubScenario.GateSplitInput;

public class GateActivator implements BundleActivator{

	@Override
	public void start(BundleContext arg0) throws Exception {
		SubScenarioRegistry.register(GateDataExtraction.class);
		SubScenarioRegistry.register(GatePreRead.class);
		SubScenarioRegistry.register(GatePrePos.class);
		SubScenarioRegistry.register(GateNEAnalyse.class);
		SubScenarioRegistry.register(GateLearning.class);
		SubScenarioRegistry.register(GateSplitInput.class);
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		SubScenarioRegistry.deregister(GateDataExtraction.class);
		SubScenarioRegistry.deregister(GatePreRead.class);
		SubScenarioRegistry.deregister(GatePrePos.class);
		SubScenarioRegistry.deregister(GateNEAnalyse.class);
		SubScenarioRegistry.deregister(GateLearning.class);
		SubScenarioRegistry.deregister(GateSplitInput.class);
	}

}
