package framesis.webservice.dto;

import java.io.Serializable;

public class SubScenarioDump implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9081667374215194280L;
	private String name;
	private String phase;
	private String description;
	
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
}
