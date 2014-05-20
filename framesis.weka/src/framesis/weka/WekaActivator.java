package framesis.weka;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import framesis.api.SubScenarioRegistry;
import framesis.weka.DataPreparations.BugDataPreparation;
import framesis.weka.DataPreparations.BugzillaBugsToArff;
import framesis.weka.SubScenario.ClassifierEvaluationScenario;
import framesis.weka.SubScenario.CrossValidationScenario;
import framesis.weka.SubScenario.PreprocessingSubScenario;
import framesis.weka.SubScenario.Classifiers.IBkSubScenario;
import framesis.weka.SubScenario.Classifiers.J48SubScenario;
import framesis.weka.SubScenario.Classifiers.LogisticSubScenario;
import framesis.weka.SubScenario.Classifiers.NaiveBayesScenario;
import framesis.weka.SubScenario.Classifiers.SMOSubScenario;
import framesis.weka.SubScenario.Classifiers.ZeroRSubScenario;

public class WekaActivator implements BundleActivator{

	@Override
	public void start(BundleContext arg0) throws Exception {
		SubScenarioRegistry.register(NaiveBayesScenario.class);
		SubScenarioRegistry.register(BugDataPreparation.class);
		SubScenarioRegistry.register(BugzillaBugsToArff.class);
		SubScenarioRegistry.register(PreprocessingSubScenario.class);
		SubScenarioRegistry.register(ClassifierEvaluationScenario.class);
		SubScenarioRegistry.register(J48SubScenario.class);
		SubScenarioRegistry.register(SMOSubScenario.class);
		SubScenarioRegistry.register(ZeroRSubScenario.class);
		SubScenarioRegistry.register(IBkSubScenario.class);
		SubScenarioRegistry.register(CrossValidationScenario.class);
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {		
		SubScenarioRegistry.deregister(NaiveBayesScenario.class);
		SubScenarioRegistry.deregister(BugDataPreparation.class);
		SubScenarioRegistry.deregister(BugzillaBugsToArff.class);
		SubScenarioRegistry.deregister(PreprocessingSubScenario.class);
		SubScenarioRegistry.deregister(ClassifierEvaluationScenario.class);
		SubScenarioRegistry.deregister(J48SubScenario.class);
		SubScenarioRegistry.deregister(SMOSubScenario.class);
		SubScenarioRegistry.deregister(ZeroRSubScenario.class);
		SubScenarioRegistry.deregister(IBkSubScenario.class);
		SubScenarioRegistry.deregister(CrossValidationScenario.class);
	}

}
