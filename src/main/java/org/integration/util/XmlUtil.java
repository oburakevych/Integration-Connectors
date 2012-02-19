package org.integration.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Predicate;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class XmlUtil {
	private static XMLInputFactory xif = XMLInputFactory.newInstance();
	
	private static final byte[] XML_DECL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>".getBytes(Charset.forName("UTF-8"));
	
	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
	static {
		dbf.setNamespaceAware(true);
        dbf.setValidating(false);
	}


	public static boolean hasValidXmlDeclaration(byte[] doc) throws Exception {
		try {
			XMLStreamReader r = xif.createXMLStreamReader(new ByteArrayInputStream(doc), null);
			if (!"UTF-8".equalsIgnoreCase(r.getEncoding())) return false;
			if (!"1.0".equals(r.getVersion())) return false;
			
			r.close();
			return true;
		} catch (XMLStreamException e) {
			throw new Exception("Unable to parse xml", e);
		}
	}
	
	public static byte[] prependXmlDeclaration(byte[] doc) throws Exception {
		if (!hasValidXmlDeclaration(doc)) {
			byte[] newdoc = new byte[doc.length + XML_DECL.length];
			System.arraycopy(XML_DECL, 0, newdoc, 0, XML_DECL.length);
			System.arraycopy(doc, 0, newdoc, XML_DECL.length, doc.length);
			return newdoc;
		} else {
			return doc;
		}
	}
	
	public static Document parse(byte[] xml) {
		try {
            EntityResolver resolver = new EntityResolver() {
                @Override
                public InputSource resolveEntity(String s, String s1) throws SAXException, IOException {
                    return null;
                }
            };
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            documentBuilder.setEntityResolver(resolver);
            Document doc = documentBuilder.parse(new ByteArrayInputStream(xml));

			return doc;
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid xml", e);
		}
	}
	
	public static byte[] toByteArray(Node doc) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(os);
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);
			
			return os.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Element getChildElement(Element parent, String childName, String childNamespace) {
		NodeList ns = parent.getChildNodes();
		for (int i = 0; i < ns.getLength(); i++) {
			Node n = ns.item(i);
			if (n instanceof Element) {
				Element child = (Element) n;
				if (childName.equals(child.getLocalName()) && childNamespace.equals(child.getNamespaceURI())) {
					return child;
				}
			}
		}
		return null;
	}
	
	public static Element find(Element parent, Predicate<Element> matcher) {
		NodeList ns = parent.getChildNodes();
		for (int i = 0; i < ns.getLength(); i++) {
			Node n = ns.item(i);
			if (n instanceof Element) {
				if (matcher.apply((Element) n)) {
					return (Element) n;
				}
			}
		}
		return null;
	}
	
	public static String getElementValue(Element parent, String childName, String childNamespace) {
		Element child = getChildElement(parent, childName, childNamespace);
		if (child == null) return null;
		
		return child.getTextContent();
	}
	
	public static void insertValue(Element parent, String childName, String childNamespace, String value) {
		Element child = parent.getOwnerDocument().createElementNS(childNamespace, childName);
		child.setTextContent(value);
		parent.appendChild(child);
	}
}
