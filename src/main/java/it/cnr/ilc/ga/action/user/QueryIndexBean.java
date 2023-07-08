/**
 * 
 */
package it.cnr.ilc.ga.action.user;

import it.cnr.ilc.ga.action.management.ManagerBean;
import it.cnr.ilc.ga.action.management.QueryManagerBean;
import it.cnr.ilc.ga.model.indexsearch.IndexEntry;
import it.cnr.ilc.ga.model.indexsearch.SearchResult;
import it.cnr.ilc.ga.model.pericope.Text;
import it.cnr.ilc.ga.utils.Regex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * @author Angelo Del Grosso
 *
 */
@ManagedBean
@SessionScoped
public class QueryIndexBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3888652191048852176L;

	@ManagedProperty(value="#{qmanager}")
	private QueryManagerBean queryManagerBean;
	
	@ManagedProperty(value="#{manager}")
	private ManagerBean managerBean;
	
	/**
	 * 
	 */
	private String operator = "false"; //Operatore tra le due QueryLang
	private String opTypegr = "false"; // AND OR NEAR GRECO
	private String opTypear = "false"; // AND OR NEAR ARABO
	//private String orderedgr; // TRUE FALSE, indica se mantenere o meno l'ordine delle parole cercate
	
	private String termType0gr = "form";
	private String termType1gr = "form";
	private String termType2gr = "form";
	
	private String termValue0gr = "x";
	private String termValue1gr = "x";
	private String termValue2gr = "x";
	
	private String pos0gr = "ANY";
	private String pos1gr = "ANY";
	private String pos2gr = "ANY";
	
	private String termType0ar = "form";
	private String termType1ar = "form";
	private String termType2ar = "form";
	
	private String termValue0ar = "x";
	private String termValue1ar = "x";
	private String termValue2ar = "x";
	
	private String pos0ar = "ANY";
	private String pos1ar = "ANY";
	private String pos2ar = "ANY";
	
	private String numTermgr = "0";
	private String numTermar = "1";
	
	// TODO Arabo parameters
	
	private String results = " ";
	private ArrayList<SearchResult> listOfRefs = null;

	private String[] words = {"word1", "word2", "word3","word4", "word5"};
	private String[] freqs = {"35", "27","19","5","1"};
	
	private String selectedWord;
	
	public QueryIndexBean() {
		System.err.println("nel queryIndexBean");
		//System.err.println(queryManagerBean.toString());
	}
	
	//FIXME indice per Saussure
	public ArrayList<IndexEntry> getIndex(){
		return managerBean.getIndexItemSet().getindex(Text.LangType.FRENCH);
	}
	
	public ArrayList<IndexEntry> getArindex(){
		//System.out.println("in index");
		return managerBean.getIndexItemSet().getLemmaIndexListAr();
	}
	
	public ArrayList<IndexEntry>getFrindex(){
		System.err.println("in indice francese");
		return managerBean.getIndexItemSet().getindex(Text.LangType.FRENCH);
	}
	
	public ArrayList<IndexEntry>getFrcindex(){
		System.err.println("in indice Forma ricostruita");
		return managerBean.getIndexItemSet().getindex(Text.LangType.TERME_RECONSTRUIT);
	}
	
	public ArrayList<IndexEntry> getGrindex(){
		System.out.println("in indice Greco");
		return managerBean.getIndexItemSet().getindex(Text.LangType.GREEK);
	}
	
	public ArrayList<IndexEntry> getLatindex(){
		System.out.println("in indice Latino");
		return managerBean.getIndexItemSet().getindex(Text.LangType.LATIN);
	}
	
	public ArrayList<IndexEntry> getSnindex(){
		System.out.println("in indice Sanscrito");
		return managerBean.getIndexItemSet().getindex(Text.LangType.SANSCRIT);
	}
	
	public ArrayList<IndexEntry> getGeindex(){
		System.out.println("in indice Tedesco");
		return managerBean.getIndexItemSet().getindex(Text.LangType.GERMAN);
	}
	
	public ArrayList<IndexEntry> getItindex(){
		System.out.println("in indice Italiano");
		return managerBean.getIndexItemSet().getindex(Text.LangType.ITALIEN);
	}
	
	
	public String searchOnClick () {
		
		if(termValue0gr==null||"".equals(termValue0gr)){
			numTermgr="0";
		}else if(termValue1gr==null||"".equals(termValue1gr)){
			numTermgr="1";
		}else if(termValue2gr==null||"".equals(termValue2gr)){
			numTermgr="2";
		}else{
			numTermgr="3";
		}

		if(termValue0ar==null||"".equals(termValue0ar)){
			numTermar="0";
		}else if(termValue1ar==null||"".equals(termValue1ar)){
			numTermar="1";
		}else if(termValue2ar==null||"".equals(termValue2ar)){
			numTermar="2";
		}else{
			numTermar="3";
		}

		termValue0ar = this.getSelectedWord();
		termType0ar = "form";
		
		results = queryManagerBean.search(
				numTermgr,
				numTermar,
				
				termType0gr,
				termValue0gr,
				pos0gr,
				
				termType1gr,
				termValue1gr,
				pos1gr,
				
				termType2gr,
				termValue2gr,
				pos2gr,
				
				termType0ar,
				termValue0ar,
				pos0ar,
				
				termType1ar,
				termValue1ar,
				pos1ar,
				
				termType2ar,
				termValue2ar,
				pos2ar,
				
				opTypegr,
				opTypear,
				operator
				
				);
		
		//results = queryManagerBean.search("1","0","form","σῶμα","ANY","form","x","ANY","form","x","ANY","form","x","ANY","form","x","ANY","form","x","ANY","false","false","false");
		//System.err.println("da eXist (" + results+")");
		
		
		//creazione di results come lista di stringhe
		//<div class="bold">f. 51r</div>
		markOccurrences();
		prepareResultsForSearch();
		//System.err.println("stringa evidenziata (" + results+")");
		
		return "controlPanelSearch.xhtml"; 
	}
	
	
	public String search(){

            
		if(termValue0gr==null||"".equals(termValue0gr)){
			numTermgr="0";
		}else if(termValue1gr==null||"".equals(termValue1gr)){
			numTermgr="1";
		}else if(termValue2gr==null||"".equals(termValue2gr)){
			numTermgr="2";
		}else{
			numTermgr="3";
		}
		
		if(termValue0ar==null||"".equals(termValue0ar)){
			numTermar="0";
		}else if(termValue1ar==null||"".equals(termValue1ar)){
			numTermar="1";
		}else if(termValue2ar==null||"".equals(termValue2ar)){
			numTermar="2";
		}else{
			numTermar="3";
		}

		printStatusGr();
		System.out.println("--^-- greek" + " --v-- arabic");
		printStatusAr();
		System.out.println("termini di ricerca greca: " + numTermgr + " termini di ricerca araba: " + numTermar + " operatore generale: " + operator);
		
		results = queryManagerBean.search(
				numTermgr,
				numTermar,
				
				termType0gr,
				termValue0gr,
				pos0gr,
				
				termType1gr,
				termValue1gr,
				pos1gr,
				
				termType2gr,
				termValue2gr,
				pos2gr,
				
				termType0ar,
				termValue0ar,
				pos0ar,
				
				termType1ar,
				termValue1ar,
				pos1ar,
				
				termType2ar,
				termValue2ar,
				pos2ar,
				
				opTypegr,
				opTypear,
				operator
				
				);

		
		markOccurrences();
		prepareResultsForSearch();
		
		
		return ""; // TODO cambiare pagina di ritorno
	}
	
	
	private void markOccurrences () {
		String repl = "<span style='background-color:yellow'>$1</span>";
		System.err.println("escapato lo *? " + termValue0ar);
		String regex ="\\b([\\*\\-\\.;,]?"+termValue0ar.replace("*", "")+"[\\*\\-\\.;]?)\\b";
		results = results.replaceAll("]]>", "");
		results = Regex.repAll(regex, repl, results);
	}
	
	private void prepareResultsForSearch () {

		Pattern p = Pattern.compile("<div class=\"bold\">f\\. (.*?)<\\/div>(.+)");
		
		String[] splittedResult = results.split("<hr />");
		
		listOfRefs = new ArrayList<SearchResult>();
		for (String split : splittedResult) {
			Matcher m = p.matcher(split);
			while ( m.find()) {
				String ref  = m.group(1).trim();
				String text = m.group(2).trim();
				listOfRefs.add(new SearchResult(ref, text));
				//TODO fare una lista di oggetti contenenti i refs e le pericope associate!!!!
			}
		}

		
	}
	
	
