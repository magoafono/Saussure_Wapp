/**
 * 
 */
package it.cnr.ilc.ga.utils;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.facelets.FaceletContext;

/**
 * @author Angelo Del Grosso
 *
 */
@ManagedBean
@SessionScoped
public class ValidatorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3184887565937488972L;
	String valore = "";
	
	/**
	 * 
	 */
	public ValidatorBean() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException{
		if(true){
			System.out.println("eccezione");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "validatore funziona", "validatore funziona : " + this.valore));
		}
	}
	

	/**
	 * @return the valore
	 */
	public String getValore() {
		return valore;
	}

	/**
	 * @param valore the valore to set
	 */
	public void setValore(String valore) {
		this.valore = valore;
	}
	

}
