/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;
import it.cnr.ilc.ga.model.comment.*;

import java.util.ArrayList;
import java.util.List;



/**
 * @author Emiliano
 *
 */
public class CommentSet {

	private List<PericopeComments> pericopeCommentsSet = new ArrayList<PericopeComments>(5000);
	
	/**
	 * 
	 */
	public CommentSet() {
		// TODO Auto-generated constructor stub
	}
	
	public void addPericopeComments(PericopeComments pericopeComments){
		this.pericopeCommentsSet.add(pericopeComments);
	}

	/**
	 * @return the pericopeCommentsSet
	 */
	public List<PericopeComments> getPericopeCommentsSet() {
		return pericopeCommentsSet;
	}

	/**
	 * @param pericopeCommentsSet the pericopeCommentsSet to set
	 */
	public void setPericopeCommentsSet(List<PericopeComments> pericopeCommentsSet) {
		this.pericopeCommentsSet = pericopeCommentsSet;
	}
}
