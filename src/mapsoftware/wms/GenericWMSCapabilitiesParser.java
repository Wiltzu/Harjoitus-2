package mapsoftware.wms;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import mapsoftware.wms.domain.LayerInformation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * <p>
 * Parses layer information from xml data
 * </p>
 * 
 * @author Ville Ahti
 * 
 */
class GenericWMSCapabilitiesParser implements WMSCapabilitiesParser {

    private static final String LAYER_TAG = "Layer";
    private List<LayerInformation> layerInformation;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mapsoftware.wms.WMSCapabilitiesParser#parseDocument(java.io.InputStream)
     */
    @Override
    public List<LayerInformation> parseDocument(InputStream xmlDocument) {
        Document doc = null;
        Tidy tidy = new Tidy();
        if (layerInformation == null) {
            tidy.setXmlTags(true); // Defines that doc contains XML
            doc = tidy.parseDOM(xmlDocument, null);

            parseData(doc);
        }

        return layerInformation;
    }

    private void parseData(Document doc) {
        layerInformation = new ArrayList<LayerInformation>();
        NodeList nl = doc.getElementsByTagName(LAYER_TAG);
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            Element el = (Element) node;
            NodeList layerNodes = el.getChildNodes();
            Node name = layerNodes.item(0);
            Node title = layerNodes.item(1);
            // TODO: Dummy block
            if (name == null || title == null || name.getFirstChild() == null
                    || title.getFirstChild() == null) {
            } else {
                // space in the parameter fix
                LayerInformation info = new LayerInformation(name
                        .getFirstChild().getNodeValue().replace(" ", "+"),
                        title.getFirstChild().getNodeValue());

                layerInformation.add(info);
            }

        }
    }

}
