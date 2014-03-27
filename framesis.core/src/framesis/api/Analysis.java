package framesis.api;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class Analysis {

	private TextMiningTask task;
	
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
	}
}
