/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;

/**
 * @author Angelo Del Grosso
 *
 */
public class Query {

	private String queryOperator = null;
	private String commentType = null;
	
	private QueryLang qlGreek = null;
	private QueryLang qlArabic = null;
	
	/**
	 * 
	 */
	public Query() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the queryOperator
	 */
	public String getQueryOperator() {
		return queryOperator;
	}
	/**
	 * @param queryOperator the queryOperator to set
	 */
	public void setQueryOperator(String queryOperator) {
		this.queryOperator = queryOperator;
	}
	/**
	 * @return the commentType
	 */
	public String getCommentType() {
		return commentType;
	}
	/**
	 * @param commentType the commentType to set
	 */
	public void setCommentType(String commentType) {
		this.commentType = commentType;
	}
	/**
	 * @return the qlGreek
	 */
	public QueryLang getQlGreek() {
		return qlGreek;
	}
	/**
	 * @param qlGreek the qlGreek to set
	 */
	public void setQlGreek(QueryLang qlGreek) {
		this.qlGreek = qlGreek;
	}
	/**
	 * @return the qlArabic
	 */
	public QueryLang getQlArabic() {
		return qlArabic;
	}
	/**
	 * @param qlArabic the qlArabic to set
	 */
	public void setQlArabic(QueryLang qlArabic) {
		this.qlArabic = qlArabic;
	}

	
	
}
