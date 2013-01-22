package mapsoftware.wms;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

		tidy.setXmlTags(true); // Defines that doc contains XML
		doc = tidy.parseDOM(xmlDocument, System.out);

		System.out.println("ennen capabilityjen hakua");
		NodeList nl = doc.getElementsByTagName("Layer");
		for (int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			Element el = (Element) node;
			NodeList layerNodes = el.getChildNodes();
			Node name = layerNodes.item(0);
			Node title = layerNodes.item(1);
			LayerInformation info = new LayerInformation(name.getFirstChild().getNodeValue(),
					title.getFirstChild().getNodeValue());
			System.out.println(info);
			result.add(info);

		}

//		parseSuccesConfirmation(result);
		System.out.println("\n"+"meni loppuun asti (b¤_¤)b");
		return result;
	}
	
	private void parseSuccesConfirmation(List<LayerInformation> list) {
		for(int i=0; i<list.size(); i++){
			LayerInformation temp = list.get(i);
			System.out.println(temp.getName()+" "+temp.getTitle());
		}
	}
	
}
