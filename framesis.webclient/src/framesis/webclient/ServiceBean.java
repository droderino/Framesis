package framesis.webclient;

import javax.ejb.EJB;
import javax.xml.ws.WebServiceRef;

import framesis.webservice.AnalysisService;

public class ServiceBean {

	@WebServiceRef(wsdlLocation="http://localhost:8080/AnalysisServiceService/AnalysisService?wsdl")
	private AnalysisService service;
	
	private String source;
	
	public void getSubScenarios()
	{
		service.dumpSubScenarios();
	}
	
	public String getUri(String uri)
	{
		this.source = service.getURI(uri);
		return source;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
