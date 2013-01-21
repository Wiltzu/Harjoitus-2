package mapsoftware.wms;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LounaispaikkaCapParser implements WMSCapabilitiesParser {

	@Override
	public List<LayerInformation> parseDocument(InputStream xmlDocument) {
		System.out.println("paska alkaa");
		List<LayerInformation> result = new ArrayList<LayerInformation>();
		DocumentBuilder dBuilder = null;
		Document document = null;
		//Omaa shaibaa
		
		try {
			dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ennen parsea");
		try {
			System.out.print(dBuilder.isValidating());
			document = dBuilder.parse(xmlDocument);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println("ennen capabilityjen hakua");
		NodeList nl = document.getElementsByTagName("Layer");
		for(int i = 0; i < nl.getLength(); i++){
			Node node = nl.item(i);
			Element el = (Element) node;
			LayerInformation info = new LayerInformation(el.getAttribute("Name"), el.getAttribute("Title"));
			result.add(info);
			
		}
		
		System.out.println("meni loppuun asti (b¤_¤)b");
		return result;
	}

}
