package framesis.webservice.dto;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.Entity;

public class DataPreparationDump implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4471570141829695405L;
	private String name;
	private String description;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
