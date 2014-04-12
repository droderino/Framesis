package framesis.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;

import org.glassfish.osgicdi.OSGiService;

import framesis.api.Analysis;
import framesis.api.SubScenario;
import framesis.api.SubScenarioRegistry;

/**
 * Session Bean implementation class Service
 */
@Stateless
@LocalBean
@WebService
public class Service implements ServiceInterface {

	@Inject @OSGiService(dynamic=true)
	private static SubScenarioRegistry reg;
    /**
     * Default constructor. 
     */
    public Service() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<String> subScens() {
		// TODO Auto-generated method stub
		List<String> names = new ArrayList<String>();
		names.add("first");
		names.add("second");
		
		List<SubScenario> scen = SubScenarioRegistry.createInstances();
		for(SubScenario sub : scen)
			names.add(sub.getName());
		
		return names;
	}

}
