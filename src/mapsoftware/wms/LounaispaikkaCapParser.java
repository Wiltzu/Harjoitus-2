package mapsoftware.wms;

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
import org.w3c.tidy.Tidy;

public class LounaispaikkaCapParser implements WMSCapabilitiesParser {

	@Override
	public List<LayerInformation> parseDocument(InputStream xmlDocument) {
		System.out.println("paska alkaa");
		List<LayerInformation> result = new ArrayList<LayerInformation>();
		Document doc = null;
		Tidy tidy = new Tidy();
		
		System.out.println("ennen parsea");

		tidy.setXmlTags(true);
		doc = tidy.parseDOM(xmlDocument, System.out);


		System.out.println("ennen capabilityjen hakua");
		NodeList nl = doc.getElementsByTagName("Layer");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			Element el = (Element) node;
			LayerInformation info = new LayerInformation(
					el.getAttribute("Name"), el.getAttribute("Title"));
			result.add(info);

		}

		System.out.println("meni loppuun asti (b¤_¤)b");
		return result;
	}

}
