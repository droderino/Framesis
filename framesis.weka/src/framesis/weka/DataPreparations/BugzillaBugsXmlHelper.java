package framesis.weka.DataPreparations;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class BugzillaBugsXmlHelper extends DefaultHandler{

	private Instances data;
	private Instance newInstance;
	private FastVector atts, classes;
	String tmpDesc;
	
	private boolean bBug = false;
	private boolean bBugId = false;
	private boolean bShortDesc = false;
	private boolean bProduct = false;
	private boolean bComponent = false;
	private boolean bReporter = false;
	private boolean bAssignedTo = false;
	private boolean bLongDesc = false;
	private boolean bTheText = false;
	private boolean bBugClassification = false;
	
	private String prefix = "bug_";
	private String bug = "bug";
	private String bugId = "bug_id";
	private String shortDesc = "short_desc";
	private String product = "product";
	private String component = "component";
	private String reporter = "reporter";
	private String assignedTo = "assigned_to";
	private String longDesc = "long_desc";
	private String theText = "thetext";
	private String bugClassification = "bug_classification";
	
	public BugzillaBugsXmlHelper()
	{
		init();
	}
	
	private void init()
	{
		classes = new FastVector();
		classes.addElement("BUG");
		classes.addElement("RFE");
		classes.addElement("IMPROVEMENT");
		classes.addElement("DOCUMENTATION");
		classes.addElement("BUILD_SYSTEM");
		classes.addElement("REFACTORING");
		classes.addElement("CLEANUP");
		classes.addElement("SPEC");
		classes.addElement("TEST");
		classes.addElement("BACKPORT");
		classes.addElement("TASK");
		classes.addElement("OTHER");
		classes.addElement("DESIGN_DEFECT");
		
		atts = new FastVector();
		atts.addElement(new Attribute(prefix + bugId));
		atts.addElement(new Attribute(prefix + shortDesc, (FastVector)null));
		atts.addElement(new Attribute(prefix + product, (FastVector)null));
		atts.addElement(new Attribute(prefix + component, (FastVector)null));
		atts.addElement(new Attribute(prefix + reporter, (FastVector)null));
		atts.addElement(new Attribute(prefix + assignedTo, (FastVector)null));
		atts.addElement(new Attribute(prefix + longDesc, (FastVector)null));
		atts.addElement(new Attribute(prefix + theText, (FastVector)null));
		atts.addElement(new Attribute(bugClassification, classes));
		
		data = new Instances("Bugzilla data", atts, 0);
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)
	{
		if(qName.equalsIgnoreCase(bug))
		{
			newInstance = new Instance(data.numAttributes());
			bBug = true;
			tmpDesc = new String();
		}
		else if(qName.equalsIgnoreCase(bugId))
			bBugId = true;
		else if(qName.equalsIgnoreCase(shortDesc))
			bShortDesc = true;
		else if(qName.equalsIgnoreCase(product))
			bProduct = true;
		else if(qName.equalsIgnoreCase(component))
			bComponent = true;
		else if(qName.equalsIgnoreCase(reporter))
			bReporter = true;
		else if(qName.equalsIgnoreCase(assignedTo))
			bAssignedTo = true;
		else if(qName.equalsIgnoreCase(longDesc))
			bLongDesc = true;
		else if(qName.equalsIgnoreCase(theText))
			bTheText = true;
		else if(qName.equalsIgnoreCase(bugClassification))
			bBugClassification = true;
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
	{
		if(qName.equalsIgnoreCase(bug))
		{
			newInstance.setValue((Attribute)atts.elementAt(6), tmpDesc);
			data.add(newInstance);
			bBug = false;
		}
		else if(qName.equalsIgnoreCase(bugId))
			bBugId = false;
		else if(qName.equalsIgnoreCase(shortDesc))
			bShortDesc = false;
		else if(qName.equalsIgnoreCase(product))
			bProduct = false;
		else if(qName.equalsIgnoreCase(component))
			bComponent = false;
		else if(qName.equalsIgnoreCase(reporter))
			bReporter = false;
		else if(qName.equalsIgnoreCase(assignedTo))
			bAssignedTo = false;
		else if(qName.equalsIgnoreCase(longDesc))
		{
			bLongDesc = false;
		}
		else if(qName.equalsIgnoreCase(theText))
			bTheText = false;
		else if(qName.equalsIgnoreCase(bugClassification))
			bBugClassification = false;
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		if(bBug)
		{
			String value = new String(ch, start, length);
			
			if(bBugId)
				newInstance.setValue((Attribute)atts.elementAt(0), Integer.parseInt(value));
			else if(bShortDesc)
				newInstance.setValue((Attribute)atts.elementAt(1), value);
			else if(bProduct)
				newInstance.setValue((Attribute)atts.elementAt(2), value);
			else if(bComponent)
				newInstance.setValue((Attribute)atts.elementAt(3), value);
			else if(bReporter)
				newInstance.setValue((Attribute)atts.elementAt(4), value);
			else if(bAssignedTo)
				newInstance.setValue((Attribute)atts.elementAt(5), value);
			else if(bLongDesc)
			{
				if(bTheText)
				{
					value.replaceAll("\n", " ");
					tmpDesc = tmpDesc + value;
				}
			}
			else if(bBugClassification)
			{
				//System.out.println(value);
				newInstance.setValue((Attribute)atts.elementAt(8), value);
			}
		}
	}

	public Instances getData() {
		return data;
	}

	public void setData(Instances data) {
		this.data = data;
	}
}
