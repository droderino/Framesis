package framesis.api;

import java.io.File;

public class Analysis {

	private Scenario scenario;
	private String dataSource;
	
	public Analysis()
	{
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
		this.scenario.execute();
	}
	
	public File getResults()
	{
		return this.scenario.exportResults();
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
