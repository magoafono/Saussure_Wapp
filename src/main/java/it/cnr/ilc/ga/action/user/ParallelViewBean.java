/**
 * 
 */
package it.cnr.ilc.ga.action.user;

import it.cnr.ilc.ga.action.management.ManagerBean;
import it.cnr.ilc.ga.model.pericope.Pericope;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * @author Angelo Del Grosso
 *
 */
@ManagedBean
@SessionScoped
public class ParallelViewBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8761677373617116582L;

	/**
	 * 
	 */

	@ManagedProperty(value="#{manager}")
	private ManagerBean managerBean;
	
	/**
	 * 
	 */
	private Pericope selectedPericope;
	private String selectedContent;
	
	public ParallelViewBean() {
		// TODO Auto-generated constructor stub
		System.out.println("parallel");
	}
	
	public ArrayList<Pericope> getPericopesBya(){
		
		return managerBean.getPericopesOrdereda();
		
	}
	
	public ArrayList<Pericope> getPericopesByb(){
		
		return managerBean.getPericopesOrderedb();
		
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
	System.out.println("select pericope in parallel");
}

/**
 * @return the selectedContent
 */
public String getSelectedContent() {
	return selectedContent;
}

/**
 * @param selectedContent the selectedContent to set
 */
public void setSelectedContent(String selectedContent) {
	this.selectedContent = selectedContent;
	System.out.println("selected content da table view");
}

	

}
