package framesis.api;

import java.io.File;
import java.net.URI;

public class Analysis implements AnalysisInterface {

	private Scenario scenario;
	private String dataSource;
	
	/* (non-Javadoc)
	 * @see framesis.api.AnalysisInterface#setScenario(framesis.api.Scenario)
	 */
	@Override
	public void setScenario(Scenario scenario)
	{
		this.scenario = scenario;
	}
	
	/* (non-Javadoc)
	 * @see framesis.api.AnalysisInterface#setDataSource(java.lang.String)
	 */
	@Override
	public void setDataSource(String source)
	{
		this.dataSource = source;
	}
	
	/* (non-Javadoc)
	 * @see framesis.api.AnalysisInterface#execute()
	 */
	@Override
	public void execute()
	{
		this.scenario.setDataSource(URI.create(dataSource));
		this.scenario.execute();
	}
	
	/* (non-Javadoc)
	 * @see framesis.api.AnalysisInterface#getResults()
	 */
	@Override
	public File getResults()
	{
		return this.scenario.exportResults();
	}
	
	/* (non-Javadoc)
	 * @see framesis.api.AnalysisInterface#getUri()
	 */
	@Override
	public URI getUri()
	{
		return URI.create(dataSource);
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
