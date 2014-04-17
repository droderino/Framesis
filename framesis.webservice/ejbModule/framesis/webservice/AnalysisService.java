package framesis.webservice;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.glassfish.osgicdi.OSGiService;

import framesis.api.Analysis;
import framesis.api.DataPreparation;
import framesis.api.Scenario;
import framesis.api.SubScenario;
import framesis.api.SubScenarioRegistry;
import framesis.webservice.dto.DataPreparationDump;
import framesis.webservice.dto.SubScenarioDump;
import framesis.webservice.eao.AnalysisEao;
import framesis.webservice.util.Conversion;

/**
 * Session Bean implementation class Service
 */
@Stateless
@LocalBean
@WebService
public class AnalysisService implements AnalysisServiceInterface {

	/*@Inject @OSGiService(dynamic=true)
	private static SubScenarioRegistry reg;*/
	
	@EJB
	AnalysisEao eao;
	@EJB
	Conversion conv;
	
    /**
     * Default constructor. 
     */
    public AnalysisService() {
        // TODO Auto-generated constructor stub
    }    

	@Override
	public List<SubScenarioDump> dumpSubScenarios() {
		List<SubScenarioDump> dump = new ArrayList<SubScenarioDump>();
		List<SubScenario> subScens = eao.getSubScenarios();
		for(SubScenario element : subScens)
			dump.add(conv.fromSubScenario(element));
		
		return dump;
	}

	@Override
	public List<DataPreparationDump> dumpDataPreparations() {
		List<DataPreparationDump> dump = new ArrayList<DataPreparationDump>();
		List<DataPreparation> preps = eao.getDataPreparations();
		for(DataPreparation element : preps)
			dump.add(conv.fromDataPreparation(element));
		
		return dump;
	}

	@Override
	public String executeScenario(List<DataPreparationDump> preps,
			List<SubScenarioDump> subScens) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDP(DataPreparationDump dump) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getSubScenConfig(String name) {
		List<SubScenario> subs = eao.getSubScenarios();
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
	public String execute(List<SubScenarioDump> list, String sourceURI) {
		List<SubScenario> subs = eao.getSubScenarios();
		
		Scenario scen = new Scenario("dummy");
		for(SubScenarioDump d : list)
		{
			SubScenario newSub = getSubScenario(d, subs);
			scen.addScenario(newSub);
		}
		
		Analysis analyse = new Analysis();
		analyse.setScenario(scen);
		analyse.setDataSource(sourceURI);
		analyse.execute();
		
		return "success";
	}
	
	private SubScenario getSubScenario(SubScenarioDump dump, List<SubScenario> list)
	{
		Iterator<SubScenario> iter = list.iterator();
		while(iter.hasNext())
		{
			SubScenario cur = iter.next();
			if(cur.getName().equals(dump.getName()))
			{
				return conv.fromSubScenarioDump(dump, cur);
			}
		}
		return null;
	}

	@Override
	public String getURI(String source) {
		return URI.create(source).toString();
	}
}
