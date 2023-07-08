/**
 * 
 */
package it.cnr.ilc.ga.handlers.exist;


import java.util.List;

import it.cnr.ilc.ga.model.comment.Comment;
import it.cnr.ilc.ga.model.comment.PericopeComments;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

/**
 * @author Angelo Del Grosso
 *
 */
public class CommentStorer {

	protected final String driver = "org.exist.xmldb.DatabaseImpl";
	private Collection root = null;
	
	/**
	 * 
	 */
	public CommentStorer() {
		// TODO Auto-generated constructor stub
	}

	public boolean connect() {
        try {
            
            Class<?> c = Class.forName(driver);
            Database db = (Database)c.newInstance();
            DatabaseManager.registerDatabase(db);
            root = DatabaseManager.getCollection("xmldb:exist://localhost:8088/xmlrpc/db/saussurecomments","admin","angelodel80");
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
	
//	public boolean store(String xmlComment, int commentNum,  String idPericopeStr) throws Exception{
//		System.err.println(connect() + " connessione comment store");
//
//		try
//		{
//			String resourceName = pc;
//			// create new XMLResource; an id will be assigned to the new resource
//			XMLResource document = (XMLResource) root.createResource(resourceName, "XMLResource");
//			System.out.println("docID: " + document.getDocumentId());
//			
//			document.setContent(xmlComment);
//			System.out.println("storing document " + document.getId() + "...");
//			System.out.println(document.getContent().toString());
//			root.storeResource(document);
//			System.out.println("ok.");
//		}catch(Exception e){
//			return false;
//		}
//		
//		return true;	
//	}
	
	
	//TODO rivedere completamente quando cambieremo la modellizzazione
	private Document createJDOMComment (Comment comment, String idComment) {
	
		Element xmlComment = new Element("comment");
		xmlComment.setAttribute(new Attribute("id", idComment));
		Document doc = new Document(xmlComment);
		doc.setRootElement(xmlComment);
		
		Element refsNode = new Element("refs");

		String[] type = {"greek selection", "arabic selection"};
		for (int i = 0; i < type.length; i++) {
			Element rts = new Element("ref-to-selection");
			rts.setAttribute("target", idComment.substring(0,idComment.indexOf('_')));
			rts.setAttribute("select-name", String.valueOf(i));
			rts.setAttribute("name", type[i]);
			refsNode.addContent(rts);
		}
		
		doc.getRootElement().addContent(refsNode);

		Element versNode = new Element("vers");
		Element verNode = new Element("ver");
		verNode.setAttribute("id", "0");
		Element authorNode = new Element("author");
		authorNode.setAttribute("author-id", "0");
		Element commnetTypeNode = new Element("comment-type");
		commnetTypeNode.setAttribute("type", comment.getType());
		Element textRefNode = new Element("text-ref");
		textRefNode.setAttribute("text-id", "0");
		Element validNode = new Element("valid");
		validNode.setAttribute("from", "0");

		verNode.addContent(authorNode);
		verNode.addContent(commnetTypeNode);
		verNode.addContent(textRefNode);
		verNode.addContent(validNode);

		versNode.addContent(verNode);

		doc.getRootElement().addContent(versNode);

		Element selectionsNode = new Element("selections");
		
		
		List<Integer> greekBound = comment.getGreekSelectedBound();
		List<Integer> arabicBound = comment.getArabicSelectedBound();
		
		Integer[] start =  {greekBound.get(0), arabicBound.get(0)};
		Integer[] end =  {greekBound.get(1), arabicBound.get(1)};
		String[] fragments = {comment.getGreekSelectedPosition(),comment.getArabicSelectedPosition()};
		
		for (int i = 0; i < 2; i++) {
			Element selectionNode = new Element("selection");
			selectionNode.setAttribute("type", "text");
			selectionNode.setAttribute("name", String.valueOf(i));
			Element selectNode = new Element("select");
			selectNode.setAttribute("sel", "0");
			Element startNode = new Element("start");
			startNode.setAttribute("x", String.valueOf(start[i]));
			Element endNode = new Element("end");
			endNode.setAttribute("x", String.valueOf(end[i]));
			//AGGIUNTA per immettere anche il testo della selezione
			Element fragmentNode = new Element("fragment");
			fragmentNode.setAttribute("value",fragments[i]);
			
			
			selectNode.addContent(startNode);
			selectNode.addContent(endNode);
			// aggiunta fragment per visualizzazione e memorizzazione della porzione di risorsa selezionata
			selectNode.addContent(fragmentNode);
			
			selectionNode.addContent(selectNode);

			selectionsNode.addContent(selectionNode);
		}
		
		doc.getRootElement().addContent(selectionsNode);
		
		Element textsNode = new Element("texts");
		Element textNode = new Element("text");
		textNode.setAttribute("id", "0");
		Element richNode = new Element("rich");
		richNode.addContent(comment.getCommentText());
		Element plainNode = new Element("plain");
		plainNode.addContent(comment.getCommentText().replaceAll("\\<.*?\\>", ""));
		
		textNode.addContent(plainNode);
		textNode.addContent(richNode);
		textsNode.addContent(textNode);

		doc.getRootElement().addContent(textsNode);

		return doc;
		
	}
	
	public boolean store(Comment comment, PericopeComments pc) throws Exception{
		System.err.println(connect() + " connessione comment store");

		try
		{
			String idComment = comment.getIdComment();
			// create new XMLResource; an id will be assigned to the new resource
			XMLResource document = (XMLResource) root.createResource("comment-" + idComment + ".xml", "XMLResource");
			System.out.println("docID: " + document.getDocumentId());
			
			Document jdomComment = createJDOMComment(comment, idComment);
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			document.setContent(xmlOutput.outputString(jdomComment));
			System.out.println("storing document " + document.getId() + "...");
			System.out.println(document.getContent().toString());
			root.storeResource(document);
			System.out.println("Commento salvato con successo");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;	
	}
	
	/**
	 * 
	 * @param comment
	 * @return
	 * @throws Exception
	 */
	public boolean remove(Comment comment){
		System.err.println(connect() + " connessione comment store");

		String idComment = comment.getIdComment();
		// create new XMLResource; an id will be assigned to the new resource
		XMLResource document;
		try {
			document = (XMLResource) root.createResource("comment-" + idComment + ".xml", "XMLResource");
			System.out.println("remove() docID: " + document.getDocumentId());
			root.removeResource(document);
			System.out.println("removed!");
		} catch (XMLDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;	
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String  xmlComment="<?xml version=\"1.0\" encoding=\"UTF-8\"?> <comment id=\"429.2\"><refs><text1 target=\"429\">ἀθάνατος</text1><text2 target=\"429\">كلّه واقعٌ</text2></refs><vers><ver id=\"0\"><comment-type>literal translation</comment-type><text-ref>COMMENTO SALVATO COMMENTO SALVATO COMMENTO SALVATO</text-ref></ver></vers></comment>";
		final String resourceName="comment-429.2.xml"; 
		CommentStorer cs = new CommentStorer();
		
		
	}

}
