package framesis.demo2;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{

	public void start(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("hello world");
	}

	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("bye");
	}

}
