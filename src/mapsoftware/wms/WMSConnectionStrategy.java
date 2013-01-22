package mapsoftware.wms;

import java.net.URL;
import java.util.List;

public interface WMSConnectionStrategy {
	 List<LayerInformation> getCapabilities();
	 URL getMap(List<LayerInformation> layers, LocationArea area);
}
