/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;

import it.cnr.ilc.ga.model.analysis.Analysis;
import it.cnr.ilc.ga.model.analysis.Form;
import it.cnr.ilc.ga.model.analysis.Lemma;
import it.cnr.ilc.ga.model.analysis.LinguisticUnit;
import it.cnr.ilc.ga.model.analysis.Root;
import it.cnr.ilc.ga.model.pericope.Pericope;
import it.cnr.ilc.ga.model.pericope.Text;
import it.cnr.ilc.ga.model.pericope.Text.LangType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.icu.text.Collator;
import com.ibm.icu.util.ULocale;

/**
 * @author Emiliano
 * 
 * This class contains the lists of all linguistic units, forms, lemmas and roots appearing in the pericopeSet of reference
 *
 */
public class IndexItemSet{

	//FIXME HashMap con la chiave la lingua e il lavore la lilsta delle parole ad essa riferita memorizzata come un indice inverso
	// la chiave della HashMap è la lingua, mentre la chiave della TreeMap è la forma da indicizzare. L'Integer è la frequenza della forma attestata
	private HashMap<LangType, TreeMap<String, Integer>> indices;

	/**
	 * @return the indices
	 */
	public HashMap<LangType, TreeMap<String, Integer>> getIndices() {
		return indices;
	}



	/**
	 * @param indices the indices to set
	 */
	public void setIndices(HashMap<LangType, TreeMap<String, Integer>> indices) {
		this.indices = indices;
	}




	private ArrayList<LinguisticUnit> linguisticUnitSetAr;
	private ArrayList<LinguisticUnit> linguisticUnitSetGr;
	
	private TreeMap<String,Integer> formSetAr;
	private TreeMap<String,Integer> formSetGr;
	private TreeMap<String,Integer> lemmaSetAr;
	private TreeMap<String,Integer> lemmaSetGr;
	private TreeMap<String,Integer> rootSet;

	private ArrayList<IndexEntry> formIndexListAr;
	private ArrayList<IndexEntry> formIndexListGr;
	private ArrayList<IndexEntry> lemmaIndexListAr;
	private ArrayList<IndexEntry> lemmaIndexListGr;
	private ArrayList<IndexEntry> rootIndexList;


	/**
	 * 
	 */
	public IndexItemSet() {

		//FIXME inizializzazione indices
		indices = new HashMap<LangType, TreeMap<String,Integer>>(1000);

		linguisticUnitSetAr = new ArrayList<LinguisticUnit>(10000); 
		linguisticUnitSetGr = new ArrayList<LinguisticUnit>(10000); 
		formSetAr = new TreeMap<String, Integer>();
		formSetGr = new TreeMap<String, Integer>();
		lemmaSetAr = new TreeMap<String, Integer>();
		lemmaSetGr = new TreeMap<String, Integer>(
				Collator.getInstance(new ULocale("grc")));
		rootSet = new TreeMap<String, Integer>();

		formIndexListAr = new ArrayList<IndexEntry>();
		formIndexListGr = new ArrayList<IndexEntry>();
		lemmaIndexListAr = new ArrayList<IndexEntry>();
		lemmaIndexListGr = new ArrayList<IndexEntry>();
		rootIndexList = new ArrayList<IndexEntry>();


	}



	public void addLinguisticUnitAr(LinguisticUnit unit) {
		linguisticUnitSetAr.add(unit);
	}

	public void addLinguisticUnitGr(LinguisticUnit unit) {
		linguisticUnitSetGr.add(unit);
	}


