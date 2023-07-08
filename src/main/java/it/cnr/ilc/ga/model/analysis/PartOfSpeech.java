package it.cnr.ilc.ga.model.analysis;

public class PartOfSpeech {
	
	private String value; 
	
	public PartOfSpeech() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String getUniqueValue(){
		int i = value.indexOf(" ");
		if( i == -1)
			return value;
		else return value.substring(0, i)+"*";
	}
}
