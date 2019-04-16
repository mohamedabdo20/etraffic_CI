package isoft.etraffic.utils;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlFile {
	public void editURL(String filePath, String envUrl) {
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// update attribute value
			updateAttributeValue(doc, envUrl);

		

			// write the updated document to file or console
			// doc.getDocumentElement().normalize();
			doc.setXmlStandalone(true);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlFile);
			
			
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
			transformer.transform(source, result);
			System.out.println("XML file updated successfully");

		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
			e1.printStackTrace();
		}
	}


	public void readExcelFilePath(String filePath, String envUrl) {
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// update attribute value
			updateAttributeValue(doc, envUrl);

		

			// write the updated document to file or console
			// doc.getDocumentElement().normalize();
			doc.setXmlStandalone(true);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlFile);
			
			
			// transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://testng.org/testng-1.0.dtd");
			transformer.transform(source, result);
			System.out.println("XML file updated successfully");

		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void updateAttributeValue(Document doc, String envUrl) {
	        NodeList parameters = doc.getElementsByTagName("parameter");
	        Element param = null;
	        String url = "";
	        //loop for each employee
	        for(int i=0; i<parameters.getLength();i++){
	        param = (Element) parameters.item(i);
	        url = param.getAttribute("value");
	        System.out.println("url : ----" + url);
	            if(param.getAttribute("name").equalsIgnoreCase("url")){
	            param.setAttribute("value", envUrl);
	            NodeList suites = doc.getElementsByTagName("suite");
	            Element suite = (Element) suites.item(0);
	            suite.removeAttribute("allow-return-values");
	            suite.removeAttribute("configfailurepolicy");
	            suite.removeAttribute("data-provider-thread-coun");
	            suite.removeAttribute("group-by-instances");
	            suite.removeAttribute("guice-stage");
	            suite.removeAttribute("junit");
	        }
	    }
	        System.out.println("Url updated successfully");
	}
}
