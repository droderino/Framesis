package framesis.webservice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.ejb.Stateless;

import framesis.api.DataPreparation;
import framesis.api.SubScenario;
import framesis.webservice.dto.SubScenarioDump;

@Stateless
public class Conversion {
	
	public SubScenarioDump fromSubScenario(SubScenario element)
	{
		SubScenarioDump dump = new SubScenarioDump();
		dump.setName(element.getName());
		dump.setPhase(element.getPhase());
		dump.setDescription(element.getDescription());
		dump.setConfig(this.fromMap(element.getConfig()));
		return dump;
	}
	
	public SubScenario fromSubScenarioDump(SubScenarioDump dump, SubScenario sub)
	{
		Map<String, String> config = new HashMap<String, String>();
		List<String> dumpConfig = dump.getConfig();
		for(String s : dumpConfig)
		{
			String[] entry = s.split(":");
			config.put(entry[0], entry[1]);
		}
		sub.setConfig(config);
		return sub;
	}
	
	private List<String> fromMap(Map<String, String> config)
	{
		Set<Entry<String,String>> set = config.entrySet();
		Iterator<Entry<String,String>> iter = set.iterator();
		
		List<String> ret = new ArrayList<String>();
		while(iter.hasNext())
		{
			Entry<String,String> e = iter.next();
			ret.add(e.getKey() + ":" + e.getValue());
		}
		return ret;
	}
}
