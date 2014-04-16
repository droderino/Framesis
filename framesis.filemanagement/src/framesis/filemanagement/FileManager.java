package framesis.filemanagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager {

	private Path rootPath;
	private List<File> files;
	
	public FileManager(String baseDir) throws IOException
	{
		Path basePath = Paths.get(baseDir);
		if( !basePath.toFile().exists() )
			basePath = Paths.get(System.getProperty("java.io.tmpdir"));
		
		this.rootPath = Files.createTempDirectory(basePath, "framesisTmp");
		files = new ArrayList<File>();
	}
	
	public void addFile(File file)
	{
		files.add(file);
	}
	
	public void deleteFile(File file)
	{
		if( !files.contains(file) )
			return;
		files.remove(file);
		file.delete();
	}
	
	public List<File> getFiles()
	{
		return this.files;
	}
	
	public File writeFile(String name, String content) throws IOException
	{
		File output = new File(rootPath.toFile(), name);
		FileWriter fw = new FileWriter(output);
		fw.write(content);
		fw.close();
		return output;
	}
	
	public File exportFiles(String filename)
	{
		File zipFile = new File(rootPath.toFile(), filename);
		
		try {
			byte[] buffer = new byte[1024];
			ZipOutputStream stream = new ZipOutputStream(new FileOutputStream(zipFile.getAbsolutePath()));

			for(File f : files)
			{
				FileInputStream in = new FileInputStream(f);
				stream.putNextEntry(new ZipEntry(f.getName()));
				
				int len;
				while( (len = in.read(buffer)) > 0 )
					stream.write(buffer, 0, len);
				
				stream.closeEntry();
				in.close();
			}
			
			stream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return zipFile;
	}
}