//	public String searchAr(){
//		System.out.println("searchAr");
//		results = queryManagerBean.searchAr(termValue0ar);
//		return "searchIndex.xhtml"; //TODO cambiare pagina di ritorno
//	}
	
	public String grclear(){
		
		termType0gr = "";
		termType1gr = "";
		termType2gr = "";
		
		termValue0gr = "";
		termValue1gr = "";
		termValue2gr = "";
		
		pos0gr = "";
		pos1gr = "";
		pos2gr = "";
		
		opTypegr = "false";
		
		return "";
	}
	
	public String arclear(){
		termType0ar = "";
		termType1ar = "";
		termType2ar = "";
		
		termValue0ar = "";
		termValue1ar = "";
		termValue2ar = "";
		
		pos0ar = "";
		pos1ar = "";
		pos2ar = "";
		
		opTypear = "false";
		
		
		return "";
	}
	
	public String printStatusGr(){
		
		System.out.println(termType0gr);
		System.out.println(termType1gr);
		System.out.println(termType2gr);
		
		System.out.println(termValue0gr);
		System.out.println(termValue1gr);
		System.out.println(termValue2gr);
		
		System.out.println(pos0gr);
		System.out.println(pos1gr);
		System.out.println(pos2gr);
		
		System.out.println(opTypegr);
		
		return "";
		
	}
	
	public String printStatusAr(){
		
		System.out.println(termType0ar);
		System.out.println(termType1ar);
		System.out.println(termType2ar);
		
		System.out.println(termValue0ar);
		System.out.println(termValue1ar);
		System.out.println(termValue2ar);
		
		System.out.println(pos0ar);
		System.out.println(pos1ar);
		System.out.println(pos2ar);
		
		System.out.println(opTypear);
		
		return "";
		
	}
	
	/**
	 * @return the queryManagerBean
	 */
	public QueryManagerBean getQueryManagerBean() {
		return queryManagerBean;
	}

	/**
	 * @param queryManagerBean the queryManagerBean to set
	 */
	public void setQueryManagerBean(QueryManagerBean queryManagerBean) {
		this.queryManagerBean = queryManagerBean;
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
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the opTypegr
	 */
	public String getOpTypegr() {
		return opTypegr;
	}

	/**
	 * @param opTypegr the opTypegr to set
	 */
	public void setOpTypegr(String opTypegr) {
		this.opTypegr = opTypegr;
	}


	/**
	 * @return the termType0gr
	 */
	public String getTermType0gr() {
		return termType0gr;
	}

	/**
	 * @param termType0gr the termType0gr to set
	 */
	public void setTermType0gr(String termType0gr) {
		this.termType0gr = termType0gr;
		System.err.println(termType0gr);
	}

	/**
	 * @return the termType1gr
	 */
	public String getTermType1gr() {
		return termType1gr;
	}

	/**
	 * @param termType1gr the termType1gr to set
	 */
	public void setTermType1gr(String termType1gr) {
		this.termType1gr = termType1gr;
	}

	/**
	 * @return the termType2gr
	 */
	public String getTermType2gr() {
		return termType2gr;
	}

	/**
	 * @param termType2gr the termType2gr to set
	 */
	public void setTermType2gr(String termType2gr) {
		this.termType2gr = termType2gr;
	}

	/**
	 * @return the termValue0gr
	 */
	public String getTermValue0gr() {
		return termValue0gr;
	}

	/**
	 * @param termValue0gr the termValue0gr to set
	 */
	public void setTermValue0gr(String termValue0gr) {
		this.termValue0gr = termValue0gr;
		System.out.println(termValue0gr);
	}

	/**
	 * @return the termValue1gr
	 */
	public String getTermValue1gr() {
		return termValue1gr;
	}

	/**
	 * @param termValue1gr the termValue1gr to set
	 */
	public void setTermValue1gr(String termValue1gr) {
		this.termValue1gr = termValue1gr;
	}

	/**
	 * @return the termValue2gr
	 */
	public String getTermValue2gr() {
		return termValue2gr;
	}

	/**
	 * @param termValue2gr the termValue2gr to set
	 */
	public void setTermValue2gr(String termValue2gr) {
		this.termValue2gr = termValue2gr;
	}

	/**
	 * @return the pos0gr
	 */
	public String getPos0gr() {
		return pos0gr;
	}

	/**
	 * @param pos0gr the pos0gr to set
	 */
	public void setPos0gr(String pos0gr) {
		this.pos0gr = pos0gr;
	}

	/**
	 * @return the pos1gr
	 */
	public String getPos1gr() {
		return pos1gr;
	}

	/**
	 * @param pos1gr the pos1gr to set
	 */
	public void setPos1gr(String pos1gr) {
		this.pos1gr = pos1gr;
	}

	/**
	 * @return the pos2gr
	 */
	public String getPos2gr() {
		return pos2gr;
	}

	/**
	 * @param pos2gr the pos2gr to set
	 */
	public void setPos2gr(String pos2gr) {
		this.pos2gr = pos2gr;
	}
	
	/**
	 * @return the orderedgr
	 */
//	public String getOrderedgr() {
//		return orderedgr;
//	}

	/**
	 * @param orderedgr the orderedgr to set
	 */
//	public void setOrderedgr(String orderedgr) {
//		this.orderedgr = orderedgr;
//	}

	/**
	 * @return the results
	 */
	public String getResults() {
		return results;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(String results) {
		this.results = results;
	}

	/**
	 * @return the termValue0ar
	 */
	public String getTermValue0ar() {
		return termValue0ar;
	}

	/**
	 * @param termValue0ar the termValue0ar to set
	 */
	public void setTermValue0ar(String termValue0ar) {
		this.termValue0ar = termValue0ar;
	}

	/**
	 * @return the worlds
	 */
	public String[] getWords() {
		return words;
	}

	/**
	 * @param worlds the worlds to set
	 */
	public void setWords(String[] words) {
		this.words = words;
	}

	/**
	 * @return the freqs
	 */
	public String[] getFreqs() {
		return freqs;
	}

	/**
	 * @param freqs the freqs to set
	 */
	public void setFreqs(String[] freqs) {
		this.freqs = freqs;
	}

	/**
	 * @return the selectedWord
	 */
	public String getSelectedWord() {
		return selectedWord;
	}

	/**
	 * @param selectedWord the selectedWord to set
	 */
	public void setSelectedWord(String selectedWord) {
		this.selectedWord = selectedWord;
	}

	/**
	 * @return the opTypear
	 */
	public String getOpTypear() {
		return opTypear;
	}


	/**
	 * @param opTypear the opTypear to set
	 */
	public void setOpTypear(String opTypear) {
		this.opTypear = opTypear;
	}


	/**
	 * @return the termType0ar
	 */
	public String getTermType0ar() {
		return termType0ar;
	}


	/**
	 * @param termType0ar the termType0ar to set
	 */
	public void setTermType0ar(String termType0ar) {
		this.termType0ar = termType0ar;
	}


	/**
	 * @return the termType1ar
	 */
	public String getTermType1ar() {
		return termType1ar;
	}


	/**
	 * @param termType1ar the termType1ar to set
	 */
	public void setTermType1ar(String termType1ar) {
		this.termType1ar = termType1ar;
	}


	/**
	 * @return the termType2ar
	 */
	public String getTermType2ar() {
		return termType2ar;
	}


	/**
	 * @param termType2ar the termType2ar to set
	 */
	public void setTermType2ar(String termType2ar) {
		this.termType2ar = termType2ar;
	}


	/**
	 * @return the termValue1ar
	 */
	public String getTermValue1ar() {
		return termValue1ar;
	}


	/**
	 * @param termValue1ar the termValue1ar to set
	 */
	public void setTermValue1ar(String termValue1ar) {
		this.termValue1ar = termValue1ar;
	}


	/**
	 * @return the termValue2ar
	 */
	public String getTermValue2ar() {
		return termValue2ar;
	}


	/**
	 * @param termValue2ar the termValue2ar to set
	 */
	public void setTermValue2ar(String termValue2ar) {
		this.termValue2ar = termValue2ar;
	}


	/**
	 * @return the pos0ar
	 */
	public String getPos0ar() {
		return pos0ar;
	}


	/**
	 * @param pos0ar the pos0ar to set
	 */
	public void setPos0ar(String pos0ar) {
		this.pos0ar = pos0ar;
	}


	/**
	 * @return the pos1ar
	 */
	public String getPos1ar() {
		return pos1ar;
	}


	/**
	 * @param pos1ar the pos1ar to set
	 */
	public void setPos1ar(String pos1ar) {
		this.pos1ar = pos1ar;
	}


	/**
	 * @return the pos2ar
	 */
	public String getPos2ar() {
		return pos2ar;
	}


	/**
	 * @param pos2ar the pos2ar to set
	 */
	public void setPos2ar(String pos2ar) {
		this.pos2ar = pos2ar;
	}


	/**
	 * @return the numTermgr
	 */
	public String getNumTermgr() {
		return numTermgr;
	}


	/**
	 * @param numTermgr the numTermgr to set
	 */
	public void setNumTermgr(String numTermgr) {
		this.numTermgr = numTermgr;
	}


	/**
	 * @return the numTermar
	 */
	public String getNumTermar() {
		return numTermar;
	}


	/**
	 * @param numTermar the numTermar to set
	 */
	public void setNumTermar(String numTermar) {
		this.numTermar = numTermar;
	}


	/**
	 * @return the listOfRefs
	 */
	public ArrayList<SearchResult> getListOfRefs() {
		return listOfRefs;
	}

	/**
	 * @param listOfRefs the listOfRefs to set
	 */
	public void setListOfRefs(ArrayList<SearchResult> listOfRefs) {
		this.listOfRefs = listOfRefs;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		QueryManagerBean qumbean = new QueryManagerBean();
		QueryIndexBean qibean = new QueryIndexBean();
		qibean.setQueryManagerBean(qumbean);
		qibean.search();
	}


}
