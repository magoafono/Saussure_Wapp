package it.cnr.ilc.ga.model.pericope;

import it.cnr.ilc.ga.model.comment.PericopeComments;

public class Pericope {
	
	private double id;
	
	private Text atext;
	private Text btext;
	
	private PericopeComments comments;
	
	public Pericope() {
		
		// constructor
		
	}
	
	/**
	 * @return the id
	 */
	public double getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(double id) {
		this.id = id;
	}
	/**
	 * @return the aText
	 */
	public Text getAtext() {
		return atext;
	}
	/**
	 * @param aText the aText to set
	 */
	public void setAtext(Text atext) {
		this.atext = atext;
	}
	/**
	 * @return the arabicText
	 */
	public Text getBtext() {
		return btext;
	}
	/**
	 * @param arabicText the arabicText to set
	 */
	public void setBtext(Text btext) {
		this.btext = btext;
	}

	/**
	 * @return the comments
	 */
	public PericopeComments getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(PericopeComments comments) {
		this.comments = comments;
	}
	
	

}
