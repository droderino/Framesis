package framesis.webclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import framesis.webservice.AnalysisService;
import framesis.webservice.AnalysisServiceInterface;
import framesis.webservice.dto.SubScenarioDump;

@ManagedBean
@ViewScoped
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
	private String result;
	
	private List<SubScenarioDump> subScens;
	
	private SubScenarioDump selectedScen;
	private String select;

	private UploadedFile uploadedFile;
	private StreamedContent downloadFile;
	
	private int inputOption;
	
	@PostConstruct
	public void init()
	{
		this.fetchSubScenarios();
		
		subScenarios.setSubDe(fillSub(subScens, SubScenarioDump.PHASE_DE));
		subScenarios.setSubPre(fillSub(subScens, SubScenarioDump.PHASE_PRE));
		subScenarios.setSubAn(fillSub(subScens, SubScenarioDump.PHASE_AN));
		subScenarios.setSubEval(fillSub(subScens, SubScenarioDump.PHASE_EVAL));
		
		inputOption = 0;
		result = null;
	}

	public void fetchSubScenarios()
	{
		this.subScens = service.dumpSubScenarios();
	}
	
	public void executeScenario()
	{
		List<SubScenarioDump> execSequence = scenario.generateExecSequence();
		
		if( !source.isEmpty() && !execSequence.isEmpty() )
			result = service.execute(execSequence, source);
	}
	
	public void deleteScenario()
	{
		scenario.delete();
		result = null;
	}
	
	private List<SubScenarioDump> fillSub(List<SubScenarioDump> input, String phase)
	{
		List<SubScenarioDump> output = new ArrayList<SubScenarioDump>();
		
		SubScenarioDump dump = new SubScenarioDump();
		//dump.setName("Select...");
		//output.add(dump);
		
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
			if(selectedScen.getPhase() == SubScenarioDump.PHASE_DE)
				scenario.getSubDe().add(selectedScen);
			break;
		case SubScenarioDump.PHASE_PRE:
			if(selectedScen.getPhase() == SubScenarioDump.PHASE_PRE)
				scenario.getSubPre().add(selectedScen);
			break;
		case SubScenarioDump.PHASE_AN:
			if(selectedScen.getPhase() == SubScenarioDump.PHASE_AN)
				scenario.getSubAn().add(selectedScen);
			break;
		case SubScenarioDump.PHASE_EVAL:
			if(selectedScen.getPhase() == SubScenarioDump.PHASE_EVAL)
				scenario.getSubEval().add(selectedScen);
			break;
		}
	}
	
	public void removeSubScenario(String phase)
	{
		switch(phase)
		{
		case SubScenarioDump.PHASE_DE:
			if(selectedScen.getPhase() == SubScenarioDump.PHASE_DE)
				scenario.getSubDe().remove(selectedScen);
			break;
		case SubScenarioDump.PHASE_PRE:
			if(selectedScen.getPhase() == SubScenarioDump.PHASE_PRE)
				scenario.getSubPre().remove(selectedScen);
			break;
		case SubScenarioDump.PHASE_AN:
			if(selectedScen.getPhase() == SubScenarioDump.PHASE_AN)
				scenario.getSubAn().remove(selectedScen);
			break;
		case SubScenarioDump.PHASE_EVAL:
			if(selectedScen.getPhase() == SubScenarioDump.PHASE_EVAL)
				scenario.getSubEval().remove(selectedScen);
			break;
		}
	}
	
	public void fileUpload(FileUploadEvent ev) throws IOException
	{
		setUploadedFile(ev.getFile());
		
		String prefix = FilenameUtils.getBaseName(uploadedFile.getFileName());
		String suffix = FilenameUtils.getExtension(uploadedFile.getFileName());
		
		Path tmpDir = Paths.get(System.getProperty("java.io.tmpdir"));
		Path root = Files.createTempDirectory(tmpDir, "framesis");
		
		File file = null;
		OutputStream stream = null;
		
		file = File.createTempFile(prefix, "." + suffix, root.toFile());
		stream = new FileOutputStream(file);
		IOUtils.copy(uploadedFile.getInputstream(), stream);
		
		source = file.toURI().toString();
	}
	
	public void fileDownload() throws FileNotFoundException
	{
		InputStream input = new FileInputStream(result);
		this.downloadFile = new DefaultStreamedContent(input, "application/zip", "ergebnisse.zip");
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

	public StreamedContent getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(StreamedContent downloadFile) {
		this.downloadFile = downloadFile;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getInputOption() {
		return inputOption;
	}

	public void setInputOption(int inputOption) {
		this.inputOption = inputOption;
	}
}
