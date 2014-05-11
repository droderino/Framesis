package framesis.webservice.ao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.glassfish.osgicdi.OSGiService;

import framesis.api.Analysis;
import framesis.api.AnalysisInterface;
import framesis.api.Scenario;
import framesis.api.SubScenario;
import framesis.api.SubScenarioRegistry;
import framesis.webservice.dto.SubScenarioDump;
import framesis.webservice.util.Conversion;

/**
 * Session Bean implementation class SubScenarioEao
 */
@Stateless
@LocalBean
public class AnalysisAO {

	/*@Inject 
	@OSGiService(dynamic=true)*/
	private AnalysisInterface analyse;
	
	@EJB
	private Conversion conv;
	
    /**
     * Default constructor. 
     */
    public AnalysisAO() {
        // TODO Auto-generated constructor stub
    }

    public List<SubScenarioDump> getSubScenarioDumps()
    {
    	List<SubScenario> subs = this.getSubScenarios();

		List<SubScenarioDump> dumps = new ArrayList<SubScenarioDump>();
		for(SubScenario s : subs)
			dumps.add(conv.fromSubScenario(s));
		
    	return dumps;
    }
    
    public List<SubScenario> getSubScenarios()
    {
    	return SubScenarioRegistry.createInstances();
    }
    
    public String execute(List<SubScenarioDump> list, String sourceURI)
    {
    	analyse = new Analysis();
    	
    	List<SubScenario> subs = getSubScenarios();
		
		Scenario scen = new Scenario("framesis");
		for(SubScenarioDump d : list)
		{
			SubScenario newSub = getSubScenario(d, subs);
			scen.addScenario(newSub);
		}
		
		analyse.setScenario(scen);
		analyse.setDataSource(sourceURI);
		String ret = analyse.execute();
    	return ret;
    }
    
    public SubScenario getSubScenario(SubScenarioDump dump, List<SubScenario> list)
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
}
