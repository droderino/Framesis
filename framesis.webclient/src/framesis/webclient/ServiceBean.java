package framesis.webclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import framesis.webservice.AnalysisService;
import framesis.webservice.AnalysisServiceInterface;
import framesis.webservice.dto.SubScenarioDump;

@ManagedBean
@SessionScoped
public class ServiceBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2919531447069349288L;

	@EJB
	private AnalysisService service;
	
	@ManagedProperty(value="#{scenarioBean}")
	private ScenarioBean scenario;
	
	@ManagedProperty(value="#{subScenariosBean}")
	private SubScenariosBean subScenarios;

	private String source;
	private UploadedFile uploadedFile;
	
	private List<SubScenarioDump> subScens;
	
	private SubScenarioDump selectedScen;
	private String select;
	
	@PostConstruct
	public void init()
	{
		this.fetchSubScenarios();
		
		subScenarios.setSubDe(fillSub(subScens, SubScenarioDump.PHASE_DE));
		subScenarios.setSubPre(fillSub(subScens, SubScenarioDump.PHASE_PRE));
		subScenarios.setSubAn(fillSub(subScens, SubScenarioDump.PHASE_AN));
		subScenarios.setSubEval(fillSub(subScens, SubScenarioDump.PHASE_EVAL));
	}

	public void fetchSubScenarios()
	{
		this.subScens = service.dumpSubScenarios();
	}
	
	public void executeScenario()
	{
		List<SubScenarioDump> execSequence = scenario.generateExecSequence();
		service.execute(execSequence, source);
	}
	
	public void deleteScenario()
	{
		scenario.delete();
	}
	
	private List<SubScenarioDump> fillSub(List<SubScenarioDump> input, String phase)
	{
		List<SubScenarioDump> output = new ArrayList<SubScenarioDump>();
		
		SubScenarioDump dump = new SubScenarioDump();
		dump.setName("Select...");
		output.add(dump);
		
		for(SubScenarioDump s : input)
			if(s.getPhase().equals(phase))
				output.add(s);
		return output;
	}
	
	public void listener(AjaxBehaviorEvent e)
	{
		HtmlSelectOneListbox a = (HtmlSelectOneListbox) e.getSource();
		String name = (String) a.getValue();
		
		for(SubScenarioDump s : subScens)
			if(s.getName().equals(name))
				this.selectedScen = s;
	}

	public void addSubScenario(String phase)
	{
		switch(phase)
		{
		case SubScenarioDump.PHASE_DE:
			scenario.getSubDe().add(selectedScen);
			break;
		case SubScenarioDump.PHASE_PRE:
			scenario.getSubPre().add(selectedScen);
			break;
		case SubScenarioDump.PHASE_AN:
			scenario.getSubAn().add(selectedScen);
			break;
		case SubScenarioDump.PHASE_EVAL:
			scenario.getSubEval().add(selectedScen);
			break;
		}
	}
	
	public void removeSubScenario(String phase)
	{
		switch(phase)
		{
		case SubScenarioDump.PHASE_DE:
			scenario.getSubDe().remove(selectedScen);
			break;
		case SubScenarioDump.PHASE_PRE:
			scenario.getSubPre().remove(selectedScen);
			break;
		case SubScenarioDump.PHASE_AN:
			scenario.getSubAn().remove(selectedScen);
			break;
		case SubScenarioDump.PHASE_EVAL:
			scenario.getSubEval().remove(selectedScen);
			break;
		}
	}
	
	public void upload()
	{
		
	}
		
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}

	public List<SubScenarioDump> getSubScens() {
		return subScens;
	}

	public void setSubScens(List<SubScenarioDump> subScens) {
		this.subScens = subScens;
	}
	
	public SubScenarioDump getSelectedScen() {
		return selectedScen;
	}

	public void setSelectedScen(SubScenarioDump selectedScen) {
		this.selectedScen = selectedScen;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public ScenarioBean getScenario() {
		return scenario;
	}

	public void setScenario(ScenarioBean scenario) {
		this.scenario = scenario;
	}

	public SubScenariosBean getSubScenarios() {
		return subScenarios;
	}

	public void setSubScenarios(SubScenariosBean subScenarios) {
		this.subScenarios = subScenarios;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}
}
