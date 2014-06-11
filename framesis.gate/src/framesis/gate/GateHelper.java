package framesis.gate;

import gate.Annotation;
import gate.AnnotationSet;
import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Resource;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.persist.SerialDataStore;
import gate.util.GateException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class GateHelper {

	public static void initAnnie() throws MalformedURLException, GateException {
		initPlugin("ANNIE");
	}
	
	@SuppressWarnings("unchecked")
	public static void initPlugin(String name) throws MalformedURLException, GateException
	{
		File pluginDir = Gate.getPluginsHome();
		File aPluginDir = new File(pluginDir, name);
		
		Set<URL> dirs = Gate.getCreoleRegister().getDirectories();
		if(!dirs.contains(aPluginDir.toURI().toURL()))
			Gate.getCreoleRegister().registerDirectories(aPluginDir.toURI().toURL());
	}
	
	@SuppressWarnings("rawtypes")
	public static Document lookForBackup(SerialDataStore ds) throws PersistenceException, ResourceInstantiationException
	{
		Document doc = null;
		doc = (Document)lookForResource(ds, "gate.corpora.DocumentImpl", "backup");
		
		return doc;
	}
	
	@SuppressWarnings("rawtypes")
	public static Resource lookForResource(SerialDataStore ds, String type, String name) throws PersistenceException, ResourceInstantiationException
	{
		Resource res = null;
		
		List docIds = ds.getLrIds(type);
		for(Object o : docIds)
		{
			FeatureMap fm = Factory.newFeatureMap();
			fm.put(DataStore.LR_ID_FEATURE_NAME, o);
			fm.put(DataStore.DATASTORE_FEATURE_NAME, ds);
			
			Resource d = Factory.createResource(type, fm);
			if(d.getName().equals(name))
				res = d;
		}
		
		return res;
	}
	
	public static SerialDataStore createDataStore(URI uri) throws PersistenceException, UnsupportedOperationException
	{
		File source = new File(uri);
		URI dataStore = URI.create(source.getParentFile().toURI().toString() + "/DataStore");
		SerialDataStore ds = null;
		
		if(!(new File(dataStore).exists()))
			ds = (SerialDataStore)Factory.createDataStore("gate.persist.SerialDataStore", dataStore.toString());
		else
			ds = new SerialDataStore(dataStore.toString());
		
		return ds;
	}
	
	public static String extractResults(Document doc, boolean xml)
	{
		String res = "";
		if(xml)
			res = doc.toXml();
		else
			res = extractAllInfos(doc); // doc.toString();
		return res;
	}
	
	public static String extractAllInfos(Document doc)
	{
		String ret = "";
		
		ret = doc.getContent().toString();
		ret = ret + "\nNamedAnnotations\n" + extractAnnotationsTable(doc.getNamedAnnotationSets());
		ret = ret + "\nAnnotations\n" + getAnnotations(doc.getAnnotations());
		return ret;
	}

	public static String extractAnnotationsTable(Map<String, AnnotationSet> namedAnno) {
		Set<Entry<String, AnnotationSet>> annoSet = namedAnno.entrySet();
		Iterator<Entry<String, AnnotationSet>> iter = annoSet.iterator();
		String ret = "";
		while(iter.hasNext())
		{
			Entry<String, AnnotationSet> cur = iter.next();
							
			ret = ret.concat("\nAnnotationSet: " + cur.getKey() + "\n");
			
			AnnotationSet as = cur.getValue();
			ret = ret + getAnnotations(as);
		}
		return ret;
	}

	public static String getAnnotations(AnnotationSet as) {
		String ret = "";
		ret = ret + String.format("%20s %10s %10s %s\n", "Annotation", "Start", "Stop", "FeatureMap");
		
		Iterator<Annotation> annoIter = as.iterator();
		while(annoIter.hasNext())
		{
			Annotation an = annoIter.next();
			FeatureMap f = an.getFeatures();
			
			Set<Entry<Object,Object>> set = f.entrySet();
			Iterator<Entry<Object,Object>> it = set.iterator();
			String fme = "";
			while(it.hasNext())
			{
				Entry<Object,Object> ob = it.next();
				fme = fme + "(" + ob.getKey().toString() + " ; " + ob.getValue().toString() + ") ";
			}
			ret = ret + String.format("%20s %10d %10d %s\n", an.getType(), an.getStartNode().getOffset(), an.getEndNode().getOffset(), fme);
		}
		return ret;
	}

	public static void initializeGate(Map<String, String> config) throws GateException {
		if(!Gate.isInitialised())
		{
			Gate.setGateHome(new File(config.get(GateParams.GATEHOME)));
			Gate.setPluginsHome(new File(config.get(GateParams.PLUGINSHOME)));
			Gate.setSiteConfigFile(new File(config.get(GateParams.SITECONFIG)));
			Gate.init();
		}
	}
}
