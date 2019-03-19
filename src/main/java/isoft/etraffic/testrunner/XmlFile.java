package isoft.etraffic.testrunner;

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
	public void updateXmlParameters(String filePath, String envUrl, String filename) {
		File xmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			// update attribute value
			updateAttributeValue(doc, "url", envUrl);
			updateAttributeValue(doc, "filename", filename);

			// update Element value
			// updateElementValue(doc);
			//
			// //delete element
			// deleteElement(doc);
			//
			// //add new element
			// addElement(doc);

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
			System.out.println("XML parameters 'url' & 'filename' were updated successfully.");

		} catch (SAXException | ParserConfigurationException | IOException | TransformerException e1) {
			e1.printStackTrace();
		}
	}

	public static void addElement(Document doc) {
		// NodeList employees = doc.getElementsByTagName("Employee");
		// Element emp = null;
		//
		// //loop for each employee
		// for(int i=0; i<employees.getLength();i++){
		// emp = (Element) employees.item(i);
		// Element salaryElement = doc.createElement("salary");
		// salaryElement.appendChild(doc.createTextNode("10000"));
		// emp.appendChild(salaryElement);
		// }
	}

	public static void deleteElement(Document doc) {
		NodeList employees = doc.getElementsByTagName("Employee");
		Element emp = null;
		// loop for each employee
		for (int i = 0; i < employees.getLength(); i++) {
			emp = (Element) employees.item(i);
			Node genderNode = emp.getElementsByTagName("gender").item(0);
			emp.removeChild(genderNode);
		}

	}

	public static void updateElementValue(Document doc) {
		NodeList employees = doc.getElementsByTagName("Employee");
		Element emp = null;
		// loop for each employee
		for (int i = 0; i < employees.getLength(); i++) {
			emp = (Element) employees.item(i);
			Node name = emp.getElementsByTagName("name").item(0).getFirstChild();
			name.setNodeValue(name.getNodeValue().toUpperCase());
		}
	}

	public static void updateAttributeValue(Document doc, String attribute, String value) {
		NodeList parameters = doc.getElementsByTagName("parameter");
		Element param = null;
		
		// loop for each employee
		for (int i = 0; i < parameters.getLength(); i++) {
			param = (Element) parameters.item(i);
			if (param.getAttribute("name").equalsIgnoreCase(attribute)) {
				System.out.println("Attribute found:");
				param.setAttribute("value", value);
//				NodeList suites = doc.getElementsByTagName("suite");
//				Element suite = (Element) suites.item(0);
//				suite.removeAttribute("allow-return-values");
			}
		}
	}
}
