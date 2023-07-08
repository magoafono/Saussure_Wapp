package it.cnr.ilc.ga.action.management;

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
*
* @author Administrator
*/

@ManagedBean(name = "themeSwitcher")
@SessionScoped
public class ThemeSwitcher implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -3940391454064831781L;
	
	private Map<String, String> themes;

	//@ManagedProperty(value="#{param.theme}")
    private String theme;
    //private UserPreferences userPrefs;

    public ThemeSwitcher () {
       
        //theme = userPrefs.getTheme();
        theme = "humanity";
       
        themes = new TreeMap<String, String>();
        themes.put("Aristo", "aristo");
        themes.put("Black-Tie", "black-tie");
        themes.put("Blitzer", "blitzer");
        themes.put("Bluesky", "bluesky");
        //themes.put("Casablanca", "casablanca");
        themes.put("Cupertino", "cupertino");
        themes.put("Dark-Hive", "dark-hive");
        themes.put("Dot-Luv", "dot-luv");
        themes.put("Eggplant", "eggplant");
        themes.put("Excite-Bike", "excite-bike");
        themes.put("Flick", "flick");
        themes.put("Glass-X", "glass-x");
        themes.put("Hot-Sneaks", "hot-sneaks");
        themes.put("Home", "home");
        themes.put("Humanity", "humanity");
        themes.put("Le-Frog", "le-frog");
        themes.put("Midnight", "midnight");
        themes.put("Mint-Choc", "mint-choc");
        themes.put("Overcast", "overcast");
        themes.put("Pepper-Grinder", "pepper-grinder");
        themes.put("Redmond", "redmond");
        themes.put("Rocket", "rocket");
        themes.put("Sam", "sam");
        themes.put("Smoothness", "smoothness");
        themes.put("South-Street", "south-street");
        themes.put("Start", "start");
        themes.put("Sunny", "sunny");
        themes.put("Swanky-Purse", "swanky-purse");
        themes.put("Trontastic", "trontastic");
        themes.put("UI-Darkness", "ui-darkness");
        themes.put("UI-Lightness", "ui-lightness");
        themes.put("Vader", "vader");
       
    }
   
    public Map<String, String> getThemes() {
        return themes;
    }

    public String getTheme() {
    	System.err.println("getTheme: " + theme);
        return theme;
    }

    public void setTheme() {
    	String newtheme = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("theme");
    	System.err.println("setTheme: " + FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("theme"));
    	System.err.println("setTheme: " + theme);
        this.theme = newtheme; 
    }

    public void saveTheme() {
    	System.err.println("saveTheme: " + theme);

        //userPrefs.setTheme(theme);
    }

/*
    public void setUserPrefs(UserPreferences userPrefs) {
        this.userPrefs = userPrefs;
    }
*/
   
}
