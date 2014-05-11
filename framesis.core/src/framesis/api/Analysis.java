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
	public String execute()
	{
		this.scenario.setDataSource(URI.create(dataSource));
		String resultsPath = this.scenario.execute();
		return resultsPath;
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
}
