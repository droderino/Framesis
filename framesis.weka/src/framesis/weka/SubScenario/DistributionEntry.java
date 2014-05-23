package framesis.weka.SubScenario;

import weka.core.Attribute;
import weka.core.Instance;

public class DistributionEntry {

	private Instance instance;
	private double[] distribution;
	private String actual, predicted;
	
	public DistributionEntry(Instance i, double[] dist, String act, String pred)
	{
		this.instance = i;
		this.distribution = dist;
		this.actual = act;
		this.predicted = pred;
	}

	public Instance getInstance() {
		return instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}

	public double[] getDistribution() {
		return distribution;
	}

	public void setDistribution(double[] distribution) {
		this.distribution = distribution;
	}
	
	@Override
	public String toString()
	{
		String output = String.format("%20s%20s", actual, predicted);
		
		for(int i=0; i<distribution.length; i++)
		{
			output = output + String.format("%20f", distribution[i]);
		}
		
		return output;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public String getPredicted() {
		return predicted;
	}

	public void setPredicted(String predicted) {
		this.predicted = predicted;
	}
}
