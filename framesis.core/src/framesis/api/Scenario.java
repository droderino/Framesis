package framesis.api;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import framesis.filemanagement.FileManager;

public class Scenario {

	private List<SubScenario> subScenarios;
	private String name;
	private FileManager fileManager;
	private URI dataSource;
	
	public Scenario(String name)
	{
		this.subScenarios = new ArrayList<SubScenario>();
		this.name = name;
	}

	public Scenario(String name, URI source)
	{
		this(name);
		this.setDataSource(dataSource);
	}
		
	public void addScenario(SubScenario element)
	{
		this.subScenarios.add(element);
	}
	
	public void removeScenario(SubScenario element)
	{
		this.subScenarios.remove(element);
	}
	
	public String execute()
	{
		if(dataSource == null)
			return null;
		
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
		File results = exportResults();
		fileManager.deleteAll();
		return results.getAbsolutePath();
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
	
	private String parseForBaseDir(URI uri)
	{
		String base = null;
		
		if(uri.getScheme().equalsIgnoreCase("file"))
			base = uri.getPath();
		
		File tmp = new File(base);
		if(tmp.isFile())
			base = tmp.getParent();
		else if(tmp.isDirectory())
			base = tmp.getAbsolutePath();
			
		return base;
	}
	
	private File createTempDir(String prefix)
	{
		if(dataSource.getScheme().equalsIgnoreCase("file"))
			System.out.println(this.parseForBaseDir(dataSource));
			
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
		
		String baseDir = this.parseForBaseDir(dataSource);
		if(baseDir == null)
			baseDir = this.createTempDir(name).getAbsolutePath();
		
		try {
			fileManager = new FileManager(baseDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
