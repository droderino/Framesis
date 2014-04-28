package framesis.webservice.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.ejb.Stateless;

import framesis.api.SubScenario;
import framesis.webservice.dto.ConfigElementDump;
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
		List<ConfigElementDump> dumpConfig = dump.getConfig();
		for(ConfigElementDump s : dumpConfig)
		{
			config.put(s.getParameter(), s.getValue());
		}
		sub.setConfig(config);
		return sub;
	}
	
	private List<ConfigElementDump> fromMap(Map<String, String> config)
	{
		Set<Entry<String,String>> set = config.entrySet();
		Iterator<Entry<String,String>> iter = set.iterator();
		
		List<ConfigElementDump> ret = new ArrayList<ConfigElementDump>();
		while(iter.hasNext())
		{
			Entry<String,String> e = iter.next();
			ret.add( new ConfigElementDump(e.getKey(), e.getValue()) );
		}
		return ret;
	}
}
