/*
 * 
 * TODO Eliminare completamente il bean in quanto sostituito da CommentBean
 * 
 */

package it.cnr.ilc.ga.action.user;

import it.cnr.ilc.ga.model.pericope.Pericope;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.SessionScoped;
//import javax.faces.component.UIInput;
//import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


@ManagedBean
@SessionScoped
public class TextBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2027200486422067009L;
	//private String greek = "Εἰ δέ ἐστιν ἀθάνατος ἕκαστος ἡμῶν, ἢ φθείρεται¶ πᾶς,";
	//private String arabic = "إنا نريد أن نعلم هل الإنسان بأسره كلّه واقعٌ تحت الفساد والفناء؟";
	//private HttpServletRequest request;
	
	private String greek = "Εἰ δέ ἐστιν ἀθάνατος ἕκαστος ἡμῶν, ἢ φθείρεται¶ πᾶς,";
	private String arabic = "إنا نريد أن نعلم هل الإنسان بأسره كلّه واقعٌ تحت الفساد والفناء؟";
	
	/*
	private UIInput greektext = new UIInput();
	private UIInput arabictext = new UIInput();
	
	private UIOutput greekselectedText = new UIOutput();
	private UIOutput arabicselectedText = new UIOutput();
	
	private UIInput greektextarea = new UIInput();
	private UIInput arabictextarea = new UIInput();
	*/
	
	
	private String greektext;
	private String arabictext;
	
	private String greekselectedText;
	private String arabicselectedText;
	
	private String greektextarea = new String(greek);
	private String arabictextarea = new String(arabic);
	
	private String commentvalue = null;
	private int commenttype;
	
	private Pericope selectedPericope;
	
	
	public TextBean(){ // TODO Eliminare completamente il bean in quanto sostituito da CommentBean
		//
		//this.greektextarea.setValue(greek);
		//this.arabictextarea.setValue(arabic);
		//this.greektext.setValue(new String());
		//this.arabictext.setValue(new String());
		//this.greekselectedText.setValue(new String());
		//this.arabicselectedText.setValue(new String());
		//this.greekselectedText.setRendered(true);
		//this.arabicselectedText.setRendered(true);
//		request= (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		try{
//			request.setCharacterEncoding("UTF-8");
//		}catch(Exception ex){
//			//
//		}
		System.out.println("costruttore di text bean");
		//greektext = "gr";
		//arabictext = "ar";
		//greekselectedText = "grrrr";
		//arabicselectedText = "arrrr";
		
	}

	public String greekclick(){
		System.out.println("greek pre --> " + greektext);
		greekselectedText = greektext;
		System.out.println("greek clicked --> " + greekselectedText);
		System.out.println("selected pericope: " + selectedPericope.getBtext().getContent());
		return "";
	}
	
	public String arabicclick(){
		System.out.println("arabic pre --> " + arabictext);
		arabicselectedText = arabictext;
		System.out.println("arabic clicked --> " + arabicselectedText);
		System.out.println("selected pericope: " + selectedPericope.getAtext().getContent());
		return "";
	}
	
	
//	private void processSelectedText(){
		
		//String selection = (String)text.getValue();
		/*String selection = (String)textarea.getLocalValue();
		String selection1 = " "; //(String)textarea.getSubmittedValue();
		StringBuilder selectionall = new StringBuilder();
		selectionall.append(selection);
		selectionall.append(" || ");
		selectionall.append(selection1);*/
		//this.selectedText.setValue(this.text.getValue());
//	}
	

//	public UIInput getGreektextarea() {
//		return greektextarea;
//	}

//	public void setGreektextarea(UIInput greektextarea) {
//		this.greektextarea = greektextarea;
//	}
	
//	public UIInput getArabictextarea() {
//		return arabictextarea;
//	}

//	public void setArabictextarea(UIInput arabictextarea) {
//		this.arabictextarea = arabictextarea;
//	}

//	public UIInput getGreektext() {
//		return greektext;
//	}

//	public void setGreektext(UIInput greektext) {
//		this.greektext = greektext;
//	}

//	public UIInput getArabictext() {
//		return arabictext;
//	}

//	public void setArabictext(UIInput arabictext) {
//		this.arabictext = arabictext;
//	}

//	public UIOutput getGreekselectedText() {
//		return greekselectedText;
//	}

//	public void setGreekselectedText(UIOutput greekselectedText) {
//		this.greekselectedText = greekselectedText;
//	}

//	public UIOutput getArabicselectedText() {
//		return arabicselectedText;
//	}

//	public void setArabicselectedText(UIOutput arabicselectedText) {
//		this.arabicselectedText = arabicselectedText;
//	}

	public String getCommentvalue() {
		return commentvalue;
	}

	public void setCommentvalue(String commentvalue) {
		this.commentvalue = commentvalue;
	}

	public int getCommenttype() {
		return commenttype;
	}

	public void setCommenttype(int commenttype) {
		this.commenttype = commenttype;
	}

	public String getValue() {
		return commentvalue + String.valueOf(commenttype) + greekselectedText + arabicselectedText;
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
		System.out.println("setPericopeSelected");
		greektextarea = selectedPericope.getBtext().getContent();
		arabictextarea = selectedPericope.getAtext().getContent();
		System.out.println(greektextarea);
		System.out.println(arabictextarea);
	}

	
	
}
