package mapsoftware.wms;

import java.io.InputStream;

public interface WMSCapabilitiesParser {
	/**
	 *  <p>parses xml document and gives layers to costumer</p>
	 */
	String[] parseDocument(InputStream xmlDocument);
}
