package framesis.http;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator{

	@Override
	public void start(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Bye World");
	}

}
