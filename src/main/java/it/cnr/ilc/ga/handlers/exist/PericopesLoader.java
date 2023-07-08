package it.cnr.ilc.ga.handlers.exist;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;

import it.cnr.ilc.ga.model.analysis.Analysis;
import it.cnr.ilc.ga.model.analysis.Form;
import it.cnr.ilc.ga.model.analysis.Lemma;
import it.cnr.ilc.ga.model.analysis.LinguisticUnit;
import it.cnr.ilc.ga.model.analysis.PartOfSpeech;
import it.cnr.ilc.ga.model.analysis.Root;
import it.cnr.ilc.ga.model.comment.PericopeComments;
import it.cnr.ilc.ga.model.indexsearch.PericopeSet;
import it.cnr.ilc.ga.model.pericope.Pericope;
import it.cnr.ilc.ga.model.pericope.Text;
import it.cnr.ilc.ga.model.pericope.Text.LangType;
import it.cnr.ilc.ga.utils.FileWr;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMFactory;
import org.jdom2.input.*;
import org.jdom2.input.sax.SAXHandler;
import org.jdom2.output.Format;
import org.jdom2.output.Format.TextMode;
import org.jdom2.output.XMLOutputter;
import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

public class PericopesLoader {

	//TODO iniziato a modificare il loader con i campi per Saussure
	final int ID=0;
	final int FOLIO=1; 
	final int SCANS=2; 
	final int ID_A=3;
	final int ID_B=4;
	final int INFO_A=5; 
	final int INFO_B=6; 
	final int NOTA=7;
	final int ANALYSIS_A=8;
	final int ANALYSIS_B=9;

	protected final String driver = "org.exist.xmldb.DatabaseImpl";
	private Collection root = null;
	//	private PericopeSet pericopeSet;


	//TODO le liste di parole vengono caricate dal'XML
	// Tenere presente le varie tipologie di parole: gerco, sanscrito, cambiolingua, forma ricostruita, etc..
	List<Element> words = null;
	List<Element> greekWords = null;
	//TODO
	/**
	 * Le liste in vari indici possono essere gestiti separatamente
	 * es.: fare un helper per dividere la lista di parole in sottoliste una per ciascuna lingua
	 */

	//TODO
	// trattare l'analisi in modo che sia coerente con l'impostazione di Saussure
	// avere solo una istanza analisi?
	Analysis aAnalysis = null;
	Analysis bAnalysis = null;

