/**
 * 
 */
package it.cnr.ilc.ga.model.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Emiliano
 *
 */
public class PericopeComments {

	//private int idPericopeComments=0;
	private String pericopeRef;
	
	private HashMap<String,Comment> comments = new HashMap<String,Comment>(512);
	
	/**
	 * 
	 */
	public PericopeComments(String pString) {
		// 
		pericopeRef = pString;
	}

	public void addComment(Comment comment){
		try {
			this.comments.put(comment.getIdComment(),comment);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param comment
	 */
	public void removeComment(Comment comment){
		try {
			this.comments.remove(comment.getIdComment());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @return the idPericopeComments
	 */
//	public int getIdPericopeComments() {
//		return idPericopeComments;
//	}

	/**
	 * @param idPericopeComments the idPericopeComments to set
	 */
//	public void setIdPericopeComments(int id) {
//		this.idPericopeComments = id;
//	}

	/**
	 * @return the pericopeRef
	 */
	public String getPericopeRef() {
		return pericopeRef;
	}

	/**
	 * @param pericopeRef the pericopeRef to set
	 */
	public void setPericopeRef(String pericopeRef) {
		this.pericopeRef = pericopeRef;
	}

	/**
	 * @return the comments
	 */
	public HashMap<String, Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(HashMap<String, Comment> comments) {
		this.comments = comments;
	}

	
	
}
