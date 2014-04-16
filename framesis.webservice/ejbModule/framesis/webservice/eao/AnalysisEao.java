package framesis.webservice.eao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import framesis.api.DataPreparation;
import framesis.api.PreparationRegistry;
import framesis.api.SubScenario;
import framesis.api.SubScenarioRegistry;

/**
 * Session Bean implementation class SubScenarioEao
 */
@Stateless
@LocalBean
public class AnalysisEao {

    /**
     * Default constructor. 
     */
    public AnalysisEao() {
        // TODO Auto-generated constructor stub
    }

    public List<SubScenario> getSubScenarios()
    {
    	List<SubScenario> ret = SubScenarioRegistry.createInstances();
    	return ret;
    }
    
    public List<DataPreparation> getDataPreparations()
    {
    	List<DataPreparation> ret = PreparationRegistry.createInstances();
    	return ret;
    }
}
