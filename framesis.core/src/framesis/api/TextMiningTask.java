package framesis.api;

import java.util.Map;

public interface TextMiningTask<T> {

	public static final String FILE = "file";
	public static final String PREPARATEDFILE = "preparatedFile";
	
	public String execute(Map<String, String> params);
	public T executeTask(Map<String, String> params);
	public String getDescription();
}
