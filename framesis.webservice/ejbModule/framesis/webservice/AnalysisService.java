package framesis.webservice;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;

import framesis.api.SubScenario;
import framesis.webservice.ao.AnalysisAO;
import framesis.webservice.dto.SubScenarioDump;

/**
 * Session Bean implementation class Service
 */
@Stateless
@LocalBean
@WebService
public class AnalysisService implements AnalysisServiceInterface {
	
	@EJB
	AnalysisAO aao;

    /**
     * Default constructor. 
     */
    public AnalysisService() {
        // TODO Auto-generated constructor stub
    }    

	@Override
	public List<String> getSubScenConfig(String name) {
		List<SubScenario> subs = aao.getSubScenarios();
		for(SubScenario element : subs)
		{
			if(element.getName().equals(name))
			{
				Set<Entry<String,String>> set = element.getConfig().entrySet();
				Iterator<Entry<String,String>> iter = set.iterator();
				
				List<String> ret = new ArrayList<String>();
				while(iter.hasNext())
				{
					Entry<String,String> e = iter.next();
					ret.add(e.getKey());
					ret.add(e.getValue());
				}
				return ret;
			}
		}
		return null;
	}

	@Override
	public String execute(
			@WebParam(name="SubScenarioDump") List<SubScenarioDump> list, 
			@WebParam(name="sourceURI") String sourceURI) {
		
		String ret = aao.execute(list, sourceURI);
		
		return ret;
	}

	@Override
	public String getURI(String source) {
		return URI.create(source).toString();
	}

	@Override
	public List<SubScenarioDump> dumpSubScenarios() {
		List<SubScenarioDump> dumps = aao.getSubScenarioDumps();
		
		return dumps;
	}
}
