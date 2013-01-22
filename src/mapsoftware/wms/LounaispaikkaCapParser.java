package mapsoftware.wms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LounaispaikkaCapParser implements WMSCapabilitiesParser {

	@Override
	public List<LayerInformation> parseDocument(InputStream xmlDocument) {
		System.out.println("paska alkaa");
		List<LayerInformation> result = new ArrayList<LayerInformation>();
		DocumentBuilder dBuilder = null;
		Document document = null;
		Document doc = null;
		// SAXReader reader = new SAXReader();
		// Omaa shaibaa

		try {
			dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ennen parsea");
		try {
			// System.out.print(dBuilder.isValidating());
			xmlDocument.skip(264);
			InputStreamReader isr = new InputStreamReader(xmlDocument);
			BufferedReader br = new BufferedReader(isr);
			StringBuilder xml = new StringBuilder();
			while (br.read() != -1) {
				xml.append(br.readLine());
			}
			//kikkailut
			xml.replace(0, 1, "<");
			xml.insert(60, "<");
			xml.insert(12957, "<");
			xml.insert(12970, "<");
			xml.insert(1082, "<");

			System.out.println(xml.toString());
			xmlDocument = new ByteArrayInputStream(xml.toString().trim()
					.getBytes());
			// isr = new InputStreamReader(xmlDocument);
			// br = new BufferedReader(isr);
			// StringBuilder xml2 = new StringBuilder();
			// while(br.read() != -1) {
			// xml2.append(br.readLine());
			// }
			// System.out.println(xml2.toString());
			// System.out.println(xmlDocument.markSupported());
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(xmlDocument));
			// for (int i = 0; i < 269; i++) {
			// br.readLine();
			// }
			// br.skip(1);
			// //br.reset();

			try {
				doc = dBuilder.parse(xmlDocument);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		// System.out.println(doc.asXML());
		System.out.println("ennen capabilityjen hakua");
		// NodeList nl = doc.getElementsByTagName("Layer");
		// for(int i = 0; i < nl.getLength(); i++){
		// Node node = nl.item(i);
		// Element el = (Element) node;
		// LayerInformation info = new LayerInformation(el.getAttribute("Name"),
		// el.getAttribute("Title"));
		// result.add(info);
		//
		// }

		System.out.println("meni loppuun asti (b¤_¤)b");
		return result;
	}

}
