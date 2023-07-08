package it.cnr.ilc.ga.model.indexsearch;

import it.cnr.ilc.ga.model.pericope.Pericope;

import java.util.ArrayList;

public class PericopeSet {
	  
	private ArrayList<Pericope> pericopeSet;

	/**
	 * @return the pericopeSet
	 */
	
	
	public PericopeSet() {
		pericopeSet=new ArrayList<Pericope>(1000);
		System.err.println("sono in pericope set...");
	}

	
	public ArrayList<Pericope> getPericopeSet() {
		return pericopeSet;
	}

	/**
	 * @param pericopeSet the pericopeSet to set
	 */
	public void setPericopeSet(ArrayList<Pericope> pericopeSet) {
		this.pericopeSet = pericopeSet;
	}
	
	public void addPericope(Pericope pericope){
		pericopeSet.add(pericope);
		//System.out.println(pericope.getGreekText().getContent());
	}
	
}
