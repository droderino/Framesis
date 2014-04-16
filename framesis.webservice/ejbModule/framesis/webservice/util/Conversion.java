package framesis.webservice.util;

import javax.ejb.Stateless;

import framesis.api.DataPreparation;
import framesis.api.SubScenario;
import framesis.webservice.dto.DataPreparationDump;
import framesis.webservice.dto.SubScenarioDump;

@Stateless
public class Conversion {
	
	public SubScenarioDump fromSubScenario(SubScenario element)
	{
		SubScenarioDump dump = new SubScenarioDump();
		dump.setName(element.getName());
		dump.setPhase(element.getPhase());
		dump.setDescription(element.getDescription());
		return dump;
	}
	
	public DataPreparationDump fromDataPreparation(DataPreparation element)
	{
		DataPreparationDump dump = new DataPreparationDump();
		dump.setName(element.getName());
		dump.setDescription(element.getDescription());
		return dump;
	}
}
