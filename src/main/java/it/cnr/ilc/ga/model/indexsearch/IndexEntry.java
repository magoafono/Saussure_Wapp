/**
 * 
 */
package it.cnr.ilc.ga.model.indexsearch;

/**
 * @author Emiliano
 *
 */
public class IndexEntry {

	private String value;
	private int freq;
	
	/**
	 * Used to represent entries inside form, lemma and root indexes
	 */
	public IndexEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public IndexEntry(String value, int freq) {
		// TODO Auto-generated constructor stub
		this.value = value;
		this.freq = freq;
		
		
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	
	

}
