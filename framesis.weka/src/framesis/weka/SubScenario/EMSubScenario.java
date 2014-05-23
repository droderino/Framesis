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

import weka.classifiers.lazy.IBk;
import weka.clusterers.Clusterer;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import framesis.api.SubScenario;
import framesis.weka.WekaParams;

public class EMSubScenario implements SubScenario{

	String name, phase, desc, results;
	private Map<String, String> config;
	String result;
	
	public EMSubScenario()
	{
		name = "AN-WEKA-EM";
		phase = PHASE_AN;
		desc = "Anwendung des EM Clustiering auf eine Datenmenge.";
		results = "";
		
		this.config = new HashMap<String, String>();
		config.put(SOURCE, "");
		config.put(WekaParams.CLASSATTRIBUTE, "");
		config.put(WekaParams.CLASSINDEX, "");
	}
	
	@Override
	public URI execute() {
		try
		{
			File file = new File(URI.create(config.get(SubScenario.SOURCE)));
			InputStream is = new FileInputStream(file);
	
			DataSource input = new DataSource(is);
	
			Instances data = input.getDataSet();
			setClassAttribute(data);
			
			Clusterer cls = new EM();
			cls.buildClusterer(data);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
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

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPhase() {
		return phase;
	}

	@Override
	public String getDescription() {
		return desc;
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
		return this.results;
	}
}
