package framesis.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import framesis.api.SubScenario;

public class SubScenario1 implements SubScenario{

	private Map<String, String> config;
	private String result;
	private String name;
	
	public SubScenario1()
	{
		this.config = new HashMap<String, String>();
		this.config.put("sample", "out");
		this.config.put("foo", "bar");
		
		this.name = "SubScenario1";
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
			
			result = result + " sub Scenario 1 ";
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
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public String getPhase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResults() {
		// TODO Auto-generated method stub
		return this.result;
	}

	@Override
	public void setConfig(Map<String, String> params) {
		this.config = params;
	}

	@Override
	public Map<String, String> getConfig() {
		// TODO Auto-generated method stub
		return this.config;
	}

}
