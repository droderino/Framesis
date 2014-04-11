package framesis.http;

import javax.servlet.ServletException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

public class Activator implements BundleActivator, ServiceListener{

	private BundleContext context;
	
	@Override
	public void start(BundleContext context) throws Exception {
		this.context = context;
		context.addServiceListener(this);
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		System.out.println("service unregistered");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void serviceChanged(ServiceEvent event) {
		if(event.getType() == ServiceEvent.REGISTERED)
		{
			ServiceReference ref = context.getServiceReference(HttpService.class.getName());
			if(ref != null)
			{
				HttpService service = (HttpService)context.getService(ref);
				
				try {
					System.out.println("Registering service....");
					service.registerServlet("/hello", new DemoServlet(), null, null);
					System.out.println("Service registered");
					
					service.registerServlet("/rpc", new RpcServlet(), null, null);
					System.out.println("RPC registered");
				} catch (ServletException | NamespaceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
