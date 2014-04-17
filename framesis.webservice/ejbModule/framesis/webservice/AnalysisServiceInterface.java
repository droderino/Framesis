package framesis.webservice;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import framesis.webservice.dto.DataPreparationDump;
import framesis.webservice.dto.SubScenarioDump;

@Remote
public interface AnalysisServiceInterface {

	public List<SubScenarioDump> dumpSubScenarios();
	public List<DataPreparationDump> dumpDataPreparations();
	public String executeScenario(List<DataPreparationDump> preps, List<SubScenarioDump> subScens);
	public String execute(List<SubScenarioDump> list, String sourceURI);
	
	public void createDP(DataPreparationDump dump);
	
	public List<String> getSubScenConfig(String name);
	public String getURI(String source);
}
