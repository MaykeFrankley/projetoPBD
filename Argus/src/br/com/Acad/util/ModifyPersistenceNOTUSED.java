package br.com.Acad.util;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ModifyPersistenceNOTUSED {

	public ModifyPersistenceNOTUSED(String usuario, String senha) {
		try {

			String filepath = "src/META-INF/persistence.xml";
			String filepath2 = "bin/META-INF/persistence.xml";

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = null;
			try {
				doc = docBuilder.parse(filepath);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Get the root element
			XPath xp = XPathFactory.newInstance().newXPath();
			Element user = (Element) xp.evaluate("//properties/property[@name='hibernate.connection.user']",
					doc, XPathConstants.NODE);
			user.setAttribute("value", usuario);

			Element pass = (Element) xp.evaluate("//properties/property[@name='hibernate.connection.password']",
					doc, XPathConstants.NODE);
			pass.setAttribute("value", senha);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(new File(filepath));
	        transformer.transform(source, result);

	        //BIN

	        DocumentBuilderFactory docFactory2 = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder2 = docFactory2.newDocumentBuilder();
			Document doc2 = null;
			try {
				doc2 = docBuilder2.parse(filepath2);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// Get the root element
			XPath xp2 = XPathFactory.newInstance().newXPath();
			Element user2 = (Element) xp2.evaluate("//properties/property[@name='hibernate.connection.user']",
					doc2, XPathConstants.NODE);
			user2.setAttribute("value", usuario);

			Element pass2 = (Element) xp2.evaluate("//properties/property[@name='hibernate.connection.password']",
					doc2, XPathConstants.NODE);
			pass2.setAttribute("value", senha);

			Transformer transformer2 = TransformerFactory.newInstance().newTransformer();
	        DOMSource source2 = new DOMSource(doc2);
	        StreamResult result2 = new StreamResult(new File(filepath2));
	        transformer2.transform(source2, result2);


		} catch (ParserConfigurationException | XPathExpressionException | TransformerException pce) {
			pce.printStackTrace();
		}

	}


}