/**
 * 
 */
package it.cnr.ilc.ga.handlers.exist;

import it.cnr.ilc.ga.model.comment.Comment;
import it.cnr.ilc.ga.model.comment.PericopeComments;
import it.cnr.ilc.ga.model.indexsearch.PericopeSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXHandler;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

/**
 * @author Angelo Del Grosso
 * 
 */
public class CommentsLoader {

	protected final String driver = "org.exist.xmldb.DatabaseImpl";
	private Collection root = null;

	/**
	 * 
	 */
	public CommentsLoader() {
		System.err.println("in comments Loader Saussure");
	}

	public boolean connect() {
		try {

			Class<?> c = Class.forName(driver);
			Database db = (Database) c.newInstance();
			DatabaseManager.registerDatabase(db);
			root = DatabaseManager.getCollection(
					"xmldb:exist://localhost:8088/xmlrpc/db/saussurecomments",
					"admin", "angelodel80");
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

	// private Refs[] getRefs(Document document) {
	// Element commentNode = document.getRootElement();
	// Element refsNode = commentNode.getChild("refs");
	// List<Element> refs2selection = refsNode.getChildren();
	//
	// }
	//
	// private void getVers() {
	//
	// }
	// private void getSelections() {
	//
	// }
	// private void getTexts() {
	//
	// }
	public boolean load(LinkedHashMap<String, PericopeComments> lhm)
			throws Exception {

		// collegarsi alla collection dei commenti di exist
		System.err.println(connect() + " connessione comments load Saussure");

		for (String resource : root.listResources()) {
			// i++;
			XMLResource xr = (XMLResource) root.getResource(resource);
			System.err.println("dopo - " + resource);
			SAXHandler saxhandler = new SAXHandler();
			// //System.err.println("dopo 1");

			// System.out.println(xr.getContent());
			xr.getContentAsSAX(saxhandler);
			Document document = saxhandler.getDocument();

			Element commentNode = document.getRootElement();
			Element refsNode = commentNode.getChild("refs");
			Element versNode = commentNode.getChild("vers");
			Element selsNode = commentNode.getChild("selections");
			Element textsNode = commentNode.getChild("texts");

			System.out.println(commentNode.toString());
			System.out.println(refsNode.toString());
			System.out.println(versNode.toString());
			System.out.println(selsNode.toString());
			System.out.println(textsNode.toString());

			List<Element> refs2selection = refsNode.getChildren();
			List<Element> vers = versNode.getChildren();
			List<Element> sels = selsNode.getChildren();
			List<Element> texts = textsNode.getChildren();

			System.out.println(refs2selection.toString());
			System.out.println(vers.toString());
			System.out.println(selsNode.toString());
			System.out.println(textsNode.toString());

			String idTarget = commentNode.getAttributeValue("id");
			// refs2selection.get(0).getAttributeValue("target");
			idTarget = idTarget.substring(0, idTarget.indexOf('_'));
			/*
			 * TODO OCCHIO all'idTarget dipendente dal fatto che l'id del
			 * commento corrisponde alla pericope direttamente!
			 */
			PericopeComments pc = lhm.get(idTarget);
			if (pc == null) {

				pc = new PericopeComments(idTarget);
				lhm.put(idTarget, pc);
			}
			System.out.println(idTarget);

			Comment comment = new Comment();

			int currVersion = vers.size();
			Element currentComment = vers.get(currVersion - 1);
			System.out.println(currentComment.toString());

			String commentType = currentComment.getChild("comment-type")
					.getAttributeValue("type");
			// currentComment.getChildTextNormalize("comment-type");
			System.out.println(commentType);
			comment.setType(commentType);

			String commentText = currentComment.getChild("text-ref")
					.getAttributeValue("text-id");
			// currentComment.getChildTextNormalize("text-ref");
			commentText = texts.get(Integer.parseInt(commentText))
					.getChildText("rich");
			System.out.println(commentText);
			comment.setCommentText(commentText);

			int refs2selSize = refs2selection.size();
			ArrayList[] bounds = new ArrayList[2];
			String[] fragment = new String[2];
			for (int i = 0; i < refs2selSize; i++) {
				Element selection1Node = sels.get(Integer
						.parseInt(refs2selection.get(i).getAttributeValue(
								"select-name")));
				List<Element> selects = selection1Node.getChildren();
				Iterator<Element> ie = selects.iterator();
				if (ie.hasNext()) {
					Element e = ie.next();
					Integer start = new Integer(e.getChild("start")
							.getAttributeValue("x"));
					Integer end = new Integer(e.getChild("end")
							.getAttributeValue("x"));
					bounds[i] = new ArrayList(2);
					bounds[i].add(start);
					bounds[i].add(end);
					
					//aggiunta stringa di selezione nel XML
					try{
						fragment[i] = e.getChild("fragment").getAttributeValue("value");
					}catch (Exception ex) {
						// TODO: handle exception
					}

				}
			}

			comment.setGreekSelectedBound(bounds[0]);
			comment.setArabicSelectedBound(bounds[1]);
			
			//aggiunta testo selezionato per il commento nell XML
			comment.setGreekSelectedPosition(fragment[0]);
			comment.setArabicSelectedPosition(fragment[1]);

			comment.setIdComment(commentNode.getAttributeValue("id"));

			pc.addComment(comment);

		}
		return true;
	}

	public static void main(String[] args) {
		try {
			PericopeSet pericopeSet = new PericopeSet();
			LinkedHashMap<String, PericopeComments> lhm = new LinkedHashMap<String, PericopeComments>(
					1000);
			PericopesLoader pl = new PericopesLoader();
			pl.load(pericopeSet);
			CommentsLoader cl = new CommentsLoader();
			cl.load(lhm);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// try{
		// CommentsLoader cl = new CommentsLoader();
		// System.err.println(cl.connect());
		// System.out.println(cl.root.getName());
		// }catch (Exception e) {
		// // TODO: handle exception
		// }
	}
}
