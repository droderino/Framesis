package framesis.webclient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.xml.ws.WebServiceRef;

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

	private String source = "default";
	
	private List<SubScenarioDump> subScens;
	private List<SubScenarioDump> subDe;
	private List<SubScenarioDump> subPre;
	private List<SubScenarioDump> subAn;
	private List<SubScenarioDump> subEval;
	
	private SubScenarioDump selectedScen;
	private String select;
	
	@PostConstruct
	public void init()
	{
		this.fillSubScens();
		this.subDe = this.fillSub(subScens, SubScenarioDump.PHASE_DE);
		this.subPre = this.fillSub(subScens, SubScenarioDump.PHASE_PRE);
		this.subAn = this.fillSub(subScens, SubScenarioDump.PHASE_AN);
		this.subEval = this.fillSub(subScens, SubScenarioDump.PHASE_EVAL);
		
	}

	public void fillSubScens()
	{
		this.subScens = service.dumpSubScenarios();
	}
	
	public List<SubScenarioDump> fillSub(List<SubScenarioDump> input, String phase)
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

	public List<SubScenarioDump> getSubDe() {
		return subDe;
	}

	public void setSubDe(List<SubScenarioDump> subDe) {
		this.subDe = subDe;
	}

	public List<SubScenarioDump> getSubPre() {
		return subPre;
	}

	public void setSubPre(List<SubScenarioDump> subPre) {
		this.subPre = subPre;
	}

	public List<SubScenarioDump> getSubAn() {
		return subAn;
	}

	public void setSubAn(List<SubScenarioDump> subAn) {
		this.subAn = subAn;
	}

	public List<SubScenarioDump> getSubEval() {
		return subEval;
	}

	public void setSubEval(List<SubScenarioDump> subEval) {
		this.subEval = subEval;
	}
}
