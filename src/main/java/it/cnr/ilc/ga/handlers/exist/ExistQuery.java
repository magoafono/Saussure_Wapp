/**
 * 
 */
package it.cnr.ilc.ga.handlers.exist;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.xml.transform.OutputKeys;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.CompiledExpression;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XQueryService;

/**
 * @author Angelo Del Grosso
 *
 */
public class ExistQuery {

	protected final String driver = "org.exist.xmldb.DatabaseImpl";
	protected final String collection = "xmldb:exist://localhost:8088/xmlrpc/db/saussurepericopes";
	protected final String file = "/it/cnr/ilc/ga/handlers/exist/resources/query.xq";

	private Collection root = null;

	String query = null;
	CompiledExpression compiled = null;
	XQueryService service = null;
	ResourceSet result = null;
	ResourceIterator it = null;

	/**
	 * 
	 */
	public ExistQuery() {
		System.err.println("in Query...");
	}


	public boolean connect() {
		try {

			Class<?> c = Class.forName(driver);
			Database db = (Database)c.newInstance();
			DatabaseManager.registerDatabase(db);
			root = DatabaseManager.getCollection(collection,"admin","angelodel80");

			service = (XQueryService) root.getService("XQueryService", "1.0");
			service.setProperty(OutputKeys.INDENT, "no");
			service.setProperty(OutputKeys.ENCODING, "UTF-8");

			//initQuery();

			BufferedReader br=new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file),"UTF-8"));
			StringBuilder sb=new StringBuilder("");
			String line="";
			while((line=br.readLine())!=null){
				sb.append(line).append("\n");
			}
			query=sb.toString();

			//System.err.println(query);

			compiled = service.compile(query);

			return true;

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (XMLDBException ex) {
			ex.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace(System.err);
		}
		return false;
	}

	//	private void initQuery(){
	//        try{
	//        query = "declare variable $tg1 as xs:string external; for $w1 in /add/doc/field[ft:query(@name,\"analysis_gr\")]/w[ft:query(@form,$tg1)]\n"
	//                + "let $n:=$w1/../../field[@name='info_gr']/text()\n"
	//                + "let $t:=$w1/../../field[@name='pericope_gr']/text()\n"
	//                + "return <span><span>{xs:string($n)}</span><br/><span>{xs:string($t)}</span><br/><br/></span>";
	//        compiled = service.compile(query);
	//        if(compiled != null){
	//        	
	//        	System.err.println("puntatore compiled: " + compiled.toString());
	//        	
	//        }
	//        }catch(Exception ex){
	//            ex.printStackTrace(System.err);
	//        }
	//    }

	// CAMBIARE: term non è definitivo da togliere
	public String query(
			String ntgs,
			String ntas,

			String fg1,
			String tg1,
			String pg1,

			String fg2,
			String tg2,
			String pg2,

			String fg3,
			String tg3,
			String pg3,

			String fa1,
			String ta1,
			String pa1,

			String fa2,
			String ta2,
			String pa2,

			String fa3,
			String ta3,
			String pa3,

			String bgs,
			String bas,
			String bs   ){
		//TODO string builder
		String res="";
		try{
			tg1 = adjust(tg1);
			tg2 = adjust(tg2);
			tg3 = adjust(tg3);
			//aggiunta per trattare i segni di Saussure
			ta1 = adj(ta1);
			if(false){
				service.declareVariable("ntgs","1");
				service.declareVariable("ntas","0");

				service.declareVariable("fg1","form");
				service.declareVariable("tg1","σῶμα");
				service.declareVariable("pg1","ANY");

				service.declareVariable("fg2","form");
				service.declareVariable("tg2","x");
				service.declareVariable("pg2","ANY");

				service.declareVariable("fg3","form");
				service.declareVariable("tg3","x");
				service.declareVariable("pg3","ANY");

				service.declareVariable("fa1","form");
				service.declareVariable("ta1","x");
				service.declareVariable("pa1","ANY");

				service.declareVariable("fa2","form");
				service.declareVariable("ta2","x");
				service.declareVariable("pa2","ANY");

				service.declareVariable("fa3","form");
				service.declareVariable("ta3","x");
				service.declareVariable("pa3","ANY");

				service.declareVariable("bgs","false");
				service.declareVariable("bas","false");
				service.declareVariable("bs","false");
			}else{

				service.declareVariable("ntgs",ntgs);
				service.declareVariable("ntas",ntas);

				service.declareVariable("fg1",fg1);
				service.declareVariable("tg1",tg1);
				service.declareVariable("pg1",pg1);

				service.declareVariable("fg2",fg2);
				service.declareVariable("tg2",tg2);
				service.declareVariable("pg2",pg2);

				service.declareVariable("fg3",fg3);
				service.declareVariable("tg3",tg3);
				service.declareVariable("pg3",pg3);

				service.declareVariable("fa1",fa1);
				service.declareVariable("ta1",ta1);
				service.declareVariable("pa1",pa1);

				service.declareVariable("fa2",fa2);
				service.declareVariable("ta2",ta2);
				service.declareVariable("pa2",pa2);

				service.declareVariable("fa3",fa3);
				service.declareVariable("ta3",ta3);
				service.declareVariable("pa3",pa3);

				service.declareVariable("bgs",bgs);
				service.declareVariable("bas",bas);
				service.declareVariable("bs",bs);
			}

			//compiled = service.compile(query);
			result = service.execute(compiled);
			System.err.println("query: " + query);
			System.out.println("ExistQuery termine: " + fa1);
			it = result.getIterator();
			while (it.hasMoreResources()) {
				System.err.print("|");
				res+= (String) it.nextResource().getContent()+"<br/>";
			}
		}catch(Exception ex){
			ex.printStackTrace(System.err);
		}
		String resText = res.replaceAll("\\<.*?\\>", "").replaceAll("[\n\r\t]","");
		resText = resText.replaceAll("folio(\\s\\d{1,3}[rv])", "<hr /><div class=\"bold\">f.$1</div> ");
		System.out.println(resText);
		return resText;

	}

	private String adjust(String str){
		if(str==null) return "";
		//str=str.replaceAll("ά","ά");
		//str=str.replaceAll("έ","έ");
		//str=str.replaceAll("ή","ή");
		//str=str.replaceAll("ί","ί");
		//str=str.replaceAll("ό","ό");
		//str=str.replaceAll("ύ","ύ");
		//str=str.replaceAll("ώ","ώ");
		return str;
	}
	
	private String adj(String str){
		if(str==null) return"";
		if(str.length()>0){
			if(str.charAt(0)=='-')
				str=str.substring(1);
			if(str.endsWith("-"))
				str = str.substring(0, str.length()-1);
			str = str.trim();
		}
		return str;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

		ExistQuery eq = new ExistQuery();
		System.out.println(eq.connect());

		System.out.println(eq.query("1","0","form","σῶμα","ANY","form","x","ANY","form","x","ANY","form","x","ANY","form","x","ANY","form","x","ANY","false","false","false"));

	}

}
