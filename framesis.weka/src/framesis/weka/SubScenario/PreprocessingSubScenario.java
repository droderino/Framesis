package framesis.weka.SubScenario;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import framesis.api.SubScenario;
import framesis.weka.WekaParams;

public class PreprocessingSubScenario implements SubScenario{

	private String name, phase, desc, results;
	private Map<String, String> config;
	
	public PreprocessingSubScenario()
	{
		name = "PRE-WEKA";
		phase = PHASE_PRE;
		desc = "Preprocessing von ARFF Input durch den StringToWordVector Filter. Die Beschreibung der spezifischen Weka-Optionen kann aus der Weka-Dokumentation entnommen werden." +
				" Optionen ohne Parameter können mithilfe von \"true\" aktiviert werden";
		config = new HashMap<String, String>();
		
		initConfig();
	}

	private void initConfig() {
		config.put(SOURCE, "");
		config.put(WekaParams.CLASSATTRIBUTE, "");
		config.put(WekaParams.CLASSINDEX, "");
		config.put("-C", "");
		config.put("-R", "");
		config.put("-V", "");
		config.put("-P", "");
		config.put("-W", "");
		config.put("-prune-rate", "");
		config.put("-T", "");
		config.put("-I", "");
		config.put("-N", "");
		config.put("-L", "");
		config.put("-S", "");
		config.put("-stemmer", "");
		config.put("-M", "");
		config.put("-O", "");
		config.put("-stopwords", "");
		config.put("-tokenizer", "");
	}
	
	@Override
	public URI execute() {
		Instances processedData = null;
		
		try {
			File file = new File(URI.create(config.get(SOURCE)));
			InputStream is = new FileInputStream(file);
			
			DataSource source = new DataSource(is);
			Instances instances = source.getDataSet();
			
			if(instances.classIndex() == -1)
			{
				if(config.get(WekaParams.CLASSINDEX) != null && !config.get(WekaParams.CLASSINDEX).isEmpty())
					instances.setClassIndex(Integer.parseInt(config.get(WekaParams.CLASSINDEX)));
				else if(config.get(WekaParams.CLASSATTRIBUTE) != null && !config.get(WekaParams.CLASSATTRIBUTE).isEmpty())
					instances.setClass(instances.attribute(config.get(WekaParams.CLASSATTRIBUTE)));
			}
			
			StringToWordVector filter = new StringToWordVector();
			
			String[] options = configureOptions(config);
			filter.setOptions(options);
			filter.setInputFormat(instances);
			
			processedData = Filter.useFilter(instances, filter);
			
			results = processedData.toString();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String[] configureOptions(Map<String, String> params)
	{
		ArrayList<String> options = new ArrayList<String>();
		
		Set<Entry<String, String>> entries = params.entrySet();
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
		return !entry.getKey().equals(SOURCE)  && !entry.getKey().equals(WekaParams.EVALUATIONRESULTS)
				&& !entry.getKey().equals(WekaParams.CLASSINDEX) && !entry.getKey().equals(WekaParams.CLASSATTRIBUTE);
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
