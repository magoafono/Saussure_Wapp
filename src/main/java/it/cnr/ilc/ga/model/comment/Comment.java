/**
 * 
 */
package it.cnr.ilc.ga.model.comment;

import java.io.Serializable;
import java.util.List;

/**
 * @author Emiliano
 *
 */

public class Comment{


	//private enum CommentType {VARIANT, NOTE, MISUNDERTANDING}
	
	private final int SNIPPET_LENGTH = 15;
	
	private String type;
	private String commentTest = "CommentTest";

	private String idComment;
	private long refComment;
	private String greekSelectedPosition;
	private String arabicSelectedPosition;
	private List<Integer> greekSelectedBound;
	private List<Integer> arabicSelectedBound;
	//private CommentType type;
	private String commentText;

	private String editor;
	private String versionid;

	private String data;



	/**
	 * @param commentTest
	 * @param idComment
	 * @param refComment
	 * @param greekSelectedPosition
	 * @param arabicSelectedPosition
	 * @param type
	 * @param commentText
	 */

	public Comment() {
		this.idComment="0";
		this.greekSelectedPosition = "";
		this.arabicSelectedPosition = "";
		//this.type = CommentType.NOTE;
		//this.commentText = "testo del commento";
	}

	/**
	 * @return the idComment
	 */
	public String getIdComment() {
		return idComment;
	}
	/**
	 * @param idComment the idComment to set
	 */
	public void setIdComment(String commentID) {
		this.idComment = commentID;
	}
	/**
	 * @return the refComment
	 */
	public long getRefComment() {
		return refComment;
	}
	/**
	 * @param refComment the refComment to set
	 */
	public void setRefComment(long commentREF) {
		this.refComment = commentREF;
	}
	/**
	 * @return the greekSelectedPosition
	 */
	public String getGreekSelectedPosition() {
		return greekSelectedPosition;
	}
	/**
	 * @param greekSelectedPosition the greekSelectedPosition to set
	 */
	public void setGreekSelectedPosition(String greekSelectedPosition) {
		this.greekSelectedPosition = greekSelectedPosition;
	}
	/**
	 * @return the arabicSelectedPosition
	 */
	public String getArabicSelectedPosition() {
		return arabicSelectedPosition;
	}
	/**
	 * @param arabicSelectedPosition the arabicSelectedPosition to set
	 */
	public void setArabicSelectedPosition(String arabicSelectedPosition) {
		this.arabicSelectedPosition = arabicSelectedPosition;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
		//			if(type.equals("variant reading")){
		//				this.type = CommentType.VARIANT;
		//			}else if(type.equals("note")){
		//				this.type = CommentType.NOTE;
		//			}else if(type.equals("misunderstanding")){
		//				this.type = CommentType.MISUNDERTANDING;
		//			}else {
		//				this.type=null; 
		//			} 

	}
	/**
	 * @return the commentText
	 */
	public String getCommentText() {
		return commentText;
	}
	/**
	 * @param commentText the commentText to set
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public String getSnippet(){
		
		String snippet = commentText;
		snippet = snippet.replaceAll("\\<.+?\\>", "");
		if(snippet.length() > SNIPPET_LENGTH){
			snippet=snippet.substring(0,SNIPPET_LENGTH) + "...";
		}
		
		return  snippet;
	}

	public void setCommentTest(String commentTest) {
		this.commentTest = commentTest;
	}
	public String getCommentTest() {
		return commentTest;
	}

	/**
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
	}

	/**
	 * @param editor the editor to set
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}

	/**
	 * @return the versionid
	 */
	public String getVersionid() {
		return versionid;
	}

	/**
	 * @param versionid the versionid to set
	 */
	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}

	/**
	 * @param type the type to set
	 */
	//		public void setType(CommentType type) {
	//			this.type = type;
	//		}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the greekSelectedBound
	 */
	public List<Integer> getGreekSelectedBound() {
		return greekSelectedBound;
	}

	/**
	 * @param greekSelectedBound the greekSelectedBound to set
	 */
	public void setGreekSelectedBound(List<Integer> greekSelectedBound) {
		this.greekSelectedBound = greekSelectedBound;
	}

	/**
	 * @return the arabicSelectedBound
	 */
	public List<Integer> getArabicSelectedBound() {
		return arabicSelectedBound;
	}

	/**
	 * @param arabicSelectedBound the arabicSelectedBound to set
	 */
	public void setArabicSelectedBound(List<Integer> arabicSelectedBound) {
		this.arabicSelectedBound = arabicSelectedBound;
	}

	@Override
	public String toString() {
		return "Comment{" + "ID=" + idComment + ", Greek=" + greekSelectedPosition + ", arabo=" + arabicSelectedPosition + ", TEXT=" + commentText + ", type=" + type.toString() + '}';
	}


}