package framesis.filemanagement.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import framesis.filemanagement.FileManager;

public class FileManagerTestCase {

	File dir;
	
	@Before
	public void init()
	{
		this.dir = this.createDir("test");
		System.out.println("created " + dir.getAbsolutePath());
	}
	
	@After
	public void clean()
	{
		this.deleteDir(dir);
		System.out.println("deleted " + dir.getAbsolutePath());
	}
	
	@Test
	public void test() throws IOException {
		FileManager fileManager = new FileManager(dir.getAbsolutePath());
		
		String filename = "foo.txt";
		String content = "testcase";
		
		File file = fileManager.writeFile(filename, content);
		assertEquals("foo.txt", file.getName());
		
		fileManager.addFile(file);
		assertEquals(1, fileManager.getFiles().size());
		
		String zip = "foo.zip";
		File zipFile = fileManager.exportFiles(zip);
		assertEquals("foo.zip", zipFile.getName());
	}

	private File createDir(String prefix)
	{
		String dirStr = System.getProperty("java.io.tmpdir");
		File tmpDir = new File(dirStr);
		String suffix = String.valueOf(System.currentTimeMillis());
		
		File newDir = new File(tmpDir, prefix + suffix);
		newDir.mkdir();
		return newDir;
	}
	
	private void deleteDir(File direc)
	{
		File[] files = direc.listFiles();
		for(File f : files)
			f.delete();
		direc.delete();
	}
}
