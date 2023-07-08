package it.cnr.ilc.ga.model.analysis;

import it.cnr.ilc.ga.model.pericope.Text.LangType;

public class LinguisticUnit {
	
	private String id;
	private LangType language;
	private int positionStart;
	private int positionEnd;
	private Form form;
	private Lemma lemma;
	private Root root;
	private PartOfSpeech pos;
	
	public LinguisticUnit(){
	
		// constructor
	}

	/**
	 * @return the form
	 */
	public Form getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(Form form) {
		this.form = form;
	}

	/**
	 * @return the lemma
	 */
	public Lemma getLemma() {
		return lemma;
	}

	/**
	 * @param lemma the lemma to set
	 */
	public void setLemma(Lemma lemma) {
		this.lemma = lemma;
	}

	/**
	 * @return the root
	 */
	public Root getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(Root root) {
		this.root = root;
	}

	/**
	 * @return the pos
	 */
	public PartOfSpeech getPos() {
		return pos;
	}

	/**
	 * @param pos the pos to set
	 */
	public void setPos(PartOfSpeech pos) {
		this.pos = pos;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the language
	 */
	public LangType getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(LangType language) {
		this.language = language;
	}

	/**
	 * @return the positionStart
	 */
	public int getPositionStart() {
		return positionStart;
	}

	/**
	 * @param positionStart the positionStart to set
	 */
	public void setPositionStart(int positionStart) {
		this.positionStart = positionStart;
	}

	/**
	 * @return the positionEnd
	 */
	public int getPositionEnd() {
		return positionEnd;
	}

	/**
	 * @param positionEnd the positionEnd to set
	 */
	public void setPositionEnd(int positionEnd) {
		this.positionEnd = positionEnd;
	}
	
	

}
