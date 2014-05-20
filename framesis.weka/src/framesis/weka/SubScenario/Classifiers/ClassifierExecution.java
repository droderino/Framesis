package framesis.weka.SubScenario.Classifiers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import framesis.api.SubScenario;
import framesis.weka.WekaParams;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class ClassifierExecution {

	private Classifier cls;
	private Map<String, String> config;
	private Instances test, train;

	public void setClassifier(Classifier cls, Map<String, String> config)
	{
		this.cls = cls;
		this.config = config;
	}
	
	public String execute() {
		if(cls != null)
		{
			this.executeTask();
			Instances output = mergeInstances(train, test);
	
			return output.toString();
		}
		else
			return null;
	}

	private Instances mergeInstances(Instances first, Instances second)
	{
		Instances result = first;
		for(int i=0; i<second.numInstances(); i++)
			result.add(second.instance(i));
		return result;
	}

	private void executeTask() {
		try {
			File file = new File(URI.create(config.get(SubScenario.SOURCE)));
			InputStream is = new FileInputStream(file);

			DataSource input = new DataSource(is);

			Instances data = input.getDataSet();
			this.setTrainTestInstances(data);

			String[] options = this.configureOptions();
			cls.setOptions(options);

			cls.buildClassifier(train);

			backupForEvaluation(cls, data);

			for(int i=0; i<test.numInstances(); i++)
			{
				test.instance(i).setClassValue(cls.classifyInstance(test.instance(i)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void backupForEvaluation(Classifier cls, Instances data) throws Exception,
	IOException {
		File file = new File(URI.create(config.get(SubScenario.SOURCE)));
		SerializationHelper.write(file.getParent() + "/classifier.model", cls);

		File backup = new File(file.getParent() + "/backup.arff");
		FileWriter fw = new FileWriter(backup);
		fw.write(data.toString());
		fw.close();
	}

	private void setTrainTestInstances(Instances data)
			throws Exception {

		int trainSize = 0;
		if(config.get(WekaParams.TRAINDATA) != null && !config.get(WekaParams.TRAINDATA).isEmpty())
			trainSize = (int)Math.round(data.numInstances() * Double.parseDouble(config.get(WekaParams.TRAINDATA)));
		else
			trainSize = (int)Math.round(data.numInstances() * 0.8);

		int testSize = data.numInstances() - trainSize;
		train = new Instances(data, 0, trainSize);
		test = new Instances(data, trainSize, testSize);

		setClassAttribute(train);
		setClassAttribute(test);
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

	private String[] configureOptions()
	{
		ArrayList<String> options = new ArrayList<String>();

		Set<Entry<String, String>> entries = config.entrySet();
		Iterator<Entry<String, String>> iter = entries.iterator();

		while(iter.hasNext())
		{
			Entry<String, String> entry = iter.next();
			if( filterForWekaOptions(entry) )
			{
				if(entry.getKey() != null && entry.getValue() != null)
				{
					if(Boolean.parseBoolean(entry.getValue()))
						options.add(entry.getKey());
					else if(!entry.getValue().isEmpty())
						options.add( entry.getKey().concat(" " + entry.getValue()));
				}
			}
		}

		String[] ret = new String[options.size()];
		return options.toArray(ret);
	}

	private boolean filterForWekaOptions(Entry<String, String> entry) {
		return !entry.getKey().equals(SubScenario.SOURCE) && 
				!entry.getKey().equals(SubScenario.OUTPUT) && 
				!entry.getKey().equals(WekaParams.EVALUATIONRESULTS) && 
				!entry.getKey().equals(WekaParams.CLASSATTRIBUTE) && 
				!entry.getKey().equals(WekaParams.CLASSINDEX) &&
				!entry.getKey().equals(WekaParams.TRAINDATA);
	}
}
