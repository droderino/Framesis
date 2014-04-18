package framesis.webservice;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import framesis.webservice.dto.DataPreparationDump;
import framesis.webservice.dto.SubScenarioDump;

@Remote
public interface AnalysisServiceInterface {

	public List<SubScenarioDump> dumpSubScenarios();
	public String execute(List<SubScenarioDump> list, String sourceURI);	
	public List<String> getSubScenConfig(String name);
	public String getURI(String source);
}
