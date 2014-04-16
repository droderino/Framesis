package framesis.weka.SubScenario;

import java.io.FileWriter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import framesis.api.SubScenario;
import framesis.weka.WekaParams;

public class NaiveBayesScenario implements SubScenario{

	private Map<String, String> config;
	private Instances train;
	private Instances test;
	private Instances original;
	String result;
	
	public NaiveBayesScenario()
	{
		this.config = new HashMap<String, String>();
	}
	
	@Override
	public URI execute() {
		String filename = config.get(SOURCE) + "_naiveBayes.txt";
		this.executeTask();
		try {
			Instances output = mergeInstances(train, test);
			result = output.toString();
			
			FileWriter fw = new FileWriter(filename);
			fw.write(result);
			fw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return URI.create(filename);
	}

	private Instances mergeInstances(Instances first, Instances second)
	{
		Instances result = first;
		for(int i=0; i<second.numInstances(); i++)
			result.add(second.instance(i));
		return result;
	}

	private void executeTask() {
		Classifier naiveBayes = null;
		try {
			this.setTrainTestInstances();
			naiveBayes = new NaiveBayes();
			String[] options = this.configureOptions();
			naiveBayes.setOptions(options);
			
			naiveBayes.buildClassifier(train);
			
			evaluateClassifier(naiveBayes);
			
			for(int i=0; i<test.numInstances(); i++)
			{
				test.instance(i).setClassValue(naiveBayes.classifyInstance(test.instance(i)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void evaluateClassifier(Classifier naiveBayes) throws Exception {
		Evaluation eval = new Evaluation(train);
		eval.evaluateModel(naiveBayes, test);
		String results = eval.toSummaryString() + eval.toClassDetailsString();
		config.put(WekaParams.EVALUATIONRESULTS, results);
	}

	private void setTrainTestInstances()
			throws Exception {
		DataSource input = new DataSource(config.get(OUTPUT));
		Instances data = input.getDataSet();
		original = input.getDataSet();
		
		int trainSize = (int)Math.round(data.numInstances() * 0.8);
		int testSize = data.numInstances() - trainSize;
		train = new Instances(data, 0, trainSize);
		test = new Instances(data, trainSize, testSize);
		
		setClassAttribute(train);
		setClassAttribute(test);
	}

	private void setClassAttribute(Instances instances) {
		if(instances.classIndex() == -1)
		{
			if(config.get(WekaParams.CLASSINDEX) != null)
				instances.setClassIndex(Integer.parseInt(config.get(WekaParams.CLASSINDEX)));
			else if(config.get(WekaParams.CLASSATTRIBUTE) != null)
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
			if( !entry.getKey().equals(SOURCE) && !entry.getKey().equals(OUTPUT) && !entry.getKey().equals(WekaParams.EVALUATIONRESULTS)
					&& !entry.getKey().equals(WekaParams.CLASSATTRIBUTE) && !entry.getKey().equals(WekaParams.CLASSINDEX) )
			{
				options.add(entry.getKey());
				if(entry.getValue() != null)
					options.add(entry.getValue());
			}
		}
		
		String[] ret = new String[options.size()];
		return options.toArray(ret);
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "NaiveBayesScenario";
	}

	@Override
	public String getPhase() {
		// TODO Auto-generated method stub
		return "Analysis";
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Weka Naive Bayes Classification";
	}

	@Override
	public Map<String, String> getConfig() {
		return this.config;
	}

	@Override
	public void setConfig(Map<String, String> params) {
		this.config = params;
	}

	@Override
	public String getResults() {
		// TODO Auto-generated method stub
		return this.result;
	}

}