	public void buildIndexes(PericopeSet pSet) {
		ArrayList<Pericope> pericopeSet = pSet.getPericopeSet();
		Text arabicText;
		Text greekText;
		Form form;
		Lemma lemma;
		Root root;
		String formValue;
		String lemmaValue;
		String rootValue;

		Analysis analysisAr;
		Analysis analysisGr;

		List<LinguisticUnit> luListAr;
		List<LinguisticUnit> luListGr;
		int value = 0;
		Pattern stripPunct = Pattern.compile("[\\[\u0640\u0660\u066c\u066d\u0021\u003A\u060c\u061b\u061f]");
		//for every pericope of the pericopeSet...

		//FIXME costruzione degli indici lingua/valore introdotti in Saussure
		Text text;
		Form _form;
		Lemma _lemma;
		Root _root;
		String _formValue;
		String _lemmaValue;
		String _rootValue;
		Analysis analysis;
		List<LinguisticUnit> linguisticUnits;

		for (Pericope pericope : pericopeSet) {
			arabicText = pericope.getAtext();
			analysisAr = arabicText.getAnalysis();
			luListAr = analysisAr.getAnalysis();
			// FIXME introdotto con Saussure	
			text = pericope.getAtext();
			analysis = text.getAnalysis();
			linguisticUnits = analysis.getAnalysis();

			//FIXME intrudotto for sulla linguisticUnits per Saussure
			String loc = "fr";
			LangType lang = Text.LangType.FRENCH;
			for(LinguisticUnit l : linguisticUnits){
				if(l!=null){
					//System.err.println("IndexItem(): Costruzione indice delle lingue");
					lang = l.getLanguage();
					//System.err.println(lang.toString());
					if(!indices.containsKey(lang)){
						indices.put(lang, new TreeMap<String, Integer>(Collator.getInstance(new ULocale(loc))));
						System.err.println("BuildIndex: creo struttura per la lingua " + lang.toString());
					}
					_form = l.getForm();
					_formValue = _form.getValue();
					//System.err.print("BuidIndex leggo forma e lingua: " + _formValue + " : ");
					//System.err.println(lang);
					if(_formValue.length()>0){
						if (!_formValue.startsWith("–")) {
							Matcher mat = stripPunct.matcher(_formValue);
							_formValue = mat.replaceAll("");
						} else {
							System.err.println("scarto " + _formValue);
							continue;
						}
					}
					
					if(!indices.get(lang).containsKey(_formValue)){
						//System.err.println("BuildIndex: creo struttura per il termine " + _formValue + " nella lingua " + lang.toString());
						indices.get(lang).put(_formValue, new Integer(1));
					}
					else{
						value = (indices.get(lang).get(_formValue)).intValue();
						value = value+1;
						indices.get(lang).put(_formValue, new Integer(value));
					}
					
					
				}
			}
			
//			List<IndexEntry> provaIndice = treeMap2Array(indices.get(Text.LangType.FRENCH), false);
//			for (IndexEntry indexEntry : provaIndice) {
//				System.err.print(indexEntry.getValue() + " : ");
//				System.err.println(indexEntry.getFreq());
//			}
			//FIXME fine del ciclo sulla linguistic unit del testo?
			
			//for every linguistic unit belonging to the analyzed arabic text of the current pericope...

			for (LinguisticUnit lu : luListAr) {
				if (lu != null) {
					addLinguisticUnitAr(lu);
					//insert the form in the formSetAr, if already existing, increment its value (representing the frequency) by 1
					form = lu.getForm(); 
					formValue = form.getValue();
					if (formValue.length() >0) {
						Matcher mat = stripPunct.matcher(formValue);

						formValue = mat.replaceAll("");

						/*int i = formValue.indexOf('،');
					if (i!=-1){
						System.out.println("tolgo la virgola");
						formValue = formValue.substring(0, i);

					}*/
						if (!formSetAr.containsKey(formValue)) {
							formSetAr.put(formValue, new Integer(1));
						}
						else {
							value = (formSetAr.get(formValue)).intValue();
							value++;
							formSetAr.put(formValue, new Integer(value));
						}
					}
					//insert the lemma in the lemmaSetAr, if already existing, increment its value (representing the frequency) by 1 
					//TODO gestire lemmi multipli e vuoti
					lemma = lu.getLemma();
					lemmaValue = lemma.getValue();
					if(lemmaValue.contains(" ")){
						//lemmaValue = lemmaValue.split(" ")[0];
					}
					if("".equals(lemmaValue))
						lemmaValue = " ";
					if (!lemmaSetAr.containsKey(lemmaValue)) {
						lemmaSetAr.put(lemmaValue, new Integer(1));
					}
					else {
						value = (lemmaSetAr.get(lemmaValue)).intValue();
						value++;
						lemmaSetAr.put(lemmaValue, new Integer(value));
					}
					//insert the root in the rootSet, if already existing, increment its value (representing the frequency) by 1
					root = lu.getRoot();
					rootValue = root.getValue();
					if (!rootSet.containsKey(rootValue)) {
						rootSet.put(rootValue, new Integer(1));
					}
					else {
						value = (rootSet.get(rootValue)).intValue();
						value++;
						rootSet.put(rootValue, new Integer(value));
					}
				}
			}

			greekText = pericope.getBtext();
			analysisGr = greekText.getAnalysis();
			luListGr = analysisGr.getAnalysis();

			//for every linguistic unit belonging to the analyzed greek text of the current pericope... 
			for (LinguisticUnit lu : luListGr) {
				if (lu != null) {
					addLinguisticUnitGr(lu);
					//insert the form in the formSetGr, if already existing, increment its value by one (the frequency)
					form = lu.getForm();
					formValue = form.getValue();

					if (!formSetGr.containsKey(formValue)) {
						formSetGr.put(formValue, new Integer(1));
					}
					else {
						value = (formSetGr.get(formValue)).intValue();
						value++;
						formSetGr.put(formValue, new Integer (value));
					}

					//insert the lemma in the lemmaSetGr, if already existing, increment its value by one (the frequency)
					lemma = lu.getLemma();
					lemmaValue = lemma.getValue().split(" ")[0];
					//TODO controllare valore vuoto della stringa lemma
					if("".equals(lemmaValue))
						lemmaValue = " ";
					if(lemmaValue.charAt(0)!='*'){
						if (!lemmaSetGr.containsKey(lemmaValue)) {
							lemmaSetGr.put(lemmaValue, new Integer(1));
						}
						else {
							value = (lemmaSetGr.get(lemmaValue)).intValue();
							value++;
							lemmaSetGr.put(lemmaValue, new Integer(value));
						}
					}

				}
			}
		}	

		//System.err.println("linguisticUnitSetAr.size() --> "+linguisticUnitSetAr.size());
		//System.err.println("linguisticUnitSetGr.size() --> "+linguisticUnitSetGr.size());

		//System.err.println("formSetAr.size() --> "+formSetAr.size());
		//System.err.println("formSetGr.size() --> "+formSetGr.size());


		//populate the ordered list of forms, lemmas and roots
		//arabic
		formIndexListAr = treeMap2Array (formSetAr, false);
		lemmaIndexListAr = treeMap2Array(lemmaSetAr, false);
		rootIndexList = treeMap2Array(rootSet, true);
		
		
		//		for (String entry : formSetAr.descendingKeySet()) {
		//			Integer avalue = formSetAr.get(entry);
		//	        //System.err.println(entry + "\t" + value);
		//			formIndexListAr.add(new IndexEntry(entry, avalue.intValue()));
		//	    }
		//		for (String entry : lemmaSetAr.descendingKeySet()) {
		//			Integer avalue = lemmaSetAr.get(entry);
		//	        //System.err.println(entry + "\t" + value);
		//			lemmaIndexListAr.add(new IndexEntry(entry, avalue.intValue()));
		//	    }
		//		for (String entry : rootSet.descendingKeySet()) {
		//			Integer avalue = rootIndexList.get(entry);
		//	        //System.err.println(entry + "\t" + value);
		//			rootIndexList.add(new IndexEntry(entry, avalue.intValue()));
		//	    }

		//greek

		formIndexListGr = treeMap2Array(formSetGr, true);
		lemmaIndexListGr = treeMap2Array(lemmaSetGr, false);
		//		for (String entry : formSetGr.descendingKeySet()) {
		//			Integer avalue = formSetGr.get(entry);
		//	        //System.err.println(entry + "\t" + value);
		//			formIndexListGr.add(new IndexEntry(entry, avalue.intValue()));
		//	    }
		//		for (String entry : lemmaSetGr.descendingKeySet()) {
		//			Integer avalue = lemmaSetGr.get(entry);
		//	        //System.err.println(entry + "\t" + value);
		//			lemmaIndexListGr.add(new IndexEntry(entry, avalue.intValue()));
		//	    }

	}

