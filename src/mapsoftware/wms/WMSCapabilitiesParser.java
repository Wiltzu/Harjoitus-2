package mapsoftware.wms;

public interface WMSCapabilitiesParser {
	/**
	 *  <p>parses xml document and gives layers to costumer</p>
	 */
	String[] parseDocument(String xmlDocument);
}
