package framesis.webservice;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.glassfish.osgicdi.OSGiService;

import framesis.api.Analysis;
import framesis.api.DataPreparation;
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
}
