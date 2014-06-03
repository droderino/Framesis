package framesis.gate.SubScenario;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import framesis.api.SubScenario;
import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;

public class GateDemo implements SubScenario{

	Map<String, String> config;
	String result;
	
	public GateDemo()
	{
		config = new HashMap<String, String>();
		config.put(SOURCE, "");
		result = "";
	}
	
	public URI execute() {
		// TODO Auto-generated method stub
		try {
			Gate.setGateHome(new File("C:\\Program Files\\GATE_Developer_8.0"));
			Gate.setPluginsHome(new File("C:\\Program Files\\GATE_Developer_8.0\\plugins"));
			Gate.setSiteConfigFile(new File("C:\\Program Files\\GATE_Developer_8.0\\gate.xml"));
			Gate.init();
			File home = Gate.getGateHome();
			System.out.println(home.getAbsolutePath());
			
			URI uri = URI.create(config.get(SOURCE));
			
			FeatureMap fm = Factory.newFeatureMap();
			fm.put("sourceUrl", uri.toURL());
			
			Document doc = (Document)Factory.createResource("gate.corpora.DocumentImpl", fm);
			String content = doc.getContent().toString();
			//System.out.println(content);
			Map<String, AnnotationSet> namedAnno = doc.getNamedAnnotationSets();
			Set<Entry<String, AnnotationSet>> annoSet = namedAnno.entrySet();
			Iterator<Entry<String, AnnotationSet>> iter = annoSet.iterator();

			while(iter.hasNext())
			{
				Entry<String, AnnotationSet> cur = iter.next();
								
				result = result.concat("Entry: " + cur.getKey() + "\n");
				
				AnnotationSet as = cur.getValue();
				Iterator<Annotation> annoIter = as.iterator();
				while(annoIter.hasNext())
				{
					Annotation an = annoIter.next();
					
					result = result.concat("\tAnnotation: " + an.getType() + "\n");
					
					FeatureMap f = an.getFeatures();
					Set<Entry<Object,Object>> set = f.entrySet();
					Iterator<Entry<Object,Object>> it = set.iterator();
					while(it.hasNext())
					{
						Entry<Object,Object> ob = it.next();
						result = result.concat("\t\t" + ob.getKey().toString() + " " + ob.getValue().toString() + "\n");
					}
				}
			}
			
		} catch (GateException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "GateDemo";
	}

	public String getPhase() {
		// TODO Auto-generated method stub
		return PHASE_DE;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return "foooo";
	}

	public Map<String, String> getConfig() {
		// TODO Auto-generated method stub
		return config;
	}

	public void setConfig(Map<String, String> params) {
		// TODO Auto-generated method stub
		config = params;
	}

	public String getResults() {
		// TODO Auto-generated method stub
		return result;
	}

}
