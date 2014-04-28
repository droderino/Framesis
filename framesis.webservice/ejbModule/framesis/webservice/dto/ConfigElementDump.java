package framesis.webservice.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"parameter", "value"})
public class ConfigElementDump implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5184155619812396152L;

	private String parameter;
	private String value;
	
	public ConfigElementDump(){ }
	
	public ConfigElementDump(String parameter, String value)
	{
		this.parameter = parameter;
		this.value = value;
	}
	
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
