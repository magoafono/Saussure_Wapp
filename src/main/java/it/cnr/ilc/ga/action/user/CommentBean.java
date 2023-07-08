/**
 * 
 */
package it.cnr.ilc.ga.action.user;

import it.cnr.ilc.ga.action.management.ManagerBean;
import it.cnr.ilc.ga.model.analysis.Analysis;
import it.cnr.ilc.ga.model.comment.Comment;
import it.cnr.ilc.ga.model.pericope.Pericope;
import it.cnr.ilc.ga.model.pericope.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

/**
 * @author Emiliano
 *
 */
@ManagedBean
@SessionScoped
public class CommentBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5238122327626183705L;

	//private String[] type = {"variant reading, note, misunderstanding"};

	@ManagedProperty(value="#{manager}")
	private ManagerBean managerBean;

	private String greektext;
	private String arabictext;

	private String greekselectedText = "";
	private String arabicselectedText = "";

	private List<Integer> greekselectedBound = null;
	private List<Integer> arabicselectedBound = null;

	private String greektextarea = new String();
	private String arabictextarea = new String();

	private String commentvalue = null; // TODO refactoring in commentText (cambiare in XHTML)
	private String commenttype; //TODO refactoring in commentType (cambiare in XHTML)

	private Pericope selectedPericope;
	private String pericopeRef;
	
	private String arabicSelectedPosition;
	private String greekSelectedPosition;
	private String commentText; // TODO correggere perchè uguale a commentvalue

	private Comment selectedComment = null;

	
	private List <SelectItem> options;

	/**
	 * 
	 */
	public CommentBean() {

		selectedPericope = new Pericope();
		Text testo = new Text();
		Analysis anal = new Analysis();
		testo.setAnalysis(anal);
		selectedPericope.setAtext(testo);
		selectedPericope.setBtext(testo);

		options = new ArrayList<SelectItem>(100);

		SelectItemGroup critiquesAuteur  = new SelectItemGroup("Notes critiques au texte par l'auteur");
		critiquesAuteur.setSelectItems(new SelectItem[]{
				new SelectItem("bi","Biffure"),
				new SelectItem("aj","Ajout"),
				new SelectItem("va","Variantes"),
				new SelectItem("re","Restitution")

		});
		options.add(critiquesAuteur);


		SelectItemGroup critiquesEditeur = new SelectItemGroup("Notes critiques au texte par l'éditeur");
		critiquesEditeur.setSelectItems(new SelectItem[]{
				new SelectItem("cr","Correction"),
				new SelectItem("in","Integration"),
				new SelectItem("dv","Dével. des abrév.")
		});
		options.add(critiquesEditeur);
		
		SelectItemGroup davantage = new SelectItemGroup("Autres typologies");
		davantage.setSelectItems(new SelectItem[]{
				new SelectItem("nt","Notes théoriques"),
				new SelectItem("pe","Personne"),
				new SelectItem("bl","Bibliographie"),
				new SelectItem("gl","Glossaire"),
				new SelectItem("ns","Notes supplémentaires")
		});
		options.add(davantage);
	}

	public List<Comment> getPericopeComments(){
		return managerBean.getPericopeComments(selectedPericope.getId());
	}

	public String greekclick(){

		greekselectedBound = new ArrayList();

		Pattern p = Pattern.compile("(.+?)\\s+\\[(\\d+)\\$(\\d+)\\)");
		Matcher m = p.matcher(greektext);
		if (m.find()) {
			greekselectedText = m.group(1);
			greekselectedBound.add(new Integer (m.group(2)));
			greekselectedBound.add(new Integer (m.group(3)));
			//System.out.println("greek clicked --> " + greekselectedText);
			//System.out.println("selected pericope: " + selectedPericope.getBtext().getContent());
		} else {
			System.err.println("Error in evaluate " + p.toString() + " on " + greektext);
		}

		return "";
	}

	public String arabicclick(){
		//System.out.println("arabic pre --> " + arabictext);
		//arabicselectedText = arabictext.substring(0,arabictext.indexOf('['));
		arabicselectedBound = new ArrayList();

		//limite sx, limite dx, testo selezionato
		Pattern p = Pattern.compile("(.+?)\\s+\\[(\\d+)\\$(\\d+)\\)");
		Matcher m = p.matcher(arabictext);
		if (m.find()) {
			arabicselectedText = m.group(1);
			arabicselectedBound.add(new Integer (m.group(2)));
			arabicselectedBound.add(new Integer (m.group(3)));
			//System.out.println("arabic clicked --> " + arabicselectedText);
			//System.out.println("selected pericope: " + selectedPericope.getAtext().getContent());
		} else {
			System.err.println("Error in evaluate " + p.toString() + " on " + arabictext);
		}

		//		arabicselectedBound.add(new Integer (arabictext.substring(arabictext.indexOf('[') + 1, arabictext.indexOf('-'))));
		//arabicselectedBound.add(new Integer (arabictext.substring(arabictext.indexOf('-') + 1, arabictext.indexOf(')'))));

		return "";
	}

	public String getValue() {
		return commentvalue + String.valueOf(commenttype) + greekselectedText + arabicselectedText;
	}

	/**
	 * Evento collegato al submit della form di commento
	 * @return
	 */
	public String submit(){

		Comment comment = null;

		if (null != selectedComment) {
			comment = selectedComment;
		} else {
			comment = new Comment();
			if (null == arabicselectedBound) {
				arabicselectedBound = new ArrayList();
				arabicselectedBound.add(new Integer (0));
				arabicselectedBound.add(new Integer (0));
				//arabicselectedText = "";
			}
			if (null == greekselectedBound) {
				greekselectedBound = new ArrayList();
				greekselectedBound.add(new Integer (0));
				greekselectedBound.add(new Integer (0));
				//greekselectedText = "";
			}
			comment.setArabicSelectedPosition(arabicselectedText);
			comment.setGreekSelectedPosition(greekselectedText);
			comment.setArabicSelectedBound(arabicselectedBound);
			comment.setGreekSelectedBound(greekselectedBound);
			List<Comment> pcs = managerBean.getPericopeComments(selectedPericope.getId());
			String  nextId = "0"; //caso iniziale quando inserisco il primo commento alla pericope
			try {
				Comment lastComment = pcs.get(pcs.size() - 1);
				String idLastComment = lastComment.getIdComment();
				idLastComment = idLastComment.substring(idLastComment.indexOf("_") + 1);
				nextId = String.valueOf(Integer.parseInt(idLastComment) + 1);
			} catch (IndexOutOfBoundsException e) {

			}
			comment.setIdComment(selectedPericope.getId() + "_" +  nextId);

		}

		comment.setCommentText(commentvalue);
		comment.setType(commenttype);

		managerBean.addComment(selectedPericope.getId(), comment);
		newComment();

		return "";
	}


	private void getLastId() {

	}

	/**
	 * 
	 * @return
	 */
	public String deleteComment( ) {

		managerBean.removeComment(selectedPericope.getId(), selectedComment);
		clearValue();
		return "";
	}

	/**
	 * 
	 * @return
	 */
	public String clearCommentValue(){
		//arabicselectedText = "";
		//greekselectedText = "";
		commentvalue = "";
		return "";
	}

	/**
	 * 
	 */
	private void clearValue(){


		greekselectedText = "";
		arabicselectedText = "";

		greekselectedBound = null;
		arabicselectedBound = null;

		commentvalue = null;
		commenttype= "";

		arabicSelectedPosition= "";
		greekSelectedPosition= "";
		commentText = "";

		selectedComment = null;


	}


	/**
	 * 
	 * @return
	 */
	public String newComment() {

		selectedComment = null;
		clearValue();

		return "";
	}

	private String getGreekSelectedText (){

		String gt = selectedPericope.getBtext().getContent();
		//String selected = gt.substring(selectedComment.getGreekSelectedBound().get(0), selectedComment.getGreekSelectedBound().get(1));
		String selected = selectedComment.getGreekSelectedPosition();
		return selected;
	}
	private String getArabicSelectedText(){

		String gt = selectedPericope.getAtext().getContent();
		//String selected = gt.substring(selectedComment.getArabicSelectedBound().get(0), selectedComment.getArabicSelectedBound().get(1));
		String selected = selectedComment.getArabicSelectedPosition();
		return selected;
	}

	public String viewSelectedComment(){
		//System.out.println("view selected comment: " + selectedComment.getType());
		if(null != selectedComment.getGreekSelectedBound()){
			greekselectedText = getGreekSelectedText();
			
		}

		if(null != selectedComment.getArabicSelectedBound()){
			arabicselectedText = getArabicSelectedText();
			if (null!=arabicselectedText) {
				String escaped = arabicselectedText.replace("[", "\\[").replace("]", "\\]").replace("’","\\’").replace(")", "\\)").replace("(", "\\(");
				String pattern = ">[^<]*?("+escaped+")[^>]*?<";
				Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
				arabictextarea = selectedPericope.getAtext().getContent().replaceAll(" +", " ");

				Matcher m = p.matcher(arabictextarea);
				if (m.find()) {
					int start = m.start(1);
					int end = m.end(1);
					String hi = "<span class='highlight'>";
					StringBuffer sbArabictextarea = new StringBuffer(arabictextarea);
					sbArabictextarea.insert(start, hi);

					sbArabictextarea.insert(end + hi.length(), "</span>");

					arabictextarea = sbArabictextarea.toString();
				}
			} else {
				System.err.println("CommentBean.viewSelectedComment(): arabicselectedText is null!!!");
			}
		}

		if(null != selectedComment.getCommentText()){
			commentvalue = selectedComment.getCommentText();
		}

		if(null != selectedComment.getType()){
			commenttype = selectedComment.getType();
		}

		return "";
	}

	public String removeComments(){
		managerBean.removeComments(selectedPericope.getId());
		//System.out.println("remove comments da commentBean");
		return "";
	}

	
	public String selectedPericopeByRef () { 
		
		if (pericopeRef != null) {
			setSelectedPericope(managerBean.getPericopeByRef(pericopeRef));
		}
		return "controlPanelComment.xhmtl";
		
	}
	
	/**
	 * @return the type
	 */
	//	public String[] getType() {
	//		return type;
	//	}

	/**
	 * @param type the type to set
	 */
	//	public void setType(String[] type) {
	//		this.type = type;
	//	}

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



	/**
	 * @return the greektext
	 */
	public String getGreektext() {
		return greektext;
	}

	/**
	 * @param greektext the greektext to set
	 */
	public void setGreektext(String greektext) {
		this.greektext = greektext;
	}

	/**
	 * @return the arabictext
	 */
	public String getArabictext() {
		return arabictext;
	}

	/**
	 * @param arabictext the arabictext to set
	 */
	public void setArabictext(String arabictext) {
		this.arabictext = arabictext;
	}

	/**
	 * @return the greekselectedText
	 */
	public String getGreekselectedText() {
		return greekselectedText;
	}

	/**
	 * @param greekselectedText the greekselectedText to set
	 */
	public void setGreekselectedText(String greekselectedText) {
		this.greekselectedText = greekselectedText;
	}

	/**
	 * @return the arabicselectedText
	 */
	public String getArabicselectedText() {
		return arabicselectedText;
	}

	/**
	 * @param arabicselectedText the arabicselectedText to set
	 */
	public void setArabicselectedText(String arabicselectedText) {
		this.arabicselectedText = arabicselectedText;
	}

	/**
	 * @return the greektextarea
	 */
	public String getGreektextarea() {
		return greektextarea;
	}

	/**
	 * @param greektextarea the greektextarea to set
	 */
	public void setGreektextarea(String greektextarea) {
		this.greektextarea = greektextarea;
	}

	/**
	 * @return the arabictextarea
	 */
	public String getArabictextarea() {
		return arabictextarea;
	}

	/**
	 * @param arabictextarea the arabictextarea to set
	 */
	public void setArabictextarea(String arabictextarea) {
		this.arabictextarea = arabictextarea;
	}

	/**
	 * @return the commentvalue
	 */
	public String getCommentvalue() {
		return commentvalue;
	}

	/**
	 * @param commentvalue the commentvalue to set
	 */
	public void setCommentvalue(String commentvalue) {
		this.commentvalue = commentvalue;
	}

	/**
	 * @return the commenttype
	 */
	public String getCommenttype() {
		return commenttype;
	}

	/**
	 * @param commenttype the commenttype to set
	 */
	public void setCommenttype(String commenttype) {
		this.commenttype = commenttype;
	}

	/**
	 * @return the selectedPericope
	 */
	public Pericope getSelectedPericope() {
		return selectedPericope;
	}

	/**
	 * @param selectedPericope the selectedPericope to set
	 */
	public void setSelectedPericope(Pericope selectedPericope) {
		this.selectedPericope = selectedPericope;
		greektextarea = selectedPericope.getBtext().getContent();
		arabictextarea = selectedPericope.getAtext().getContent();
		clearValue();
	}

	/**
	 * @return the managerBean
	 */
	public ManagerBean getManagerBean() {
		return managerBean;
	}

	/**
	 * @param managerBean the managerBean to set
	 */
	public void setManagerBean(ManagerBean managerBean) {
		this.managerBean = managerBean;
	}

	/**
	 * @return the selectedComment
	 */
	public Comment getSelectedComment() {
		return selectedComment;
	}

	/**
	 * @param selectedComment the selectedComment to set
	 */
	public void setSelectedComment(Comment selectedComment) {
		this.selectedComment = selectedComment;
		//System.out.println("ho settato il comment: " + selectedComment.getCommentText());
	}

	/**
	 * @return the greekselectedBound
	 */
	public List<Integer> getGreekselectedBound() {
		return greekselectedBound;
	}

	/**
	 * @param greekselectedBound the greekselectedBound to set
	 */
	public void setGreekselectedBound(List<Integer> greekselectedBound) {
		this.greekselectedBound = greekselectedBound;
	}

	/**
	 * @return the arabicselectedBound
	 */
	public List<Integer> getArabicselectedBound() {
		return arabicselectedBound;
	}

	/**
	 * @param arabicselectedBound the arabicselectedBound to set
	 */
	public void setArabicselectedBound(List<Integer> arabicselectedBound) {
		this.arabicselectedBound = arabicselectedBound;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the options
	 */
	public List<SelectItem> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(List<SelectItem> options) {
		this.options = options;
	}

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

}
