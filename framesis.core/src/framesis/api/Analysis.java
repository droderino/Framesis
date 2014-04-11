package framesis.api;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Analysis {

	private Scenario scenario;
	private DataPreparation preparation;
	private String dataSource;
	
	public void setDataPreparation(DataPreparation prep)
	{
		this.preparation = prep;
	}
	
	public void setScenario(Scenario scenario)
	{
		this.scenario = scenario;
	}
	
	public void setDataSource(String source)
	{
		this.dataSource = source;
	}
	
	public void execute()
	{
		List<SubScenario> subScens = scenario.getExecSequence();
		for(SubScenario sub : subScens)
			System.out.println(sub.getName());
	}
	
	public File getResults()
	{
		return null;
	}
	/*private TextMiningTask task;
	
	public void setTextMiningTask(TextMiningTask task)
	{
		this.task = task;
	}
	
	@SuppressWarnings("unchecked")
	public String execute(Map<String, String> params)
	{
		params.put("preparatedFile", params.get(TextMiningTask.FILE));
		return task.execute(params);
	}
	public String execute(Map<String, String> tmParams, DataPreparation prep, Map<String, String> dpParams)
	{
		String preparatedFile = prep.prepare(dpParams);
		tmParams.put("preparatedFile", preparatedFile);
		String processedFile = execute(tmParams);
		return processedFile;
	}*/
}