	public ArrayList<IndexEntry> getindex(Text.LangType lang){
		System.err.println("IndexItemSet - getindex: " + lang.toString());
		try{
		return treeMap2Array(indices.get(lang),false);
		}catch (Exception e) {
			System.err.println("IndexItemSet - getindex: " + lang.toString() + "VUOTO");
			return new ArrayList<IndexEntry>();// TODO: handle exception
		}
	}
	
	public ArrayList<IndexEntry> treeMap2Array(TreeMap<String,Integer> tm, boolean reverse) {
		ArrayList<IndexEntry> al = new ArrayList<IndexEntry>();
		Set<String> ns; 
		if (reverse) {
			ns = tm.descendingKeySet();
		} else{
			ns = tm.keySet();
		}
		for (String entry : ns) {
			Integer avalue = tm.get(entry);
			//System.err.println(entry + "\t" + avalue);
			if((entry.length()>0)&& (!" ".equals(entry)) && (!"+".equals(entry)))
				al.add(new IndexEntry(entry, avalue.intValue()));

		}
		return al;
	}

	public ArrayList<LinguisticUnit> getLinguisticUnitSet() {
		return linguisticUnitSetAr;
	}




	public void setLinguisticUnitSet(ArrayList<LinguisticUnit> linguisticUnitSet) {
		this.linguisticUnitSetAr = linguisticUnitSet;
	}




