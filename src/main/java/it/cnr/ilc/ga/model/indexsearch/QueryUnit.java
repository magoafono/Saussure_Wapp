/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;

import it.cnr.ilc.ga.model.analysis.PartOfSpeech;

/**
 * @author Angelo Del Grosso
 *
 */
public class QueryUnit {

	private QueryTerm qterm;
	private PartOfSpeech pos = null;
	
	/**
	 * 
	 */
	public QueryUnit() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the qterm
	 */
	public QueryTerm getQterm() {
		return qterm;
	}

	/**
	 * @param qterm the qterm to set
	 */
	public void setQterm(QueryTerm qterm) {
		this.qterm = qterm;
	}

	/**
	 * @return the pos
	 */
	public PartOfSpeech getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(PartOfSpeech pos) {
		this.pos = pos;
	}

	
	
}
