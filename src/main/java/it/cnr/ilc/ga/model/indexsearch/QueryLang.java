/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;

import it.cnr.ilc.ga.model.pericope.Text;

/**
 * @author Angelo Del Grosso
 *
 */
public class QueryLang {

	private Text.LangType langType= null;
	private Operator operator = null;
	private QueryUnit[] quArray = new QueryUnit[3];
	
	/**
	 * 
	 */
	public QueryLang() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the langType
	 */
	public Text.LangType getLangType() {
		return langType;
	}

	/**
	 * @param langType the langType to set
	 */
	public void setLangType(Text.LangType langType) {
		this.langType = langType;
	}

	/**
	 * @return the operator
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * @return the quArray
	 */
	public QueryUnit[] getQuArray() {
		return quArray;
	}

	/**
	 * @param quArray the quArray to set
	 */
	public void setQuArray(QueryUnit[] quArray) {
		this.quArray = quArray;
	}

	
}
