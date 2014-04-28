package framesis.webservice.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"name", "phase", "description", "config"})
public class SubScenarioDump implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9081667374215194280L;
	
	public static final String PHASE_DE = "Data Extraction";
	public static final String PHASE_PRE = "Preprocessing";
	public static final String PHASE_AN = "Analysis";
	public static final String PHASE_EVAL = "Evaluation";
	
	private String name;
	private String phase;
	private String description;
	private List<ConfigElementDump> config;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ConfigElementDump> getConfig() {
		return config;
	}
	public void setConfig(List<ConfigElementDump> config) {
		this.config = config;
	}
}
