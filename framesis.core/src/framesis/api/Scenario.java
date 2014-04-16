package framesis.api;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import framesis.filemanagement.FileManager;

public class Scenario {

	private List<SubScenario> subScenarios;
	private List<DataPreparation> preparations;
	private String name;
	private FileManager fileManager;
	private File outputDir;
	
	public Scenario()
	{
		this.subScenarios = new LinkedList<SubScenario>();
		this.preparations = new LinkedList<DataPreparation>();
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
	
	public void addPreparation(DataPreparation element)
	{
		this.preparations.add(element);
	}
	
	public void execute()
	{
		for(DataPreparation p : preparations)
		{
			p.prepare();
		}
		
		for(SubScenario s : subScenarios)
		{
			s.execute();
			try {
				fileManager.addFile( fileManager.writeFile(s.getName(), s.getResults()) );
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
		return fileManager.exportFiles(getName());
	}
	
	public List<SubScenario> getExecSequence()
	{
		return this.subScenarios;
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
}
