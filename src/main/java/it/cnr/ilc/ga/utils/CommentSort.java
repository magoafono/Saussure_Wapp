package it.cnr.ilc.ga.utils;

import it.cnr.ilc.ga.model.comment.Comment;

import java.util.Comparator;

public class CommentSort {
	
	 public static final Comparator<Comment> 
	    ID_ORDER = 
	    	new Comparator<Comment>() {
	        	
	    		public int compare(Comment c1, Comment c2) {
	    			String id1str = c1.getIdComment();
	    			String id2str = c2.getIdComment();
	    			int id1 = Integer.parseInt(id1str.substring(id1str.indexOf('_') + 1));
	    			int id2 = Integer.parseInt(id2str.substring(id2str.indexOf('_') + 1));
	        		return 	(id1 < id2) ? -1 : (id1 == id2) ? 0 : 1;
	           }
	    	};
}
