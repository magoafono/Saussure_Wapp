/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;

/**
 * @author Angelo Del Grosso
 *
 */
public class QueryLemma extends QueryTerm {

	/* (non-Javadoc)
	 * @see it.cnr.ilc.ga.model.indexsearch.QueryTerm#getValue()
	 */
	@Override
	public String getValue() {
		return this.value;
	}

	/* (non-Javadoc)
	 * @see it.cnr.ilc.ga.model.indexsearch.QueryTerm#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		this.value = value;

	}

}
