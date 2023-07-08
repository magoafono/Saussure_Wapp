/**
 * 
 */
package it.cnr.ilc.ga.model.analysis;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo Del Grosso
 *
 */
public class Analysis {
	
	private List<LinguisticUnit> analysis;
	
	public Analysis(){
		analysis = new ArrayList<LinguisticUnit>(1000);
		// constructor
	}

	
	public void addLinguisticUnit(LinguisticUnit lu){
		
		analysis.add(lu);
	}
	
	/**
	 * @return the analysis
	 */
	public List<LinguisticUnit> getAnalysis() {
		return analysis;
	}

	/**
	 * @param analysis the analysis to set
	 */
	public void setAnalysis(List<LinguisticUnit> analysis) {
		this.analysis = analysis;
	}
	
}
