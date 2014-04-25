package framesis.webclient2;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.xml.ws.WebServiceRef;

import framesis.webservice.AnalysisService;
import framesis.webservice.AnalysisServiceInterface;

@ManagedBean
@SessionScoped
public class ServiceBean {

	private String source = "default";
	

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public void change()
	{
		this.source = "changed";
	}
	
	public void getUri(String input)
	{
		//this.source = service.getURI(input);
	}
}
