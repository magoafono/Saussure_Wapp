/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;

/**
 * @author Angelo Del Grosso
 *
 */
public abstract class QueryTerm {
	protected String value;

	/**
	 * @return the value
	 */
	public abstract String getValue();

	/**
	 * @param value the value to set
	 */
	public abstract void setValue(String value);
	
	// classe astratta per i termini di ricerca : FORM, LEMMA oppure ROOT
	
}
