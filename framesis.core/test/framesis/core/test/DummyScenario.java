package framesis.core.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import framesis.api.SubScenario;

public class DummyScenario implements SubScenario{

	private Map<String, String> config;
	private String result;
	private String name;
	
	public DummyScenario(String name)
	{
		this.config = new HashMap<String, String>();
		this.name = name;
		this.result = "";
	}
	
	@Override
	public URI execute() {
		try {
			FileReader reader = new FileReader( new File( URI.create(config.get(SOURCE)) ) );
			char[] buffer = new char[1024];
			int len;
			while( (len = reader.read(buffer)) != -1 )
				result = result + String.copyValueOf(buffer, 0, len);
			reader.close();
			
			result = result + " dummy Scenario ";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getPhase() {
		return "test";
	}

	@Override
	public String getDescription() {
		return "does nothing";
	}

	@Override
	public Map<String, String> getConfig() {
		return this.config;
	}

	@Override
	public void setConfig(Map<String, String> params) {
		this.config = params;
	}

	@Override
	public String getResults() {
		return result;
	}

}