	public ArrayList<LinguisticUnit> getLinguisticUnitSetAr() {
		return linguisticUnitSetAr;
	}




	public void setLinguisticUnitSetAr(ArrayList<LinguisticUnit> linguisticUnitSetAr) {
		this.linguisticUnitSetAr = linguisticUnitSetAr;
	}




	public ArrayList<LinguisticUnit> getLinguisticUnitSetGr() {
		return linguisticUnitSetGr;
	}




	public void setLinguisticUnitSetGr(ArrayList<LinguisticUnit> linguisticUnitSetGr) {
		this.linguisticUnitSetGr = linguisticUnitSetGr;
	}




	private TreeMap<String, Integer> getFormSetAr() {
		return formSetAr;
	}




	private void setFormSetAr(TreeMap<String, Integer> formSetAr) {
		this.formSetAr = formSetAr;
	}




	private TreeMap<String, Integer> getFormSetGr() {
		return formSetGr;
	}




	private void setFormSetGr(TreeMap<String, Integer> formSetGr) {
		this.formSetGr = formSetGr;
	}




	private TreeMap<String, Integer> getLemmaSetAr() {
		return lemmaSetAr;
	}




	private void setLemmaSetAr(TreeMap<String, Integer> lemmaSetAr) {
		this.lemmaSetAr = lemmaSetAr;
	}




	private TreeMap<String, Integer> getLemmaSetGr() {
		return lemmaSetGr;
	}




	private void setLemmaSetGr(TreeMap<String, Integer> lemmaSetGr) {
		this.lemmaSetGr = lemmaSetGr;
	}




	private TreeMap<String, Integer> getRootSet() {
		return rootSet;
	}




	private void setRootSet(TreeMap<String, Integer> rootSet) {
		this.rootSet = rootSet;
	}




	public ArrayList<IndexEntry> getFormIndexListAr() {
		return formIndexListAr;
	}




	public void setFormIndexListAr(ArrayList<IndexEntry> formIndexListAr) {
		this.formIndexListAr = formIndexListAr;
	}




	public ArrayList<IndexEntry> getFormIndexListGr() {
		return formIndexListGr;
	}




	public void setFormIndexListGr(ArrayList<IndexEntry> formIndexListGr) {
		this.formIndexListGr = formIndexListGr;
	}




	public ArrayList<IndexEntry> getLemmaIndexListAr() {
		return lemmaIndexListAr;
	}




	public void setLemmaIndexListAr(ArrayList<IndexEntry> lemmaIndexListAr) {
		this.lemmaIndexListAr = lemmaIndexListAr;
	}




	public ArrayList<IndexEntry> getLemmaIndexListGr() {
		return lemmaIndexListGr;
	}




	public void setLemmaIndexListGr(ArrayList<IndexEntry> lemmaIndexListGr) {
		this.lemmaIndexListGr = lemmaIndexListGr;
	}




	public ArrayList<IndexEntry> getRootIndexList() {
		return rootIndexList;
	}




	public void setRootIndexList(ArrayList<IndexEntry> rootIndexList) {
		this.rootIndexList = rootIndexList;
	}




}
