package mapsoftware.wms;

import java.io.InputStream;
import java.util.List;

public interface WMSCapabilitiesParser {
	/**
	 *  <p>parses XML document and gives layers to client</p>
	 */
	List<LayerInformation> parseDocument(InputStream xmlDocument);
}
