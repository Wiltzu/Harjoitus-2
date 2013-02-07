package mapsoftware.wms;

import java.io.InputStream;
import java.util.List;

import mapsoftware.wms.domain.LayerInformation;

public interface WMSCapabilitiesParser {
    // TODO: parseDocument parses only document and return nothing, getLayers
    // gives LayerInformation and getLocationBoundaries gives
    // LocationInformation
    /**
     * <p>
     * Returns a List containing LayerInformation fetched from the InputStream.
     * </p>
     * 
     * @param xmlDocument
     *            InputStream containing getCapabilities request xml
     * @return List containing LayerInformation.
     * 
     */
    List<LayerInformation> parseDocument(InputStream xmlDocument);
}
