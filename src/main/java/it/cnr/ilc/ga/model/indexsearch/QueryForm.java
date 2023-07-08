/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;

/**
 * @author Angelo Del Grosso
 *
 */
public class QueryForm extends QueryTerm {

	/**
	 * 
	 */
	public QueryForm() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see it.cnr.ilc.ga.model.indexsearch.QueryTerm#getValue()
	 */
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	/* (non-Javadoc)
	 * @see it.cnr.ilc.ga.model.indexsearch.QueryTerm#setValue(java.lang.String)
	 */
	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		this.value = value;
		
	}
	
	

}
