package mapsoftware.wms;

import java.io.InputStream;
import java.util.List;

public interface WMSCapabilitiesParser {
	/**
	 * @param xmlDocument InputStream containing xml
	 * @return List containing LayerInformation.
	 *  <p>Returns a List containing LayerInformation fetched from the InputStream.</p>
	 */
	List<LayerInformation> parseDocument(InputStream xmlDocument);
}
