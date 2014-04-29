package framesis.webclient;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import framesis.webservice.dto.SubScenarioDump;

@ManagedBean
@SessionScoped
public class ScenarioBean {
	
	private String source;
	private List<SubScenarioDump> sequence;
	private List<SubScenarioDump> subDe;
	private List<SubScenarioDump> subAn;
	private List<SubScenarioDump> subPre;
	private List<SubScenarioDump> subEval;
	
	@PostConstruct
	public void init()
	{
		this.sequence = new ArrayList<SubScenarioDump>();
		this.subAn = new ArrayList<SubScenarioDump>();
		this.subDe = new ArrayList<SubScenarioDump>();
		this.subEval = new ArrayList<SubScenarioDump>();
		this.subPre = new ArrayList<SubScenarioDump>();
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<SubScenarioDump> getSequence() {
		return sequence;
	}
	public void setSequence(List<SubScenarioDump> sequence) {
		this.sequence = sequence;
	}
	public List<SubScenarioDump> getSubDe() {
		return subDe;
	}
	public void setSubDe(List<SubScenarioDump> subDe) {
		this.subDe = subDe;
	}
	public List<SubScenarioDump> getSubAn() {
		return subAn;
	}
	public void setSubAn(List<SubScenarioDump> subAn) {
		this.subAn = subAn;
	}
	public List<SubScenarioDump> getSubPre() {
		return subPre;
	}
	public void setSubPre(List<SubScenarioDump> subPre) {
		this.subPre = subPre;
	}
	public List<SubScenarioDump> getSubEval() {
		return subEval;
	}
	public void setSubEval(List<SubScenarioDump> subEval) {
		this.subEval = subEval;
	}
}
