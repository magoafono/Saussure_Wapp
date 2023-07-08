/**
 * 
 */
package it.cnr.ilc.ga.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Angelo Del Grosso
 *
 */
public class Regex {

	/**
	 * 
	 */
	public Regex() {
		// TODO Auto-generated constructor stub
	}
	
	public static final String repAll(String regexp, String repl, String source){
		System.out.println("sono in regex con parametri: "+regexp+" "+repl);
		String result = source;
		try {
			
			Pattern p = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(source);
			if (m.find()) {
				result = m.replaceAll(repl);
				System.err.println("matching ok!" + result);
			} else {
				System.err.println("No matching!");
			}
		} catch(PatternSyntaxException ep){
			ep.printStackTrace();
		} catch (IllegalArgumentException ea){
			ea.printStackTrace();
		}

		System.out.println(result);
		return result;
	}
}
