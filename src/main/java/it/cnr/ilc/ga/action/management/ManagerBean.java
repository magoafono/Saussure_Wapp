/**
 * 
 */
package it.cnr.ilc.ga.action.management;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import it.cnr.ilc.ga.handlers.exist.CommentStorer;
import it.cnr.ilc.ga.handlers.exist.CommentsLoader;
import it.cnr.ilc.ga.handlers.exist.PericopesLoader;
import it.cnr.ilc.ga.model.comment.Comment;
import it.cnr.ilc.ga.model.comment.PericopeComments;
import it.cnr.ilc.ga.model.indexsearch.CommentSet;
import it.cnr.ilc.ga.model.indexsearch.IndexEntry;
import it.cnr.ilc.ga.model.indexsearch.IndexItemSet;
import it.cnr.ilc.ga.model.indexsearch.PericopeSet;
import it.cnr.ilc.ga.model.pericope.Pericope;
import it.cnr.ilc.ga.utils.CommentSort;
import it.cnr.ilc.ga.utils.PericopeSort;

//import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Angelo Del Grosso
 *
 */
@ManagedBean(name="manager", eager=true)
@ApplicationScoped
public class ManagerBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2298329252729573985L;
	private PericopesLoader pl;
	private CommentsLoader cl;
	private PericopeSet pericopeSet;
	private IndexItemSet indexItemSet;
//	private CommentSet commentSet;
	private LinkedHashMap<String, PericopeComments> hashComments;
	private CommentStorer storer;	
	/**
	 * 
	 */
	
	public ManagerBean() {
		System.out.println("manager Application init");
		//System.getProperties().list(System.out);
		pl = new PericopesLoader();
		cl = new CommentsLoader();
		//TODO
		//cl = new CommentLoader();
		pericopeSet = new PericopeSet();
		indexItemSet = new IndexItemSet(); //CONTROLLARE!!!!!
		storer = new CommentStorer();
		
		//LOG4J
		Logger logger = Logger.getLogger(ManagerBean.class);
		/*String path="log4j.properties";
        PropertyConfigurator.configure(path);
		logger.info("Test Log");*/
		//System.err.println("user dir: " + System.getProperty("user.dir"));
		//commentSet = new CommentSet();

		try {
			hashComments = pl.load(pericopeSet);
			cl.load(hashComments);
			indexItemSet.buildIndexes(pericopeSet); //TODO esportare il build in un handler come pl
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
	}
	
	public ArrayList<Pericope> getPericopesOrdereda(){
		
		ArrayList<Pericope> pericopes = pericopeSet.getPericopeSet();
		Collections.sort(pericopes,PericopeSort.A_ORDER);
		return pericopes;
	}

	public ArrayList<Pericope> getPericopesOrderedb(){
		ArrayList<Pericope> pericopes = pericopeSet.getPericopeSet();
		Collections.sort(pericopes,PericopeSort.B_ORDER);
		return pericopes;
	}
	
	
	/**
	 * @return the pl
	 */
	public PericopesLoader getPl() {
		return pl;
	}


	/**
	 * @param pl the pl to set
	 */
	public void setPl(PericopesLoader pl) {
		this.pl = pl;
	}


	/**
	 * @return the cl
	 */
	public CommentsLoader getCl() {
		return cl;
	}

	/**
	 * @param cl the cl to set
	 */
	public void setCl(CommentsLoader cl) {
		this.cl = cl;
	}

	/**
	 * @return the pericopeSet
	 */
	public PericopeSet getPericopeSet() {
		return pericopeSet;
	}


	/**
	 * @param pericopeSet the pericopeSet to set
	 */
	public void setPericopeSet(PericopeSet pericopeSet) {
		this.pericopeSet = pericopeSet;
	}

	/**
	 * @return the indexItemSet
	 */
	public IndexItemSet getIndexItemSet() {
		return indexItemSet;
	}

	/**
	 * @param indexItemSet the indexItemSet to set
	 */
	public void setIndexItemSet(IndexItemSet indexItemSet) {
		this.indexItemSet = indexItemSet;
	}

	/**
	 * @return the hashComments
	 */
	public LinkedHashMap<String, PericopeComments> getHashComments() {
		return hashComments;
	}

	/**
	 * @param hashComments the hashComments to set
	 */
	public void setHashComments(LinkedHashMap<String, PericopeComments> hashComments) {
		this.hashComments = hashComments;
	}
	
	public synchronized boolean addComment(double idPericope, Comment comment){
		String idPericopeStr = Double.toString(idPericope);
		boolean ret = true;
		if (null != hashComments) {
			if( hashComments.containsKey(idPericopeStr)) {
				if (null == hashComments.get(idPericopeStr)){
					hashComments.put(idPericopeStr, new PericopeComments(idPericopeStr));
				}
				PericopeComments pc = hashComments.get(idPericopeStr);

				//sia x agguinta che update perche' uso una hashmap
				pc.addComment(comment);

				//Salvataggio del commento nel db eXist
				try {
					storer.store(comment, pc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			ret = false;
		}
		return ret;
		
	}
	
	/**
	 * 
	 * @param idPericope
	 * @param comment
	 * @return
	 */
	public synchronized boolean removeComment(double idPericope, Comment comment){
		String idPericopeStr = Double.toString(idPericope);
		boolean ret = true;
		if (null != hashComments) {
			if( hashComments.containsKey(idPericopeStr)) {
				PericopeComments pc = hashComments.get(idPericopeStr);
				pc.removeComment(comment);
				storer.remove(comment);
			}
		} else {
			ret = false;
		}
		return ret;
		
	}
	
	public synchronized boolean removeComments(double idPericope){
		String idPericopeStr = Double.toString(idPericope);
		if (null != hashComments) {
			hashComments.put(idPericopeStr,new PericopeComments(idPericopeStr)); //TODO inserire il controllo in add comment sulla lista di pericopeComments

		} else {System.err.println("di lì no!");}
		
		return true;
	}
	
	public List<Comment> getPericopeComments(double idPericope){
		String idPericopeStr = Double.toString(idPericope);
		//String idPericopeStr = "429";
		System.err.println(idPericopeStr);
		HashMap<String, Comment> comments = new HashMap<String, Comment>();
		PericopeComments pc ;
		if (null != hashComments) {
			if(null != (pc = hashComments.get(idPericopeStr) )){
				comments = pc.getComments();
			}
		}
		List<Comment> lc = new ArrayList<Comment>(comments.values());
		Collections.sort(lc, CommentSort.ID_ORDER);
		return lc;
	}
	
	/**
	 * Individua nella lista delle pericopi la pericope con riferimento idRefPericope
	 * @param idRefPericope
	 * @return Pericope con idRefPericope
	 */
	public Pericope getPericopeByRef (String idRefPericope) {
		
		Pericope p = null;
		for (Pericope peric : pericopeSet.getPericopeSet()) {
			
			if (idRefPericope.equals(peric.getAtext().getReference()) ) {
				p = peric;
				break;
			}
			
		}
		return p;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ManagerBean mb = new ManagerBean();

	}
	
}
