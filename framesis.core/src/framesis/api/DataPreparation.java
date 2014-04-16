package framesis.api;

import java.net.URI;
import java.util.Map;

public interface DataPreparation {

	public static final String FILE = "file";
	public static final String PREPARATEDFILE = "convertedFile";
	public static final String ISSUEROOT = "root";
	
	public static final String SOURCE = "sourceUri";
	
	public URI prepare();
	public Map<String, String> getConfig();
	public void setConfig(Map<String, String> params);
	public String getName();
	public String getDescription();
}