	public boolean connect() {
		try {

			Class<?> c = Class.forName(driver);
			Database db = (Database)c.newInstance();
			DatabaseManager.registerDatabase(db);
			root = DatabaseManager.getCollection("xmldb:exist://localhost:8088/xmlrpc/db/saussurepericopes","admin","angelodel80");
			return true;

		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (XMLDBException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public PericopesLoader() {
		System.err.println("in pericope Loader Saussure");
	}

	public LinkedHashMap<String, PericopeComments> load(PericopeSet pericopeSet) throws Exception{
		System.err.println(connect());
		LinkedHashMap<String, PericopeComments> lhm = new LinkedHashMap<String, PericopeComments>(10000);
		int i = 0;
		//TODO controllare l'outputter per una corretta formattazione
		//XMLOutputter xop=new XMLOutputter(Format.getCompactFormat().setTextMode(TextMode.TRIM_FULL_WHITE));
		XMLOutputter xop=new XMLOutputter(Format.getPrettyFormat().setTextMode(TextMode.PRESERVE));
		root.setProperty("indent", "no");
		for(String resource: root.listResources()){
			i++;
			System.out.println("Resource Loader: " + resource);
			XMLResource xr=(XMLResource)root.getResource(resource);

			//String resType = xr.RESOURCE_TYPE;
			//String content = (String)xr.getContent();
			//System.out.println("[Resource type:] " + resType);
			//System.out.println("[Resource content:] " + content);
			SAXHandler saxhandler=new SAXHandler();
			xr.getContentAsSAX(saxhandler);
			Document document=saxhandler.getDocument();

			Element addNode=document.getRootElement();
			Element docNode=addNode.getChild("doc");
			List<Element> fields=docNode.getChildren();

			String idStr = fields.get(ID).getTextNormalize();
			if ( -1 == idStr.indexOf('.')) {
				idStr =  idStr + ".0";
			}
			double pericopeId = Double.parseDouble(idStr);
			double aTextId = Double.parseDouble(fields.get(ID_A).getTextNormalize());
			double bTextId = Double.parseDouble(fields.get(ID_B).getTextNormalize());
			String folio = xop.outputString(fields.get(FOLIO).getChild("div"));
			//TODO trattare meglio il testo del folio!
			//String folio = fields.get(FOLIO).getChild("div").getValue();
			//System.out.println("[FOLIO OUT:]" + folio);
			String scans = fields.get(SCANS).getTextNormalize();
			String aInfo = fields.get(INFO_A).getTextNormalize();
			String bInfo = fields.get(INFO_B).getTextNormalize();
			String nota = fields.get(NOTA).getTextNormalize();

			try{
				words = fields.get(ANALYSIS_A).getChildren();
			}catch(Exception ex){
				words=new ArrayList<Element>();
			}
			try{
				greekWords = fields.get(ANALYSIS_B).getChildren();
			}catch(Exception ex){
				greekWords=new ArrayList<Element>();
			}

			Pericope pericope=new Pericope();
			pericope.setId(pericopeId);
			Text aText = new Text();
			Text bText = new Text();

			aAnalysis = new Analysis();
			bAnalysis = new Analysis();

			aText.setLangType(LangType.FRENCH);
			//ripulire testo e inserire tag

			//folio = folio.replaceAll("¶","<br />")
			folio = folio.replaceAll("‐","-");
			//.replaceAll("_", "&nbsp;")
			//.replaceAll("«","<em class=\"integration\">")
			//.replaceAll("»","</em>");

			// il testo di Saussure è formattato e strutturato in HTML quindi prendere il testo plain
			String arabicTextPericopePlain = folio.replaceAll("\\<.*?\\>", "");
			fields.get(FOLIO).getChild("div").removeChild("h1");
			//TODO verificare perchè la getValue non funziona bene sugli spazi
			String folioTextPlain = fields.get(FOLIO).getChild("div").getValue().replaceAll("[\n\r]", "").replaceAll("(\u0020)+", " ");
			//System.out.println(arabicTextPericopePlain);
			//System.out.println("[FOLIO TEXT PLAIN:] " + folioTextPlain);

			//TODO gestire gli indici in modo opportuno e cambiare il textPlain
			StringTokenizer attestedForms = new StringTokenizer(arabicTextPericopePlain);
			int ind = -1;

			for (Element word : words) {
//				System.err.print(word.getAttributeValue("token") + " : ");
//				System.err.print(word.getAttributeValue("lang") + "-->");
//				System.err.println(Language(word.getAttributeValue("lang")));

				ind++;
				LinguisticUnit lunit = new LinguisticUnit();

				String token = word.getAttributeValue("token");
				String prog = " ";
				String id = " ";
				String start = "0";
				String end = "0";
				//String form = attestedForm;

				String lemma = token;
				String pos = "ANY";
				String status = "ANY";
				String root = token;

				lunit.setId(String.valueOf(ind));
				lunit.setPositionStart(Integer.parseInt(start, 10));
				lunit.setPositionEnd(Integer.parseInt(end, 10));
				lunit.setPositionEnd(Integer.parseInt(end, 10));
				lunit.setLanguage(Language(word.getAttributeValue("lang")));

				Form luForm = new Form();
				Lemma luLemma = new Lemma();

				PartOfSpeech luPOS = new PartOfSpeech();
				Root luRoot = new Root();

				luForm.setValue(token);
				luForm.setExtendedValue(token);
				luForm.setNormalizedValue(token);
				lunit.setForm(luForm);


				root = root.replaceAll("#", "NaN");
				luRoot.setValue(root);
				lunit.setRoot(luRoot);

				//TODO attenzione cambiare il valore del lemma da normalized Form a lemma
				luLemma.setValue(token);

				lunit.setLemma(luLemma);


				luPOS.setValue(pos);
				lunit.setPos(luPOS);


				aAnalysis.addLinguisticUnit(lunit);
				bAnalysis.addLinguisticUnit(lunit);

			}

			//			while(attestedForms.hasMoreTokens()){
			//				ind++;
			//				LinguisticUnit lu = new LinguisticUnit();
			//				LinguisticUnit lu2 = new LinguisticUnit();
			//				String attestedForm = attestedForms.nextToken();
			//				String prog = " ";
			//				String id = " ";
			//				String start = "0";
			//				String end = "0";
			//				String form = attestedForm;
			//				//System.out.println(pre);
			//				String normalizedForm = attestedForm.toLowerCase()
			//				.replaceAll("[\\[\\.,!\\?|\"\\(\\)\\]]", "");
			//				String normalizedForm2 = "";
			//
			//				//attestedForm.startsWith("&#x")&& !"&#x0259;".equals(attestedForm) || 
			//				if((attestedForm.charAt(0)>='\u0370' &&attestedForm.charAt(0)<='\u03FF') ){
			//					normalizedForm="";
			//					normalizedForm2 = attestedForm.replaceAll("\\)", "");
			//					System.out.println("**************");
			//					//					normalizedForm2 = attestedForm.replaceAll("&#x", "")
			//					//					.replaceAll(";", " ")
			//					//					.replaceAll("-\\)", "");
			//					//					String[] codepoints = normalizedForm2.split(" ");
			//					//					StringBuilder str = new StringBuilder();
			//					//					for (String codepoint : codepoints) {
			//					//						int codep = Integer.parseInt(codepoint, 16);
			//					//						char c = (char)codep;
			//					//						str.append(c);
			//					//					}
			//					//					System.out.println("**************");
			//					//					normalizedForm2=str.toString();
			//
			//				}else if(attestedForm.contains("&#x")){
			//					normalizedForm = attestedForm.replaceAll("&#x", " ");
			//					int idx1 = normalizedForm.indexOf(' ');
			//					int idx2 = normalizedForm.indexOf(';');
			//					String codepoint = normalizedForm.substring(idx1+1, idx2);
			//					int codep = Integer.parseInt(codepoint, 16);
			//					char c = (char)codep;
			//					System.out.println("+++++ " +c+ " ++++++++++++");
			//					normalizedForm = (normalizedForm.substring(0,idx1) + c + normalizedForm.substring(idx2));
			//					normalizedForm = normalizedForm.replace('«', '\u0259') + "*";
			//				}
			//
			//
			//				normalizedForm = normalizedForm.replaceAll(";", "")
			//				.replaceAll(" ", "")
			//				.replaceAll(",", "")
			//				.replaceAll(":","");
			//				normalizedForm2 = normalizedForm2.replaceAll(";", "")
			//				.replaceAll(" ", "")
			//				.replaceAll(",", "")
			//				.replaceAll(":","");
			//				//System.out.println("<w prog=\"#\" id=\"#\" start=\"#\" end=\"#\" form=\"#\" normalizedform=\""+normalizedForm2+"\" pos=\"ANY\"/>");
			//				//System.out.println("<w prog=\"#\" id=\"#\" start=\"#\" end=\"#\" form=\"#\" normalizedform=\""+normalizedForm+"\" pos=\"ANY\"/>");
			//				//String.valueOf(ext);
			//				//System.out.println(ext);
			//
			//				//scrivo su file gli indici delle forme attestate
			//				//				String idx1="";
			//				//				String idx2="";
			//				//				if(!"".equals(normalizedForm2)){
			//				//					 idx1 = "<w prog=\"#\" id=\"#\" start=\"#\" end=\"#\" form=\"#\" normalizedform=\""+normalizedForm2+"\" pos=\"ANY\"/>";
			//				//					 FileWr.print(idx1);
			//				//				}
			//				//				if(!"".equals(normalizedForm)){
			//				//					 idx2 = "<w prog=\"#\" id=\"#\" start=\"#\" end=\"#\" form=\"#\" normalizedform=\""+normalizedForm+"\" pos=\"ANY\"/>";
			//				//					 FileWr.print(idx2);
			//				//				}
			//				// fine scrittura indici
			//
			//				String lemma = attestedForm;
			//				String pos = "ANY";
			//				String status = "ANY";
			//				String root = attestedForm;
			//
			//				lu.setId(String.valueOf(ind));
			//				lu2.setId(String.valueOf(ind));
			//				lu.setPositionStart(Integer.parseInt(start, 10));
			//				lu2.setPositionStart(Integer.parseInt(start, 10));
			//				lu.setPositionEnd(Integer.parseInt(end, 10));
			//				lu2.setPositionEnd(Integer.parseInt(end, 10));
			//				lu.setLanguage(Text.LangType.FRENCH);
			//				lu2.setLanguage(Text.LangType.GREEK);
			//
			//				Form luForm = new Form();
			//				Lemma luLemma = new Lemma();
			//				Lemma luLemma2 = new Lemma();
			//				PartOfSpeech luPOS = new PartOfSpeech();
			//				Root luRoot = new Root();
			//
			//				luForm.setValue(form);
			//				luForm.setExtendedValue(normalizedForm);
			//				luForm.setNormalizedValue(normalizedForm);
			//				lu.setForm(luForm);
			//				lu2.setForm(luForm);
			//
			//				root = root.replaceAll("#", "NaN");
			//				luRoot.setValue(root);
			//				lu.setRoot(luRoot);
			//				lu2.setRoot(luRoot);
			//				//TODO attenzione cambiare il valore del lemma da normalized Form a lemma
			//				luLemma.setValue(normalizedForm);
			//				luLemma2.setValue(normalizedForm2);
			//				lu.setLemma(luLemma);
			//				lu2.setLemma(luLemma2);
			//
			//				luPOS.setValue(pos);
			//				lu.setPos(luPOS);
			//				lu2.setPos(luPOS);
			//
			//				aAnalysis.addLinguisticUnit(lu);
			//				bAnalysis.addLinguisticUnit(lu2);
			//
			//			}

			aText.setContent(folio);
			aText.setId(aTextId);
			aText.setBid(bTextId);
			aText.setOffset(0);
			aText.setReference(aInfo);

			bText.setLangType(LangType.GREEK);

			//ripulire testo e inserire tag

			scans = scans.replaceAll("¶","<br />")
			.replaceAll("‐","-")
			//.replaceAll("_", "&nbsp;")
			.replaceAll("«","<em class=\"integration\">")
			.replaceAll("»","</em>");

			bText.setContent(scans);
			bText.setId(aTextId);
			bText.setBid(bTextId);
			bText.setOffset(0);
			bText.setReference(bInfo);


			// inserire funzione per istanziare l'analisi linguistica

			if(words!=null && greekWords!=null){
				//				boolean lang1OK = parseWords(words,aAnalysis,1);
				//				boolean lang2OK = parseWords(greekWords,bAnalysis,2);
				//			
				//				System.out.println(lang1OK);
				//				System.out.println(lang2OK);
			}
			//			String provaAnalisi1 = aAnalysis.getAnalysis().get(0).getRoot().getValue();
			//			String provaAnalisi2 = aAnalysis.getAnalysis().get(0).getForm().getNormalizedValue();
			//			String provaAnalisi3 = bAnalysis.getAnalysis().get(0).getForm().getNormalizedValue();
			//			String provaAnalisi4 = bAnalysis.getAnalysis().get(0).getLemma().getValue();
			//			System.out.println(provaAnalisi4);
			//			

			//			for(Element w : words){
			//				LinguisticUnit arlu = new LinguisticUnit();
			//				String prog = w.getAttributeValue("prog");
			//				String id = w.getAttributeValue("id");
			//				String start = w.getAttributeValue("start");
			//				String end = w.getAttributeValue("end");
			//				String form = w.getAttributeValue("form");
			//				String br = w.getAttributeValue("BR");
			//				String lemma = w.getAttributeValue("lemma");
			//				String root = w.getAttributeValue("root");
			//				String pos = w.getAttributeValue("pos");
			//				String voc = w.getAttributeValue("voc");
			//				
			//				arlu.setLanguage(Text.LangType.ARABIC);
			//				arlu.setId(id);
			//				arlu.setPositionStart(Integer.parseInt(start, 10));
			//				arlu.setPositionEnd(Integer.parseInt(end, 10));
			//				
			//				Form arluForm = new Form();
			//				Lemma arluLemma = new Lemma();
			//				Root arluRoot = new Root();
			//				PartOfSpeech arluPOS = new PartOfSpeech();
			//				
			//				arluForm.setValue(form);
			//				arluForm.setExtendedValue(voc);
			//				arlu.setForm(arluForm);
			//				
			//				
			//				arluLemma.setValue(lemma);
			//				arlu.setLemma(arluLemma);
			//				
			//				arluPOS.setValue(pos);
			//				arlu.setPos(arluPOS);
			//				
			//				root = root.replaceAll("#", "");
			//				arluRoot.setValue(root);
			//				arlu.setRoot(arluRoot);
			//				
			//				aAnalysis.addLinguisticUnit(arlu);
			//				
			//			}

			//			for(Element w : greekWords){
			//				LinguisticUnit grlu = new LinguisticUnit();
			//				String prog = w.getAttributeValue("prog");
			//				String id = w.getAttributeValue("id");
			//				String bibref = w.getAttributeValue("bibref");
			//				String ucform = w.getAttributeValue("ucform");
			//				String start = w.getAttributeValue("start");
			//				String end = w.getAttributeValue("end");
			//				String form = w.getAttributeValue("form");
			//				String lemma = w.getAttributeValue("lemma");
			//				String pos = w.getAttributeValue("pos");
			//				
			//				grlu.setLanguage(Text.LangType.GREEK);
			//				grlu.setId(id);
			//				grlu.setPositionStart(Integer.parseInt(start, 10));
			//				grlu.setPositionEnd(Integer.parseInt(end, 10));
			//				
			//				Form grluForm = new Form();
			//				Lemma grluLemma = new Lemma();
			//				PartOfSpeech grluPOS = new PartOfSpeech();
			//				
			//				grluForm.setValue(form);
			//				grluForm.setExtendedValue(ucform);
			//				grlu.setForm(grluForm);
			//				
			//				grluLemma.setValue(lemma);
			//				grlu.setLemma(grluLemma);
			//				
			//				grluPOS.setValue(pos);
			//				grlu.setPos(grluPOS);
			//				
			//				bAnalysis.addLinguisticUnit(grlu);
			//				
			//			}
			if(aAnalysis!=null){
				aText.setAnalysis(aAnalysis);
				bText.setAnalysis(bAnalysis);
			}
			pericope.setAtext(aText);
			pericope.setBtext(bText);


			pericopeSet.addPericope(pericope);
			lhm.put(idStr, null);
		}

		//TODO DISCONNECT Exist
		//	parseWords(words, aAnalysis, 1);
		return lhm ;

	}

	private LangType Language(String attributeValue) {
		//System.err.println("in Language()");
		if("Fr".equals(attributeValue)){
			return Text.LangType.FRENCH;
		}
		else if("gr".equals(attributeValue)){
			return Text.LangType.GREEK;
		}
		else if("Gr".equals(attributeValue)){
			return Text.LangType.GREEK;
		}
		else if("Ge".equals(attributeValue)){
			return Text.LangType.GERMAN;
		}
		else if("It".equals(attributeValue)){
			return Text.LangType.ITALIEN;
		}
		else if("sonante".equals(attributeValue)){
			return Text.LangType.TERME_RECONSTRUIT;
		}
		else if("so".equals(attributeValue)){
			return Text.LangType.TERME_RECONSTRUIT;
		}
		else if("fo".equals(attributeValue)){
			return Text.LangType.TERME_RECONSTRUIT;
		}
		else if("sa".equals(attributeValue)){
			return Text.LangType.SANSCRIT;
		}
		else if("la".equals(attributeValue)){
			return Text.LangType.LATIN;
		}
		else if("La".equals(attributeValue)){
			return Text.LangType.LATIN;
		}
		
		else if (attributeValue.contains("French")){
			return Text.LangType.FRENCH;
		}
		

		else
			return Text.LangType.FRENCH;
	}

	private boolean parseWords(List<Element> words, Analysis analysis, int lang){
		for(Element w : words){
			//String token = w.getAttributeValue("token");
			//System.out.println(token);
			//			LinguisticUnit lu = new LinguisticUnit();
			//			String prog = w.getAttributeValue("prog");
			//			String id = w.getAttributeValue("id");
			//			String start = w.getAttributeValue("start");
			//			String end = w.getAttributeValue("end");
			//			String form = w.getAttributeValue("form");
			//			String normalizedForm = w.getAttributeValue("normalizedform");
			//			String lemma = w.getAttributeValue("lemma");
			//			String pos = w.getAttributeValue("pos");
			//			String status = w.getAttributeValue("status");
			//			String root = "";
			//			if(lang==1){
			//			root = w.getAttributeValue("root");
			//			}
			//			//String voc = w.getAttributeValue("voc");
			//			
			//			
			//			// TODO gestione della lingua per l'unità linguistica
			//			//lu.setLanguage(Text.LangType.ARABIC); 
			//			lu.setId(id);
			//			lu.setPositionStart(Integer.parseInt(start, 10));
			//			lu.setPositionEnd(Integer.parseInt(end, 10));
			//			lu.setLanguage(Text.LangType.GREEK);
			//			
			//			Form luForm = new Form();
			//			Lemma luLemma = new Lemma();
			//			PartOfSpeech luPOS = new PartOfSpeech();
			//			Root luRoot = new Root();
			//			
			//			
			//			luForm.setValue(form);
			//			luForm.setExtendedValue(normalizedForm);
			//			luForm.setNormalizedValue(normalizedForm);
			//			lu.setForm(luForm);
			//			
			//			//gestione di lemmi multipli
			//			int i = lemma.indexOf(" ");
			//			lemma= (i==-1)? lemma:lemma.substring(0, i);
			//			luLemma.setValue(lemma);
			//			lu.setLemma(luLemma);
			//		
			//		if (lang==1){
			//			lu.setLanguage(Text.LangType.ARABIC);
			//			try{
			//				root = root.replaceAll("#", "NaN");
			//				luRoot.setValue(root);
			//	 			lu.setRoot(luRoot);
			//			}catch(Exception ex){
			//					ex.printStackTrace();
			//			}
			//		}
			//			
			//			luPOS.setValue(pos);
			//			lu.setPos(luPOS);
			//			
			//			analysis.addLinguisticUnit(lu);
			//			
		}

		return true;
	}

	public static void main(String[] args){
		try{
			PericopesLoader pl=new PericopesLoader();
			//System.err.println(pl.connect());
			PericopeSet pset = new PericopeSet();

			LinkedHashMap<String, PericopeComments> ris=pl.load(pset);
			//System.out.println(ris);
			//						for (Pericope p : pset.getPericopeSet()) {
			//							try {
			//								System.out.println(p.getAtext().getId());
			//								System.out.println(p.getAtext().getBid());
			//							}catch(Exception ex){
			//								//
			//							}
			//							
			//			//				//System.out.println(p.getArabicText().getContent()); 
			//			//				
			//						}
		}catch(Exception ex){
			ex.printStackTrace(System.err);
		}
	}
}
