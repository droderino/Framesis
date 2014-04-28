package framesis.api;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import framesis.filemanagement.FileManager;

public class Scenario {

	private List<SubScenario> subScenarios;
	private String name;
	private FileManager fileManager;
	private File outputDir;
	private URI dataSource;
	
	public Scenario(String name)
	{
		this.subScenarios = new ArrayList<SubScenario>();
		this.name = name;
		this.outputDir = this.createTempDir(name);
		try {
			this.fileManager = new FileManager(this.outputDir.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addScenario(SubScenario element)
	{
		this.subScenarios.add(element);
	}
	
	public void removeScenario(SubScenario element)
	{
		this.subScenarios.remove(element);
	}
	
	public void execute()
	{
		if(dataSource == null)
			return;
		
		URI tmpSource = dataSource;
		Map<String, String> config;
		
		for(SubScenario s : subScenarios)
		{
			config = s.getConfig();
			config.put(SubScenario.SOURCE, tmpSource.toString());

			s.execute();
			try {
				int i = subScenarios.indexOf(s);
				File res = fileManager.writeFile(subScenarios.indexOf(s) + s.getName() + ".txt", s.getResults());
				fileManager.addFile( res );
				tmpSource = res.toURI();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<File> getResults()
	{
		return fileManager.getFiles();
	}
	
	public File exportResults()
	{
		return fileManager.exportFiles(getName() + ".zip");
	}
	
	public List<SubScenario> getExecSequence()
	{
		return this.subScenarios;
	}
	
	public void clean()
	{
		this.fileManager.deleteAll();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private File createTempDir(String prefix)
	{
		File dir = new File( System.getProperty("java.io.tmpdir") );
		String suffix = String.valueOf(System.currentTimeMillis());
		
		File tmpDir = new File(dir, prefix + suffix);
		tmpDir.mkdir();
		
		return tmpDir;
	}

	public URI getDataSource() {
		return dataSource;
	}

	public void setDataSource(URI dataSource) {
		this.dataSource = dataSource;
	}
}
