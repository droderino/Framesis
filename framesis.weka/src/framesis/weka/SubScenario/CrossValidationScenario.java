package framesis.weka.SubScenario;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import framesis.api.SubScenario;
import framesis.weka.WekaParams;

public class CrossValidationScenario implements SubScenario{

	String name, phase, desc, results;
	Map<String, String> config;
	Instances test, train;
	
	public CrossValidationScenario()
	{
		name = "EVAL-WEKA-CLASSIFIER-CROSS";
		phase = PHASE_EVAL;
		desc = "Cross-validation eines Classifiers durch die Eingabedaten.";
		config = new HashMap<String, String>();
		config.put(WekaParams.CLASSATTRIBUTE, "");
		config.put(WekaParams.CLASSINDEX, "");
		config.put(WekaParams.TRAINDATA, "");
		config.put(SOURCE, "");
	}
	
	@Override
	public URI execute() {
		File source = new File(URI.create(config.get(SOURCE)));
		
		try {
			Classifier cls = (Classifier) SerializationHelper.read(source.getParent() + "/classifier.model");
			
			DataSource ds = new DataSource(source.getParent() + "/backup.arff");
			Instances instances = ds.getDataSet();
			this.setClassAttribute(instances);

			Instances tmp = ds.getDataSet();
			this.setClassAttribute(tmp);
			tmp.delete();
			cls.buildClassifier(tmp);
			
			Evaluation eval = new Evaluation(instances);
			eval.crossValidateModel(cls, instances, 10, new Random(1));
			
			results = eval.toSummaryString() + eval.toClassDetailsString();
			
			deleteFile(source.getParent() + "/classifier.model");
			deleteFile(source.getParent() + "/backup.arff");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private void deleteFile(String filename) {
		File file = new File(filename);
		file.delete();
	}

	private void setClassAttribute(Instances instances) {
		if(instances.classIndex() == -1)
		{
			if(config.get(WekaParams.CLASSINDEX) != null && !config.get(WekaParams.CLASSINDEX).isEmpty())
				instances.setClassIndex(Integer.parseInt(config.get(WekaParams.CLASSINDEX)));
			else if(config.get(WekaParams.CLASSATTRIBUTE) != null && !config.get(WekaParams.CLASSATTRIBUTE).isEmpty())
				instances.setClass(instances.attribute(config.get(WekaParams.CLASSATTRIBUTE)));
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String getPhase() {
		// TODO Auto-generated method stub
		return phase;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return desc;
	}

	@Override
	public Map<String, String> getConfig() {
		// TODO Auto-generated method stub
		return config;
	}

	@Override
	public void setConfig(Map<String, String> params) {
		config = params;
	}

	@Override
	public String getResults() {
		// TODO Auto-generated method stub
		return results;
	}
}
